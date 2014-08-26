import string
import json
from bs4 import BeautifulSoup as bs4

files = []
for i in xrange(26):
    files.append('gcide_' + string.lowercase[i])

for xml_file in files:
    out_file = 'json_dict/' + xml_file + '.json'
    ofile = open(out_file, 'w')

    print "Decoding xml file: " + xml_file

    soup = bs4(open('../xml_files/' + xml_file + '.xml'))
    tags = soup.find_all('p')

    out = {}
    for tag in tags:
        if tag.hw and tag.find('def'):
            key = tag.hw.text
            key = key.replace('"', "")
            key = key.replace('*', "")
            key = key.replace('`', "")
            key = key.replace("'", "")
            out[key] = tag.find('def').text

    ofile.write(json.dumps(out))

    print "Decoded xml file: %s to JSON format" % xml_file
