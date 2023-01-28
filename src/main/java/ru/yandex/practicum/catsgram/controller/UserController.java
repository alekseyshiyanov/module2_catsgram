package ru.yandex.practicum.catsgram.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.exception.InvalidEmailException;
import ru.yandex.practicum.catsgram.exception.UserAlreadyExistException;
import ru.yandex.practicum.catsgram.model.User;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private final HashSet<User> users = new HashSet();

    @GetMapping
    public List<User> findAll() {
        log.info("Текущее количество пользователей: {}", users.size());
        return new ArrayList<>(users);
    }

    @PostMapping
    public User create(@RequestBody User user) {
        checkEmail(user.getEmail());
        checkUser(user);

        log.info("Сохраняется объект: {}", user);
        users.add(user);
        return user;
    }

    @PutMapping
    public User update(@RequestBody User user) throws InvalidEmailException {
        checkEmail(user.getEmail());

        users.add(user);
        return user;
    }

    private void checkEmail(String email) {
        if (email.isEmpty() || email.isBlank() || email == null) {
            throw new InvalidEmailException("Invalid email address!");
        }
    }

    private void checkUser(User user) {
        if (users.contains(user)) {
            throw new UserAlreadyExistException("User with email '"+ user.getEmail()+ "' has been exist!");
        }
    }
}
