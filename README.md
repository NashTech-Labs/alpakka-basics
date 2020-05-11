#alpakka-basics

In this I have demonstrated how to use Alpakka connectors. It shows how to use Alpakka-Kafka 
and Alpakka Elasticsearch as well.

Steps to follow to run this : 
  1. Clone the repo : 
  
  `git clone https://github.com/knoldus/alpakka-basics.git`
  2. Run Kafka and create 3 topics named 
  
  `topic-person` `topic-input` `topic-output`
  3. Run elasticsearch-7.6.2
  4. Go to main directory, type `sbt` to go to sbt console.
  5. Run following commands one after another :
   
   `clean` `compile` `run`
  6. Now you can see there are 2 main class
     - if you want to see how data is transferred from  one kafka topic to another, 
     select KafkaToKafka and start a producer for topic-input and a consumer for topic-output.
     - if you want to see how data is transferred from kafka topic to elasticsearch ,
     select KafkaToEs and start a producer for topic topic-person. The data sent will be in form of raw json.
     For example : {"id":"9","name":"Munisha","city":"Chennai"}
  
