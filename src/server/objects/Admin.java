package server.objects;

public class Admin {
    public int adminId;
    public String firstName;
    public String lastName;
    public String email;
    public String password;

    public Admin() {

    }
    // Used for retrieving the Student's information
    public Admin(String firstName, String lastName, String email, int adminId) {
        this.adminId = adminId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    // Used for creating a admin
    public Admin(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    // Used for Updating a Admin
    public Admin(String firstName, String lastName, String email, String password, int adminId) {
        this.adminId = adminId; 
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }


    public int getAdminId() {
        return adminId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
