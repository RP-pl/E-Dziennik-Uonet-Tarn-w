from typing import Tuple


class User:
    def __init__(self,login,password):
        self.password = password
        self.login = login
    def getLoginData(self) -> Tuple[str, str]:
        return self.login, self.password
