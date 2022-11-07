from flask import Flask

#user

class User:
    def singup(self):
        user:{
            "userID": "",
	        "name": "",
	        "user_name":"",
	        "password":"",
	        "schema":[{
		        "schema_id":"",
		        "nombre_schema":"",
		        "estructura":[{
			        "html": "",
			        "blockarr": [],
			        "blocks": [{
					    "id": "",
					    "parent": "",
					    "data": [{
						    "name": "",
						    "value": ""
					    }],
					    "attr": [{
						    "id": "",
						    "class": ""
					    }]
				    }]
		        }],
		        "data":[{
			            "cenotes":[{
				        "id": "",
  				        "name": "",
  				        "image": "",
  				        "network": "",
  				        "publicPort": "",
  				        "distribuitor": "",
  				        "bins":[{
  					        "id": "",
  					        "name": "",
  					        "hostId": "",
  					        "cenoteId": "",
  					        "image": "",
  					        "network": "",
  					        "cacheSize": "",
  					        "cachePolicy": "",
  					        "levels": "",
  					        "memory": "",
  					        "capacity": ""
  				        }]
			            }]
		        }]
	        }]
        }
        return jsonify(user)