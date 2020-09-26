from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
import selenium
import getpass
@DeprecationWarning
class User:
    def __init__(self,login,password):
        self.login = login
        self.password = password
        options = selenium.webdriver.chrome.options.Options()
        options.add_argument ('--headless')
        options.add_argument ('--disable-gpu')
        self.browser =selenium.webdriver.Chrome("C:\\Users\\"+getpass.getuser()+"\Downloads\chromedriver.exe",options=options)
        self.browser.get("https://adfs.umt.tarnow.pl/adfs/ls/?wa=wsignin1.0&wtrealm=https%3a%2f%2fcufs.umt.tarnow.pl%3a443%2ftarnow%2fAccount%2fLogOn&wctx=rm%3d0%26id%3dadfs%26ru%3d%252ftarnow%252fFS%252fLS%253fwa%253dwsignin1.0%2526wtrealm%253dhttps%25253a%25252f%25252fuonetplus.umt.tarnow.pl%25252ftarnow%25252fLoginEndpoint.aspx%2526wctx%253dhttps%25253a%25252f%25252fuonetplus.umt.tarnow.pl%25252ftarnow%25252fLoginEndpoint.aspx&wct=2020-05-28T10%3a49%3a41Z")
        find_serial =  self.browser.find_element_by_id("UsernameTextBox")
        find_serial.send_keys(self.login)
        find_serial =self.browser.find_element_by_id ("PasswordTextBox")
        find_serial.send_keys(self.password)
        find_serial.send_keys(Keys.ENTER)
        self.browser.get('https://uonetplus.umt.tarnow.pl/tarnow/Start.mvc/Index')
        self.browser.get('https://uonetplus-opiekun.umt.tarnow.pl/tarnow/001991/Start/Index/')
    def oceny(self):
        overall = list()
        self.browser.get('https://uonetplus-opiekun.umt.tarnow.pl/tarnow/001991/Oceny.mvc/Wszystkie')
        bs = BeautifulSoup (self.browser.page_source , 'lxml')
        raw_oceny = bs.find (name='tbody').find_all (name='tr')
        for oc in raw_oceny:
            for element in oc.find_all('td'):
                s = ""
                if 'class' in element.attrs:
                    for grade in element.find_all('span'):
                        oce = grade.attrs['alt'].split("<br/>")
                        s+=(grade.text+'\t')
                        s+=(oce[-3]+'\t')
                        s+=(oce[-1]+'\t')
                        s+=(oce[-2]+'\t')
                        s+=(oce[-4]+'\n')
                else:
                    s+= element.text
                overall.append(s)
        return overall
    def klasa(self):
        self.browser.get ('https://uonetplus-opiekun.umt.tarnow.pl/tarnow/001991/Statystyki.mvc/Uczen')
        bs = BeautifulSoup (self.browser.page_source , 'lxml')
        opts = bs.find(id="cbPrzedmioty").find_all(name='option')
        op = list()
        ret = list()
        for opt in opts:
            op.append (opt.text)
        for option in op:
            s = ""
            self.browser.find_element_by_xpath("//select[@id='cbPrzedmioty']/option[text()='"+option+"']").click()
            bs = BeautifulSoup (self.browser.page_source , 'lxml')
            pc = bs.find (name='svg' , class_='ct-chart-pie')
            if type(pc)!=type(None):
                proc = pc.find_all(name='path',class_="ct-slice ct-donut")
                for proce in proc:
                    s+=(proce.attrs['ct:value']+'\t')
                s+=option
                ret.append(s)
        return ret
    def plan(self):
        overall = list()
        self.browser.get("https://uonetplus-opiekun.umt.tarnow.pl/tarnow/001991/Lekcja.mvc/PlanZajec?data=637265664000000000")
        bs = BeautifulSoup(self.browser.page_source,"lxml")
        rows = bs.find(name="tbody").find_all(name="tr")
        for row in rows:
            rowlist = ""
            cells = row.find_all("td")
            for td in cells:
                if td.text == "\n":
                    rowlist +=("-"+"\t")
                else:
                    rowlist +=(td.text + "\t")
            overall.append(rowlist)
        return overall
    def spraw(self):
        self.browser.get("https://uonetplus-opiekun.umt.tarnow.pl/tarnow/001991/Sprawdziany.mvc/Terminarz")
        bs = BeautifulSoup(self.browser.page_source,"lxml")
        weeks = bs.find(name='table').find(name='tbody',class_='text-center').find_all(name='tr')
        overall= ""
        for week in weeks:
            days = week.find_all(name='td')
            for day in days:
                d = ""
                (data,info) = day.find_all(name='div')
                d+=(data.text.strip()+"||")
                d+=(info.text.replace("\n"," ").strip()+"\n")
                overall+=d
        return overall