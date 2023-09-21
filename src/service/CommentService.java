package service;

import model.Comment;
import model.Post;
import model.User;
import repository.CommentRepository;
import repository.PostRepository;
import repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

public class CommentService {
    private CommentRepository commentRepository=new CommentRepository();
    private PostRepository postRepository=new PostRepository();
    private UserRepository userRepository=new UserRepository();
    Scanner scNumere=new Scanner(System.in);
    Scanner scText=new Scanner(System.in);
    public void create() {
        System.out.println("Introduceti id-ul postarii.");
        int postId=scNumere.nextInt();

        ArrayList<Post> postareCitita=postRepository.readPostsFromUserWithId(postId);

        if (postareCitita==null){
            System.out.println("Postarea nu exista");
        }else {
            System.out.println("Introduceti id-ul user-ului care doriti sa comenteze.");
            int userId=scNumere.nextInt();

            User userCitit=userRepository.readById(userId);

            if (userCitit==null){
                System.out.println("User-ul care doriti sa comenteze nu exista.");
            }else {
                System.out.println("Introduceti mesajul dorit:");
                String mesajIntrodus=scText.nextLine();
                LocalDateTime createdAt=LocalDateTime.now();

                int affectedRows=commentRepository.create(postId,userId,mesajIntrodus,createdAt);

                if (affectedRows>0){
                    System.out.println("Comentariul a fost salvat cu succes.");
                }else {
                    System.out.println("Comentariul nu a putut fi salvat.");
                }
            }
        }
    }

    public void update() {
        System.out.println("Introduceti id-ul comentariului la care doriti sa faceti modificari.");
        int commentId=scNumere.nextInt();

       ArrayList<Comment> comentariu=commentRepository.readCommentsFromPostWithId(commentId);

       if (comentariu==null){
           System.out.println("Comentariul nu exista.");
       }else {
           System.out.println("Introduceti noul mesaj din comentariu.");
           String mesajNou=scText.nextLine();

           int affectedRows=commentRepository.update(commentId,mesajNou);

           if (affectedRows>0){
               System.out.println("Postarea a fost modificata cu succes.");
           }else {
               System.out.println("Postarea nu a putut fi modificata.");
           }
       }
    }

    public void delete() {
        System.out.println("Introduceti id-ul comentariului pe care doriti sa l stergeti.");
        int idComment=scNumere.nextInt();

        int affectedRows=commentRepository.delete(idComment);

        System.out.println(affectedRows==0? "Comentariul nu a putut fi sters" : "Comentariul a fost sters cu succes.");
    }
}
