from __future__ import with_statement
import sys
import time 
from datetime import datetime as dt 
def get_platform(arr):
    website_list=list(arr.split(","))
    platforms = {
        'linux1' : 'Linux',
        'linux2' : 'Linux',
        'darwin' : 'OS X',
        'win32' : 'Windows'
    }
    if sys.platform not in platforms:
        res=sys.platform
    res=platforms[sys.platform]
    if res=='Linux' or res=='OS X':
        hosts_path = "/etc/hosts"
    elif res=='Windows':
        hosts_path =  "C:\Windows\System32\drivers\etc"
    else:
        pass 
    redirect = "127.0.0.1"
    while True: 
        print("Working hours...") 
        with open(hosts_path, 'r+') as file: 
            content = file.read() 
            for website in website_list: 
                if website in content: 
                    pass
                else: 
                    file.write(redirect + " " + website + "\n") 
if __name__=="__main__":
    sit=sys.argv[1]
    get_platform(sit)	