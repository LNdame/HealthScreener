package ansteph.cct.healthscreener.entity;

/**
 * Created by loicStephan on 09/06/15.
 */
public class Nurse {


    private String id;
    private String firstname;
    private String surname;
    private String username;



    private String nu_password;
    private String email;

    public Nurse(){

    }

    public Nurse(String firstname, String surname) {
        this.firstname = firstname;
        this.surname = surname;
    }

    public Nurse(String firstname, String surname, String username, String nu_password, String email) {
        this.firstname = firstname;
        this.surname = surname;
        this.username = username;
        this.nu_password = nu_password;
        this.email = email;
    }




    public Nurse(String id, String firstname, String surname, String username, String nu_password, String email) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.username = username;
        this.nu_password = nu_password;
        this.email = email;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNu_password() {
        return nu_password;
    }

    public void setNu_password(String nu_password) {
        this.nu_password = nu_password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
