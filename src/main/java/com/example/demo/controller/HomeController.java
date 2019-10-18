/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.controller;

import com.example.demo.dto.AccountDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.demo.service.AccountService;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author Windows 10
 */
@Controller
public class HomeController {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    static final String WELCOME_HOME = "Welcome home"; //just test how set static variable's name

    @Autowired
    @Lazy
    private AccountService accountService;

    @GetMapping("/")
    public String Home() {
        return "redirect:/login";
    }

    @GetMapping("/account")
    public String getAccountManagement(ModelMap map) {
        map.addAttribute("accountList", accountService.getAllAccount());
        return "/account-management";
    }

    @PostMapping("/delete-account")
    public String deleteAccount(
            @RequestParam(value = "id", required = false) int id) {
        accountService.delete(id);
        return "redirect:/account";
    }
    
    //Ajax just use Post Method (GET method is not protected by CSRF and CORS security filters)    
    @GetMapping("/get-account")
    public ResponseEntity<AccountDTO> getAccount(@RequestParam String id) throws IOException {
        return new ResponseEntity(accountService.findById(Integer.valueOf(id)), HttpStatus.OK);
    }

    //Add & Update Account Method
    @PostMapping(value = "/add-account")
    public String addAccount(@ModelAttribute("accountDTO") @Validated AccountDTO accountDTO,
            BindingResult result  // holds the result of a validation and binding and contains errors that may have occurred
    ) {
        if (result.hasErrors()) { //check form is validated
            System.out.println("BINDING RESULT ERROR");
            //return "index";
        } else {
            if (accountDTO.getAccountId() != 0) {
                accountService.updateAccount(accountDTO);
            } else {
                accountService.addAccount(accountDTO);
            }
        }
        return "redirect:/account";
    }
}
