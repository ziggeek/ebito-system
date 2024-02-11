# RabbitMQ on Kubernetes

## Автор:<br>
Мурадов Артур ([ziggeek](https://github.com/ziggeek))<br>

## Namespace

```
kubectl create ns rabbits
```

## Storage Class

```
kubectl get storageclass
NAME                 PROVISIONER             RECLAIMPOLICY   VOLUMEBINDINGMODE      ALLOWVOLUMEEXPANSION   AGE
standard (default)   rancher.io/local-path   Delete          WaitForFirstConsumer   false                  84s
```

## Deployment

```
kubectl apply -n rabbits -f .\kubernetes\rabbit-rbac.yaml
kubectl apply -n rabbits -f .\kubernetes\rabbit-configmap.yaml
kubectl apply -n rabbits -f .\kubernetes\rabbit-secret.yaml
kubectl apply -n rabbits -f .\kubernetes\rabbit-statefulset.yaml
```

## Access the UI

```
kubectl -n rabbits port-forward rabbitmq-0 8080:15672
```
Go to http://localhost:8080 <br/>
Username: `guest` <br/>
Password: `guest` <br/>