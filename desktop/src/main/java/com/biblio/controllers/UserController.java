package com.biblio.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.biblio.config.BApp;
import com.biblio.errors.BErrorMgr;
import com.biblio.errors.BErrors;
import com.biblio.models.User;
import com.biblio.repository.UserRepository;

public class UserController extends Controller{
    public UserController(){}

    public boolean login(String email, String password){

        User user = new User().login(email, password);

        if (user != null) {
            User.setConnected(user);
            return true;
        }
        
        return false;
    }

    public List<User> search(String lastName,String firstName,String phone) {

        List<User> result = new ArrayList<>();

        for (User a : new User().index()) {

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

    public int save(Map<String, String> data){
        
        if (!isCorrectData(data)) {
            return 0;
        }
       
        if (isUserExists(data.get("email"))){
            BErrorMgr.addError("user (email) : ", BErrors.ERROR_RESOURCE_EXISTS.get());
            return 0;
        }

        if (!isPassword("password", data.get("password"))) {
            return 0;
        }

        return new User(data).save();
    }

    public boolean edit(Map<String, String> data, User user){
        
        if(!isCorrectData(data))
            return false;

        return new User(data, true).edit(user.getId());
    }

    private boolean isCorrectData(Map<String, String> data){
        if(
            data.isEmpty() ||
            isEmptyField(data)
        ) {
            BErrorMgr.addError("data : ", BErrors.ERROR_EMPTY_FIELD.get());
            return false;
        }

        if (!isEmail("email",data.get("email"))) return false;

        return true;
    }

    public User getById(int id){

        return new UserRepository(
            new User(id, null, null, null,null,null,null, null)
        ).getById();
    }

    public static boolean delete(User user){

        if(
            !isUserExists(user.getId())
        ) return false;

        return new UserRepository(user).delete();
    }

    public ArrayList<User> index(){
        ArrayList<User> users = new ArrayList<User>();

        for (User user : new User().index()) {
            
            if (user.getId() == User.getConnected().getId()) {
                continue;
            }

            user.setRole(
                user.getRole().equals("0") 
                ? BApp.USER_USER_ROLE.get() : BApp.USER_ADMIN_ROLE.get()
            );

            users.add(user);
        }

        return users;
    }

    public boolean saveTable(){
        return true;
    }

    private static boolean isUserExists(int id){
        if (new User().getById(id) != null) {
            return true;
        } 
        return false;
    }

    private static boolean isUserExists(String email){
        if (new User().getByEmail(email) != null) {
            return true;
        }
        return false;
    }

    private static boolean isEmptyField(Map<String, String> data){
        
        if (
            data.get("email").isBlank() ||
            data.get("password").isBlank() ||
            data.get("role").isBlank() 
        ) {
           return true;
        }

        return false;
    }
}
