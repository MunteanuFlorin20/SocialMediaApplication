package repository;

import model.User;

import javax.jws.soap.SOAPBinding;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public class UserRepository {




    public ArrayList<User> readAll() {
        ArrayList<User> allUsers = new ArrayList<>();
        Connection connection = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet rs = statement.executeQuery("SELECT * FROM user");

            while (rs.next()) {
                     allUsers.add(extraCTUserFromResulSet(rs));
            }
            } catch(SQLException e){
                throw new RuntimeException(e);
            }
            return allUsers;
        }

    public User readById ( int id){
            User userCitit = null;
            Connection connection = ConnectionSingleton.getInstance().getConnection();
            try {
                Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
                ResultSet rs = statement.executeQuery("SELECT * FROM user WHERE id=" + id);
                rs.next();
                userCitit=extraCTUserFromResulSet(rs);
            } catch (SQLException e) {
                return null;
            }
            return userCitit;
        }

        public boolean create (String nume, String prenume, String email, String numarTelefon){

        Connection connection=ConnectionSingleton.getInstance().getConnection();
            try {
                Statement statement=connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
                String template="INSERT INTO user(nume,prenume,email,numar_telefon) VALUES('%s','%s','%s','%s');";
                int affectedRows=statement.executeUpdate(String.format(template,nume,prenume,email,numarTelefon));
                return affectedRows>0;
            } catch (SQLException e) {
                System.out.println("Utilizatorul nu a putut fi salvat.");
                return false;
            }
        }

        public void modifyName ( int id, String numeNou){

        modifyColumn(id,numeNou,"nume");
        }

        public void modifyPrenume ( int id, String prenumeNou){
            modifyColumn(id, prenumeNou,"prenume");
        }

    public void modifyEmail ( int id, String emailNou){
        modifyColumn(id,emailNou,"email");
        }

        public void modifyNumarTelefon ( int id, String numarTelefonNou){
        modifyColumn(id,numarTelefonNou,"numar_telefon");
        }

        public boolean delete ( int id) {
            Connection connection = ConnectionSingleton.getInstance().getConnection();
            try {
                Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
                int affectedRows=statement.executeUpdate("DELETE FROM user WHERE id="+id);
                return  affectedRows>0;
            } catch (SQLException e) {
                return false;
            }
        }
    private static User extraCTUserFromResulSet(ResultSet rs) throws SQLException {
        return new User(
                rs.getInt("id"),
                rs.getString("nume"),
                rs.getString("prenume"),
                rs.getString("email"),
                rs.getString("numar_telefon"),
                new ArrayList<>(),
                new ArrayList<>());
    }
    private static void modifyColumn(int id, String prenumeNou,String columnName) {
        Connection connection=ConnectionSingleton.getInstance().getConnection();
        try {
            Statement statement=connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
            String template="UPDATE user SET %s='%s' WHERE id=%d";
            int affectedRows=statement.executeUpdate(String.format(template,columnName, prenumeNou, id));
            System.out.println(columnName+" "+(affectedRows>0? "modificat" : "nemodificat"));
        } catch (SQLException e) {
            System.out.println("Coloana nu a putut fi modificata!");
        }
    }
}
