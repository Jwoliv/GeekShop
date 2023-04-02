package com.example.GeekShop.service;

import com.example.GeekShop.model.message.Message;
import com.example.GeekShop.repository.MessageRepository;
import com.example.GeekShop.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;
    @Autowired
    public MessageService(MessageRepository messageRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.userService = userService;
    }
    public List<Message> findAll() {
        return messageRepository.findAll();
    }
    public Message findById(Long id) {
        return messageRepository.findById(id).orElse(null);
    }
    @Transactional
    public void save(Message message) {
        messageRepository.save(message);
    }
    @Transactional
    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }
    @Transactional
    public void updateAfterGetAdminAnswer(Long id, String adminAnswer) {
        Message message = findById(id);
        message.setAdminAnswer(adminAnswer);
        save(message);
    }
    @Transactional
    public void setUserAndSaveMessage(Message message, Principal principal) {
        message.setUser(userService.findByEmail(principal.getName()));
        save(message);
    }
}
