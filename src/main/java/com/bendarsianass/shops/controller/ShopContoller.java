package com.bendarsianass.shops.controller;

import com.bendarsianass.shops.entity.Shop;
import com.bendarsianass.shops.model.UserLocation;
import com.bendarsianass.shops.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopContoller {

    @Autowired
    private ShopService shopService;

    @PostMapping("/all")
    public List<Shop> all(@RequestBody UserLocation userLocation, @RequestParam int page) {
        return shopService.getAllShops(userLocation, page);
    }

    @GetMapping("/like/{userId}/{shopId}")
    public void like(@PathVariable Long userId, @PathVariable Long shopId) {
        shopService.like(userId, shopId);
    }

    @GetMapping("/getPreferred/{userId}")
    public List<Shop> getPreferred(@PathVariable Long userId, @RequestParam int page) {
        return shopService.getPreferred(userId, page);
    }
}
