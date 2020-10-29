package com.company;

import java.math.BigDecimal;
import java.util.Date;

public class User {
    private Integer id;
    private String name;
    private Integer age;
    private String sex;
    private BigDecimal amount;
    private Date date;

    public User() {
    }

    public User(Integer id, String name, Integer age, String sex, BigDecimal amount, Date date) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.sex = sex;
        this.amount = amount;
        this.date = date;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                ", amount=" + amount +
                ", date=" + date +
                '}';
    }
}
