package client.service.impl;

import client.service.StudentService;

public class StudentServiceImpl implements StudentService {

    @Override
    public void viewCourseInformation() {
        // Logic to retrieve and display course information for the student
        System.out.println("Viewing course information for the student...");
    }

    @Override
    public void enrollInCourse() {
        // Logic to allow the student to enroll in a course
        System.out.println("Enrolling in a course...");
    }

    @Override
    public void viewPersonalInformation() {
        // Logic to retrieve and display personal information for the student
        System.out.println("Viewing personal information for the student...");
    }
}
