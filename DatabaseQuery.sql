-- AdminLog Table
CREATE TABLE AdminLog (
    adminId INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    email VARCHAR(100),
    password VARCHAR(100)
);

-- StudentLog Table
CREATE TABLE StudentLog (
    studentId INT PRIMARY KEY AUTO_INCREMENT,
    firstName VARCHAR(50),
    lastName VARCHAR(50),
    email VARCHAR(100),
    password VARCHAR(100)
);

-- CoursesLog Table
CREATE TABLE CoursesLog (
    courseId INT PRIMARY KEY AUTO_INCREMENT,
    courseTitle VARCHAR(100),
    courseCode VARCHAR(50),
    courseCredit INT
);

-- EnrollmentsLog Table
CREATE TABLE EnrollmentsLog (
    StudentId INT,
    CourseId INT,
    Grade VARCHAR(50),
    FOREIGN KEY (StudentId) REFERENCES StudentLog(studentId),
    FOREIGN KEY (CourseId) REFERENCES CoursesLog(courseId)
);

-- Dummy Data for AdminLog
INSERT INTO AdminLog (firstName, lastName, email, password)
VALUES
    ('John', 'Doe', 'john.doe@example.com', 'admin123'),
    ('Jane', 'Smith', 'jane.smith@example.com', 'admin456');

-- Dummy Data for StudentLog
INSERT INTO StudentLog (firstName, lastName, email, password)
VALUES
    ('Alice', 'Johnson', 'alice.johnson@example.com', 'student123'),
    ('Bob', 'Williams', 'bob.williams@example.com', 'student456');

-- Dummy Data for CoursesLog
INSERT INTO CoursesLog (courseTitle, courseCode, courseCredit)
VALUES
    ('Introduction to Programming', 'CS101', 3),
    ('Database Management', 'CS202', 4);

-- Dummy Data for EnrollmentsLog
INSERT INTO EnrollmentsLog (StudentId, CourseId, Grade)
VALUES
    (1, 1, 'A'),
    (1, 2, 'B'),
    (2, 1, 'A-');
