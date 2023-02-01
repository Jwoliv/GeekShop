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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/message")
public class SupportController {
    private final UserService userService;
    private final MessageService messageService;
    @Autowired
    public SupportController(UserService userService, MessageService messageService) {
        this.userService = userService;
        this.messageService = messageService;
    }

    @GetMapping
    public String pageOfAllMessagesByUser(Principal principal, @NonNull Model model) {
        model.addAttribute("messages", userService.findByEmail(principal.getName()).getMessages());
        return "/support/all_message";
    }
    @GetMapping("/new")
    public String pageOfSupport(@NonNull Model model, Principal principal) {
        model.addAttribute("principal", principal);
        model.addAttribute("message", new Message());
        return "/support/support_new";
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
            return "/support/support_new";
        }
        message.setUser(userService.findByEmail(principal.getName()));
        messageService.save(message);
        return "redirect:/message";
    }
}
