```sh
$ docker-compose up
$ git clone https://github.com/spring-cloud/spring-cloud-zookeeper.git
$ cd spring-cloud-zookeeper/
$ mvn --settings .settings.xml package
$ java -Dserver.port=8081 -jar spring-cloud-zookeeper-sample/target/spring-cloud-zookeeper-sample-1.0.0.BUILD-SNAPSHOT.jar
$ java -Dserver.port=8082 -jar spring-cloud-zookeeper-sample/target/spring-cloud-zookeeper-sample-1.0.0.BUILD-SNAPSHOT.jar
```

```sh
$ open localhost:8080
$ # hit refresh a few times, look as port changing it means discovery client choose randome node
```

```sh
$ export $ZOO=$(docker ps | grep zookeeper | awk '{print $1}')
$ docker rename $ZOO zoo
$ docker exec -it zoo bash
> ./zkCli.sh
[zk: localhost:2181(CONNECTED) 0] ls /
[services, zookeeper]
[zk: localhost:2181(CONNECTED) 1] ls /services
[testZookeeperApp]
[zk: localhost:2181(CONNECTED) 2] get /services/testZookeeperApp
...
$ docker stop zoo
```

profs: easy to change deps
cons: not bloud bus implementation

