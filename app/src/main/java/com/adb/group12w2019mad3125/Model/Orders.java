package com.adb.group12w2019mad3125.Model;

public class Orders {

    private String username,phone,address,totalAmount,cardName;

    public Orders(String pname, String username, String phone, String address, String totalAmount, String cardName) {

        this.username = username;
        this.phone = phone;
        this.address = address;
        this.totalAmount = totalAmount;
        this.cardName = cardName;
    }

    public Orders() {
    }





    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }
}
