import getpass
import time

from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from xml.etree.ElementTree import Element,SubElement,tostring
import selenium
from selenium.webdriver.support import expected_conditions
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.by import By


def predicate(driver):
    return driver.find_element_by_id("container-1080-innerCt").text != ""

def test(u):
    try:
        two = str(tostring(u.oceny())).replace("'","")[1:]
        twk = str(tostring(u.klasa())).replace("'","")[1:]
        twp = str(tostring(u.plan())).replace("'","")[1:]
        tws = str(tostring(u.spraw())).replace("'","")[1:]
        twm = str(tostring(u.msg())).replace("'","")[1:]
        u.browser.quit()
        return "<uczen>"+two+twk+twp+tws+twm+"</uczen>"
    except Exception:
        u.browser.quit()
        return "error_msg"

class User:
    def __init__(self,login,password):
        self.login = login
        self.password = password
        options = selenium.webdriver.chrome.options.Options()
        options.add_argument ('--headless')
        options.add_argument ('--disable-gpu')
        self.browser =selenium.webdriver.Chrome("C:\\Users\\"+getpass.getuser()+"\\Downloads\\chromedriver.exe",options=options)
        self.browser.get("https://adfs.umt.tarnow.pl/adfs/ls/?wa=wsignin1.0&wtrealm=https%3a%2f%2fcufs.umt.tarnow.pl%3a443%2ftarnow%2fAccount%2fLogOn&wctx=rm%3d0%26id%3dadfs%26ru%3d%252ftarnow%252fFS%252fLS%253fwa%253dwsignin1.0%2526wtrealm%253dhttps%25253a%25252f%25252fuonetplus.umt.tarnow.pl%25252ftarnow%25252fLoginEndpoint.aspx%2526wctx%253dhttps%25253a%25252f%25252fuonetplus.umt.tarnow.pl%25252ftarnow%25252fLoginEndpoint.aspx&wct=2020-05-28T10%3a49%3a41Z")
        find_serial =  self.browser.find_element_by_id("UsernameTextBox")
        find_serial.send_keys(self.login)
        find_serial =self.browser.find_element_by_id ("PasswordTextBox")
        find_serial.send_keys(self.password)
        find_serial.send_keys(Keys.ENTER)
        self.browser.get('https://uonetplus.umt.tarnow.pl/tarnow/Start.mvc/Index')
        self.browser.get('https://uonetplus-opiekun.umt.tarnow.pl/tarnow/001991/Start/Index/')

    def oceny(self) ->Element:
        overall = Element("przedmioty_uczen")
        self.browser.get('https://uonetplus-opiekun.umt.tarnow.pl/tarnow/001991/Oceny.mvc/Wszystkie')
        bs = BeautifulSoup (self.browser.page_source , 'lxml')
        raw_oceny = bs.find (name='tbody').find_all (name='tr')
        for oc in raw_oceny:
            przedmiot = SubElement(overall, "przedmiot")
            i = 0
            for element in oc.find_all('td'):
                if 'class' in element.attrs:
                    s = SubElement (przedmiot , "oceny")
                    for grade in element.find_all('span'):
                        oce = grade.attrs['alt'].split("<br/>")
                        ocena = SubElement(s,"ocena")
                        stopien = SubElement(ocena,"stopien")
                        stopien.text = grade.text
                        waga= SubElement(ocena,"waga")
                        waga.text = oce[-3]
                        nauczyciel=SubElement(ocena,"nauczyciel")
                        nauczyciel.text = oce[-1]
                        data =SubElement(ocena,"data")
                        data.text = oce[-2]
                        opis = SubElement(ocena,"opis")
                        opis.text = oce[-4]
                else:
                    tab = ["koncowa","nazwa_przedmiotu","proponowana"]
                    i+=1
                    el = SubElement(przedmiot,tab[i%3])
                    el.text = element.text
        return overall

    def klasa(self) ->Element:
        oces = ["ndst","dop","dst","db","bdb","cel"]
        self.browser.get ('https://uonetplus-opiekun.umt.tarnow.pl/tarnow/001991/Statystyki.mvc/Uczen')
        bs = BeautifulSoup (self.browser.page_source , 'lxml')
        opts = bs.find(id="cbPrzedmioty").find_all(name='option')
        op = list()
        overall = Element("przedmioty_klasa")
        for opt in opts:
            op.append (opt.text)
        for option in op:
            s = SubElement(overall,"przedmiot")
            self.browser.find_element_by_xpath("//select[@id='cbPrzedmioty']/option[text()='"+option+"']").click()
            bs = BeautifulSoup (self.browser.page_source , 'lxml')
            pc = bs.find (name='svg' , class_='ct-chart-pie')
            if type(pc)!=type(None):
                proc = pc.find_all(name='path',class_="ct-slice ct-donut")
                for i in range(len(proc)):
                    e=SubElement(s,oces[i%6])
                    e.text = proc[i].attrs['ct:value']
                przed=SubElement(s,"nazwa_przedmitu")
                przed.text = option
        return overall

    def plan(self) ->Element:
        days = ["lekcja","czas","mon","tue","wen","thu","fri"]
        overall = Element("plan_lekcji")
        self.browser.get("https://uonetplus-opiekun.umt.tarnow.pl/tarnow/001991/Lekcja.mvc/PlanZajec")
        bs = BeautifulSoup(self.browser.page_source,"lxml")
        rows = bs.find(name="tbody").find_all(name="tr")
        for row in rows:
            rowXML = SubElement(overall,"tydzien")
            cells = row.find_all("td")
            for i in range(len(cells)):
                if cells[i].text == "\n":
                    rowlist = SubElement(rowXML,days[i%7])
                    rowlist.text = "-"
                else:
                    rowlist = SubElement(rowXML,days[i%7])
                    rowlist.text = cells[i].text.replace("\n","")
        return overall

    def spraw(self)->Element:
        self.browser.get("https://uonetplus-opiekun.umt.tarnow.pl/tarnow/001991/Sprawdziany.mvc/Terminarz")
        bs = BeautifulSoup(self.browser.page_source,"lxml")
        weeks = bs.find(name='table').find(name='tbody',class_='text-center').find_all(name='tr')
        overall= Element("dni")
        for week in weeks:
            days = week.find_all(name='td')
            for day in days:
                d = SubElement(overall,"dzien")
                d.text=""
                (data,info) = day.find_all(name='div')
                date = SubElement(d,"data")
                date.text = data.text.strip()
                inf = SubElement(d,"info")
                inf.text = info.text.replace("\n","").strip()
        return overall
    def msg(self)->Element:
        self.browser.get("https://uonetplus-uzytkownik.umt.tarnow.pl/tarnow/")
        root = Element("messages")
        WebDriverWait(self.browser, 5).until(expected_conditions.presence_of_element_located((By.ID,"gridview-1055-body")))
        WebDriverWait(self.browser,5).until(lambda dr: dr.find_element_by_id("gridview-1055-body").find_elements_by_tag_name("tr").__len__() > 0)
        time.sleep(0.3)
        table = self.browser.find_elements_by_id("gridview-1055-table")[0]
        tbody = table.find_element_by_tag_name("tbody")
        prev = ""
        for child in tbody.find_elements_by_tag_name("tr"):
            tds = child.find_elements_by_tag_name("td")
            msg = SubElement(root,"message")
            from_ = SubElement(msg,"from")
            from_.text = tds[1].text
            topic = SubElement(msg,"topic")
            topic.text = tds[2].text
            date = SubElement(msg,"date")
            date.text = tds[4].text
            text = SubElement(msg,"text")
            child.click()
            WebDriverWait(self.browser,5).until(lambda dr: dr.find_element_by_id("container-1080-innerCt").text!=prev)
            #time.sleep(0.2)
            text.text = self.browser.find_element_by_id("container-1080-innerCt").text
            prev = self.browser.find_element_by_id("container-1080-innerCt").text
        return root