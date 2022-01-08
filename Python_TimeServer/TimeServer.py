import time
import sys
from flask import Flask, request, jsonify, json
from flask_restful import Resource, Api

app = Flask(__name__)
api = Api(app)
time_start = time.time()
seconds = 0
minutes = 0

class TIME_Server(Resource):
    def get(self):
        hours = int((time.time() - time_start)/3600)
        minutes = int((time.time() - time_start)/60)  - (hours * 60)
        seconds = int(time.time() - time_start) - (minutes * 60) - (hours * 3600)
        time_json = {"uur": hours, "minuut": minutes, "seconde": seconds}
        return jsonify(time_json)

api.add_resource(TIME_Server, '/TimeServer')

if __name__ == '__main__':
     print('Now serving on port 5002')
     app.run(port='5002')
     