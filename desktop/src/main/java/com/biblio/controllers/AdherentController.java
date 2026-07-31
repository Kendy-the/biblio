package com.biblio.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.biblio.errors.BErrorMgr;
import com.biblio.errors.BErrors;
import com.biblio.models.Adherent;
import com.biblio.repository.AdherentRepository;

public class AdherentController extends Controller{
    
    public AdherentController(){}

    public int save(Map<String, String> data){
        
        if(!isCorrectData(data)) 
            return 0;

        return new Adherent(data).save();
    }

    public boolean edit(Map<String, String> data, Adherent adherent){
        
        if(!isCorrectData(data)) 
            return false;

        return new Adherent(data).edit(adherent.getId());
    }

    public Adherent getById(int id){

        return new AdherentRepository(
            new Adherent(id, null, null, null,null)
        ).getById();
    }

    public static boolean delete(Adherent adherent){

        if(
            !isAdherentExists(adherent.getId())
        ) return false;

        return new AdherentRepository(adherent).delete();
    }

    public static boolean isCorrectData(Map<String, String> data){

        if (
            data.isEmpty() ||
            isEmptyField(data)
        ) {
            BErrorMgr.addError("data", BErrors.ERROR_EMPTY_FIELD.get());
            return false;
        }

        if(!isPhone("phone", data.get("phone"))){
            return false;  
        }

        return true;
    }

    public List<Adherent> search(String lastName,String firstName,String phone) {

        List<Adherent> result = new ArrayList<>();

        for (Adherent a : new Adherent().index()) {

            boolean matchLast =
                    lastName.isBlank()
                    || a.getLastName()
                        .toLowerCase()
                        .contains(lastName.toLowerCase());

            boolean matchFirst =
                    firstName.isBlank()
                    || a.getFirstName()
                        .toLowerCase()
                        .contains(firstName.toLowerCase());

            boolean matchPhone =
                    phone.isBlank()
                    || a.getPhone()
                        .contains(phone);

            if (matchLast && matchFirst && matchPhone) {
                result.add(a);
            }
        }

        return result;
    }

    public ArrayList<Adherent> index(){
        return new Adherent().index();
    }

    public boolean saveTable(){
        return true;
    }

    private static boolean isAdherentExists(int id){
        if (new Adherent().getById(id) != null) {
            return true;
        }
        return false;
    }

    private static boolean isEmptyField(Map<String, String> data){
        
        if (
            data.get("firstName").isBlank() ||
            data.get("lastName").isBlank() ||
            data.get("phone").isBlank() 
        ) {
           return true;
        }

        return false;
    }
}
