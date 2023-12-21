## Student Portal - RMI Desktop Application

## Folder Structure

    RMI_STUDENT PORTAL/
    |-- src/
    |   |-- server/
    |   |   |-- DbStudentPortalImpl.java
    |   |   |-- DbStudentPortal.java
    |   |   |-- DbConnector.java
    |   |   |-- RMIServer.java
    |   |   |
    |   |   |-- object/
    |   |       |-- Admin.java
    |   |       |-- Course.java
    |   |       |-- Student.java
    |   |
    |   |-- client/
    |   |   |-- RMIClient.java
    |   |   |-- GUI/
    |   |   |   |-- admin/
    |   |   |   |   |-- AdminPanel.java       // Admin functionality GUI
    |   |   |   |
    |   |   |   |-- student/
    |   |   |   |   |-- StudentPanel.java     // Student functionality GUI
    |   |   |   |
    |   |   |   |-- LoginPanel.java
    |   |   |   |-- CreateAccount.java
    |   |   |
    |   |   |-- service/
    |   |       |-- StudentService.java       // Interface for student-related functionalities
    |   |       |-- AdminService.java         // Interface for admin-related functionalities
    |   |       |
    |   |       |-- impl/
    |   |           |-- StudentServiceImpl.java // Implementation of StudentService
    |   |           |-- AdminServiceImpl.java   // Implementation of AdminService
    |   |
    |   |-- .env
    |
    |-- build/
    |   |-- (compiled .class files)
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
