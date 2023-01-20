package com.example.GeekShop.controller.product;

import com.example.GeekShop.model.Product;
import com.example.GeekShop.model.images.ImageProduct;
import com.example.GeekShop.service.product.ImageProductService;
import com.example.GeekShop.service.product.ProductService;
import com.example.GeekShop.service.product_fields.CategoryService;
import com.example.GeekShop.service.product_fields.SeasonService;
import com.example.GeekShop.service.product_fields.ThemeService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping("/product")
public class ProductsController {
    private final ProductService productService;
    private final ImageProductService imageProductService;
    private final ThemeService themeService;
    private final SeasonService seasonService;
    private final CategoryService categoryService;

    public ProductsController(
            ProductService productService,
            ImageProductService imageProductService,
            ThemeService themeService,
            SeasonService seasonService,
            CategoryService categoryService
    ) {
        this.productService = productService;
        this.imageProductService = imageProductService;
        this.themeService = themeService;
        this.seasonService = seasonService;
        this.categoryService = categoryService;
    }
    @GetMapping
    public String pageAllProducts(@NonNull Model model) {
        model.addAttribute("all_products", productService.findAll());
        return "product/all_products";
    }
    @GetMapping("/{id}")
    public String pageSelectedProduct(@PathVariable Long id, @NonNull Model model) {
        model.addAttribute("product", productService.findById(id));
        return "product/selected_product";
    }
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "redirect:/product";
    }
    @GetMapping("/new")
    public String formNewProduct(@NonNull Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("themes", themeService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("seasons", seasonService.findAll());
        return "product/new_product";
    }
    @PostMapping
    public String saveNewProduct(
            @RequestParam MultipartFile file1,
            @RequestParam MultipartFile file2,
            @RequestParam MultipartFile file3,
            @RequestParam MultipartFile file4,
            @RequestParam MultipartFile file5,
            @ModelAttribute @Valid Product product,
            BindingResult bindingResult,
            @NonNull Model model
    ) {
        if (
                bindingResult.hasErrors() || file1.isEmpty() ||
                file2.isEmpty() || file3.isEmpty() ||
                file4.isEmpty() || file5.isEmpty()
        ) {
            model.addAttribute("product", new Product());
            model.addAttribute("themes", themeService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("seasons", seasonService.findAll());
            return "product/new_product";
        }
        productService.save(product);
        saveImages(file1, file2, file3, file4, file5, product);
        productService.save(product);
        product.setPreviewsId(productService.findById(product.getId()).getPreviewsId());
        productService.save(product);
        return "redirect:/product";
    }
    @GetMapping("/{id}/edit")
    public String editProduct(@PathVariable Long id, @NonNull Model model) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("themes", themeService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("seasons", seasonService.findAll());
        return "product/edit_product";
    }
    @PatchMapping("/{id}")
    public String saveEditedProduct(
            @PathVariable Long id,
            @ModelAttribute @Valid Product product,
            BindingResult bindingResult,
            @NonNull Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("themes", themeService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("seasons", seasonService.findAll());
            return "product/edit_product";
        }
        product.setPreviewsId(productService.findById(id).getPreviewsId());
        productService.save(product);
        return "redirect:/product/{id}";
    }
    @GetMapping("/{id}/change_photo")
    public String formChangePhoto(@PathVariable Long id, @NonNull Model model) {
        model.addAttribute("product", productService.findById(id));
        return "product/change_photo";
    }
    @PatchMapping("/{id}/change_photo")
    public String savePhotos(
            @PathVariable Long id,
            @RequestParam MultipartFile file1,
            @RequestParam MultipartFile file2,
            @RequestParam MultipartFile file3,
            @RequestParam MultipartFile file4,
            @RequestParam MultipartFile file5,
            @NonNull Model model
    ) {
        if (file1.isEmpty() || file2.isEmpty() || file3.isEmpty() || file4.isEmpty() || file5.isEmpty()) {
            model.addAttribute("product", productService.findById(id));
            return "product/change_photo";
        }
        saveImages(file1, file2, file3, file4, file5, productService.findById(id));
        return "redirect:/product/{id}";
    }
    private ImageProduct toImageEntity(MultipartFile file) throws IOException {
        ImageProduct image = new ImageProduct();
        image.setName(file.getName());
        image.setOriginalName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }
    private void saveImages(
            MultipartFile file1, MultipartFile file2,
            MultipartFile file3, MultipartFile file4,
            MultipartFile file5, Product element
    ) {
        element.setPreviewsId(null);
        element.setImages(new ArrayList<>());
        imageProductService.deleteAllByElement(element);
        try {
            ImageProduct image1 = toImageEntity(file1);
            image1.setIsPreviews(true);
            element.addImages(image1, element);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try { element.addImages(toImageEntity(file2), element); }
        catch (IOException e) { throw new RuntimeException(e); }
        try { element.addImages(toImageEntity(file3), element); }
        catch (IOException e) { throw new RuntimeException(e); }
        try { element.addImages(toImageEntity(file4), element); }
        catch (IOException e) { throw new RuntimeException(e); }
        try { element.addImages(toImageEntity(file5), element); }
        catch (IOException e) { throw new RuntimeException(e); }
        productService.save(element);
        element.setPreviewsId(element.getImages().get(0).getId());
        productService.save(element);
    }
}
