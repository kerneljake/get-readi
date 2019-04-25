#!/usr/bin/env python

import sys
import time
from redisearch import Client, TextField, NumericField, TagField, Query

client = Client('myIndex')

start = int(round(time.time() * 1000))
result = client.search(sys.argv[1])
end = int(round(time.time() * 1000))

print('number of documents: %d, total time: %d ms'  % ( result.total, end-start) )
