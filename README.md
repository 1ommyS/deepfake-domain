# Getting Started

## Deployment
1. Clone this repository.
```
git clone https://gitlab.mai.ru/deepfake/deepfake-domain.git
```
2. Go to the project directory
```
cd deepfake-domain
```
3. Run Docker
4. Run Kafka and Kookeper
```
docker-compose build 
docker-compose up -d

To stop kafka and docker use 

docker-compose down
```
5. Run the application
```
docker build -t myapp .
docker run -p 8090:8090 myapp
```
6. Open the page in your browser [Page](http://localhost:8090)
7. If you have already used port 8090 than change it:
```
src/main/resources/application.yml
server:
    port: <your_port>
```
## Built With
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [Spring Security](https://spring.io/projects/spring-security)
* [Spring Kafka](https://docs.spring.io/spring-kafka/reference/html/)
* [Lombok](https://projectlombok.org/)
* [ModelMapper](https://modelmapper.org/)

## Authors
* **Ivan Berezutskiy** - [VK](https://vk.com/1ommy)
* **Zaytsevagen** - [Gitlab](https://gitlab.mai.ru/deepfake)

