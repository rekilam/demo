/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.service;

import com.example.demo.dto.AccountDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.demo.dao.AccountDAO;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    @Lazy
    private AuthenticationManager authenticationManager;

    private PasswordEncoder passwordEncoder;
    
    private final Logger LOGGER = LogManager.getLogger(this.getClass());

    @Override
    public boolean addAccount(AccountDTO accountDTO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12
        String encodedPassword = encoder.encode(accountDTO.getPassWord());
        LOGGER.info(encodedPassword);
        accountDTO.setPassWord(encodedPassword);
        return accountDAO.addAccount(accountDTO);
    }

    @Override
    public List getAllAccount() {
        return accountDAO.getAllAccount();
    }

    @Override
    public AccountDTO findById(int id) {
        return accountDAO.findById(id);
    }

    @Override
    public boolean updateAccount(AccountDTO accountDTO) {
        return accountDAO.updateAccount(accountDTO);
    }

    @Override
    public boolean delete(int id) {
        AccountDTO account = accountDAO.findById(id);
//        if (account.isActive()) {
//            return false;
//        }
        return accountDAO.delete(id);
    }

    @Override
    public boolean checkExistAccount(String email) {
        boolean account = accountDAO.checkExistAccount(email);
        return account;
    }

    @Override
    public String checkLogin(AccountDTO account, HttpSession session, HttpServletRequest request) {
        boolean result = accountDAO.checkLogin(account, request);
        HttpSession newSession = request.getSession();

        if (result) {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            LOGGER.info(session.getAttribute("isAdmin").toString());

            if ((int) newSession.getAttribute("isAdmin") == 1) {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_admin"));
            } else {
                grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_account"));

            }

            //Make a user with username and password and added spring security context
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                    account.getEmail(), account.getPassWord(), grantedAuthorities);
            token.setDetails(new WebAuthenticationDetails(request));
            Authentication auth = authenticationManager.authenticate(token);
            SecurityContext securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(auth);
        }
        if ((int) newSession.getAttribute("isAdmin") == 1) {
            return "admin";
        }
        return "user";
    }

}
