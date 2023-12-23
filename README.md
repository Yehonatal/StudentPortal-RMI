## Student Portal - RMI Desktop Application

## Folder Structure

    RMI_STUDENT PORTAL/
    |-- src/
    |   |-- server/
    |   |   |-- DbStudentPortalImpl.java
    |   |   |-- DbStudentPortal.java
    |   |   |-- DbConnector.java
    |   |   |-- DbAccess.java
    |   |   |-- RMIServer.java
    |   |   |
    |   |   |-- object/
    |   |       |-- Admin.java
    |   |       |-- Course.java
    |   |       |-- Student.java
    |   |
    |   |-- client/
    |   |   |-- RMIClient.java
    |   |   |-- App.java
    |   |   |
    |   |   |-- GUI/
    |   |   |   |-- admin/
    |   |   |   |   |-- AdminDashboard.java       // Admin main dashboard
    |   |   |   |
    |   |   |   |-- student/
    |   |   |   |   |-- StudentDashboard.java     // Student main dashboard
    |   |   |   |
    |   |   |   |-- LoginPanel.java
    |   |   |   |-- CreateAccountPanel.java
    |   |
    |   |-- .env
    |
    |-- build/
    |   |-- (compiled .class files)
    |
    |-- README.md

## Dependency Management

- [mysql connector to java]() jar
  - protobuf java jar
- [dotenv](https://jar-download.com/artifact-search/java-dotenv) jar , to get the env variables into the program
  - annotations jar
  - kotlin stdlib jar
  - kotlin stdlib common jar

Just copy thus jar files into the lib folder and your all set 😊
