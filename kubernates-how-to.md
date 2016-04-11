kubernates getting started in docker
====================================

[source](http://kubernetes.io/docs/getting-started-guides/docker/)

1. version

try stable version:

```sh
$ export K8S_VERSION=$(curl -sS https://storage.googleapis.com/kubernetes-release/release/stable.txt)
```

or latest:

```sh
$ export K8S_VERSION=$(curl -sS https://storage.googleapis.com/kubernetes-release/release/latest.txt)
```

2. cluster

```sh
$ docker run \
    --volume=/:/rootfs:ro \
    --volume=/sys:/sys:ro \
    --volume=/var/lib/docker/:/var/lib/docker:rw \
    --volume=/var/lib/kubelet/:/var/lib/kubelet:rw \
    --volume=/var/run:/var/run:rw \
    --net=host \
    --pid=host \
    --privileged=true \
    --name=kubelet \
    -d \
    gcr.io/google_containers/hyperkube-amd64:${K8S_VERSION} \
    /hyperkube kubelet \
        --containerized \
        --hostname-override="127.0.0.1" \
        --address="0.0.0.0" \
        --api-servers=http://localhost:8080 \
        --config=/etc/kubernetes/manifests \
        --cluster-dns=10.0.0.10 \
        --cluster-domain=cluster.local \
        --allow-privileged=true --v=2
```

3. download kubectl

```sh
$ curl -O http://storage.googleapis.com/kubernetes-release/release/${K8S_VERSION}/bin/darwin/amd64/kubectl
$ chmod 755 kubectl
```

4. create ssh tunnel for

```sh
$ docker-machine ssh `docker-machine active` -N -L 8080:localhost:8080
```

5. open new terminal and config cluster

```sh
$ ./kubectl config set-cluster test-doc --server=http://localhost:8080
cluster "test-doc" set.
$ ./kubectl config set-context test-doc --cluster=test-doc
context "test-doc" set.
$ ./kubectl config use-context test-doc
switched to context "test-doc".
```

6. testing

```sh
$ ./kubectl get nodes
NAME        STATUS    AGE
127.0.0.1   Ready     11m
$ ./kubectl run nginx --image=nginx --port=80
deployment "nginx" created
```

7. open another docker terminal and show whats running

```sh
$ docker ps
```

8. expose service

```sh
$ ./kubectl expose deployment nginx --port=80
service "nginx" exposed
$ ip=$(./kubectl get svc nginx --template={{.spec.clusterIP}})
$ echo $ip
10.0.0.176
$ ./kubectl get svc nginx --template={{.spec.clusterIP}} # for os x see at the below
$ docker-machine ssh `docker-machine active` curl $ip
...you have to see nginx index html page...
```

9. deploy dns

```sh
$ wget http://kubernetes.io/docs/getting-started-guides/docker-multinode/skydns.yaml.in
$ export DNS_REPLICAS=1
$ export DNS_DOMAIN=cluster.local
$ export DNS_SERVER_IP=10.0.0.10
$ sed -e "s/{{ pillar\['dns_replicas'\] }}/${DNS_REPLICAS}/g;s/{{ pillar\['dns_domain'\] }}/${DNS_DOMAIN}/g;s/{{ pillar\['dns_server'\] }}/${DNS_SERVER_IP}/g" skydns.yaml.in > ./skydns.yaml
$ ./kubectl get ns
NAME      STATUS    AGE
default   Active    36m
```

10. turn down and clean up (in docker terminal)

```sh
$ docker rm -f $(docker ps -aq)
$ docker-machine ssh `docker-machine active`
docker@default:~$ sudo umount `cat /proc/mounts | grep /var/lib/kubelet | awk '{print $2}'`
docker@default:~$ sudo rm -rf /var/lib/kubelet
docker@default:~$ exit
```
