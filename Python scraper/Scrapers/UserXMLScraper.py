import time

from bs4 import BeautifulSoup
from selenium import webdriver
from selenium.common.exceptions import TimeoutException
from selenium.webdriver.common.keys import Keys
from xml.etree.ElementTree import Element,SubElement,tostring
import selenium
from selenium.webdriver.support import expected_conditions
from selenium.webdriver.support.wait import WebDriverWait
from selenium.webdriver.common.by import By

class UserXMLScraper:
    def __init__(self,login,password):
        options = selenium.webdriver.chrome.options.Options()
        #options.headless = True
        self.browser = selenium.webdriver.Chrome("C:\\Users\\RP\\Downloads\\chromedriver.exe",options=options)
        self.browser.get("https://adfs.umt.tarnow.pl/adfs/ls/?wa=wsignin1.0&wtrealm=https%3a%2f%2fcufs.umt.tarnow.pl%3a443%2ftarnow%2fAccount%2fLogOn&wctx=rm%3d0%26id%3dadfs%26ru%3d%252ftarnow%252fFS%252fLS%253fwa%253dwsignin1.0%2526wtrealm%253dhttps%25253a%25252f%25252fuonetplus.umt.tarnow.pl%25252ftarnow%25252fLoginEndpoint.aspx%2526wctx%253dhttps%25253a%25252f%25252fuonetplus.umt.tarnow.pl%25252ftarnow%25252fLoginEndpoint.aspx&wct=2020-05-28T10%3a49%3a41Z")
        self.browser.find_element_by_id("userNameInput").send_keys(login)
        self.browser.find_element_by_id("passwordInput").send_keys(password)
        self.browser.find_element_by_id("submitButton").click()
        self.browser.get("https://uonetplus-uczen.umt.tarnow.pl/tarnow/001991/Start")
    def grades(self):
        WebDriverWait(self.browser,7).until(expected_conditions.presence_of_element_located((By.ID,"ext-element-146")))
        self.browser.find_element_by_id("ext-element-146").click()
        WebDriverWait(self.browser,7).until(expected_conditions.presence_of_element_located((By.ID,"ext-element-126")))
        self.browser.find_element_by_id("ext-element-126").click()
        WebDriverWait(self.browser, 7).until(expected_conditions.presence_of_element_located((By.ID, "ext-element-125")))
        bs = BeautifulSoup(self.browser.page_source,"lxml")
        self.browser.find_element_by_id("ext-element-141").click()
        gr = bs.find(id="ext-element-125")
        sbs = BeautifulSoup(self.browser.page_source,"lxml")
        sbs = sbs.find(id="ext-element-138").find_all(name='div',recursive=False)
        root = Element('przedmioty_uczen')
        i= 0
        for element in gr.find_all(name="div",recursive=False):
            e = element.find("div").find("div").find_all('div',recursive=False)
            subject = e[0].text
            grades = e[1].find(name='div',recursive=False).find_all('div',recursive=False)
            subj = SubElement(root, 'przedmiot')
            xml_final = SubElement(subj,'koncowa')
            xml_final.text = sbs[i].find('div',recursive=False).find('div',recursive=False).find_all('div',recursive=True)[3].div.text
            xml_subject_name = SubElement(subj, 'nazwa_przedmiotu')
            xml_subject_name.text = subject
            xml_proposed = SubElement(subj,'proponowana')
            xml_proposed.text = sbs[i].find('div',recursive=False).find('div',recursive=False).find_all('div',recursive=True)[4].div.text
            sub = SubElement(subj,'oceny')
            i+=1
            for grade in grades:
                data = grade.find(name='div',recursive=False).find(name='div',recursive=False).find_all(recursive=False)
                mark = data[0].find('div').find_all('span')[0].text
                description = data[0].find('div').find_all('span')[1].text
                weight = data[1].find('div').text
                (teacher,date) = data[2].find('div').text.split(",")
                xml_grade = SubElement(sub,'ocena')
                xml_mark = SubElement(xml_grade,'stopien')
                xml_mark.text = mark
                xml_weight = SubElement(xml_grade,'waga')
                xml_weight.text = weight
                xml_teacher = SubElement(xml_grade,'nauczyciel')
                xml_teacher.text = teacher
                xml_date = SubElement(xml_grade,'data')
                xml_date.text = date
                xml_descr = SubElement(xml_grade,'opis')
                xml_descr.text = description
        return tostring(root)

    def hours(self):
        days = ["mon", "tue", "wen", "thu", "fri"]
        overall = Element("plan_lekcji")
        WebDriverWait(self.browser, 7).until(expected_conditions.presence_of_element_located((By.ID, "ext-element-808")))
        time.sleep(1)
        self.browser.find_element_by_id("ext-element-808").click()
        time.sleep(2)
        bs = BeautifulSoup(self.browser.page_source, "lxml")
        rows = bs.find(id="planZajecPanelId").find("table").find_all(name="tr")
        for row in rows[1:]:
            rowXML = SubElement(overall, "tydzien")
            cells = row.find_all("td")
            (lesson,lb,ub) = str(cells[0].find(recursive=False)).split(">")[1:4]
            lesson = lesson.replace('<br/',"")
            lb = lb.replace("<br/","") + "-" + ub.replace("</div","")
            less = SubElement(rowXML,'lekcja')
            less.text = lesson
            tm = SubElement(rowXML,'czas')
            tm.text = lb
            i=0
            for cell in cells[1:]:
                txt = cell.text
                el = SubElement(rowXML,days[i])
                el.text = txt
                i+=1
        return tostring(overall)

    def exams(self):
        WebDriverWait(self.browser, 7).until(expected_conditions.presence_of_element_located((By.ID, "ext-element-1029")))
        time.sleep(1)
        self.browser.find_element_by_id("ext-element-1029").click()
        time.sleep(2)
        bs = BeautifulSoup(self.browser.page_source,"lxml")
        table = bs.find(name="table",id="ext-vsprawdzianygrid-1")
        rows = table.find("div",id="ext-element-949").find_all("tr")
        overall = Element("dni")
        for row in rows:
            content = row.find('div',recursive=False)
            days = content.find_all('div',recursive=False)
            for day in days:
                d = SubElement(overall, "dzien")
                day_content = day.find('div',recursive=False).find('div',recursive=False).find('div',recursive=False).find_all('div',recursive=False)
                date = SubElement(d,"data")
                date.text=day_content[0].text
                inf = SubElement(d, "info")
                for info_data in day_content[1].find('div',recursive=False).find_all('div',recursive=False):
                    info_member = SubElement(inf,"info_member")
                    info_member.text = info_data.text
        return tostring(overall)


    def msg(self):
        try:
            self.browser.get("https://uonetplus-uzytkownik.umt.tarnow.pl/tarnow/")
            root = Element("messages")
            WebDriverWait(self.browser, 5).until(expected_conditions.presence_of_element_located((By.ID,"gridview-1055-body")))
            WebDriverWait(self.browser,5).until(lambda dr: dr.find_element_by_id("gridview-1055-body").find_elements_by_tag_name("tr").__len__() > 0)
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
            return tostring(root)
        except(TimeoutException):
            return bytes("<messages/>",encoding="UTF-8")


    def dispose(self):
        self.browser.close()



