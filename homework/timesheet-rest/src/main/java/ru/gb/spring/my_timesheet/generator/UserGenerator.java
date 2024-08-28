package ru.gb.spring.my_timesheet.generator;

import ru.gb.spring.my_timesheet.model.User;
import ru.gb.spring.my_timesheet.repository.UserRepository;

public class UserGenerator {
    User admin;
    User user;
    User anonymous;
    User rest;
    public void generateUsersInRepository(UserRepository userRepository){
        User admin = new User();
        admin.setLogin("admin");
        admin.setPassword("$2a$12$LbAPCsHn8ZN5MUDqDmIX7e9n1YlDkCxEt0lW3Q2WuW0M1vteo8jvG"); // admin - (bcrypt-generator.com) Хэш от admin - то,

        User user = new User();
        user.setLogin("user");
        user.setPassword("$2a$12$zRccUkXS1abZxoL6MYRHx.wJENZgeLJSkrIQYYxEAVj8jqPC.wgdW"); // user - Хэш от user

        User anonymous = new User();
        anonymous.setLogin("anon");
        anonymous.setPassword("$2a$12$tPkyYzWCYUEePUFXUh3scesGuPum1fvFYwm/9UpmWNa52xEeUToRu"); // anon - Хэш от anon

        User rest = new User();
        rest.setLogin("rest");
        rest.setPassword("$2a$12$/8Xwei8n8/PkBtxotUzDPuhP/ODGwY2aCJLIcIpiR0z/qQhLfZNMK");

        admin = userRepository.save(admin);
        user = userRepository.save(user);
        anonymous = userRepository.save(anonymous);
        rest = userRepository.save(rest);
    }

    public User getAdmin() {
        return admin;
    }

    public User getUser() {
        return user;
    }

    public User getAnonymous() {
        return anonymous;
    }

    public User getRest() {
        return rest;
    }
}
