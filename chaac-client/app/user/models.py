from flask import Flask, jsonify
import uuid

#user

class User:
    def signup(self):
        user:{
            "userID": uuid.uuid4().hex,
	        "name": request.form.get('Name_user'),
	        "user_name":request.form.get('UserName'),
	        "password":request.form.get('Password'),
	        "schema":[{
		        "schema_id":"",
		        "name_schema":"",
		        "structure":[],
		        "data":[]
	        }]
        }
        return jsonify(user),200