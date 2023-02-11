package com.example.GeekShop.controller.product;

import com.example.GeekShop.model.images.ImageProduct;
import com.example.GeekShop.model.product.*;
import com.example.GeekShop.model.user.User;
import com.example.GeekShop.service.product.ImageProductService;
import com.example.GeekShop.service.product.ProductService;
import com.example.GeekShop.service.product_fields.CategoryService;
import com.example.GeekShop.service.product_fields.SeasonService;
import com.example.GeekShop.service.product_fields.ThemeService;
import com.example.GeekShop.service.user.UserService;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

@Controller
@RequestMapping("/product")
public class ProductsController {
    private final ProductService productService;
    private final ImageProductService imageProductService;
    private final ThemeService themeService;
    private final SeasonService seasonService;
    private final CategoryService categoryService;
    private final UserService userService;
    public ProductsController(
            ProductService productService,
            ImageProductService imageProductService,
            ThemeService themeService,
            SeasonService seasonService,
            CategoryService categoryService,
            UserService userService
    ) {
        this.productService = productService;
        this.imageProductService = imageProductService;
        this.themeService = themeService;
        this.seasonService = seasonService;
        this.categoryService = categoryService;
        this.userService = userService;
    }
    @GetMapping
    public String pageAllProducts(@NonNull Model model, Principal principal) {
        model.addAttribute("principal", principal);
        model.addAttribute("nameOfPage", "Products");
        model.addAttribute("all_products", productService.findAll());
        addTheProductFieldsToTheModel(model);
        return "product/all_products";
    }
    @GetMapping("/for-male")
    public String productForMale(@NonNull Model model, Principal principal) {
        addTheCommonValuesInTheControllerOfChoiceGender(
                model,
                principal,
                Gender.Male,
                "Men's clothing"
        );
        addTheProductFieldsToTheModel(model);
        return "product/all_products";
    }
    @GetMapping("/for-female")
    public String productForFemale(@NonNull Model model, Principal principal) {
        addTheCommonValuesInTheControllerOfChoiceGender(
                model,
                principal,
                Gender.Female,
                "Women's clothing"
        );
        addTheProductFieldsToTheModel(model);
        return "product/all_products";
    }
    @GetMapping("/unisex")
    public String productForUniSex(@NonNull Model model, Principal principal) {
        addTheCommonValuesInTheControllerOfChoiceGender(
                model,
                principal,
                Gender.Unisex,
                "Unisex clothing"
        );
        return "product/all_products";
    }

    @GetMapping("/find")
    public String pageOfProductsLikeName(
            @PathParam("name") String name,
            @NonNull Model model,
            Principal principal
    ) {
        model.addAttribute("principal", principal);
        model.addAttribute("nameOfPage", "Products");
        model.addAttribute("all_products", productService.findProductsByName(name.toUpperCase(Locale.ROOT)));
        model.addAttribute("find_content", name);
        return "product/all_products";
    }
    @GetMapping("/find-by-all")
    public String pageOfSearchProductsByAllFields(
            @PathParam("categoryId") Long categoryId,
            @PathParam("themeId") Long themeId,
            @PathParam("seasonId") Long seasonId,
            @PathParam("gender") Gender gender,
            @PathParam("min") Integer min,
            @PathParam("max") Integer max,
            Model model

    ) {
        model.addAttribute("all_products", productService.findProductsInTheMainForm(
                categoryId, themeId, seasonId, gender, min, max
        ));
        model.addAttribute("nameOfPage", "Products");
        model.addAttribute("productByForm", true);
        return "/product/all_products";
    }
    @GetMapping("/{id}")
    public String pageSelectedProduct(
            @PathVariable Long id,
            @NonNull Model model,
            Principal principal
    ) {
        Product product = productService.findById(id);
        product.calculateRating();
        productService.save(product);
        model.addAttribute("principal", principal);
        model.addAttribute("isLikedProduct", userService.findByEmail(principal.getName()).getListOfLikedProducts().contains(product));
        model.addAttribute("comment", new Comment());
        model.addAttribute("product", product);
        model.addAttribute("recommended_products", productService.findRecommendedProduct(principal));
        model.addAttribute("isNotAvailiable", product.getNumberProduct() == 0);
        User user = userService.findByEmail(principal.getName());
        addProductToTheViewProducts(user, product);
        return "product/selected_product";
    }
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        removeProductInTheUser(productService.findById(id));
        productService.deleteById(id);
        return "redirect:/product";
    }

    private void removeProductInTheUser(Product product) {
        for (User user: userService.findAll()) {
            user.getViewedProducts().remove(product);
            user.getListOfLikedProducts().remove(product);
            userService.saveAfterChange(user);
        }
    }

    @GetMapping("/new")
    public String formNewProduct(@NonNull Model model, Principal principal) {
        model.addAttribute("product", new Product());
        model.addAttribute("themes", themeService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("seasons", seasonService.findAll());
        model.addAttribute("principal", principal);
        return "product/new_product";
    }
    @PostMapping
    public String saveNewProduct(
            @RequestParam MultipartFile file1,
            @RequestParam MultipartFile file2,
            @RequestParam MultipartFile file3,
            @RequestParam MultipartFile file4,
            @RequestParam MultipartFile file5,
            @RequestParam(required=false) SizeOfProduct size1,
            @RequestParam(required=false) SizeOfProduct size2,
            @RequestParam(required=false) SizeOfProduct size3,
            @RequestParam(required=false) SizeOfProduct size4,
            @RequestParam(required=false) SizeOfProduct size5,
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
        product.getSizeForCheck(size1, size2, size3, size4, size5);
        productService.save(product);
        saveImages(file1, file2, file3, file4, file5, product);
        productService.save(product);
        product.setPreviewsId(productService.findById(product.getId()).getPreviewsId());
        productService.save(product);
        return "redirect:/product";
    }
    @GetMapping("/{id}/edit")
    public String editProduct(@PathVariable Long id, @NonNull Model model, Principal principal) {
        model.addAttribute("product", productService.findById(id));
        model.addAttribute("principal", principal);
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
            @NonNull Model model,
            @RequestParam(required=false) SizeOfProduct size1,
            @RequestParam(required=false) SizeOfProduct size2,
            @RequestParam(required=false) SizeOfProduct size3,
            @RequestParam(required=false) SizeOfProduct size4,
            @RequestParam(required=false) SizeOfProduct size5
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("themes", themeService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("seasons", seasonService.findAll());
            return "product/edit_product";
        }
        product.getSizeForCheck(size1, size2, size3, size4, size5);
        product.setPreviewsId(productService.findById(id).getPreviewsId());
        productService.save(product);
        return "redirect:/product/{id}";
    }
    @GetMapping("/{id}/change_photo")
    public String formChangePhoto(@PathVariable Long id, @NonNull Model model, Principal principal) {
        model.addAttribute("principal", principal);
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
    @PostMapping("/{id}/add_to_basket")
    public String addProductToBasket(
            @RequestParam Integer numberProduct,
            @RequestParam SizeOfProduct sizeOfProduct,
            @PathVariable Long id,
            Principal principal
    ) {
        Product product = productService.findById(id);
        User user = userService.findByEmail(principal.getName());
        if (user != null || product != null) {
            ProductByBasket productByBasket = Objects.requireNonNull(user)
                    .getProductByBasketIfItExist(product, sizeOfProduct);
            product.setNumberProduct(product.getNumberProduct() - numberProduct);
            if (productByBasket != null) {
                productByBasket.setNumberProduct(productByBasket.getNumberProduct() + numberProduct);
                userService.saveAfterChange(user);
            }
            else {
                setFieldsForProductByBasket(product, numberProduct, user, sizeOfProduct);
                userService.saveAfterChange(user);
            }
        }
        return "redirect:/product/{id}";
    }

    @PostMapping("/{id}/liked_product")
    public String addProductToLikedList(@PathVariable Long id, Principal principal) {
        Product product = productService.findById(id);
        User user = userService.findByEmail(principal.getName());
        if (productService.findById(id) != null && user != null) {
            if (user.getListOfLikedProducts().contains(product)) {
                user.getListOfLikedProducts().remove(product);
            }
            else {
                user.getListOfLikedProducts().add(product);
            }
            userService.saveAfterChange(user);
        }
        return "redirect:/product/{id}";
    }
    @PostMapping("/{id}/add_comment")
    private String addComment(
            @PathVariable Long id,
            Principal principal,
            @ModelAttribute @Valid Comment comment,
            BindingResult bindingResult
    ) {
        User user = userService.findByEmail(principal.getName());
        Product product = productService.findById(id);
        if (comment == null || product == null || bindingResult.hasErrors() || user == null) {
            return "redirect:/product/{id}";
        }
        comment.setId(null);
        comment.setProduct(product);
        comment.setUser(user);
        product.getComments().add(comment);
        productService.save(product);
        product.calculateRating();
        productService.save(product);
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
        addImageForElement(element, file1);
        addImageForElement(element, file2);
        addImageForElement(element, file3);
        addImageForElement(element, file4);
        addImageForElement(element, file5);
        productService.save(element);
        element.setPreviewsId(element.getImages().get(0).getId());
        productService.save(element);
    }
    private void addImageForElement(Product element, MultipartFile file) {
        try {
            element.addImages(toImageEntity(file), element);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void setFieldsForProductByBasket(
            Product product,
            Integer numberProduct,
            User user,
            SizeOfProduct sizeOfProduct
    ) {
        ProductByBasket newProductByBasket = new ProductByBasket();
        newProductByBasket.setProduct(product);
        newProductByBasket.setNumberProduct(numberProduct);
        Objects.requireNonNull(user).getBasketOfProducts().add(newProductByBasket);
        newProductByBasket.setSize(sizeOfProduct);
    }
    private void addProductToTheViewProducts(User user, Product product) {
        if (user != null && !user.getViewedProducts().contains(product)) {
            if (user.getViewedProducts().size() == 40) {
                user.getViewedProducts().remove(0);
            }
            user.getViewedProducts().add(product);
            userService.saveAfterChange(user);
        }
    }
    public void addTheCommonValuesInTheControllerOfChoiceGender(Model model, Principal principal, Gender gender, String nameOfPage) {
        model.addAttribute("all_products", productService.findProductsByGender(gender));
        model.addAttribute("genderClothesIsEquals", true);
        model.addAttribute("principal", principal);
        model.addAttribute("nameOfPage", nameOfPage);
    }
    public void addTheProductFieldsToTheModel(Model model) {
        model.addAttribute("themes", themeService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("seasons", seasonService.findAll());
    }
}
