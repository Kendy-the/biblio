package com.biblio.models;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import com.biblio.repository.AdherentRepository;

public class Adherent extends Model{

    private int id, userId;
    private String firstName, lastName, phone, photo;
    
    public Adherent(){}

    public Adherent(int id, String firstName, String lastName, String phone, String photo){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.photo = photo;
        this.userId = User.getConnected().getId();
    }

    public Adherent(Map<String, String> data){

        this.firstName = data.get("firstName");
        this.lastName = data.get("lastName");
        this.phone = data.get("phone");
        this.photo = data.get("photo");
        this.userId = User.getConnected().getId();
    }

    public Vector<?> toVector(){

        Vector<String> data = new Vector<>();

        data.add(String.valueOf(this.getId()));
        data.add(this.getFirstName());
        data.add(this.getLastName());
        data.add(this.getPhone());
        data.add(this.getPhoto());

        return data;
    }

    public ArrayList<String> toArray(){

        ArrayList<String> data = new ArrayList<>();

        if (this.getId() > 0) {
            data.add(String.valueOf(this.getId()));
        }
        data.add(this.getFirstName());
        data.add(this.getLastName());
        data.add(this.getPhone());
        data.add(this.getPhoto());

        return data;
    }
    
    public Adherent getById(int id){
        this.id = id;
        return new AdherentRepository(this).getById();
    }
    
    public ArrayList<Adherent> index(){
        return new AdherentRepository(this).index();
    }

    public int save(){
        return new AdherentRepository(this).save();
    }

    public boolean edit(int id){
        this.id = id;
        return new AdherentRepository(this).edit();
    }

    public int getId(){ return this.id; }
    public void setId(int id){this.id = id;}

    public String getLastName(){return this.lastName;}

    public String getPhoto() { return photo; }
    public void setPhoto(String photo) { this.photo = photo; }

    public String getPhone() {return phone;}
    public void setPhone(String phone) {this.phone = phone;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public int getUserId() { return userId;}
    public void setUserId(int userId) {this.userId = userId;}

}
