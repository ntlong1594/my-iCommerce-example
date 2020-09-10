# my-iCommerce-example
This icommerce project is my personal project I'd like to challenge myself and built it within 3 days. ICommerce was built following microservice pattern which is using jhipster as backbone structure. 

# Technical using
- Microservices, Angular 8 , Jhipster, Keycloak, Liquibase, Docker, Zookeeper, Kafka, postgresql, mongodb
- Spring Cloud
- Java version 1.8
- Angular CLI: 8.3.8
- Node: 10.20.1
- Nvm: 6.14.4
- Spring Boot: 2.2.7

# Pre condition before starting 
1. Please ensure the below ports are available in your local machine:
- jhipster-registry (8761)
- kafka (9092)
- zookeeper (2181)
- keycloak (9080)
- gateway (8080)
- mongodb (27017)
- postgresql (5432)
- product (8082)
- historical (8083)

2. Ensure you already installed Docker Desktop ( most of the services are running in docker)
3. Edit `hosts` file by adding this line `127.0.0.1 keycloak`. 
   - For macOs hosts file is located at `/private/etc`
   - For windows hosts file is located at `C:\Windows\System32\drivers\etc`

# Overview about gateway service
 - An important service in microservice to centralize and dispatch request. This is built via jhipster cli and I customized a bit to support social login (Facebook),
 - You can access to icommerce system by access : http://localhost:8080 you can choose login with your social Facebook account or with 2 created account:`admin/admin` or `user/user`. The login mechanism is working with `Keycloak server` which is running silenly in docker (you can access keycloak via this url: http://localhost:9080)
 - If you login as `admin`, you are able to use SWAGGER UI , this is my recommendation if you want to discover how icommerce works vìa API.

# Overview about historical service
 - The place to keep track user's activies, when ever user interact with our system.
 - There is a kafka consumer that listen on the topic and consume the message from `product service` then save it to MongoDB.
 - Only 1 endpoint to retrieve all user activities and 1 endpoint to retrieve product update changelog.

# Overview about product service
 - The place to keep track products list, shopping cart and Order entity information when user make a payment
 - At the very begining, I already prepared 100 product records with liquibase for testing.
 - Any action of user in this product service will be publish to kafka event for tracking.
 - Users are able to search product by brand , title, and range of price.
 - Users are able to add their prefer product to their own shopping cart - Shopping cart is built in memory. which no interact with Database
 
# ERD Diagram
![ERD-Diagram](https://github.com/ntlong1594/my-iCommerce-example/blob/master/imgs/ERD.png)

# Component Architecture
![Component-Architecture](https://github.com/ntlong1594/my-iCommerce-example/blob/master/imgs/Component-Architecture.png)

# Key feartures
- Login with Facebook
- CRUD Products ( searching by price , brand , and title)
- Add Product to shopping cart
- Checkout a shopping cart ( make an payment)
- Keep track user activites
- Keep track product price update version for auditing in the future.

# How to start.
- Start the environments services (without these services , icommerce is not able to run): Go to `environments` folder and run the shell script `start-mac-os.sh` or `start-windows-os.sh` depends on your operating system ( I develop this app in macOs) => By running the above script, we list of required services will run in the background inside docker.
- Please wait for few seconds before moving to next step. ( Your docker need time to startup the required services :) ).
- Now we need to start 3 more services on our local (these 3 services is contains logic and implementation, therefore starting local could help us monitor and debug easily for understanding), they are: `gateway`, , `historical`, `product`
- `gateway` , go to `gateway` folder and run `npm run webpack:build` , when it finished , then run `./mvnw` for mac and for windows is `mvnw`
- `historical`, go to `historical` then run `./mvnw` for mac and for windows is `mvnw`
- `product`, go to `product` then run `./mvnw` for mac and for windows is `mvnw`

If your console inform that the `gateway` is started successfully, now you can access the system by going to: `http://localhost:8080/` , and the below is landing page
![Landing-Page](https://github.com/ntlong1594/my-iCommerce-example/blob/master/imgs/landing-page.png)

You can access to the swagger of system to play arround with APIS:
![Swagger-Page1](https://github.com/ntlong1594/my-iCommerce-example/blob/master/imgs/swagger-api-1.png)
![Swagger-history](https://github.com/ntlong1594/my-iCommerce-example/blob/master/imgs/swagger-history.png)
![Swagger-product](https://github.com/ntlong1594/my-iCommerce-example/blob/master/imgs/swagger-product.png)

# What could be improve in the future
- Searching engine: If I get extra time, I will use ElasticSearch for product searching instead of using mongodb
- Enhance in memory shopping cart, currently shopping cart is using ConcurrentMap and running in memory , This should be much better if I could use Redis cache instead.
- Write some additional test, due to time limitation, there are a lot of missing test. 
- Data Structure and Data type, currently Data structure and Data type of each entities model is just quick design , and could not adapt for real world ecommerce system. If I have more time, I could design much better data structure which can store many type of product.
- Bugs , of course , any system has its own bugs, and maybe some bugs and some issue I could not aware to avoid it in the short time challenge.