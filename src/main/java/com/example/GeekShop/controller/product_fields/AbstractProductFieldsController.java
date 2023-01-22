package com.example.GeekShop.controller.product_fields;

import com.example.GeekShop.model.images.ImageProductField;
import com.example.GeekShop.model.product_fields.AbstractProductField;
import com.example.GeekShop.repository.images_product_fields.ImageRepository;
import com.example.GeekShop.repository.product_fields.AbstractProductFieldRepository;
import com.example.GeekShop.service.images_product_fields.AbstractImagesService;
import com.example.GeekShop.service.product_fields.AbstractProductFieldService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class AbstractProductFieldsController<
        E extends AbstractProductField<E, TI>,
        TI extends ImageProductField<E>,
        IR extends ImageRepository<E, TI>,
        IS extends AbstractImagesService<E, TI, IR>,
        R extends AbstractProductFieldRepository<E, TI>,
        S extends AbstractProductFieldService<E, TI, R>
    > implements CommonProductFieldsController<TI, E, R, S> {
    private S service;
    private E newElement;
    private TI image;
    private String url;
    private String nameSingle;
    private String namePlural;
    private IS imageService;

    @Override
    public S getService() {
        return service;
    }

    @Override
    public String pageAllElement(@NonNull Model model) {
        model.addAttribute("all_elements", getService().findAll());
        model.addAttribute("url", url);
        model.addAttribute("nameOfPage", namePlural);
        return "product_fields/all_elements";
    }

    @Override
    public String saveNewElement(
            @RequestParam MultipartFile file1,
            @RequestParam MultipartFile file2,
            @RequestParam MultipartFile file3,
            @ModelAttribute @Valid E element,
            BindingResult bindingResult,
            @NonNull Model model
    ) {
        if (bindingResult.hasErrors() || file1.isEmpty() || file2.isEmpty() || file3.isEmpty()) {
            model.addAttribute("nameOfPage", "New " + nameSingle);
            model.addAttribute("element", newElement);
            model.addAttribute("url", url);
            return "product_fields/new_element";
        }
        getService().save(element);
        saveImages(file1, file2, file3, element);
        getService().save(element);
        element.setPreviewsId(getService().findById(element.getId()).getPreviewsId());
        getService().save(element);
        return "redirect:/" + url;
    }

    @Override
    public String formNewElement(@NonNull Model model) {
        model.addAttribute("nameOfPage", "New " + nameSingle);
        model.addAttribute("element", newElement);
        model.addAttribute("url", url);
        return "product_fields/new_element";
    }

    @Override
    public String pageSelectedElement(Long id, @NonNull Model model) {
        model.addAttribute("url", url);
        model.addAttribute("element", getService().findById(id));
        return "product_fields/selected_element";
    }

    @Override
    public String saveEditedElement(
            @ModelAttribute @Valid E element,
            BindingResult bindingResult,
            @NonNull Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("nameOfPage", "New " + nameSingle);
            model.addAttribute("element", newElement);
            model.addAttribute("url", url);
            return "product_fields/edit_element";
        }
        element.setPreviewsId(getService().findById(element.getId()).getPreviewsId());
        getService().save(element);
        return "redirect:/" + url;
    }

    @Override
    public String formChangePhoto(@PathVariable Long id, @NonNull Model model) {
        model.addAttribute("url", url);
        model.addAttribute("element", getService().findById(id));
        return "product_fields/change_photo";
    }

    @Override
    public String savePhotos(
            @PathVariable Long id,
            @RequestParam MultipartFile file1,
            @RequestParam MultipartFile file2,
            @RequestParam MultipartFile file3,
            @NonNull Model model
    ) {
        if (file1.isEmpty() || file2.isEmpty() || file3.isEmpty()) {
            model.addAttribute("element", getService().findById(id));
            model.addAttribute("url", url);
            return "product_fields/change_photo";
        }
        E element = getService().findById(id);
        saveImages(file1, file2, file3, element);
        return "redirect:/" + url + "/{id}";
    }

    @Override
    public String deleteElement(Long id) {
        getService().deleteById(id);
        return "redirect:/" + url;
    }

    @Override
    public String formEditElement(@PathVariable Long id, @NonNull Model model) {
        model.addAttribute("element", getService().findById(id));
        model.addAttribute("nameOfPage", nameSingle);
        model.addAttribute("url", url);
        return "product_fields/edit_element";
    }

    private TI toImageEntity(MultipartFile file) throws IOException {
        TI image = getImage();
        image.setName(file.getName());
        image.setOriginalName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }
    private void saveImages(MultipartFile file1, MultipartFile file2, MultipartFile file3, E element) {
        element.setPreviewsId(null);
        element.setImages(new ArrayList<>());
        getImageService().deleteAllByElement(element);
        try {
            TI image1 = toImageEntity(file1);
            image1.setIsPreviews(true);
            element.addImages(image1, element);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try { element.addImages(toImageEntity(file2), element); }
        catch (IOException e) { throw new RuntimeException(e); }
        try { element.addImages(toImageEntity(file3), element); }
        catch (IOException e) { throw new RuntimeException(e); }
        getService().save(element);
        element.setPreviewsId(element.getImages().get(0).getId());
        getService().save(element);
    }
}
