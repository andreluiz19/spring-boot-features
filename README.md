## Required

-   Java 17
-   Maven

## MySQL Docker Container

-   docker pull mysql

-   docker run -d --name mysql-server -p 3306:3306 -e "MYSQL_ROOT_PASSWORD=root" mysql mysqld --lower_case_table_names=1

## Maven Build JAR

-   mvn clean package -P dev on ../spring-security

## Run Project JAR

-   java -jar vendas-1.0-SNAPSHOT.jar on ../

## Maven Build WAR

-   mvn clean package -P prd on ../spring-security
