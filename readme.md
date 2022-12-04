Spring Boot Application

Setup:
=========
1. Run -> mvn clean install
2. Run -> java -jar {FOLDER_LOCATION}/target/api-0.0.1-SNAPSHOT.jar

Testing:
=======
To Send a list of transactions for processing, send a POST request with JSON body as below

localhost:8080/process

Sample Body:
[
"John,Doe,john@doe.com,190,TR0001",
"John,Doe1,john@doe1.com,200,TR0001",
"John,Doe2,john@doe2.com,201,TR0003",
"John,Doe,john@doe.com,9,TR0004",
"John,Doe,john@doe.com,2,TR0005"
]

Sample Curl request:

curl --location --request POST 'localhost:8080/process' \
--header 'Content-Type: application/json' \
--data-raw '[
"John,Doe,john@doe.com,190,TR0001",
"John,Doe1,john@doe1.com,200,TR0001",
"John,Doe2,john@doe2.com,201,TR0003",
"John,Doe,john@doe.com,9,TR0004",
"John,Doe,john@doe.com,2,TR0005"
]'


Dockerize the Application
=========================
1. Run -> docker build --tag=api-server:latest .
2. Run -> docker run -it -p 8080:8080 api-server

Note :
To export the docker image, run command -> docker save -o app.tar  api-server
To load the docker image, run command -> docker load app.tar


### Assumptions

1. Initial balance that each user has in their account is 200.
2. Every time a new request comes in, transactions are processed assuming for each unique user in given list of transactions, the initial balance is 200.
3. Transactions need to be in an isolated and transactional environment.
4. For ease of testing, transactions are cleared after every request run. Can change it to not clear the concurrent hashmap 
5. Flow of transactions need to be maintained for accurate result.
6. If a transaction is rejected for a user due to low balance, the next transaction that meets the requirements for that user will be successfully processed.
7. The Response should contain all the rejected transactions at once and not stream the rejected transactions one by one, which seems to be a blocking design.

### Challenges

1. Implementation with a reactive database driver includes lazy processing of result of the query after it is fetched asynchronously, but maintaining the flow of control of transactions is a major challenge.
2. While processing of a given transaction in the list and fetching the user balance, due to reactive nature of webflux, the thread picks up the next transaction due to which logging and assuming the flow control is difficult.

### Improvements

1. Given more time, I could look into frameworks and documentation into how to maintain the serial of streaming data and process them in the same order as input.
2. Given that R2DBC is in very initial phase, I would learn more on how to query and process data using reactive repository.