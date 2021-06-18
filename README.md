# TQS_Final


# Docker mysql container (deliver engine)
* $ docker run --name deliveries-mysql -p 330X:330X -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=deliveries-mysql -e MYSQL_USER=tqs -e MYSQL_PASSWORD=password -d mysql:5.6


# Spring boot container (deliver engine)
* $ ./mvnw package -DskipTests

* $ docker build -t deliveries-engine .

* $ docker run -p 8089:8089 --name deliveries-engine --link deliveries-mysql:mysql deliveries-engine


# -----------------


# Docker mysql container (business initiative)
* $ docker run --name business-mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=business-mysql -e MYSQL_USER=tqs -e MYSQL_PASSWORD=password -d mysql:5.6


# Spring boot container (business initiative)
* $ ./mvnw package -DskipTests

* $ docker build -t business-initiative .

* $ docker run -p 8090:8090 --name business-initiative --link business-mysql:mysql business-initiative



PS: Ter atencao o endereco, se for para correr o spring project sem docker no app-properties mudar para local host, se for para correr em container mudar para o nome do container
	e as portas dos containers mysql
