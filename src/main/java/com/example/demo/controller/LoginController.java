package com.example.demo.controller;

import com.example.demo.dto.AccountDTO;
import com.example.demo.service.AccountService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author FB-PC006
 */
@Controller
public class LoginController {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    @Lazy
    private AccountService accountService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "/login";
    }

//    @GetMapping("/login-error")
//    public String loginError(Model model,
//            ModelMap modelMap) {
//        model.addAttribute("loginError", true);
//        modelMap.addAttribute("loginError", true);
//        return "/login";
//    }

    @PostMapping("/check-login")
    public String checkLogin(
            AccountDTO accountDTO,
            HttpSession session,
            HttpServletRequest request,
            RedirectAttributes redirectAttrs,
            HttpServletResponse response) {

        String result = accountService.checkLogin(accountDTO, session, request);
        switch (result) {
            case "admin":
                return "redirect:/account";
            case "user":
                return "redirect:/account";
            default:
                return "redirect:/login";
        }
    }
}
