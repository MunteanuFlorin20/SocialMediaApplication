import service.CommentService;
import service.PostService;
import service.UserService;

import java.util.Scanner;

public class Controller {

    static String url = "jdbc:mysql://localhost:3306/dtb";
    static String username = "root";
    static String password = "#victoria02F";

    private static UserService userService=new UserService();
    private static PostService postService=new PostService();
    private static CommentService commentService=new CommentService();

    static Scanner scText=new Scanner(System.in);
    public static void main(String[] args) {
        while (true) {
            System.out.println("Introduceti flow-ul dorit. (user/post/comment)");
            String response = scText.nextLine();

            switch (response) {
                case "user":
                    startUserFlow();
                    break;
                case "post":
                    startPostFlow();
                    break;
                case "comment":
                    startCommentFlow();
                    break;
                default:
                    System.out.println("Flow-ul nu exista.");
            }
        }
        }

    private static void startUserFlow () {
            String operatieAleasa = chooseOperation("RA,RBI,C,U,D");

            switch (operatieAleasa) {

                case "RA":
                    userService.readAll();
                    break;
                case "RBI":
                    userService.readById();
                    break;
                case "C":
                    userService.create();
                    break;
                case "U":
                    userService.update();
                    break;
                case "D":
                    userService.delete();
                    break;

                default:
                    System.out.println("Operatie invalida");

            }
        }
    private static void startPostFlow () {
            String operatieAleasa = chooseOperation("RA,RUP,C,U,D");
            switch (operatieAleasa){
                case "RA":
                    postService.readAll();
                    break;
                case "RUP":
                    postService.readUserPost();
                    break;
                case "C":
                    postService.create();
                    break;
                case "U":
                    postService.update();
                    break;
                case "D":
                    postService.delete();
                    break;
                default:
                    System.out.println("Operatie invalida.");
            }
        }
    private static void startCommentFlow() {
        String operatieAleasa=chooseOperation("C,U,D");
        switch (operatieAleasa){
            case "C":
                commentService.create();
                break;
            case "U":
                commentService.update();
                break;
            case "D":
                commentService.delete();
                break;
            default:
                System.out.println("Operatie invalida.");
        }
    }
    private static String chooseOperation(String operations) {
        System.out.println("Introduceti operatia dorita: ("+operations+") ");
        String operatieAleasa = scText.nextLine();
        return operatieAleasa;
        }
    }

