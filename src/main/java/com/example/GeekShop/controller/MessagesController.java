package com.example.GeekShop.controller;

import com.example.GeekShop.model.message.Message;
import com.example.GeekShop.service.MessageService;
import com.example.GeekShop.service.user.UserService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/message")
public class MessagesController {
    private final UserService userService;
    private final MessageService messageService;
    @Autowired
    public MessagesController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping
    public String pageOfAllMessagesByUser(Principal principal, @NonNull Model model) {
        model.addAttribute("messages", userService.findByEmail(principal.getName()).getMessages());
        return "/message/all_message";
    }
    @GetMapping("/new")
    public String pageOfSupport(@NonNull Model model, Principal principal) {
        model.addAttribute("principal", principal);
        model.addAttribute("message", new Message());
        return "/message/new_message";
    }
    @PostMapping
    public String saveNewMessage(
            Principal principal,
            @NonNull Model model,
            @ModelAttribute @Valid Message message,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("principal", principal);
            model.addAttribute("message", new Message());
            return "/message/new_message";
        }
        message.setUser(userService.findByEmail(principal.getName()));
        messageService.save(message);
        return "redirect:/message";
    }
    @DeleteMapping("/{id}")
    public String deleteMessageByUser(@PathVariable Long id) {
        messageService.deleteById(id);
        return "redirect:/message";
    }
}
