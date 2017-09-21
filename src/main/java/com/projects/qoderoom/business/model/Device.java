package com.projects.qoderoom.business.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="devices")
public class Device implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private double price;
    private int reviews;
    private String name;
    private String category;

    public Device(double price, int reviews, String name, String category){
        if(name == null || category == null){
            throw new IllegalArgumentException("Name or category is null");
        }

        this.price = price;
        this.reviews = reviews;
        this.name = name;
        this.category = category;
    }

    public Device(){}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "Device{" +
                "id=" + id +
                ", price=" + price +
                ", reviews=" + reviews +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                '}';
    }
}
