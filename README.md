# demo_kafka
e_banking transactions publish/query method using kafka
## install software required for running this project  
- Java17  
- intellij idea, version 2023.1.2  
- kafka, version 2.12-3.4.0  
- postman or any other api test platform  
## run the project on Windows 10  
1. open zookeeper.properties file in kafka's config folder and add two lines  
audit.enable=true  
admin.enableServer=true  
2. back to the upper level directory of bin folder and open two cmd window  
3. for one cmd window, use command ".\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties"  
4. for another cmd window, use command ".\bin\windows\kafka-server-start.bat .\config\server.properties"  
5. open intellij idea and demo_kafka project, run the project  
6. open api test platform, publish transactions by POST request and query transactions by GET request  
7. an example of the POST request format:  
POST http://localhost:9000/kafka/publish  
{
    "username":"jerry1",
    "unique_id":"69d3o179-abcd-465b-o9ee-e2d5f6ofEld46",
    "amount":500,
    "currency":"CHN",
    "iban":"CH03-0000-0000-0000-0000-0",
    "date":"2000-10-01",
    "description":"Online payment CHN"
}  
8. an example of the GET request format:  
GET http://localhost:9000/kafka/query  
{
    "username":"tom1"
}  
## validation  
everytime the producers send transaction and the consumers receive transaction, the project will print the information of transaction in log  
log will show on intellij idea terminal when the project is running  
kafka consumers will query the transactions via username  
thus, we can use POST request to publish multiple transactions with different username. then send query request with one specific username  
if the log only show those transactions with this specific username, then demo_kafka project is valid
