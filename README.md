odjug-cloud [![build](https://travis-ci.org/daggerok/odjug-cloud.svg?branch=master)](https://travis-ci.org/daggerok/odjug-cloud)
===========

Odessa JUG April live demo project skeleton (in progress)

install docker, and expose env variable:

```sh
export DOCKER_HOST=$(docker-machine ip default)
```

kafka:

```sh
docker-compose -f docker/kafka-osx-docker-compose.yml up # edit env vars 
```

rabbit:

```sh
docker-compose -f docker/rabbit-docker-compose.yml up
```

zipkin:

```sh
docker-compose -f zipkin-docker/docker-compose-legacy.yml up
```

topics:

- config server, encryption
- discovery register service (eureka server regions)
- refresh scope, spring cloud bus amqp/kafka
- ribbon client load balancer
- feign declarative service
- zuul proxy server
- hystrix dashboard (Netflix monitor)
- eventual consistency on write with kafka and rabbitmq
- zipkin (distributed tracer from Twitter)

todo:

- config encryption / decrtyption

maybe:

- turbine (collect hystrix streams)
- consul (config/discovery)
- zoo (config/discovery)

links:

- [spring cloud](http://projects.spring.io/spring-cloud/)
- [spring cloud reference](http://cloud.spring.io/spring-cloud-static/spring-cloud.html)
- [handle instance status change](https://github.com/netflix-spring-one/spring-cloud-netflix-contrib)
- [circuit breaker](http://martinfowler.com/bliki/CircuitBreaker.html)
- [spring cloud netflix contrib](https://github.com/netflix-spring-one/spring-cloud-netflix-contrib)
- [netflix spectator](https://github.com/Netflix/spectator)
- [spring cloud stream samples](https://github.com/spring-cloud/spring-cloud-stream-samples)
- [zipkin get started](http://zipkin.io/pages/quickstart)
- [docker](https://docs.docker.com/engine/installation)
- [docker-kafka](https://github.com/spotify/docker-kafka)
- [docker-rabbitmq](https://github.com/spotify/docker-kafka)
- [docker-zipkin](https://github.com/openzipkin/docker-zipkin)
- [spring executable jar](https://docs.spring.io/spring-boot/docs/current/reference/html/deployment-install.html)

