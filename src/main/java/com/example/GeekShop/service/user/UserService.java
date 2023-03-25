package com.example.GeekShop.service.user;

import com.example.GeekShop.model.order.Order;
import com.example.GeekShop.model.order.StatusOfOrder;
import com.example.GeekShop.model.product.Product;
import com.example.GeekShop.model.user.Role;
import com.example.GeekShop.model.user.User;
import com.example.GeekShop.repository.UserRepository;
import com.example.GeekShop.service.product.ProductByBasketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserService(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public List<User> findAll() {
        return userRepository.findAll();
    }
    public List<User> findByName(String name) {
        return userRepository.findAllByUsername(name);
    }
    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }
    @Transactional
    public void save(User user) {
        user.setActive(true);
        user.setRole(Role.USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setSpendMoney(0);
        user.setBonusPoints(0);
        user.selectLevelOfSupport();
        userRepository.save(user);
    }
    @Transactional
    public void saveAfterChange(User user) {
        userRepository.save(user);
    }
    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
    @Transactional
    public void deleteByEmail(String email) {
        userRepository.deleteUserByEmail(email);
    }
    @Transactional
    public void setFieldsForUserAfterCreateOrder(User user, Order order) {
        if (user != null && order != null) {
            user.getOrders().add(order);
            user.setBasketOfProducts(new ArrayList<>());
            user.setBonusPoints(0);
            saveAfterChange(user);
        }
    }
    @Transactional
    public void setFieldsForUserAfterCloseOrder(Order order) {
        User user = order.getUser();
        user.setSpendMoney(user.getSpendMoney() + order.getPriceOfOrder());
        user.setBonusPoints(order.getPriceOfOrder() / 100);
        user.selectLevelOfSupport();
        saveAfterChange(user);
    }
    @Transactional
    public void changeName(Principal principal, String lastname, String firstname) {
        if (principal != null) {
            User user = findByEmail(principal.getName());
            user.setLastname(lastname);
            user.setFirstname(firstname);
            saveAfterChange(user);
        }
    }
    @Transactional
    public void changePassword(User user, String newPassword) {
        if (user != null) {
            user.setPassword(passwordEncoder.encode(newPassword));
            saveAfterChange(user);
        }
    }
    @Transactional
    public void changeEmailForUser(String email, Principal principal) {
        if (principal != null) {
            User user = findByEmail(principal.getName());
            user.setEmail(email);
            for (Order order : user.getOrders()) {
                order.setEmail(email);
            }
            saveAfterChange(user);
        }
    }
    @Transactional
    public Boolean checkOrderDoesNotHasCompletedStatus(Principal principal) {
        User user = findByEmail(principal.getName());
        if (user.getOrders() == null) return false;
        for (Order orderFromList: user.getOrders()) {
            if (!orderFromList.getStatusOfOrder().equals(StatusOfOrder.Completed)) return true;
        }
        return false;
    }
    @Transactional
    public void removeProductInTheUser(Product product) {
        for (User user: findAll()) {
            user.getViewedProducts().remove(product);
            user.getListOfLikedProducts().remove(product);
            saveAfterChange(user);
        }
    }
}
