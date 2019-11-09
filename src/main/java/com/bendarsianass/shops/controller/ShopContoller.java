package com.bendarsianass.shops.controller;

import com.bendarsianass.shops.model.Shop;
import com.bendarsianass.shops.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/shops")
public class ShopContoller {

    @Autowired
    private ShopService shopService;

    @GetMapping("/all")
    public List<Shop> all(@RequestParam double lat, @RequestParam double lon, @RequestParam int page) {
        System.out.println(lat+ " " + lon);
        return shopService.getAllShops(lat, lon, page);
    }
}
