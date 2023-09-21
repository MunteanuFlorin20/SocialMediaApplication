package service;

import javafx.geometry.Pos;
import model.Comment;
import model.Post;
import model.User;
import repository.CommentRepository;
import repository.PostRepository;
import repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class PostService {
    private Scanner scNumere=new Scanner(System.in);
    private Scanner scText=new Scanner(System.in);
    private Scanner scBoolean=new Scanner(System.in);

    private PostRepository postRepository=new PostRepository();
    private UserRepository userRepository=new UserRepository();
    private CommentRepository commentRepository=new CommentRepository();

    public void readAll() {
        ArrayList<Post> allPosts=postRepository.readAll();
        for (Post post:allPosts){
            post.comentarii=commentRepository.readCommentsFromPostWithId(post.id);
        }
        allPosts.forEach(post -> System.out.println(post));
    }

    public void readUserPost() {
        System.out.println("Introduceti id-ul user-ului ale carui postari doriti sa le vedeti.");
        int idUser=scNumere.nextInt();

        User userCitit=userRepository.readById(idUser);
        if (userCitit==null){
            System.out.println("Nu exista niciun user cu id-ul "+idUser);
        }else {
            ArrayList<Post> postarileUserului=postRepository.readPostsFromUserWithId(idUser);

            for (Post post : postarileUserului){
                post.comentarii=commentRepository.readCommentsFromPostWithId(post.id);
            }
            postarileUserului.forEach(post -> System.out.println(post));
        }
    }

    public void create() {
        System.out.println("Introduceti id-ul userului:");
        int idUser=scNumere.nextInt();

        User userCitit=userRepository.readById(idUser);

        if (userCitit==null){
            System.out.println("Nu exista niciun user cu id-ul "+idUser);
        }else {
            System.out.println("Introduceti mesajul:");
            String mesaj=scText.nextLine();

            LocalDateTime createdAt=LocalDateTime.now();
            int affectedRows=postRepository.create(idUser,mesaj,createdAt);

            if (affectedRows>0){
                System.out.println("Postarea a fost salvata cu succes.");
            }else {
                System.out.println("A aparut o problema.Postarea nu a putut fi salvata.");
            }
        }
    }

    public void update() {
        System.out.println("Introduceti id-ul postarii pe care doriti sa o modificati.");
        int idPost=scNumere.nextInt();

        Post postare=postRepository.readById(idPost);

        if (postare==null){
            System.out.println("Postarea  cu id-ul "+idPost+" nu exista.");
        }else {
            System.out.println("Introduceti noul mesaj:");
            String mesajNou=scText.nextLine();

            int affectedRows=postRepository.update(idPost,mesajNou);

            if (affectedRows>0){
                System.out.println("Postarea a fost modificata.");
            }else {
                System.out.println("Postarea nu a putut fi modificata.");
            }
        }
    }
    public void delete() {
        System.out.println("Introduceti id-ul postarii pe care doriti sa o stergeti.");
        int idPost=scNumere.nextInt();

        int affectedRows=postRepository.delete(idPost);
        System.out.println(affectedRows==0? "Postarea nu a putut fi stearsa." : "Postarea a fost stearsa cu succes");
    }
}
