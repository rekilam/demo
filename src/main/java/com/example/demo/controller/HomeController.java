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
import org.springframework.ui.Model;
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
//        List<AccountDTO> accountList = accountService.getAllAccount();
//        LOGGER.info(accountList.get(0).getAccountName());
        return "/account-management";
    }

    @GetMapping("/delete-account")
    public String deleteAccount(
            @RequestParam(value = "id", required = false) int id, Model model) {
        LOGGER.info(" what is id : " + id);
//        if (accountService.delete(id)); else {
//            model.addAttribute("error", "error on delete");
//        }
        accountService.delete(id);
        return "redirect:/account";
    }

//    @GetMapping("/get-account")
//    public void getAccount(@RequestParam String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
//        LOGGER.info(" what is id : " + id);
//        PrintWriter out = response.getWriter(); //lam gon code
//        response.setContentType("text/html;charset=UTF-8");
//        request.setCharacterEncoding("utf-8");
//        //response.setHeader("Content-Type", "application/json;charset=UTF-8");
//        //response.setContentType("application/json;charset=UTF-8");
//        response.setContentType( "application/json" );
//        response.setCharacterEncoding( "UTF-8" );
//        if(accountService.findById(Integer.valueOf(id)) != null) {
//            response.setContentType("application/json");
//            response.setCharacterEncoding("utf-8");
//            // Import gson-2.2.2.jar
//            Gson gson = new Gson();
//            String objectToReturn = gson.toJson(accountService.findById(Integer.valueOf(id))); // Convert List -> Json
//            out.write(objectToReturn); // Ä�Æ°a Json tráº£ vá»� Ajax
//            out.flush();
//            // response.getWriter().write(objectToReturn);
//        }else {
//            response.setContentType("application/json");
//            out.write("{\"check\":\"fail\"}");
//            out.flush();
//        }
//
//    }
    @GetMapping("/get-account")
    public ResponseEntity<AccountDTO> getAccount(@RequestParam String id) throws IOException {
        return new ResponseEntity(accountService.findById(Integer.valueOf(id)), HttpStatus.OK);
    }

    @PostMapping(value = "/add-account")
    public String addAccount(@ModelAttribute("accountDTO") @Validated AccountDTO accountDTO,
            BindingResult result // holds the result of a validation and binding and contains errors that may have occurred
    ) {
        if (result.hasErrors()) { //check form is validated
            System.out.println("BINDING RESULT ERROR");
            //return "index";
        } else {
            LOGGER.info("account id: " + accountDTO.getAccountId());
            System.out.println("add :: account id: " + accountDTO.getAccountId());

//            if (accountService.checkExistAccount(accountDTO.getAccountName()) == false) { //account exits
//            } else {
//                //accountService.addAccount(accountDTO);
//            }
            if (accountDTO.getAccountId() != 0) {
                accountService.updateAccount(accountDTO);
            } else {
                accountService.addAccount(accountDTO);
            }
            //accountService.addAccount(accountDTO);
        }
        return "redirect:/account";
    }
}
