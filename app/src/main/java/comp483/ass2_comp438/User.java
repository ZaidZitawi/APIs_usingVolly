package comp483.ass2_comp438;

import android.os.Parcelable;

public class User {
    private int id;
    private String name;
    private String Email;
    private String pass;

    public User(int id, String name, String email, String pass) {
        this.id = id;
        this.name = name;
        Email = email;
        this.pass = pass;
    }

    public User(String name, String email, String pass) {
        this.name = name;
        Email = email;
        this.pass = pass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", Email='" + Email + '\'' +
                ", pass='" + pass + '\'' +
                '}';
    }
}
