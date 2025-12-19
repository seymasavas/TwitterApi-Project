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
@Table(name="tweet", schema="twitter")
public class Tweet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="tweet_desc")
    @NotNull
    private String tweetDesc;

    @Column(name="tweet_time")
    @NotNull
    private LocalDateTime tweetTime;

    @ManyToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tweet")
    private List<Like> likes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tweet")
    private List<Comment> comments;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tweet")
    private List <Retweet> retweets;

}
