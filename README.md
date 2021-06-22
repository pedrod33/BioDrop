# TQS BioDrop
This project was created within the scope of the [Software Testing and Quality](https://www.ua.pt/pt/uc/8109) discipline.
## Introduction
BioDrop is a vegetable delivery service, it has two main target users:
* Rider who accesses the application to deliver products
* Client who accesses the application to place product orders
## team members
| NMec|  Roles | Name | email |
|--:|---|---|---|
| 91359 | DevOps Master | Juan Lessa | juanvlessa@ua.pt |
| 93310 | Product Owner | Gonçalo Pereira | pereira.goncalo@ua.pt |
| 93103 | Team Leader | Pedro Tavares | pedrod33@ua.pt |
| 87456 | Quality Assurance | José Carlos | jose.carlos94@ua.pt |
| 95278 | Quality Assurance | Diogo Cunha | dgcunha@ua.pt |
## project resources
* [BusinessInitiative](https://github.com/pedrod33/BioDrop/tree/develop/BusinessInitiative): Springboot Application
* [DeliveriesEngine](https://github.com/pedrod33/BioDrop/tree/develop/DeliveriesEngine): Springboot Application
* [ClientApp](https://github.com/pedrod33/BioDrop/tree/develop/TQS_final/ClientApp): Reactjs Website
* [RiderApp](https://github.com/pedrod33/BioDrop/tree/develop/TQS_final/RiderApp): Reactjs Website
## Hosts
| Service |  VM address |
|--|---|
| BusinessInitiative |  http://192.168.160.229:8090 |
| DeliveriesEngine | http://192.168.160.229:8089 |
| ClientApp | http://192.168.160.229:81 |
| RiderApp | http://192.168.160.229:82 |
  
**Note**: to access the addresses you need to be connected to the [UA](https://www.ua.pt) network.
## API documentation

## Static analysis
We used Sonarqube services to generate such analysis
* [deliveriesEngine static analysis](https://sonarcloud.io/dashboard?id=deliveriesEnginesonarcloudkey_BioDrop)
* [BusinessInitiative static analysis](https://sonarcloud.io/dashboard?id=pedrod33_BioDrop)

## CI/CD environment
we developed a CI pipeline running in all branches. The CI workflow run unit tests and do Sonarcloud verify. An integration workflow is also part of our CI pipeline, all pull requests for the Main and Develop branches must run integration tests.  
For CD we also created actions. when front-end is updated at branch main one [action](https://github.com/pedrod33/BioDrop/blob/develop/.github/workflows/deploy.frontend.yml) uses one runner to build react app, and serve website in VM using XGINX. When the backend is updated at branch main one [action](https://github.com/pedrod33/BioDrop/blob/develop/.github/workflows/main.yml) uses one runner to build and up backend docker container with updated code.

# INFRA
## Docker mysql container (deliver engine)
* $ docker run --name deliveries-mysql -p 330X:330X -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=deliveries-mysql -e MYSQL_USER=tqs -e MYSQL_PASSWORD=password -d mysql:5.6


## Spring boot container (deliver engine)
* $ ./mvnw package -DskipTests

* $ docker build -t deliveries-engine .

* $ docker run -p 8089:8089 --name deliveries-engine --link deliveries-mysql:mysql deliveries-engine


# -----------------


## Docker mysql container (business initiative)
* $ docker run --name business-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=business-mysql -e MYSQL_USER=tqs -e MYSQL_PASSWORD=password -d mysql:5.6


# Spring boot container (business initiative)
* $ ./mvnw package -DskipTests

* $ docker build -t business-initiative .

* $ docker run -p 8090:8090 --name business-initiative --link business-mysql:mysql business-initiative



PS: Ter atencao o endereco, se for para correr o spring project sem docker no app-properties mudar para local host, se for para correr em container mudar para o nome do container
	e as portas dos containers mysql
