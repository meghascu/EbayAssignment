## EligibitlitySvc

Created a REST API using Spring Boot and Swagger Codegen for service specification.
Currently, two services are exposed:
- To find the eligibility of an item for the new shipping program.
- To update the existing rules which include:
    - Adding /removing Item category from list pre-approved categories.
    - Adding/removing seller name from a list of enrolled sellers.
    - Updating minimum allowed price of an item.

### Installation
#### Maven install
- sudo apt policy maven
- sudo apt install maven

#### Application installation with maven plugin
- mvn -N io.takari:maven:wrapper

#### Finally
Can run application from command line: ./mvnw spring-boot:run

#### Links of service URL:
- https://www.getpostman.com/collections/d1f9409548f1ad18c486
