# SpringBootMicroserviceProject
SpringBootMicroserviceProject is developed using SpringBoot, API gateway,Eureka Server, Kafka,Kafka-UI, Zipkin, Grafana,Micrometer,prometheus,Docker, keyCloak(for access token) etc..
Note: Keycloak is configured on local machine: To access any API endpoint you will have to setup keycloak and update the issuer-url in api-gateway service's application.properties file. Also setup postman with OAuth2.0
Use **docker compose up -d** to create the containers in docker through the docker image present in the docker-compose.yml file
