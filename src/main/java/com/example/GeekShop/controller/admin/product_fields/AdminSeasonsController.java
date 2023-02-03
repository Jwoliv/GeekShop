package com.example.GeekShop.controller.admin.product_fields;

import com.example.GeekShop.service.product_fields.SeasonService;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminSeasonsController {
    private final SeasonService seasonService;

    @Autowired
    public AdminSeasonsController(SeasonService seasonService) {
        this.seasonService = seasonService;
    }

    @GetMapping("/season")
    public String pageOfSeasons(@NotNull Model model) {
        model.addAttribute("url", "season");
        model.addAttribute("title", "Seasons");
        model.addAttribute("elements", seasonService.findAll());
        return "/admin/all_elements";
    }
}
