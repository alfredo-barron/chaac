from crypt import methods
import sys
import time
import datetime
import requests
import simplejson as json
import os

from flask import Flask, request, jsonify, abort, render_template, redirect, url_for, session, escape

app = Flask(__name__)
app.secret_key = 'jupiter'
app.config.update(
    DEBUG=True,
    JSON_SORT_KEYS=True
)

nodes = {
    "name": "http://acquisition:45000",
    "endpoint": "hosts"
}

# Root
@app.route('/')
@app.route('/index.html')
def main():
    return render_template('index.html')


@app.route('/health')
def health():
    return 'Web is healthy'

@app.route('/monitor')
def monitor():
    return render_template('monitor.html')

@app.route('/getnodes', methods=['GET'])
def getNodes():
    try:
        url = nodes['name'] + "/" + nodes['endpoint']
        res = requests.get(url, timeout=3.0)
    except:
        res = None
    if res and res.status_code == 200:
        return jsonify(res.json())
    else:
        status = (res.status_code if res != None and res.status_code else 500)
        return jsonify({'error': 'Sorry, hosts aren\'t available at this time.'})


@app.route('/gethostscpu/<int:id>', methods=['GET'])
def getResults(id):
    try:
        url = 'http://acquisition:45000/hosts/' + str(id) + '/cpu'
        res = requests.get(url)
    except:
        res = None
    if res and res.status_code == 200:
        return jsonify(res.json())
    else:
        status = (res.status_code if res != None and res.status_code else 500)
        return jsonify({'error': 'Sorry, hosts aren\'t available at this time.'})


@app.route('/gethostsmemory/<int:id>', methods=['GET'])
def getMemory(id):
    try:
        url = 'http://acquisition:45000/hosts/' + str(id) + '/memory'
        res = requests.get(url)
    except:
        res = None
    if res and res.status_code == 200:
        return jsonify(res.json())
    else:
        status = (res.status_code if res != None and res.status_code else 500)
        return jsonify({'error': 'Sorry, hosts aren\'t available at this time.'})

#pools
@app.route('/cenotes', methods=['GET'])
def getPools ():
    try:
        url='http://creation:45000/cenotes'
        res= requests.get(url)
    except:
        res = None
    if res and res.status_code== 200:
        return jsonify(res.json)
    else:
        status = (res.status_code if res != None and res.status_code else 500)
        return jsonify({'error': 'sorry, pools aren\'t available at this time :('})

@app.route('/cenotes', methods=['POST'])
def createPools(json):
    try:
        
        url='http://middleware:45000/api/v1/cenote/'
        res=requests.post(url,json)
    except :
        res = None
    if res and res.status_code == 201:
        return jsonify(res.json)
    else:
        status = (res.status_code if res != None and res.status_code else 500)
        return jsonify({'error': 'sorry, pools aren\'t available at this time :('})

@app.route('/cenotes/<int:idPool>/ delete',methods=['DELETE'])
def deletePool(idPool):
    try:    
        url='http://middleware:45000/api/v1/cenotes/'+idPool
        res=requests.delete(url)
    except :
        res = None
    if res and res.status_code == 201:
        return jsonify(res.json)
    else:
        status = (res.status_code if res != None and res.status_code else 500)
        return jsonify({'error': 'sorry, pools aren\'t available at this time :('})
#containers / bins 
@app.route('/middleware/pools/<int:idpool>/containers',methods=['Get'])
def getContainers(idpool):
    try:
        url='http://middleware:45000/api/v1/pools/'+idpool
        res=requests.get(url)
    except :
        res = None
    if res and res.status_code == 201:
        return jsonify(res.json)
    else:
        status = (res.status_code if res != None and res.status_code else 500)
        return jsonify({'error': 'sorry, bins aren\'t available at this time :('})

@app.route('/middleware/pools/<int:idPool>/bins',methods=['POST'])
def createContainers(json):
    try:
        url='http://middleware:45000/api/v1/bins'
        res=requests.post(url,json)
    except :
        res = None
    if res and res.status_code == 201:
        return jsonify(res.json)
    else:
        status = (res.status_code if res != None and res.status_code else 500)
        return jsonify({'error': 'sorry, bins aren\'t available at this time :('})


@app.route('/middleware/pools/<int:idpool>/containers/<int:idBin>',methods=['DELETE'])
def deleteContainer(idBin):
    try:
        url='http://middleware:45000/api/v1/bins/'+idBin
        res=requests.delete(url)
    except :
        res = None
    if res and res.status_code == 201:
        return jsonify(res.json)
    else:
        status = (res.status_code if res != None and res.status_code else 500)
        return jsonify({'error': 'sorry, bins aren\'t available at this time :('})

if __name__ == '__main__':
    if len(sys.argv) < 2:
        #print "usage: %s port" % (sys.argv[0])
        sys.exit(-1)

    p = int(sys.argv[1])
    #print "start at port %s" % (p)
    app.run(host='0.0.0.0', port=p, debug=True, threaded=True)
