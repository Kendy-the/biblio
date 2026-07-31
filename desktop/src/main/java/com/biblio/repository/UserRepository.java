package com.biblio.repository;

import java.util.ArrayList;

import com.biblio.models.User;

public class UserRepository extends Repository{

    private User user;

    public UserRepository(User user){
        super();
        this.user = user;
    }

    public User login(){

        String query = "SELECT * FROM user WHERE email = ? And password = ?";

        try {
            pst = db.prepareStatement(query);
            pst.setString(1, this.user.getEmail());
            pst.setString(2, this.user.getPassword());
            rs = pst.executeQuery();

            while (rs.next()) {

                return new User(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("adresse"),
                    rs.getString("role"),
                    rs.getString("password")
                );
            }

        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        return null;
    }

    public boolean delete(){

        String query = "DELETE FROM user WHERE id = ?";

        try{
            this.pst = db.prepareStatement(query);

            this.pst.setLong(1, this.user.getId());

            if(this.pst.executeUpdate() == 0) 
                return false;

        }catch(Exception e){
            System.err.print("Error, User Delete : " + e.getMessage());
            return false;
        }

        return true;
    }

    public User getById(){

        String query = "SELECT * FROM user WHERE id = ?";

        try {
            pst = db.prepareStatement(query);
            pst.setInt(1, this.user.getId());
            rs = pst.executeQuery();

            while (rs.next()) {

                return new User(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("adresse"),
                    rs.getString("role"),
                    rs.getString("password")
                );
            }

        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        return null;
    }

    public User getByEmail(){

        String query = "SELECT * FROM user WHERE email = ?";

        try {
            pst = db.prepareStatement(query);
            pst.setString(1, this.user.getEmail());
            rs = pst.executeQuery();

            while (rs.next()) {

                return new User(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("adresse"),
                    rs.getString("role"),
                    rs.getString("password")
                );
            }

        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        return null;
    }
    
    public ArrayList<User> index(){

        ArrayList<User> users = new ArrayList<User>();
        
        String query = "SELECT * FROM user";

        try {

            pst = db.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {

                users.add(new User(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("adresse"),
                    rs.getString("role"),
                    rs.getString("password")
                ));
            }

        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        return users;
    }

    public boolean edit(){

        String query = "UPDATE user SET firstName = ?, lastname = ?, phone = ?, email = ?, adresse = ?, role = ?, password = ? WHERE id = ?";

        try{
            this.pst = db.prepareStatement(query);

            this.pst.setString(1, this.user.getFirstName());
            this.pst.setString(2, this.user.getLastName());
            this.pst.setString(3, this.user.getPhone());
            this.pst.setString(4, this.user.getEmail());
            this.pst.setString(5, this.user.getAdresse());
            this.pst.setString(6, this.user.getRole());
            this.pst.setString(7, this.user.getPassword());
            this.pst.setInt(8, this.user.getId());

            if(this.pst.executeUpdate() == 0) 
                return false;

        }catch(Exception e){
            System.err.print("Error, User update : " + e.getMessage());
            return false;
        }

        return true;
    }

    public int save(){

        String query = "INSERT INTO user(email, role, password) VALUES(?,?,?)";

        try{
            this.pst = db.prepareStatement(query);

            this.pst.setString(1, this.user.getEmail());
            this.pst.setString(2, this.user.getRole());
            this.pst.setString(3, this.user.getPassword());

            if(this.pst.executeUpdate() == 0) 
                return 0;

        }catch(Exception e){
            System.err.print("Error, user insertion : " + e.getMessage());
            return 0;
        }

        User lastest = getLastRecord();
        return (lastest != null ? lastest.getId() : 0);
    }

    public User getLastRecord(){

        String query = "SELECT * FROM user ORDER BY id DESC LIMIT 1";

        try {

            pst = db.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {

                return new User(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("email"),
                    rs.getString("phone"),
                    rs.getString("adresse"),
                    rs.getString("role"),
                    rs.getString("password")
                );
            }

        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        return null;
    }
}
