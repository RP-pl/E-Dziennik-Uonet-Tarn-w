login = input()
password = input()
s = ""
for letter in login:
    s += chr (ord (letter) + 2)
s+=2*(chr(ord("|")+2))
for letter in password:
    s += chr (ord (letter) + 2)
print(s)