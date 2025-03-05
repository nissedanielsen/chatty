package chatty.chatty_ms.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "user_chat")
public class UserChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String chatId;

    @Column(nullable = false)
    private String joinedAt;

    @PrePersist
    public void setJoinedAt() {
        // make sure the join time is always set to current time
        joinedAt = LocalDateTime.now().toString();
    }
}
