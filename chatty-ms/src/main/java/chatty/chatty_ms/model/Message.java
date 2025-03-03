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
@Table(name = "messages")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String chatId;

    @Column(nullable = false)
    private String senderId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true, updatable = false)
    private String timestamp;

    @PrePersist
    public void setTimestamp() {
        //make sure timestamp is always current time
        timestamp = LocalDateTime.now().toString();
    }
}
