from Scrapers.User_Data_XML import User,test
from flask import Flask
from flask import request
app = Flask (__name__)

@DeprecationWarning
@app.route("/data/",methods=['GET'])
def ret():
    login = request.headers["login"]
    password = request.headers["password"]
    return test(User(login,password))
if __name__ == "__main__":
    app.run(port=64)