package chatty.chatty_ms.controller;

import chatty.chatty_ms.service.UserChatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserChatController {

    private final UserChatService userChatService;

    public UserChatController(UserChatService userChatService) {
        this.userChatService = userChatService;
    }

    @GetMapping("/{userId}/chats")
    public ResponseEntity<List<String>> getChatsByUser(@PathVariable("userId") String userId) {
        return ResponseEntity.ok(userChatService.getChatsByUser(userId));
    }
}