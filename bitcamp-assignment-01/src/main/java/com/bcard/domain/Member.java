package com.bcard.domain;

public class Member {

    protected String name;
    protected String email;
    protected String password;
    
    protected String cell;
    protected String tel;
    protected String fax;
    protected String memo;
    protected String rem;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public Member setEmail(String email) {
        this.email = email;
        return this;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getCell() {
        return cell;
    }
    public void setCell(String cell) {
        this.cell = cell;
    }
    public String getTel() {
        return tel;
    }
    public void setTel(String tel) {
        this.tel = tel;
    }
    public String getFax() {
        return fax;
    }
    public void setFax(String fax) {
        this.fax = fax;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getRem() {
        return rem;
    }
    public void setRem(String rem) {
        this.rem = rem;
    }
    
    @Override
    public String toString() {
        return "Member [name=" + name + ", email=" + email + ", password=" + password + ", cell=" + cell + ", tel="
                + tel + ", fax=" + fax + ", memo=" + memo + ", rem=" + rem + "]";
    }
}
