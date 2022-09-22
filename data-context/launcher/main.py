from crypt import methods
from importlib import resources
from re import M
import docker
from flask import Flask, jsonify, request, make_response
from flask_api import status
from flask_cors import CORS
import simplejson as json

app = Flask(__name__)
app.debug = True
cors = CORS(app, resources={r"/api/*": {"origins": "*"}})
client = docker.DockerClient(base_url='unix://var/run/docker.sock')

@app.route("/api/containers", methods=['GET'])
def get_containers():
    array = []
    containers = client.containers.list()
    for container in containers:
        c = {}
        c['id'] = container.id
        c['short_id'] = container.short_id
        c['name'] = container.name
        c['status'] = container.status
        array.append(c)
    response = make_response(jsonify(array), 200)
    return response

@app.route("/api/containers/<id>/stats", methods=['GET'])
def get_containers_stats(id):
    container = client.containers.get(id)
    c = {}
    c['id'] = container.id
    c['short_id'] = container.short_id
    c['name'] = container.name
    stats = container.stats(decode = None, stream = False)
    '''
    cpu_usage = stats['cpu_stats']['cpu_usage']['total_usage']
    print("CPU")
    print(cpu_usage)
    memory_usage = stats['memory_stats']['usage']
    memory_total = stats['memory_stats']['limit']
    print("Memory")
    print(str(memory_usage) + ' / ' + str(memory_total))
    '''
    c['stats'] = stats
    response = make_response(jsonify(c), 200)
    return response

@app.route("/api/containers/<id>", methods=['GET'])
def get_container(id):
    container = client.containers.get(id)
    d = {}
    d['id'] = container.id
    d['name'] = container.name
    response = make_response(jsonify(d), 200)
    return response

@app.route("/api/containers", methods=['POST'])
def create_container():
    content = request.get_json()
    print(content)
    if 'image' in content:
        image = content['image']
    if 'name' in content:
        name = content['name']
    else:
        name = None
    if 'network' in content:
        network = content['network']
    else:
        network = None
    if 'ports' in content:
        ports = content['ports']
        ports_dic = {}
        for port in ports:
            ports_dic[port['privatePort']] = port['publicPort']
    else:
        ports_dic = {}
    if 'env' in content:
        environments = content['env']
    else:
        environments = {}
    if 'tmpfs' in content:
        tmpfs = content['tmpfs']
        tmps_dic = {}
        for tmp in tmpfs:
            tmps_dic[tmpfs['target']] = ''
    else:
        tmps_dic = {}
    if 'volumes' in content:
        volumes = content['volumes']
        vols_dic = {}
        for vol in volumes:
            bind_mode = {}
            bind_mode['bind'] = vol['bind']
            bind_mode['mode'] = vol['mode']
            vols_dic[vol['volume']] = bind_mode
    else:
        vols_dic = {}
    container = client.containers.run(image=image, name=name, ports=ports_dic, environment=environments, network=network, tmpfs=tmps_dic, volumes=vols_dic, detach=True)
    r = {}
    r['id'] = container.id
    r['short_id'] = container.short_id
    r['message'] = 'Container created and runned'
    response = make_response(jsonify(r), 200)
    return response

@app.route("/api/containers/<id>/start", methods=['GET'])
def start_container(id):
    container = client.containers.get(id)
    container.start()
    r = {}
    r['id'] = container.id
    r['short_id'] = container.short_id
    r['message'] = 'Container start'
    response = make_response(jsonify(r), 200)
    return response

@app.route("/api/containers/<id>/restart", methods=['GET'])
def restart_container(id):
    container = client.containers.get(id)
    container.restart()
    r = {}
    r['id'] = container.id
    r['short_id'] = container.short_id
    r['message'] = 'Container restart'
    response = make_response(jsonify(r), 200)
    return response

@app.route("/api/containers/<id>/stop", methods=['GET'])
def stop_container(id):
    container = client.containers.get(id)
    container.stop()
    r = {}
    r['id'] = container.id
    r['short_id'] = container.short_id
    r['message'] = 'Container stop'
    response = make_response(jsonify(r), 200)
    return response

@app.route("/api/containers/<id>", methods=['DELETE'])
def remove_container(id):
    container = client.containers.get(id)
    container.remove()
    r = {}
    r['id'] = container.id
    r['short_id'] = container.short_id
    r['message'] = 'Container deleted'
    response = make_response(jsonify(r), 200)
    return response

@app.route("/api/networks", methods=['GET'])
def get_networks():
    array = []
    networks = client.networks.list()
    for network in networks:
        n = {}
        n['id'] = network.id
        n['short_id'] = network.short_id
        n['name'] = network.name
        array.append(n)
    response = make_response(jsonify(array), 200)
    return response

@app.route("/api/networks/<id>", methods=['GET'])
def get_network(id):
    network = client.networks.get(id)
    n = {}
    n['id'] = network.id
    n['name'] = network.name
    response = make_response(jsonify(n), 200)
    return response

@app.route("/api/networks", methods=['POST'])
def create_network():
    content = request.get_json()
    if 'name' in content:
        name = content['name']
    else:
        name = None
    if 'driver' in content:
        driver = content['driver']
    else:
        driver = "bridge"
    if 'attachable' in content:
        attachable = content['attachable']
    else:
        attachable = True
    network = client.networks.create(name=name, driver=driver, attachable=attachable)
    r = {}
    r['id'] = network.id
    r['short_id'] = network.short_id
    r['message'] = 'Network created'
    response = make_response(jsonify(r), 200)
    return response

@app.route("/api/networks/<id>", methods=['DELETE'])
def remove_network(id):
    network = client.networks.get(id)
    network.remove()
    r = {}
    r['id'] = network.id
    r['short_id'] = network.short_id
    r['message'] = 'Network deleted'
    response = make_response(jsonify(r), 200)
    return response

@app.route("/api/nodes", methods=['GET'])
def get_nodes():
    array = []
    nodes = client.nodes.list()
    for node in nodes:
        n = {}
        n['id'] = node.id
        n['short_id'] = node.short_id
        array.append(n)
    response = make_response(jsonify(array), 200)
    return response

@app.route("/api/services", methods=['GET'])
def get_services():
    array = []
    services = client.services.list()
    for service in services:
        s = {}
        s['id'] = service.id
        s['short_id'] = service.short_id
        s['name'] = service.name
        array.append(s)
    response = make_response(jsonify(array), 200)
    return response

@app.route("/api/services/<id>", methods=['GET'])
def get_service(id):
    service = client.services.get(id)
    n = {}
    n['id'] = service.id
    n['name'] = service.name
    response = make_response(jsonify(n), 200)
    return response

# Pendiente
@app.route("/api/services", methods=['POST'])
def create_service():
    content = request.get_json()
    if 'image' in content:
        image = content['image']
    if 'name' in content:
        name = content['name']
    else:
        name = None
    if 'ports' in content:
        ports = content['ports']
        ports_dic = {}
        for port in ports:
            ports_dic[port['published_port']] = port['target_port']
        endpoint_spec = docker.types.EndpointSpec(ports=ports_dic)
        print(endpoint_spec)
    else:
        endpoint_spec = docker.types.EndpointSpec()
    if 'env' in content:
        environments = content['env']
    else:
        environments = {}
    if 'mounts' in content:
        mounts = content['mounts']
        mounts_dic = {}
        for mount in mounts:
            bind_mode = {}
            bind_mode['source'] = mount['source']
            bind_mode['target'] = mount['target']
            mounts_dic[mount['type']] = bind_mode
        print(mounts_dic)
    else:
        mounts_dic = {}
    if 'networks' in content:
        networks = content['networks']
        list_net = []
        for network in networks:
            list_net.append(network)
    else:
        list_net = []
    if 'resources' in content:
        if 'cpu_limit' in content['resources']:
            cpu_limit = content['resources']['cpu_limit']
        else:
            cpu_limit = None
        if 'mem_limit' in content['resources']:
            mem_limit = content['resources']['mem_limit']
        else:
            mem_limit = None
        resources = docker.types.Resources(cpu_limit=cpu_limit, mem_limit=mem_limit)
    else:
        resources = docker.types.Resources(cpu_limit=None, mem_limit=None)
    service = client.services.create(image=image, name=name, endpoint_spec=endpoint_spec, env=environments, networks=list_net, mounts=mounts_dic)
    r = {}
    r['id'] = service.id
    r['short_id'] = service.short_id
    r['message'] = 'Service created'
    response = make_response(jsonify(r), 200)
    return response

@app.route("/api/services/<id>", methods=['DELETE'])
def remove_service(id):
    service = client.services.get(id)
    service.remove()
    r = {}
    r['id'] = service.id
    r['short_id'] = service.short_id
    r['message'] = 'Service deleted'
    response = make_response(jsonify(r), 200)
    return response

@app.route("/api/", methods=['GET'])
def api():
    return "API ready to receive requests"

@app.route("/", methods=['GET'])
def index():
    return "Ready to receive requests"

if __name__ == '__main__':
    app.run(host= '0.0.0.0')