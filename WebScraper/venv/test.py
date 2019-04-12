import selenium
import time
import requests

from bs4 import BeautifulSoup

from selenium import webdriver
from selenium.webdriver.support.ui import Select
from selenium.common.exceptions import NoSuchElementException
from selenium.webdriver.common.keys import Keys


def get_show_history(username, password):

    # option = webdriver.ChromeOptions()
   try:
        browser = webdriver.Chrome(executable_path=r"/Users/quincycopeland/qcopeland45/cs6015/WebScraper/venv/chromedriver")

        browser.get("https://www.hulu.com")
        login_button = browser.find_element_by_class_name("navigation__action-button")
        login_button.click()

        email_box = browser.find_element_by_name("email")
        pswrd_box = browser.find_element_by_name("password")
        submit_button = browser.find_element_by_xpath("// *[ @ id = 'login-modal'] / div[2] / div[2] / div / div / div[1] / div[2] / div[3] / button")
        email_box.send_keys(username)
        pswrd_box.send_keys(password)
        submit_button.click()


        #getting "my stuff" link and clicking it
        time.sleep(5)
        mystuff_link = browser.find_element_by_xpath("//*[@id='__next']/div/div[2]/header/div/div/div[3]/a")
        mystuff_link.click()

        time.sleep(5)
        mystuff_list = browser.find_elements_by_class_name('MyStuff__tile')

        print(len(mystuff_list))

        #cascading down to grab the title text
        shows_list = []
        for x in mystuff_list:
            #getting the title of each show ".// means to start searching from that element
            title = x.find_element_by_xpath('.//div/div/div[2]/a/div').text
            shows_list.append(title)
            print(title)


        write_showlist("shows_list.txt", shows_list)

        print(len(shows_list))
        # browser.quit()

   except:
        print("Error ocurered while attempting open scrap webpage. Please run program again")
        browser.quit()



def write_showlist(filename, shows_list):

    file = open(filename, "w") # w means write

    file.write("Your current list of shows:\n")
    for show in shows_list:
        file.write(show + "\n")




def remove_show_from_queue(showToRemove, username, password):

    try:
        browser = webdriver.Chrome(executable_path=r"/Users/quincycopeland/qcopeland45/cs6015/WebScraper/venv/chromedriver")

        browser.get("https://www.hulu.com")
        login_button = browser.find_element_by_class_name("navigation__action-button")
        login_button.click()
        time.sleep(4)

        email_box = browser.find_element_by_name("email")
        pswrd_box = browser.find_element_by_name("password")
        submit_button = browser.find_element_by_xpath("//*[@id='login-modal']/div[2]/div[2]/div/div/div[1]/div[2]/div[3]/button")
        email_box.send_keys(username)
        pswrd_box.send_keys(password)
        submit_button.click()

        time.sleep(5)
        #nagivate to your "my stuff" watched history
        mystuff_link = browser.find_element_by_xpath("//*[@id='__next']/div/div[2]/header/div/div/div[3]/a")
        mystuff_link.click()


        time.sleep(5)
        myshow_list = browser.find_elements_by_class_name('MyStuff__tile') #list of all shows in queue
        remove_buttons = browser.find_elements_by_class_name('MyStuffTile__toggle') #list of all remove buttons
        print(len(remove_buttons))

        j = 0
        for i in myshow_list:
            title = i.find_element_by_xpath('.//div/div/div[2]/a/div').text
            j = j + 1
            if(title == showToRemove):
                print("Found " + title + " at location " + str(j))
                pre_remove = remove_buttons[j].find_element_by_xpath('//*[@id="__next"]/div/div[2]/div[1]/div/div[2]/div/div/div[2]/div'+ str([j]) +'/div/button')
                pre_remove.click()

                # initial click to remove show from queue
                footer_actions = browser.find_element_by_class_name('MyStuffFooter__actions')
                remove = footer_actions.find_element_by_xpath('//*[@id="__next"]/div/div[2]/div[1]/div/div[3]/div/div[2]/button[2]')
                time.sleep(1)
                remove.click()

                time.sleep(2)

                # WORKED ONCE. CANT FIGURE OUT BUG, FINDS SHWO TO DELETE CLICKS THE BUTTON THEN CLICKS THE BUTTON AGAIN TO CONFIRM BUT SHOW IS STILL IN QUEUE
                # second click to confirm you actually do want to delete the selected show
                footer_actions2 = browser.find_element_by_class_name('MyStuffFooter__actions')
                remove2 = footer_actions2.find_element_by_xpath("//*[@class='MyStuffFooter__btn MyStuffFooter__btn--accept btn Button Button--cta']")
                time.sleep(1)
                remove2.click()

                time.sleep(20)

                break

            # print("Show not found in queue")

        browser.close()
        browser.quit()

    except:

        print("ERROR OCCURED WHILE OPENING BROWSER")
        browser.close()
        browser.quit()












password = "huluQc*981300"
usermame = "qcopeland45@gmail.com"

remove_show_from_queue("Family Guy",usermame,password)
# get_show_history(usermame, password)
