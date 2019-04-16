package com.adb.group12w2019mad3125.Model;

public class Orders {

    private String name,phone,address,totalAmount,cardNumber;

    public Orders(String name, String phone, String address, String totalAmount, String cardNumber) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.totalAmount = totalAmount;
        this.cardNumber = cardNumber;
    }

    public Orders() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
}