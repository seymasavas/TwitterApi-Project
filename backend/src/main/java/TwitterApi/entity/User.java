package TwitterApi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Data
@Table(name="users", schema="twitter")

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="user_name")
    @NotNull
    private String userName;

    @Column(name ="email")
    @NotNull
    private String email;

    @Column(name="password")
    @NotNull
    private String password;

    @Column(name="registration_date")
    @NotNull
    private LocalDateTime registrationDate;

    @OneToMany(cascade=CascadeType.ALL,mappedBy = "user")
    private List<Tweet> tweets;
    @OneToMany(cascade=CascadeType.ALL,mappedBy = "user")
    private List<Retweet> retweets;
    @OneToMany(cascade=CascadeType.ALL,mappedBy = "user")
    private List<Comment> comments;



}
