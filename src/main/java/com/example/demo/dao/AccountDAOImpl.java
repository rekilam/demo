/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.dao;

import com.example.demo.dto.AccountDTO;
import com.example.demo.units.CloseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

@Repository
public class AccountDAOImpl implements AccountDAO {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BasicDataSource basicDataSource;
    
    @Override
    public boolean checkLogin(AccountDTO accountDTO, HttpServletRequest request) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "select * from ACCOUNT where unique_email=?;";
            con = basicDataSource.getConnection();
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setString(1, accountDTO.getEmail());
            rs = ps.executeQuery();

            //Check account exists?
            if (rs.next()) {
                //If it exists, check password
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12
                //LOGGER.info("password1: " + accountDTO.getPassWord() + "password database: " + rs.getString("password"));
                
                if (encoder.matches(accountDTO.getPassWord(), rs.getString("password"))) {
                   HttpSession session = request.getSession();
                   session.setAttribute("accountId", rs.getInt("ACCOUNT_ID"));
                   session.setAttribute("email", accountDTO.getEmail());
                   session.setAttribute("isAdmin", rs.getInt("is_admin"));
                }
            }
            return true;
        } catch (SQLException | AuthenticationException ex) {
        } finally {
            CloseUtils.close(rs, ps, con);
        }
        return false;
    }
    
    @Override
    public AccountDTO findById(int id) {
        String sql = "select * from ACCOUNT where ACCOUNT_ID = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        AccountDTO account = new AccountDTO();
        try {
            con = basicDataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                account = account.getAccountDTO(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            CloseUtils.close(ps, con);
        }
        return account;
    }
    
    @Override
    public List getAllAccount() {
        List<AccountDTO> accountList = new ArrayList<>();
        String sql = "SELECT *\n"
                + "FROM ACCOUNT\n";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = basicDataSource.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setAccountId(rs.getInt("ACCOUNT_ID"));
                accountDTO.setEmail(rs.getString("unique_email"));
                accountDTO.setPassWord(rs.getString("password"));
                accountDTO.setFullName(rs.getString("fullname"));
                accountDTO.setSex(rs.getString("sex"));
                accountDTO.setBirth(rs.getDate("birth"));
                accountDTO.setPhone(rs.getString("phone"));
                accountDTO.setAddress(rs.getString("address"));
                accountDTO.setIsAdmin(rs.getBoolean("is_admin"));
                accountList.add(accountDTO);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            CloseUtils.close(rs, ps, con);
        }
        return accountList;

    }

    @Override
    public boolean addAccount(AccountDTO accountDTO) {
        final String SQL_INSERT = "INSERT INTO ACCOUNT(unique_email,password,fullname,sex,birth,phone,address,is_admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = basicDataSource.getConnection();
            ps = con.prepareStatement(SQL_INSERT);
            ps.setString(1, accountDTO.getEmail());
            ps.setString(2, accountDTO.getPassWord());
            ps.setString(3, accountDTO.getFullName());
            ps.setString(4, accountDTO.getSex());
            ps.setDate(5, accountDTO.getBirth());
            ps.setString(6, accountDTO.getPhone());
            ps.setString(7, accountDTO.getAddress());
            ps.setBoolean(8, accountDTO.getIsAdmin());
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            CloseUtils.close(ps, con);
        }
        return false;
    }

    @Override
    public boolean updateAccount(AccountDTO accountDTO) {
        final String SQL_UPDATE = "Update ACCOUNT \n"
                + "set password = ?, fullname = ?, sex = ?, birth = ?, phone = ?, address = ?, is_admin = ? \n"
                + "where unique_email = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = basicDataSource.getConnection();
            ps = con.prepareStatement(SQL_UPDATE);
            ps.setString(1, accountDTO.getPassWord());
            ps.setString(2, accountDTO.getFullName());
            ps.setString(3, accountDTO.getSex());
            ps.setDate(4, accountDTO.getBirth());
            ps.setString(5, accountDTO.getPhone());
            ps.setString(6, accountDTO.getAddress());
            ps.setBoolean(7, accountDTO.getIsAdmin());
            ps.setString(8, accountDTO.getEmail());
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            CloseUtils.close(ps, con);
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "delete from ACCOUNT where ACCOUNT_ID = ?";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = basicDataSource.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(id));
            ps.executeUpdate();
        } catch (SQLException ex) {
            return false;
        } finally {
            CloseUtils.close(ps, con);
        }
        return true;
    }
}
