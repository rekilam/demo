package com.example.demo.dto;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDTO {

    private int accountId;
    private String email;
    private String passWord;
    private String fullName;
    private String sex;
    private Date birth;
    private String phone;
    private String address;
    private boolean isAdmin;

    public AccountDTO() {
        super();
        // TODO Auto-generated constructor stub
    }

    public AccountDTO(int accountId, String email, String passWord, String fullName, String sex,
            Date birth, String phone, String address, boolean isAdmin) {
        super();
        this.accountId = accountId;
        this.email = email;
        this.passWord = passWord;
        this.fullName = fullName;
        this.sex = sex;
        this.birth = birth;
        this.phone = phone;
        this.address = address;
        this.isAdmin = isAdmin;
    }

    public AccountDTO getAccountDTO(ResultSet rs) throws SQLException {
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

        return accountDTO;
    }
    
    /**
     * @return the accountId
     */
    public int getAccountId() {
        return accountId;
    }

    /**
     * @param accountId the accountId to set
     */
    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    /**
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email the email to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return the passWord
     */
    public String getPassWord() {
        return passWord;
    }

    /**
     * @param passWord the passWord to set
     */
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the sex
     */
    public String getSex() {
        return sex;
    }

    /**
     * @param sex the sex to set
     */
    public void setSex(String sex) {
        this.sex = sex;
    }

    /**
     * @return the birth
     */
    public Date getBirth() {
        return birth;
    }

    /**
     * @param birth the birth to set
     */
    public void setBirth(Date birth) {
        this.birth = birth;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the address
     */
    public boolean getIsAdmin() {
        return isAdmin;
    }

    /**
     * @param isAdmin the address to set
     */
    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
