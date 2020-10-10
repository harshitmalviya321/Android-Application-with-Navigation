package com.halo.signup;

public class DataItems {
    private String name,phone, quali, exp, specs;

    public DataItems() {}

    public DataItems(String name, String phone, String quali, String exp, String specs) {
        this.name = name;
        this.phone = phone;
        this.quali = quali;
        this.exp = exp;
        this.specs = specs;
    }

    public String getName() {
        return name;
    }

    public String getQuali() {
        return quali;
    }

    public String getExp() {
        return exp;
    }

    public String getSpecs() {
        return specs;
    }

    public String getPhone() {
        return phone;
    }
}
