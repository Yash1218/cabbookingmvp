package com.example.cabbookingmvp.controller;

import com.example.cabbookingmvp.entity.User;
import com.example.cabbookingmvp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepo;

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }
//    @GetMapping("/home")
//    public String homePage() {
//        return "home";
//    }

    @PostMapping("/register")
    public String registerUser(@RequestParam String name,
                               @RequestParam String email,
                               @RequestParam String password,
                               Model model) {

        if (userRepo.findByEmail(email) != null) {
            model.addAttribute("error", "Email already exists!");
            return "register";
        }

        userRepo.save(new User(name, email, password));

        return "redirect:/";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email,
                            @RequestParam String password,
                            Model model,
                            HttpSession session) {

        User user = userRepo.findByEmail(email);

        if (user == null || !user.getPassword().equals(password)) {
            model.addAttribute("error", "Invalid credentials");
            return "login";
        }
        session.setAttribute("email", user.getEmail());

        // redirect to booking page
        return "redirect:/home";

    }
}
