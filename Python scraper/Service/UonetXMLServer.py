from flask import Flask
from flask import request

from Scrapers.UserXMLScraper import UserXMLScraper

app = Flask(__name__)
@app.route("/data/",methods=['GET'])
def data():
    name = request.headers['login']
    password = request.headers["password"]
    u = UserXMLScraper(name,password)
    r = str(u.grades()).replace("'","")[1:]
    h = str(u.hours()).replace("'", "")[1:]
    e = str(u.exams()).replace("'", "")[1:]
    m = str(u.msg()).replace("'","")[1:]
    u.dispose()
    return "<uczen>"+r+m+h+e+"</uczen>"

if __name__ == "__main__":
    app.run(port=64)

