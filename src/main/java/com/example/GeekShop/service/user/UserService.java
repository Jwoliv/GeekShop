package com.example.GeekShop.service.user;

import com.example.GeekShop.model.order.Order;
import com.example.GeekShop.model.user.Role;
import com.example.GeekShop.model.user.User;
import com.example.GeekShop.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
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
}
