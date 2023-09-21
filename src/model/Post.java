package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Post {

    public int id;
    public int user_id;
    public String mesaj;
    public LocalDateTime createdAt;
    public ArrayList<Comment> comentarii;

    public Post(int id,int user_id,String mesaj,LocalDateTime createdAt,ArrayList<Comment> comentarii) {
        this.id = id;
        this.user_id=user_id;
        this.mesaj=mesaj;
        this.createdAt=createdAt;
        this.comentarii=comentarii;
    }

    @Override
    public String toString() {
        return "\nPost{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", mesaj='" + mesaj + '\'' +
                ", createdAt=" + createdAt +
                ", comentarii=" + comentarii +
                "}\n";
    }
}
