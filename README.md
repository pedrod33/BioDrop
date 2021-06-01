# TQS_Final


# Docker mysql container (deliver engine)
* $ docker run --name deliveries-mysql -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=deliveries-mysql -e MYSQL_USER=tqs -e MYSQL_PASSWORD=password -d mysql:5.6


# Spring boot container (deliver engine)
* $ ./mvnw package -DskipTests

* $ docker build -t deliveries-engine .

* $ docker run -p 8089:8089 --name deliveries-engine --link deliveries-mysql:mysql deliveries-engine


# -----------------


# Docker mysql container (business initiative)
* $ docker run --name business-mysql -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=business-mysql -e MYSQL_USER=tqs -e MYSQL_PASSWORD=password -d mysql:5.6


# Spring boot container (business initiative)
* $ ./mvnw package -DskipTests

* $ docker build -t business-initiative .

* $ docker run -p 8090:8090 --name business-initiative --link business-mysql:mysql business-initiative
