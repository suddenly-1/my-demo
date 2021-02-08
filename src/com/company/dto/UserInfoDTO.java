package com.company.dto;

public class UserInfoDTO {

    private String name;

    private String sex;

    private Integer age;

    private Long a;

    public UserInfoDTO() {
    }

    public UserInfoDTO(String name, String sex, Integer age, Long a) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.a = a;
    }

    public Long getA() {
        return a;
    }

    public void setA(Long a) {
        this.a = a;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
