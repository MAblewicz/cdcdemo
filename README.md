# confluent-cdc-local

This is a project containing result of Research about Kafka and CDC usage.
Following folder contains:
- docker- docker compose file for local kafka confluent platform, mongodb and mssql databases
- connectors - property files for two connectors, source for mssql and sink fo mongodb
- cdcreader - kafka consumer demo project. It processes product sending them to websockets

## Starting a confluent platform
File **docker-compose** from "confluent" folder contains definition for following docker containers:
- zookeeper - service responsible for naming ,configuration and synchronization withing distributed systems
- rest-proxy - REST interface for Apache Kafka
- broker - Kafka broker
- connect - Confluent centralized hub responsible for data integration between systems throu connectors
- control-center - UI for confluent platform
- schema-registry - JSON/Protobuf/Avro schema registry
- ksqldb-server - purpose-build database used by developer to create stream processing applications
- ksqldb-cli - CLI for ksqldb
- ksql-datagen - some exmaples for ksqldb

Please note that **ksqldb-server**,  **ksqldb-cli**, **ksql-datagen** are commented out in docker-compose. To use them if needed just uncomment their configuration.
To start the  confluent platform use following steps
1. Use command "docker-compose up" from "docker" folder. It should build and start all required docker containers.
   (First run could take even around 20 mins, ignore errors for now)
2. Check status by executing "docker container ls -a" and check which container fails to start. In case at least one of them is down follow next steps in pt2.
2a. If needed execute "docker start broker" (wait till it fully starts , around 3-5 mins, If not down restart it)
2b. If needed execute "docker start schema-registry" (wait till it fully starts , around 3-5 mins, If not down restart it)
2c. If needed execute "docker start connect" (this container has much longer start time like 10 mins, If not down restart it)
2d. If needed execute "docker start control-center" (If not down restart it))
3. Wait till everything finish starting
4. Go to http://localhost:9021/ in browser
5. Check if you got 1 healthy cluster
6. Go to that cluster by clicking on it
7. Click connect link on the left side of the screen
8. Check if you got connect-default link in the middle of screen and click it
9. There should be no connectors
10. Click add connector (there should be only 4 on list and no MONGO or MSSQL related ones) and add test one with fake values

## Adding and using MSSQL Source connector
To add MSSQL Source connector use the following steps
1. MSSQL source database should be already started as part of docker-compose for confluent platform
2. Use command "docker exec -it connect /bin/bash" to login into "connect" container
3. In docker container bash execute "confluent-hub install debezium/debezium-connector-sqlserver:1.9.7"
4. Select "2" ((where this tool is installed)
5. Confirm location with "y"
6. Confirm agreement "y"
7. Confirm installation with "y" (You are about to install...)
8. Confirm to  update all detected configs  "y"
9. Check if following information is visible on screen:
    Adding installation directory to plugin path in the following files:
    /etc/kafka/connect-distributed.properties
    /etc/kafka/connect-standalone.properties
    /etc/schema-registry/connect-avro-distributed.properties
    /etc/schema-registry/connect-avro-standalone.properties
    /etc/kafka-connect/kafka-connect.properties
    Completed
10. Restart broker (wait till it starts)
11. Restart connect (wait till it starts)
12. Restart control-center (wait till it starts)
13. Go to localhost:9021 and add new connector. One called **SqlServerConnectorConnector** should be available now.
14. Add new connector using "Upload connector config file" button
15. Select one of the input connectors from "connectors" folder (recommended one is "connector_MSSQLSourceProductJSONConnector_config.properties")
16. Confirm with Next and wait till connector is up and running
17. Using any DB tool connect to MSSQL input database using following properites
    -  db URL: jdbc:sqlserver://localhost:1433;database=demodb
    -  db user: DemoAdmin
    -  db password: Password123!
18. Add new record to "cdcdemo.product" table
19. Go to http://localhost:9021/ in browser
20. Select "Topics" link from left side of the screen
21. Check if topic related to configured connector is there and if it contains added record from pt17.

## Adding and using MSSQL Sink connector 
To add MSSQL Sink connector use the following steps
1. First uncomment mssql output db section from docker-compose file from "docker" folder. It should build and start all required docker containers.
2. Use command "docker exec -it connect /bin/bash" to login into "connect" c
3. In docker container bash execute "confluent-hub install confluentinc/kafka-connect-jdbc:latest"
4. Select "2" ((where this tool is installed)
5. Confirm location with "y"
6. Confirm agreement "y"
7. Confirm installation with "y" (You are about to install...)
8. Confirm to  update all detected configs  "y"
9. Check if following information is visible on screen:
   Adding installation directory to plugin path in the following files:
   /etc/kafka/connect-distributed.properties
   /etc/kafka/connect-standalone.properties
   /etc/schema-registry/connect-avro-distributed.properties
   /etc/schema-registry/connect-avro-standalone.properties
   /etc/kafka-connect/kafka-connect.properties
   Completed
10. Restart broker (wait till it starts)
11. Restart connect (wait till it starts)
12. Restart control-center (wait till it starts)
13. Go to localhost:9021 and add new connector. One called **JdbcSinkConnector** should be available now.
14. Add new connector using "Upload connector config file" button
15. Select one of the out connectors from "connectors" folder (recommended one is "Output MSSQL connector config.properties")
16. Confirm with Next and wait till connector is up and running
17. Using any DB tool connect to MSSQL input database using following properites
    -  db URL: jdbc:sqlserver://localhost:1433;database=demodb
    -  db user: DemoAdmin
    -  db password: Password123!
18. Check if table "cdcdemo.product" was created automatically by connector
19. Verify that record added to "cdcdemo.product" table in Source connector is in newly created "cdcdemo.product" table in output DB

## Adding and using MongoDB Sinkconnector
To add ongoDB Source connector use the following steps
1. MongoDB source database should be already started as part of docker-compose for confluent platform
2. Use command "docker exec -it connect /bin/bash" to login into "connect" container
3. In docker container bash execute "confluent-hub install mongodb/kafka-connect-mongodb:1.10.0"
4. Select "2" ((where this tool is installed)
5. Confirm location with "y"
6. Confirm agreement "y"
7. Confirm installation with "y" (You are about to install...)
8. Confirm to  update all detected configs  "y"
9. Check if following information is visible on screen:
   Adding installation directory to plugin path in the following files:
   /etc/kafka/connect-distributed.properties
   /etc/kafka/connect-standalone.properties
   /etc/schema-registry/connect-avro-distributed.properties
   /etc/schema-registry/connect-avro-standalone.properties
   /etc/kafka-connect/kafka-connect.properties
   Completed
10. Restart broker (wait till it starts)
11. Restart connect (wait till it starts)
12. Restart control-center (wait till it starts)
13. Go to localhost:9021 and add new connector. One called **MongoSourceConnector** should be available now.
14. Add new connector using "Upload connector config file" button
15. Select one of the out connectors from "connectors" folder depending on what kind of JSON should be stored in topic
    - connector_MongoSinkProductConnector_config.properties - connector that will listen for new messages in topic json.demodb.cdcdemo.product (related to source MSSQL DB)
17. Using any DB tool connect to MSSQL input database using following properites
    -  db URL: jdbc:sqlserver://localhost:1433;database=demodb
    -  db user: DemoAdmin
    -  db password: Password123!
18. Add/update record to/in "cdcdemo.product" table
19. Check if collection "test.product" was created automatically by connector
20. Verify that record added to "cdcdemo.product" table in Source connector is in newly created "test.product" collection in MongoDB

## \cdcreader
\cdcreaderis a simple SpringBoot application that connects to topic storing information about products "json.demodb.cdcdemo.product"
and deserializes that information into "Product" java class and forward them to websocket.  It was created using Java 21 and Spring 6.0.
To run it follow those steps:
1. First setup proper CDC for MSSQLSource connector using connector config file 
2. Import Project into Idea
3. If needed adjust application.properties file (by default it is connecting to topic json.demodb.cdcdemo.product using localhost:9092 kafka broker)
4. If needed adjust port in application.properties (currently set to 8888)
5. Start the application
6. Use any websocket test tool (like http://jxy.me/websocket-debug-tool/) and connect to cdcreader websocket using
    - URL: ws://localhost:8888/cdc-demo-websocket
    - connection type: Stomp
    - subscribe to topic: /topic/products
7. Using any DB tool connect to MSSQL input database using following properites
    -  db URL: jdbc:sqlserver://localhost:1433;database=demodb
    -  db user: DemoAdmin
    -  db password: Password123!
8. Add/update record to/in "cdcdemo.product" table
9. Check in console if Product from topic json.demodb.cdcdemo.product are deserialized
10. Check if new product was forwarded to websocket
