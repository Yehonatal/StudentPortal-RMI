package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import server.objects.Admin;
import server.objects.Course;
import server.objects.Student;

public class DbStudentPortalImpl extends UnicastRemoteObject implements DbStudentPortal {
    public Connection conn = null;
    public Statement statement = null;
    public PreparedStatement prepStatement = null;


    protected DbStudentPortalImpl() throws RemoteException {
        super();
    }

    // Constant queries 


    
    @Override
    public List<Admin> retrieveAdmins() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrieveAdmins'");
    }
    @Override
    public List<Student> retrieveStudents() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrieveStudents'");
    }
    @Override
    public List<Course> retrieveCourses() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'retrieveCourses'");
    }
    @Override
    public void createAdmin(Admin admin) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createAdmin'");
    }
    @Override
    public void createStudent(Student student) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createStudent'");
    }
    @Override
    public void createCourse(Course course) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createCourse'");
    }
    @Override
    public void delAdmin(int adminId) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delAdmin'");
    }
    @Override
    public void delStudent(int studentId) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delStudent'");
    }
    @Override
    public void delCourse(String courseId) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delCourse'");
    }
    @Override
    public void updateAdmin(Admin admin) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateAdmin'");
    }
    @Override
    public void updateStudent(Student student) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateStudent'");
    }
    @Override
    public void updateCourse(Course course) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateCourse'");
    }

}
