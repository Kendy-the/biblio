package com.biblio.models;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import com.biblio.config.BApp;
import com.biblio.repository.UserRepository;

public class User extends Model{
    
    private static User connected;

    private int id;
    private String firstName, lastName, email, phone, adresse, role, password;

    public User(){}

    public User(String firstName, String lastName, String email, String phone, String adresse, String role, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.adresse = adresse;
        this.role = role;
        this.password = password;
    }

    public User(int id, String firstName, String lastName, String email, String phone, String adresse, String role, String password){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.adresse = adresse;
        this.role = role;
        this.password = password;
        this.id = id;
    }

    public User(Map<String, String> data){

        this.email = data.get("email");
        this.role = data.get("role");
        this.password = data.get("password");
    }

    public User(Map<String, String> data, boolean edit){

        this.firstName = data.get("firstName");
        this.lastName = data.get("lastName");
        this.phone = data.get("phone");
        this.email = data.get("email");
        this.adresse = data.get("adresse");
        this.role = data.get("role");
        this.password = data.get("password");
    }

    public Vector<?> toVector(){

        Vector<String> data = new Vector<>();

        data.add(String.valueOf(this.getId()));
        data.add(this.getFirstName() + " " + this.getLastName());
        data.add(this.getEmail());
        data.add(this.getRole());

        return data;
    }

    public ArrayList<String> toArray(){

        ArrayList<String> data = new ArrayList<>();

        if (this.getId() > 0) {
            data.add(String.valueOf(this.getId()));
        }
        data.add(this.getFirstName() + " " + this.getLastName());
        data.add(this.getEmail());
        data.add(this.getRole());

        return data;
    }

    public User login(String email, String password){
        this.email = email;
        this.password = password;

        return new UserRepository(this).login();
    }

    public User getById(int id){
        this.id = id;
        return new UserRepository(this).getById();
    }

    public User getByEmail(String email){
        this.email = email;
        return new UserRepository(this).getByEmail();
    }
    
    public ArrayList<User> index(){
        return new UserRepository(this).index();
    }

    public int save(){
        return new UserRepository(this).save();
    }

    public boolean edit(int id){
        this.id = id;
        return new UserRepository(this).edit();
    }

    public static User getConnected(){return connected;}
    public static void setConnected(User user){connected = user;}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public String getRole() {return role;}
    public void setRole(String role) {this.role = role;}

    public String getStringRole(){
        return this.role.equals("0") 
        ? BApp.USER_USER_ROLE.get() : BApp.USER_ADMIN_ROLE.get();
    }

    public String getAdresse() {return adresse;}
    public void setAdresse(String adresse) {this.adresse = adresse;}

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}
}
