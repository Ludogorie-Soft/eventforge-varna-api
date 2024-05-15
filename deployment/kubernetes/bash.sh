kubectl apply -f mysql-deploy.yaml
sleep 2
kubectl apply -f mysql-service.yaml
sleep 2
kubectl apply -f app-deploy.yaml
sleep 2
kubectl apply -f app-service.yaml