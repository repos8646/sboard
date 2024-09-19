package com.sboard.controller;

import com.sboard.dto.TermsDTO;
import com.sboard.dto.UserDTO;
import com.sboard.service.TermsService;
import com.sboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final TermsService termsService;

    @GetMapping("/user/login")
    public String login() {
        return "/user/login";
    }

    @GetMapping("/user/register")
    public String register() {
        return "/user/register";
    }

    @PostMapping("/user/register")
    public String register(UserDTO userDTO) {
        if (userDTO == null) {
            System.out.println("UserDTO is null!");
        } else {
            System.out.println("UserDTO: " + userDTO);
        }userService.insertUser(userDTO);
        return "redirect:/user/login";
    }

    @GetMapping("/user/terms")
    public String terms(Model model) {
        List<TermsDTO> terms = termsService.selectTerms();
        model.addAttribute("terms", terms);
        return "/user/terms";
    }



}
