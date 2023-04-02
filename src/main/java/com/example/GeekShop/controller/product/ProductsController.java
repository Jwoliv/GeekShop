package com.example.GeekShop.controller.product;

import com.example.GeekShop.model.product.Comment;
import com.example.GeekShop.model.product.Gender;
import com.example.GeekShop.model.product.Product;
import com.example.GeekShop.model.product.SizeOfProduct;
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

import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Stream;

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
            ProductService productService, ImageProductService imageProductService, ThemeService themeService,
            SeasonService seasonService, CategoryService categoryService, UserService userService
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
                model, principal, Gender.Male, "Men's clothing"
        );
        addTheProductFieldsToTheModel(model);
        return "product/all_products";
    }
    @GetMapping("/for-female")
    public String productForFemale(@NonNull Model model, Principal principal) {
        addTheCommonValuesInTheControllerOfChoiceGender(
                model, principal, Gender.Female, "Women's clothing"
        );
        addTheProductFieldsToTheModel(model);
        return "product/all_products";
    }
    @GetMapping("/unisex")
    public String productForUniSex(@NonNull Model model, Principal principal) {
        addTheCommonValuesInTheControllerOfChoiceGender(
                model, principal, Gender.Unisex, "Unisex clothing"
        );
        return "product/all_products";
    }
    @GetMapping("/find")
    public String pageOfProductsLikeName(@PathParam("name") String name, @NonNull Model model, Principal principal) {
        model.addAttribute("principal", principal);
        model.addAttribute("nameOfPage", "Products");
        model.addAttribute("all_products", productService.findProductsByName(name.toUpperCase(Locale.ROOT)));
        model.addAttribute("find_content", name);
        return "product/all_products";
    }
    @GetMapping("/find-by-all")
    public String pageOfSearchProductsByAllFields(
            @PathParam("categoryId") Long categoryId, @PathParam("themeId") Long themeId,
            @PathParam("seasonId") Long seasonId, @PathParam("gender") Gender gender,
            @PathParam("min") Integer min, @PathParam("max") Integer max, Model model

    ) {
        model.addAttribute("all_products", productService.findProductsInTheMainForm(
                categoryId, themeId, seasonId, gender, min, max
        ));
        model.addAttribute("nameOfPage", "Products");
        return "/product/all_products";
    }
    @GetMapping("/{id}")
    public String pageSelectedProduct(@PathVariable Long id, @NonNull Model model, Principal principal) {
        Product product = productService.findById(id);
        productService.refreshRatingOfProduct(product);
        refreshListOfViewedProductOfUser(principal, product);

        model.addAttribute("principal", principal);
        model.addAttribute("isLikedProduct", userService.findByEmail(principal.getName()).getListOfLikedProducts().contains(product));
        model.addAttribute("comment", new Comment());
        model.addAttribute("product", product);
        model.addAttribute("recommended_products", productService.findRecommendedProduct(principal));
        model.addAttribute("isNotAvailiable", product.getNumberProduct() == 0);
        return "product/selected_product";
    }
    @DeleteMapping("/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.removeProductFromUserAndDatabase(id);
        return "redirect:/admin/product";
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
        if (bindingResult.hasErrors() || file1.isEmpty() || file2.isEmpty() || file3.isEmpty() ||
                file4.isEmpty() || file5.isEmpty()
        ) {
            model.addAttribute("product", new Product());
            model.addAttribute("themes", themeService.findAll());
            model.addAttribute("categories", categoryService.findAll());
            model.addAttribute("seasons", seasonService.findAll());
            return "product/new_product";
        }
        productService.saveProductWithAllFields(
                product, Stream.of(size1, size2, size3, size4, size5).filter(Objects::nonNull).toList(), List.of(file1, file2, file3, file4, file5)
        );
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
        productService.updateProductAfterChanges(
                id, product, Stream.of(size1, size2, size3, size4, size5).filter(Objects::nonNull).toList()
        );
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
        imageProductService.saveImages(List.of(file1, file2, file3, file4, file5), productService.findById(id));
        return "redirect:/product/{id}";
    }
    @PostMapping("/{id}/add_to_basket")
    public String addProductToBasket(
            @RequestParam Integer numberProduct, @RequestParam SizeOfProduct sizeOfProduct,
            @PathVariable Long id, Principal principal
    ) {
        Product product = productService.findById(id);
        User user = userService.findByEmail(principal.getName());
        productService.addProductToBasketOfUser(user, product, sizeOfProduct, numberProduct);
        return "redirect:/product/{id}";
    }
    @PostMapping("/{id}/liked_product")
    public String addProductToLikedList(@PathVariable Long id, Principal principal) {
        productService.likeProduct(id, userService.findByEmail(principal.getName()), productService.findById(id));
        return "redirect:/product/{id}";
    }
    @PostMapping("/{id}/add_comment")
    private String addComment(
            @PathVariable Long id, Principal principal, @ModelAttribute @Valid Comment comment, BindingResult bindingResult
    ) {
        User user = userService.findByEmail(principal.getName());
        Product product = productService.findById(id);
        if (comment == null || product == null || bindingResult.hasErrors() || user == null) {
            return "redirect:/product/{id}";
        }
        productService.addCommentToProduct(product, user, comment);
        return "redirect:/product/{id}";
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
    public void refreshListOfViewedProductOfUser(Principal principal, Product product) {
        if (product == null || principal == null) return;
        User user = userService.findByEmail(principal.getName());
        productService.addProductToTheViewProducts(user, product);
    }
}
