/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.dao;

import com.example.demo.dto.AccountDTO;
import com.example.demo.units.CloseUtils;
import com.example.demo.units.ConnectionUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.security.crypto.password.PasswordEncoder;

@Repository
public class AccountDAOImpl implements AccountDAO {

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Override
    public List getAllAccount() {
        List<AccountDTO> accountList = new ArrayList<>();
        String sql = "SELECT *\n"
                + "FROM account\n";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            con = ConnectionUtils.getMyConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            while (rs.next()) {
                AccountDTO accountDTO = new AccountDTO();
                accountDTO.setAccountId(rs.getInt("account_id"));
                accountDTO.setEmail(rs.getString("email"));
                accountDTO.setPassWord(rs.getString("pass_word"));
                accountDTO.setFullName(rs.getNString("full_name"));
                accountDTO.setSex(rs.getNString("sex"));
                accountDTO.setBirth(rs.getDate("birth"));
                accountDTO.setPhone(rs.getString("phone"));
                accountDTO.setAddress(rs.getNString("address"));
                accountDTO.setIsAdmin(rs.getBoolean("is_admin"));
                accountList.add(accountDTO);
            }
        } catch (SQLException ex) {
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AccountDAOImpl.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            CloseUtils.close(rs, ps, con);
        }
        return accountList;

    }

    @Override
    public boolean addAccount(AccountDTO accountDTO) {
        final String SQL_INSERT = "INSERT INTO account(email,pass_word,full_name,sex,birth,phone,address,is_admin) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConnectionUtils.getConnection();
            ps = con.prepareStatement(SQL_INSERT);
            ps.setString(1, accountDTO.getEmail());
            ps.setString(2, accountDTO.getPassWord());
            ps.setNString(3, accountDTO.getFullName());
            ps.setNString(4, accountDTO.getSex());
            ps.setDate(5, accountDTO.getBirth());
            ps.setString(6, accountDTO.getPhone());
            ps.setNString(7, accountDTO.getAddress());
            ps.setBoolean(8, accountDTO.getIsAdmin());
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
        } finally {
            CloseUtils.close(ps, con);
        }
        return false;
    }

    @Override
    public boolean updateAccount(AccountDTO accountDTO) {
        final String SQL_UPDATE = "Update account \n"
                + "set pass_word = ?, full_name = ?, sex = ?, birth = ?, phone = ?, address = ?, is_admin = ? \n"
                + "where email = ?";
        Connection con = null;
        PreparedStatement ps = null;

        try {
            con = ConnectionUtils.getConnection();
            ps = con.prepareStatement(SQL_UPDATE);
            ps.setString(1, accountDTO.getPassWord());
            ps.setNString(2, accountDTO.getFullName());
            ps.setNString(3, accountDTO.getSex());
            ps.setDate(4, accountDTO.getBirth());
            ps.setString(5, accountDTO.getPhone());
            ps.setNString(6, accountDTO.getAddress());
            ps.setBoolean(7, accountDTO.getIsAdmin());
            ps.setString(8, accountDTO.getEmail());
            return ps.executeUpdate() == 1;
        } catch (SQLException ex) {
        } finally {
            CloseUtils.close(ps, con);
        }
        return false;
    }

    @Override
    public AccountDTO findById(int id) {
        String sql = "select * from account where account_id = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        AccountDTO account = new AccountDTO();
        try {
            con = ConnectionUtils.getConnection();
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                account = getAccountDTO(rs);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            CloseUtils.close(ps, con);
        }
        return account;
    }

    @Override
    public boolean delete(int id) {
        String sql = "delete from account where account_id = ?";
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = ConnectionUtils.getConnection();
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

    @Override
    public boolean checkExistAccount(String email) {
        String sql = "select * from account where email = ?";
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        AccountDTO account = new AccountDTO();
        try {
            con = ConnectionUtils.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, String.valueOf(email));
            rs = ps.executeQuery();

            while (rs.next()) {
                account = getAccountDTO(rs);
            }
            if (account == null) {
                return false;
            }
        } catch (SQLException ex) {
            return false;
        } finally {
            CloseUtils.close(ps, con);
        }
        return true;
    }

    @Override
    public boolean checkLogin(AccountDTO accountDTO, HttpServletRequest request) {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String sql = "SELECT * FROM account WHERE email=?;";
            con = ConnectionUtils.getConnection();
            ps = (PreparedStatement) con.prepareStatement(sql);
            ps.setString(1, accountDTO.getEmail());
            rs = ps.executeQuery();

            //Check account exists?
            if (rs.next()) {
                //If it exists, check password
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12); // Strength set as 12
                
                //if (accountDTO.getPassWord().equals(rs.getString("pass_word"))) {
                if (encoder.matches(accountDTO.getPassWord(), rs.getString("pass_word"))) {
                   HttpSession session = request.getSession();
                   session.setAttribute("accountId", rs.getInt("account_id"));
                   session.setAttribute("email", accountDTO.getEmail());
                   session.setAttribute("isAdmin", rs.getInt("is_admin"));
                   LOGGER.info(session.getAttribute("isAdmin").toString());
                }
            }
            return true;
        } catch (SQLException | AuthenticationException ex) {
        } finally {
            CloseUtils.close(rs, ps, con);
        }
        return false;
    }

    private AccountDTO getAccountDTO(ResultSet rs) throws SQLException {
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAccountId(rs.getInt("account_id"));
        accountDTO.setEmail(rs.getString("email"));
        accountDTO.setPassWord(rs.getString("pass_word"));
        accountDTO.setFullName(rs.getNString("full_name"));
        accountDTO.setSex(rs.getNString("sex"));
        accountDTO.setBirth(rs.getDate("birth"));
        accountDTO.setPhone(rs.getString("phone"));
        accountDTO.setAddress(rs.getNString("address"));
        accountDTO.setIsAdmin(rs.getBoolean("is_admin"));

        return accountDTO;
    }
}
