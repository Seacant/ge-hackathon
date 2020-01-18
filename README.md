Instructions

---

Installation

  1. Install Maven dependencies
    ```
    mvn install -D skipTests
    ```
  2. Configure database
    ```
    cp src/main/resources/application.properties.default src/main/resources/application.properties
    ```

  3. Then, add the user/pass supplied through email to the new application.properties file

Running

    ```
    java -jar target/nku-0.0.1-SNAPSHOT.jar
    ```