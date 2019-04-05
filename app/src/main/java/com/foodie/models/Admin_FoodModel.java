package com.foodie.models;

public class Admin_FoodModel {
    String food, quantity, price;

    public Admin_FoodModel() {
    }

    public Admin_FoodModel(String food, String quantity, String price) {
        this.food = food;
        this.quantity = quantity;
        this.price = price;
    }

    public String getFood() {
        return food;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getPrice() {
        return price;
    }
}
