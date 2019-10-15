package com.example.demo.dao;

import com.example.demo.dto.AccountDTO;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

public interface AccountDAO {

    boolean checkLogin(AccountDTO account, HttpServletRequest request);

    boolean addAccount(AccountDTO accountDTO);

    boolean updateAccount(AccountDTO accountDTO);

    List<AccountDTO> getAllAccount();

    AccountDTO findById(int id);

    boolean delete(int id);

    boolean checkExistAccount(String accountName);
}
