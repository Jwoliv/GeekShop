package com.example.GeekShop.controller.admin.product_fields;

import com.example.GeekShop.model.images.ImageProductField;
import com.example.GeekShop.model.product_fields.AbstractProductField;
import com.example.GeekShop.repository.product_fields.AbstractProductFieldRepository;
import com.example.GeekShop.service.product_fields.AbstractProductFieldService;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AdminAbstractProductsFieldsController <
            TI extends ImageProductField<E>,
            E extends AbstractProductField<E, TI>,
            R extends AbstractProductFieldRepository<E, TI>,
            S extends AbstractProductFieldService<E, TI, R>
        > {
    private String url;
    private String title;
    private S service;
    @GetMapping
    public String pageOfElements(@NotNull Model model, Principal principal) {
        model.addAttribute("url", url);
        model.addAttribute("title", title);
        model.addAttribute("principal", principal);
        model.addAttribute("all_elements", service.findAll());
        return "/admin/all_elements";
    }
}
