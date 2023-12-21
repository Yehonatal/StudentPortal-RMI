package client.service.impl;

import client.service.AdminService;

public class AdminServiceImpl implements AdminService {

    @Override
    public void manageCourses() {
        // Logic to manage courses (add, edit, remove)
        System.out.println("Managing courses...");
    }

    @Override
    public void manageStudentAccounts() {
        // Logic to manage student accounts (view, add, edit, remove)
        System.out.println("Managing student accounts...");
    }

    @Override
    public void generateReports() {
        // Logic to generate reports (e.g., list of enrolled students)
        System.out.println("Generating reports...");
    }
}
