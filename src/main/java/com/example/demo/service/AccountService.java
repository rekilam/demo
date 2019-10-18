package com.example.demo.service;

import com.example.demo.dao.AccountDAO;
import java.util.ArrayList;
import java.util.List;
import com.example.demo.dto.AccountDTO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public interface AccountService {

    String checkLogin(AccountDTO account, HttpSession session, HttpServletRequest request);
    
    List<AccountDTO> getAllAccount();

    AccountDTO findById(int id);
    
    boolean addAccount(AccountDTO accountDTO);
    
    boolean updateAccount(AccountDTO accountDTO);

    boolean delete(int id);
    
    //boolean checkExistAccount(String email);
}
