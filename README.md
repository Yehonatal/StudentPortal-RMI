## Student Portal - RMI Desktop Application

## Folder Structure

    RMI_Calculator/
    |-- src/
    | |-- server/
    | | |-- DbStudentPortalImpl.java // Remote interface implementation
    | | |-- DbStudentPortal.java // Remote interface
    | | |-- DbConnector.java // Db connector
    | | |-- RMIServer.java // RMI server
    | | |
    | | |-- object/
    | | | |-- Admin.java
    | | | |-- Course.java
    | | | |-- Student.java
    |
    | |-- client/
    | | |-- RMIClient.java // RMI client
    | | |-- GUI/
    | | | |-- admin /
    | | | | |-- adminPanel.java
    | | | |
    | | | |-- student /
    | | | | |-- studentPanel.java
    | | |
    | | |-- LoginPanel.java
    | | |-- CreateAccount.java
    |
    | |-- .env
    |
    |-- build/
    | |-- (compiled .class files)
    |
    |-- README.md

## Dependency Management

-   [mysql connector to java]() jar
    -   protobuf java jar
-   [dotenv](https://jar-download.com/artifact-search/java-dotenv) jar , to get the env variables into the program
    -   annotations jar
    -   kotlin stdlib jar
    -   kotlin stdlib common jar

Just copy thus jar files into the lib folder and your all set 😊
