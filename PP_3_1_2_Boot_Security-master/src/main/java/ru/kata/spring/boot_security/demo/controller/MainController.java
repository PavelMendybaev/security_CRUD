package ru.kata.spring.boot_security.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @GetMapping(value = "/")

    public String getIndex(){
        return "index";
    }


    @GetMapping(value = "/login" )
    public String getLogin(){
        return "login";
    }

    @PreAuthorize("hasAnyAuthority('developers:write')")
    @PostMapping(value = "/admin" )
    public String PostEnter(@RequestParam("username") String name ,
                            @RequestParam("password") String password ,
                            @RequestParam("role") String role ,
                            ModelMap model ) {
        userService.save(new User(name , passwordEncoder.encode(password) ,Role.valueOf(role)));

        List<User> users = userService.users();
        model.addAttribute("users", users);
        return "admin";
    }

    @GetMapping(value = "/admin" )
    public String getAdmin(ModelMap model){

        List<User> users = userService.users();
        model.addAttribute("users", users);
        return "admin";
    }



    @GetMapping("{id}")
    public String showUser(@PathVariable("id") Long id , ModelMap model){

        User user = userService.getUserById(id);
        model.addAttribute("user" , user);
        return "user";

    }







    @RequestMapping(value = "/user" , method = RequestMethod.GET)
    public String getUser(){

        return "user";
    }

    @RequestMapping(value = "{id}" , method = RequestMethod.POST)
    public String getUser(@PathVariable("id") Long id ,@RequestParam("new_username") String new_name , ModelMap model){

        User user = userService.getUserById(id);
        user.setName(new_name);
        userService.save(user);
        model.addAttribute("user" , user);
        return "user";

    }

    @DeleteMapping(value = "{id}" )
    public String delUser(@PathVariable("id") Long id , ModelMap model){

        userService.deleteById(id);

        return "admin";

    }


}
