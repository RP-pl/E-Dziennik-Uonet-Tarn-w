from Scrapers.User_Data import User
from flask import Flask
from math import fabs

user_repo = dict()
app = Flask (__name__)
symbols = [chr(x) for x in range(65,91)]
@DeprecationWarning
def make_token(login,password):
    f = (fabs(hash(login)) + fabs(hash(password)*2))*256
    token = ""
    while f!=0 :

      token+=symbols[(int((f%100)%len(symbols)))]
      f=int(f/100)
    return token
@DeprecationWarning
@app.route("/<login>/<password>",methods=['GET'])
def init(login,password):
    global token
    token = make_token(login,password)
    while token in user_repo:
        token = make_token(login,password)
    user_repo[token] = User(login,password)
    return token
@DeprecationWarning
@app.route ("/<token>/klasa",methods=['GET'])
def kls(token):
    kls = "||"
    st=kls.join(user_repo[token].klasa())
    return st
@DeprecationWarning
@app.route ("/<token>/oceny",methods=['GET'])
def oce(token):
    kls = "||"
    st = kls.join(user_repo[token].oceny())
    return st
@DeprecationWarning
@app.route ("/<token>/plan",methods=['GET'])
def pll(token):
    kls = "||"
    st = kls.join(user_repo[token].plan())
    return st
@DeprecationWarning
@app.route ("/<token>/testy",methods=['GET'])
def tests(token):
    st = user_repo[token].spraw()
    return st

@DeprecationWarning
@app.route ("/<token>/close",methods=['POST'])
def cls(token):
    user_repo[token].browser.close()
    user_repo[token].browser.quit()
    del(user_repo[token])
    return "OK"


if __name__ == "__main__":
    app.run()