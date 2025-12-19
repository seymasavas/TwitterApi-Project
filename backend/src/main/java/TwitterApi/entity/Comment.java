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
@Table(name="comment", schema="twitter")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="comment_desc")
    @NotNull
    private String commentDesc;

    @Column(name="comment_time")
    @NotNull
    private LocalDateTime commentTime;

    @ManyToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="tweet_id")
    private Tweet tweet;

    @ManyToOne(cascade={CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH})
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private List<Like> likes;


}

