# README

### seed dummy data into Redis

clone ipsum:

`git clone https://github.com/buzzm/ipsum.git`

generate dummy data with shape file and load it into Redis:

`python ~/ipsum/pygenipsum.py --count 10000 shape.jsch | ./import.py`

### java

compile:

`mvn compile assembly:single`

run:

`java -jar target/my-app-1.0-SNAPSHOT-jar-with-dependencies.jar  @group:jake`

### python

run:

`./myapp.py @group:jake`
