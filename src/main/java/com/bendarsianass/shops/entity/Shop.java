package com.bendarsianass.shops.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String picture;
    private String name;
    private String email;
    private String city;
    @OneToOne(mappedBy = "shop", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Point location;
    @ManyToMany(mappedBy = "likedShops")
    @JsonIgnore
    private List<UserEntity> likes = new ArrayList<>();

    @OneToMany(mappedBy = "shop")
    private List<ShopDislike> dislikes = new ArrayList<>();

    public Shop() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public List<UserEntity> getLikes() {
        return likes;
    }

    public void setLikes(List<UserEntity> likes) {
        this.likes = likes;
    }

    public List<ShopDislike> getDislikes() {
        return dislikes;
    }

    public void setDislikes(List<ShopDislike> dislikes) {
        this.dislikes = dislikes;
    }
}
