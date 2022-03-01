package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.List;

@Controller

public class MainController {

    private final UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    public MainController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = "/" , method = RequestMethod.GET)
    public String getIndex(){
        return "index";
    }


    @RequestMapping(value = "/login" , method = RequestMethod.GET)
    public String getLogin(){



        return "login";
    }

    @RequestMapping(value = "/login" , method = RequestMethod.POST)
    public String login(){



        return "login";
    }





    @RequestMapping(value = "/admin" , method = RequestMethod.GET)
    public String getAdmin(ModelMap model){

        List<User> users = userService.users();
        model.addAttribute("users", users);
        return "admin";
    }


    @RequestMapping(value = "/admin" , method = RequestMethod.POST)
    public String admin(){
        userService.save(new User("user2" , passwordEncoder.encode("user2") ,Role.USER));
        return "admin";
    }





    @RequestMapping(value = "/user" , method = RequestMethod.GET)
    public String getUser(){

        return "user";
    }


}
