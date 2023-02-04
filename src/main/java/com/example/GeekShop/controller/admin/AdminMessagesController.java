package com.example.GeekShop.controller.admin;

import com.example.GeekShop.model.message.Message;
import com.example.GeekShop.service.MessageService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/message")
public class AdminMessagesController {
    private final MessageService messageService;

    @Autowired
    public AdminMessagesController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping
    public String pageOfMessages(@NotNull Model model) {
        model.addAttribute("messages", messageService.findAll());
        return "/admin/all_message";
    }
    @PatchMapping("/{id}")
    public String updateMessage(@PathVariable Long id, @RequestParam String adminAnswer) {
        Message message = messageService.findById(id);
        message.setAdminAnswer(adminAnswer);
        messageService.save(message);
        return "redirect:/admin/message";
    }
    @DeleteMapping("/{id}")
    public String deleteMessage(@PathVariable Long id) {
        messageService.deleteById(id);
        return "redirect:/admin/message";
    }
}
