package repository;

import model.Comment;
import model.Post;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class CommentRepository {

    public ArrayList<Comment> readCommentsFromPostWithId(int postId) {
        ArrayList<Comment> comentariilePostarii = new ArrayList<>();

        Connection connection = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            ResultSet resultSet = statement.executeQuery("SELECT * FROM comment WHERE post_id=" + postId);
            while (resultSet.next()) {
                comentariilePostarii.add(extractCommentFromResultSet(resultSet));
            }
        } catch (SQLException e) {
            System.out.println("Postarea nu are comentarii.");
        }
        return comentariilePostarii;
    }

    private static Comment extractCommentFromResultSet(ResultSet resultSet) {
        try {
            return new Comment(
                    resultSet.getInt("id"),
                    resultSet.getInt("post_id"),
                    resultSet.getInt("user_id"),
                    resultSet.getString("mesaj"),
                    LocalDateTime.of(
                            resultSet.getInt("an"),
                            resultSet.getInt("luna"),
                            resultSet.getInt("zi"),
                            resultSet.getInt("ora"),
                            resultSet.getInt("minut")
                    ));
        } catch (SQLException e) {
            return null;
        }
    }

    public int create(int postId, int userId, String mesajIntrodus, LocalDateTime createdAt) {
        Connection connection = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
            String template = "INSERT INTO comment (post_id,user_id,mesaj,an,luna,zi,ora,minut) VALUES (%d,%d,'%s',%d,%d,%d,%d,%d)";
            return statement.executeUpdate(String.format(template,
                    postId,
                    userId,
                    mesajIntrodus,
                    createdAt.getYear(),
                    createdAt.getMonthValue(),
                    createdAt.getDayOfMonth(),
                    createdAt.getHour(),
                    createdAt.getMinute()));
        } catch (SQLException e) {
            return 0;
        }
    }

    public int update(int commentId, String mesajNou) {
        Connection connection = ConnectionSingleton.getInstance().getConnection();
        try {
            Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
            String template = "UPDATE comment SET mesaj='%s' WHERE id=%d";
            return statement.executeUpdate(String.format(template, mesajNou, commentId));
        } catch (SQLException e) {
            return 0;
        }
    }

    public int delete(int idComment) {
        Connection connection=ConnectionSingleton.getInstance().getConnection();
        try {
            Statement statement=connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_UPDATABLE);
            return statement.executeUpdate("DELETE FROM comment WHERE id="+idComment);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
}


