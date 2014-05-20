#!/usr/bin/python

import sys,httplib2,urllib
import json
import urllib.parse

HOST="cjurl.me"
HEADERS = {'Content-type': 'application/json'}
HTTP = httplib2.Http()
SITE='http://votecastomatic.com'

####################################################
#
# http://votecastomatic.com/ api
#
####################################################
# Elections: http://votecastomatic.com/elections
# Voters: http://votecastomatic.com/voters
# Election (Galactic Emperor): http://votecastomatic.com/elections/GalacticEmperor
# Candidates (Glelectic Emperor): http://votecastomatic.com/elections/GalacticEmperor/candidates


def view(s):
  for key in s.__dict__:
    #print(key)
    print("%s:%s" % (key,s.get(key,"wtf?")))


###################################################
#
# Utilities
#
###################################################

def fetch(key):
  resp, content = HTTP.request(SITE+'/'+key, 'GET', headers=HEADERS)
  return content.decode('utf-8')

def fetchJson(key): return json.loads(fetch(key))

def fetchNames(key): return [pair.get('name','???') for pair in fetchJson(key)]
  
###################################################
#
# Rest calls
#
###################################################

def getElections(): return fetchNames('elections')

def getVoters(): return fetchNames('voters')

def getCandidates(election):
  url = SITE + '/elections/' + urllib.parse.quote(election) + '/candidates'
  resp, content = HTTP.request(url, 'GET', headers=HEADERS)
  data = json.loads(content.decode('utf-8'))
  return [pair.get('name','???') for pair in data]

elections = getElections()
voters = getVoters()
imperialCandidates = getCandidates('GalacticEmperor')
print(imperialCandidates)




  
  
  


  



