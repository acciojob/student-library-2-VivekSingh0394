package com.driver.RequestDto;

public class StudentRequestDto {
    private String name;
    private int age;
    private String country;
    private String email;

    public StudentRequestDto(String name, int age, String country, String email) {
        this.name = name;
        this.age = age;
        this.country = country;
        this.email = email;
    }

    public StudentRequestDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
