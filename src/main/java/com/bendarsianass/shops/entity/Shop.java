package com.bendarsianass.shops.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    @ManyToMany(mappedBy = "likedShops", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<UserEntity> likes = new ArrayList<>();

    @OneToMany(mappedBy = "shop", fetch = FetchType.EAGER)
    @Fetch(value = FetchMode.SUBSELECT)
    @JsonIgnore
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shop shop = (Shop) o;
        return getId().equals(shop.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Shop{" +
                "id=" + id +
                ", picture='" + picture + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", city='" + city + '\'' +
                ", location=" + location +
                ", likes=" + likes +
                ", dislikes=" + dislikes +
                '}';
    }
}
