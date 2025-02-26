package chatty.chatty_ms.controller;

import chatty.chatty_ms.model.Message;
import chatty.chatty_ms.producer.MessageProducer;
import chatty.chatty_ms.service.MessageService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    private final MessageService messageService;

    private final MessageProducer messageProducer;

    public MessageController(MessageService messageService, MessageProducer messageProducer) {
        this.messageService = messageService;
        this.messageProducer = messageProducer;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Message sendMessage(@RequestBody Message message) {

        messageService.saveMessage(message);
        messageProducer.sendMessage(message);

        return message;
    }

    @GetMapping("/{chatId}")
    public List<Message> getMessagesByChatId(@PathVariable String chatId) {
        return messageService.getMessagesByChatId(chatId);
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}

