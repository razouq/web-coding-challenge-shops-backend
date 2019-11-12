package com.bendarsianass.shops.controller;

import com.bendarsianass.shops.entity.Shop;
import com.bendarsianass.shops.entity.ShopDislike;
import com.bendarsianass.shops.entity.UserEntity;
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

//    @GetMapping("getNearby/{userId}")
//    public List<Shop> getNearby(@PathVariable Long userId, @RequestParam int page) {
//        return shopService.getNearby(userId, page);
//    }

    @GetMapping("removeLikedShop/{userId}/{shopId}")
    public void removeLikedShop(@PathVariable Long userId, @PathVariable Long shopId) {
        shopService.removeLikedShop(userId, shopId);
    }

    @GetMapping("dislike/{userId}/{shopId}")
    public void dislike(@PathVariable Long userId, @PathVariable Long shopId) {
        shopService.dislike(userId, shopId);
    }

    @GetMapping("getDisliked/{userId}")
    public List<Shop> getDisliked(@PathVariable Long userId) {
        return shopService.getDisliked(userId);
    }

    @PostMapping("getNearby/{userId}")
    public List<Shop> getNearby(@PathVariable Long userId, @RequestBody UserLocation userLocation, @RequestParam int page) {
        return shopService.getAllNearbyNotDislikedBeforeAndSorted(userId, userLocation, page);
    }
}
