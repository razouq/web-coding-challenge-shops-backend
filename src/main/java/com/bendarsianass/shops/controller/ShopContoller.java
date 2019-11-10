package com.bendarsianass.shops.controller;

import com.bendarsianass.shops.entity.Shop;
import com.bendarsianass.shops.model.UserLocation;
import com.bendarsianass.shops.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopContoller {

    @Autowired
    private ShopService shopService;

    @PostMapping("/all")
    public List<Shop> all(@RequestBody UserLocation userLocation, @RequestParam int page) {
        System.out.println(userLocation.getLat()+ " " + userLocation.getLon());
        return shopService.getAllShops(userLocation.getLat(), userLocation.getLon(), page);
    }
}
