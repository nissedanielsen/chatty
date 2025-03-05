package chatty.chatty_ms.Repository;

import chatty.chatty_ms.model.UserChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserChatRepository extends JpaRepository<UserChat, Long> {

    @Query("SELECT uc.chatId FROM UserChat uc WHERE uc.userId = :userId")
    List<String> findChatsByUserId(@Param("userId") String userId);

    Optional<UserChat> findByUserIdAndChatId(String userId, String chatId);
}