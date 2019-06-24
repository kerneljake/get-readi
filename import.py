#!/usr/bin/env python
# bulk import JSON into RediSearch
# JSON object becomes Redis hash
#
# example JSON:
# {"search": "from", "code": 65806889897, "group": "yashi", "days": "090", "qty": 7, "id": "1ae39496-ab96-42fa-a307-ace5c43b5c0b", "type": 3}
#
# USAGE: $0 <files... or stdin>
# Warning: this script will drop an existing schema (and hence database)!

from redisearch import Client, TextField, NumericField, TagField, Query
import sys, json
import argparse

parser = argparse.ArgumentParser(description='bulk importer.', add_help=False)
parser.add_argument('--host', '-h', help='endpoint host', default='localhost')
parser.add_argument('--port', '-p', help='endpoint port', type=int, default=6379)
parser.add_argument('--infile', nargs='?', type=argparse.FileType('r'), default=sys.stdin)
args = parser.parse_args()

client = Client('myIndex', args.host, args.port)
batch = client.BatchIndexer(client, chunk_size=1000)

# create schema
try:
    client.drop_index()
except:
    pass

try:
    client.create_index([
            TextField('group', no_stem=True),
            TextField('code', no_stem=True),
            TagField('type'),
            TextField('qty', no_stem=True),
            TextField('days', no_stem=True),
            TextField('search', no_stem=True)
            ])
except:
    pass

# load JSON from stdin or file
for line in args.infile:
    mydict = json.loads(line)
    id = mydict['id']
    del mydict['id']
    batch.add_document(id, **mydict)

batch.commit()
