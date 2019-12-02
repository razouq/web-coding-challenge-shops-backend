package com.bendarsianass.shops.controller;

import com.bendarsianass.shops.entity.Shop;
import com.bendarsianass.shops.entity.UserEntity;
import com.bendarsianass.shops.model.UserLocation;
import com.bendarsianass.shops.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/shops")
@CrossOrigin(origins = "*")
public class ShopController {

    @Autowired
    private ShopService shopService;

    @PostMapping("/nearby")
    public List<Shop> getNearby(@RequestBody UserLocation userLocation, @RequestParam int page, HttpServletRequest request) {
        UserEntity userEntity = (UserEntity) request.getAttribute("currentUser");
        return shopService.getAllNearbyNotDislikedBeforeAndSorted(userLocation, page, userEntity);
    }

    @GetMapping("/preferred")
    public List<Shop> getPreferred(@RequestParam int page, HttpServletRequest request) {
        UserEntity userEntity = (UserEntity) request.getAttribute("currentUser");
        return shopService.getPreferred(page, userEntity);
    }

    @GetMapping("/like/{shopId}")
    public void like(@PathVariable Long shopId, HttpServletRequest request) {
        UserEntity userEntity = (UserEntity) request.getAttribute("currentUser");
        shopService.like(shopId, userEntity);
    }

    @GetMapping("/dislike/{shopId}")
    public void dislike(@PathVariable Long shopId, HttpServletRequest request) {
        UserEntity userEntity = (UserEntity) request.getAttribute("currentUser");
        shopService.dislike(shopId, userEntity);
    }

    @GetMapping("remove/{shopId}")
    public void removeLikedShop(@PathVariable Long shopId, HttpServletRequest request) {
        UserEntity userEntity = (UserEntity) request.getAttribute("currentUser");
        shopService.removeLikedShop(shopId, userEntity);
    }
}
