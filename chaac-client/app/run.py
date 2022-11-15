from crypt import methods
import sys
import time
import datetime
import requests
import json as simplejson
import os
from flask import Flask, request, jsonify, abort, render_template, redirect, url_for, session, escape
from flask_login import current_user, LoginManager, login_user, logout_user, login_required
from flask_mongoengine import MongoEngine
import json

# Variables de configuracion
# Middleware
chaac_url = "chaac"
# DB
db_name = 'chaacDB'
db_url = "localhost"

# Config Flask
app = Flask(__name__)
app.secret_key = 'jupiter'
app.config.update(
    DEBUG=True,
    JSON_SORT_KEYS=True
)

# Config connection MongoDB
db = MongoEngine()
app.config['MONGODB_DB'] = db_name
app.config['MONGODB_HOST'] = db_url
app.config['MONGODB_PORT'] = 27017
app.config['MONGODB_USERNAME'] = 'root'
app.config['MONGODB_PASSWORD'] = 'root'

login_manager = LoginManager()
db.init_app(app)
login_manager.init_app(app)
login_manager.login_view = 'login'

nodes = {
    "name": "http://" + chaac_url,
    "endpoint": "hosts"
}

# Root
@app.route('/')
@app.route('/index')
def main():
    return render_template('index.html')


@app.route('/creation')
def creation():
    return render_template('creation.html')

@app.route('/visualize')
def visualize():
    return render_template('visualize.html')

@app.route('/health')
def health():
    return 'Web is healthy'


#manejador de logins
@login_manager.user_loader
def load_user(user_id):
    return User.objects(id=user_id).first()
#login 
@app.route('/login', methods=['POST'])
def login():
    info = json.loads(request.data)
    email = info.get('email')
    password = info.get('password')
    user = User.objects(email=email,
                        password=password).first()
    if user:
        login_user(user, remember= True)
        next= url_for('visualize')
        return redirect(next)
    else:
        return jsonify({"status": 401,
                        "reason": "Username or Password Error"})
#logout
@app.route('/logout', methods=['POST'])
def logout():
    logout_user()
    return jsonify(**{'result': 200,
                      'data': {'message': 'logout success'}})
#user information
@app.route('/user_info', methods=['POST'])
def user_info():
    if current_user.is_authenticated:
        resp = {"result": 200,
                "data": current_user.to_json()}
    else:
        resp = {"result": 401,
                "data": {"message": "user no login"}}
    return jsonify(**resp)
#class user in DB
class User(db.Document):   
    name = db.StringField()
    password = db.StringField()
    email = db.StringField() 
    user_name=db.StringField()
    schemas=db.StringField()                                                                                                
    def to_json(self):        
        return {"name": self.name,
                "email": self.email,
                "user_name":self.user_name}

    def is_authenticated(self):
        return True

    def is_active(self):   
        return True           

    def is_anonymous(self):
        return False          

    def get_id(self):       
        return str(self.id)
#get user
@app.route('/users', methods=['GET'])
def query_records():
    name = request.args.get('name')
    user = User.objects(name=name).first()
    if not user:
        return jsonify({'error': 'data not found'})
    else:
        return jsonify(user.to_json())
#update dates of users
@app.route('/users/update', methods=['POST'])
def update_record():
    record = json.loads(request.data)
    user = User.objects(name=record['name']).first()
    if not user:
        return jsonify({'error': 'data not found'})
    else:
        user.update(email=record['email'],
                    user_name=record['user_name'],
                    password=record['password'])
    return jsonify(user.to_json())

@app.route('/users', methods=['PUT'])
@login_required
def create_record():
    record = json.loads(request.data)
    user = User(name=record['name'],
                user_name=record['user_name'],
                password=record['password'],
                email=record['email'])
    user.save()
    return jsonify(user.to_json())

@app.route('/users', methods=['POST'])
def create_user():
    record= json.loads(request.data)
    user=User.objects(email=record['email']).first()
    if  not user :
        user=User(name=record['name'],
            user_name=record['user_name'],
            password=record['password'],
            email=record['email'])
        user.save()
        return jsonify({'ok': 'the user created'})
    else:
        return jsonify({'error': 'the user exist'})

#prueba para la interfaz
@app.route('/prueba', methods=['POST'])
def prueba():
    return json.loads( request.data)

#delete users
@app.route('/users', methods=['DELETE'])
@login_required
def delte_record():
    record = json.loads(request.data)
    user = User.objects(name=record['name']).first()
    if not user:
        return jsonify({'error': 'data not found'})
    else:
        user.delete()
    return jsonify(user.to_json())


#return schemas
@app.route('/users/schemas', methods=['GET'])
@login_required
def schemas():
    return user.schemas

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
        url= chaac_url +'/cenotes'
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
    app.run(host='0.0.0.0', port=45000, debug=True, threaded=True)
