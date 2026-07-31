package com.biblio.repository;

import java.util.ArrayList;

import com.biblio.models.Adherent;

public class AdherentRepository extends Repository{

    private Adherent adherent;

    public AdherentRepository(Adherent adherent){
        super();
        this.adherent = adherent;
    }

    public boolean delete(){

        String query = "DELETE FROM adherent WHERE id = ?";

        try{
            this.pst = db.prepareStatement(query);

            this.pst.setLong(1, this.adherent.getId());

            if(this.pst.executeUpdate() == 0) 
                return false;

        }catch(Exception e){
            System.err.print("Error, Adherent Delete : " + e.getMessage());
            return false;
        }

        return true;
    }

    public Adherent getById(){

        String query = "SELECT * FROM adherent WHERE id = ?";

        try {
            pst = db.prepareStatement(query);
            pst.setInt(1, this.adherent.getId());
            rs = pst.executeQuery();

            while (rs.next()) {

                return new Adherent(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("phone"),
                    rs.getString("photo")
                );
            }

        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        return null;
    }
    
    public ArrayList<Adherent> index(){

        ArrayList<Adherent> adherents = new ArrayList<Adherent>();
        
        String query = "SELECT * FROM adherent";

        try {

            pst = db.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {

                adherents.add(new Adherent(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("phone"),
                    rs.getString("photo")
                ));
            }

        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        return adherents;
    }

    public boolean edit(){

        String query = "UPDATE adherent SET firstName = ?, lastname = ?, phone = ?, photo = ? WHERE id = ?";

        try{
            this.pst = db.prepareStatement(query);

            this.pst.setString(1, this.adherent.getFirstName());
            this.pst.setString(2, this.adherent.getLastName());
            this.pst.setString(3, this.adherent.getPhone());
            this.pst.setString(4, this.adherent.getPhoto());
            this.pst.setInt(5, this.adherent.getId());

            if(this.pst.executeUpdate() == 0) 
                return false;

        }catch(Exception e){
            System.err.print("Error, Adherent update : " + e.getMessage());
            return false;
        }

        return true;
    }

    public int save(){

        String query = "INSERT INTO adherent(firstName, lastName , phone, photo, user_id) VALUES(?,?,?,?,?)";

        try{
            this.pst = db.prepareStatement(query);

            this.pst.setString(1, this.adherent.getFirstName());
            this.pst.setString(2, this.adherent.getLastName());
            this.pst.setString(3, this.adherent.getPhone());
            this.pst.setString(4, this.adherent.getPhoto());
            this.pst.setInt(5, this.adherent.getUserId());

            if(this.pst.executeUpdate() == 0) 
                return 0;

        }catch(Exception e){
            System.err.print("Error, adherent insertion : " + e.getMessage());
            return 0;
        }

        Adherent lastest = getLastRecord();
        return (lastest != null ? lastest.getId() : 0);
    }

    public Adherent getLastRecord(){

        String query = "SELECT * FROM adherent ORDER BY id DESC LIMIT 1";

        try {

            pst = db.prepareStatement(query);
            rs = pst.executeQuery();

            while (rs.next()) {

                return new Adherent(
                    rs.getInt("id"),
                    rs.getString("firstName"),
                    rs.getString("lastName"),
                    rs.getString("phone"),
                    rs.getString("photo")
                );
            }

        } catch (Exception e) {
            System.err.println("error : " + e.getMessage());
        }

        return null;
    }
}
