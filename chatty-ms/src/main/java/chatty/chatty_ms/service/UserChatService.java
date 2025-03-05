package chatty.chatty_ms.service;

import chatty.chatty_ms.Repository.UserChatRepository;
import chatty.chatty_ms.model.UserChat;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserChatService {

    private final UserChatRepository userChatRepository;

    public UserChatService(UserChatRepository userChatRepository) {
        this.userChatRepository = userChatRepository;
    }

    public List<String> getChatsByUser(String userId) {
        List<String> chats = userChatRepository.findChatsByUserId(userId);
        return chats != null ? chats : new ArrayList<>();
    }

    public void saveIfNotExists(String userId, String chatId) {
        Optional<UserChat> userChatDb = userChatRepository.findByUserIdAndChatId(userId, chatId);

        if (userChatDb.isEmpty()) {
            UserChat userChat = UserChat.builder()
                    .userId(userId)
                    .chatId(chatId)
                    .build();
            userChatRepository.save(userChat);
        }
    }
}