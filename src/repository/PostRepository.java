package repository;

import javafx.beans.property.ReadOnlySetProperty;
import model.Post;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PostRepository {
    public ArrayList<Post> readPostsFromUserWithId(int userId) {
        ArrayList<Post> postarileUserului=new ArrayList<>();

        Connection connection= ConnectionSingleton.getInstance().getConnection();
        try {
            Statement statement=connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet=statement.executeQuery("SELECT * FROM post WHERE user_id="+userId);
            while (resultSet.next()){
                postarileUserului.add(extractPostFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("User-ul nu are postari.");
        }

        return postarileUserului;
    }

    public ArrayList<Post> readAll() {
        ArrayList<Post> allPosts=new ArrayList<>();
        Connection connection=ConnectionSingleton.getInstance().getConnection();
        try {
            Statement statement=connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet=statement.executeQuery("SELECT * FROM post;");

            while (resultSet.next()){
                allPosts.add(extractPostFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return allPosts;
    }
    public Post readUserPost(int id) {
        Post postAfisat=null;
        Connection connection=ConnectionSingleton.getInstance().getConnection();
        try {
            Statement statement=connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet=statement.executeQuery("SELECT * FROM post WHERE user_id="+id);
            resultSet.next();
            extractPostFromResultSet(resultSet);
        } catch (SQLException e) {
            return null;
        }
        return postAfisat;
    }
    private static Post extractPostFromResultSet(ResultSet resultSet) {
        try {
            return new Post(
                    resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getString("mesaj"),
                    LocalDateTime.of(
                            resultSet.getInt("an"),
                            resultSet.getInt("luna"),
                            resultSet.getInt("zi"),
                            resultSet.getInt("ora"),
                            resultSet.getInt("minut")),
                            new ArrayList<>());
        } catch (SQLException e) {
            return null;
        }
    }

    public int create(int idUser, String mesaj, LocalDateTime createdAt) {
        Connection connection = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            String template = "INSERT INTO post(user_id,mesaj,an,luna,zi,ora,minut) VALUES(%d,'%s',%d,%d,%d,%d,%d);";
            return statement.executeUpdate(String.format(template,
                    idUser,
                    mesaj,
                    createdAt.getYear(),
                    createdAt.getMonthValue(),
                    createdAt.getDayOfMonth(),
                    createdAt.getHour(),
                    createdAt.getMinute()));
        } catch (SQLException e) {
            System.out.println("Postarea nu a putut fi salvata.");
        }
        return idUser;
    }
    public Post readById(int idPost) {
        Connection connection=ConnectionSingleton.getInstance().getConnection();
        try {
            Statement statement=connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet=statement.executeQuery("SELECT * FROM post WHERE id="+idPost);
            resultSet.next();
            return  new Post(resultSet.getInt("id"),
                    resultSet.getInt("user_id"),
                    resultSet.getString("mesaj"),
                    LocalDateTime.of(
                            resultSet.getInt("an"),
                            resultSet.getInt("luna"),
                            resultSet.getInt("zi"),
                            resultSet.getInt("ora"),
                            resultSet.getInt("minut")),
                    new ArrayList<>());
        } catch (SQLException e) {
            return null;
        }
    }

    public int update(int idPost, String mesajNou) {
        Connection connection=ConnectionSingleton.getInstance().getConnection();
        try {
            Statement statement=connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
            String template="UPDATE post SET mesaj='%s' WHERE id='%d';";
            return statement.executeUpdate(String.format(template,mesajNou,idPost));
        } catch (SQLException e) {
           return 0;
        }
    }

    public int delete(int idPost) {
        Connection connection=ConnectionSingleton.getInstance().getConnection();
        try {
            Statement statement=connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate("DELETE FROM post WHERE id="+idPost);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
