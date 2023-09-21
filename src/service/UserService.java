package service;

import model.Comment;
import model.Post;
import model.User;
import repository.CommentRepository;
import repository.PostRepository;
import repository.UserRepository;

import java.util.ArrayList;
import java.util.Scanner;

public class UserService {

    private Scanner scNumere=new Scanner(System.in);
    private Scanner scText=new Scanner(System.in);
    private Scanner scBoolean=new Scanner(System.in);

    private UserRepository userRepository=new UserRepository();
    private PostRepository postRepository=new PostRepository();
    private CommentRepository commentRepository=new CommentRepository();


    public void readAll(){

        ArrayList<User> allUsers=userRepository.readAll();

        for (User user:allUsers){
            user.postari=postRepository.readPostsFromUserWithId(user.id);

            for (Post post : user.postari){
                post.comentarii=commentRepository.readCommentsFromPostWithId(user.id);
            }
        }

        allUsers.forEach(user -> System.out.println(user));

    }

    public void readById(){

        System.out.println("Introduceti id-ul utilizatorului dorit.");
        int id=scNumere.nextInt();

        User userCitit=userRepository.readById(id);
        if (userCitit==null){
            System.out.println("Nu exista niciun user cu id-ul "+id);
        }else {
            userCitit.postari=postRepository.readPostsFromUserWithId(id);

            for (Post post: userCitit.postari){
                post.comentarii=commentRepository.readCommentsFromPostWithId(id);
            }
            System.out.println(userCitit);
        }
    }

    public void create(){

        System.out.println("Introduceti numele:");
        String nume=scText.nextLine();
        System.out.println("Introduceti prenumele:");
        String prenume=scText.nextLine();
        System.out.println("Introduceti emai-ul:");
        String email=scText.nextLine();
        System.out.println("Introduceti numarul de telefon:");
        String numarTelefon=scText.nextLine();


        boolean succes=userRepository.create(nume,prenume,email,numarTelefon);

        if (succes){
            System.out.println("Utilizator creat cu succes.");
        }else {
            System.out.println("A aparut o problema.");
        }
    }

    public void update(){

        System.out.println("Introduceti id-ul user-ului:");
        int id=scNumere.nextInt();


        User userCitit=userRepository.readById(id);

        if (userCitit!=null){
            boolean modificamNumele=modificamProprietatea("nume");
            boolean modificamPrenumele=modificamProprietatea("prenume");
            boolean modificamEmail=modificamProprietatea("email");
            boolean modificamNumarTelefon=modificamProprietatea("numarTelefon");

            if (modificamNumele){
                System.out.println("Introduceti noul nume:");
                String numeNou=scText.nextLine();
                userRepository.modifyName(id,numeNou);
            }

            if (modificamPrenumele){
                System.out.println("Introduceti noul prenume:");
                String prenumeNou=scText.nextLine();
                userRepository.modifyPrenume(id,prenumeNou);
            }

            if (modificamEmail){
                System.out.println("Introduceti noul email:");
                String emailNou=scText.nextLine();
                userRepository.modifyEmail(id,emailNou);
            }

            if (modificamNumarTelefon){
                System.out.println("Introduceti noul numar de telefon:?");
                String numarTelefonNou=scText.nextLine();
                userRepository.modifyNumarTelefon(id,numarTelefonNou);
            }

        }else {
            System.out.println("Nu exista niciun user cu id-ul "+id);
        }
    }
    public boolean modificamProprietatea(String proprietate){
        System.out.println("Doriti sa modificati proprietatea "+proprietate+"?");
        return scBoolean.nextBoolean();
    }
    public void delete(){
        System.out.println("Introduceti id-ul user-ului pe care doriti sa l stergeti:");
        int id=scNumere.nextInt();

        boolean succes=userRepository.delete(id);
        System.out.println(succes? "Utilizatorul a fost sters cu succes." : "Utilizatorul nu a putut fi sters.");
    }
}
