package com.example.GeekShop.controller.product_fields;

import com.example.GeekShop.model.images.ImageProductField;
import com.example.GeekShop.model.product_fields.AbstractProductField;
import com.example.GeekShop.repository.product_fields.AbstractProductFieldRepository;
import com.example.GeekShop.service.product_fields.AbstractProductFieldService;
import lombok.NonNull;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;

public interface CommonProductFieldsController<
        TI extends ImageProductField<E>,
        E extends AbstractProductField<E, TI>,
        R extends AbstractProductFieldRepository<E, TI>,
        S extends AbstractProductFieldService<E, TI, R>
        > {
    S getService();
    @GetMapping()
    String pageAllElement(@NonNull Model model, Principal principal);
    @PostMapping()
    String saveNewElement(@RequestParam MultipartFile file1, @RequestParam MultipartFile file2, @RequestParam MultipartFile file3,@ModelAttribute E element, BindingResult bindingResult, @NonNull Model model);
    @GetMapping("/new")
    String formNewElement(@NonNull Model model);
    @GetMapping("/{id}")
    String pageSelectedElement(@PathVariable Long id, @NonNull Model model, Principal principal);
    @DeleteMapping("/{id}")
    String deleteElement(@PathVariable Long id);
    @GetMapping("/{id}/edit")
    String formEditElement(@PathVariable Long id, @NonNull Model model);
    @PatchMapping("/{id}")
    String saveEditedElement(@ModelAttribute E element, BindingResult bindingResult, @NonNull Model model);
    @GetMapping("{id}/change_photo")
    String formChangePhoto(@PathVariable Long id, @NonNull Model model);
    @PatchMapping("/{id}/change_photo")
    String savePhotos(@PathVariable Long id, @RequestParam MultipartFile file1, @RequestParam MultipartFile file2, @RequestParam MultipartFile file3, @NonNull Model model);
}
