sample

```sh
$ git clone https://github.com/spring-cloud/spring-cloud-consul.git
$ cd spring-cloud-consul/
$ docker-compose up
$ mvn package
$ java -Dserver.port=8083 -jar spring-cloud-consul-sample/target/spring-cloud-consul-sample-1.0.0.BUILD-SNAPSHOT.jar
$ java -Dserver.port=8084 -jar spring-cloud-consul-sample/target/spring-cloud-consul-sample-1.0.0.BUILD-SNAPSHOT.jar
$ open localhost:8500
```

running consul in docker

```sh
$ docker run -p 8400:8400 -p 8500:8500 -p 8600:53/udp -h node1 progrium/consul -server -bootstrap -ui-dir /ui
$ open localhost:8500
```

```sh
$ export $CONSUL=$(docker ps | greep consul | awk '{print $1}')
$ docker rename $CONSUL consul
$ docker stop consul
```

