# Bayes Java Dota Challlenge

This is the [task](TASK.md).

Any additional information about your solution goes here.


## Build the Application
To build the project, run the following command in the project root directory:
```shell
mvn clean package
```
This will compile the code, run tests, and package the application into a JAR file in the `target/` directory.

## Run the Application
To run the project, use the following command:
```shell
java -jar target/dota-challenge-1.0.0-SNAPSHOT.jar
```
This will start the application and you can access it at `http://localhost:8080`.


## Solution thought process
* Parsed of events using pattern matcher to reduce the complexity.
* Tried to align with `SOLID` principals for better code quality:
  * Used `LogProcessor` that have only one responsibility for process the given logs
  * Treat each event with separate event type that enhance the future code extensibility
  * Used Factory Design Pattern to build relevant `LogLineProcessor`
  * Used Singleton Design Pattern for better memory utilization
* Used `Mapstruct` to facilitate easy mapping


## Further Improvements
* Tables can be normalized to improve the data integrity and performance.
* Adding exception handling.
* Enhance test coverage.
* Add `Spring Boot Actuator` for health check and monitoring.
* Integrate with `Docker` to run the application in a container.

