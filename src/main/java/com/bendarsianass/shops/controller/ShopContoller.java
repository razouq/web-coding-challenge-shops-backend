package com.bendarsianass.shops.controller;

import com.bendarsianass.shops.entity.Shop;
import com.bendarsianass.shops.model.UserLocation;
import com.bendarsianass.shops.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/shops")
@CrossOrigin(origins = "*")
public class ShopContoller {

    @Autowired
    private ShopService shopService;

    @PostMapping("/getNearby")
    public List<Shop> getNearby(@RequestBody UserLocation userLocation, @RequestParam int page, HttpServletRequest request) {
        return shopService.getAllNearbyNotDislikedBeforeAndSorted(userLocation, page, request);
    }

    @GetMapping("/getPreferred")
    public List<Shop> getPreferred(@RequestParam int page, HttpServletRequest request) {
        return shopService.getPreferred(page, request);
    }

    @GetMapping("/like/{shopId}")
    public void like(@PathVariable Long shopId, HttpServletRequest request) {
        shopService.like(shopId, request);
    }

    @GetMapping("/dislike/{shopId}")
    public void dislike(@PathVariable Long shopId, HttpServletRequest request) {
        shopService.dislike(shopId, request);
    }

    @GetMapping("removeLikedShop/{shopId}")
    public void removeLikedShop(@PathVariable Long shopId, HttpServletRequest request) {
        shopService.removeLikedShop(shopId, request);
    }


    /*
    Test Functions
     */
    @PostMapping("/all")
    public List<Shop> all(@RequestBody UserLocation userLocation, @RequestParam int page) {
        return shopService.getAllShops(userLocation, page);
    }

    @GetMapping("getDisliked/{userId}")
    public List<Shop> getDisliked(@PathVariable Long userId) {
        return shopService.getDisliked(userId);
    }
}
