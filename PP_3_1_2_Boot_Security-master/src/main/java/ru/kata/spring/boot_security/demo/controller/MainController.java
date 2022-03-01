package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
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
    @RequestMapping(value = "/admin" , method = RequestMethod.POST)
    public String PostEnter(@RequestParam("username") String name , ModelMap model ) {
        userService.save(new User(name , passwordEncoder.encode(name) ,Role.USER));

        List<User> users = userService.users();
        model.addAttribute("users", users);
        return "index";
    }


    @GetMapping("{id}")
    public String showUser(@PathVariable("id") Long id , ModelMap model){

        User user = userService.getUserById(id);
        model.addAttribute("user" , user);
        return "user";

    }


    @RequestMapping(value = "/admin" , method = RequestMethod.GET)
    public String getAdmin(ModelMap model){

        List<User> users = userService.users();
        model.addAttribute("users", users);
        return "admin";
    }




    @RequestMapping(value = "/user" , method = RequestMethod.GET)
    public String getUser(){

        return "user";
    }


}
