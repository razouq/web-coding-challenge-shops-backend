package com.bendarsianass.shops.service;

import com.bendarsianass.shops.model.Point;
import com.bendarsianass.shops.model.Shop;
import com.bendarsianass.shops.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    public final double SCALING_FACTOR = 3.14159 / 180;

    @Autowired
    private ShopRepository shopRepository;

    public Shop saveShopAndPoint(Shop shop, Point point) {
        shop.setLocation(point);
        point.setShop(shop);
        return shopRepository.save(shop);
    }

    public List<Shop> getAllShops(double lat, double lon) {
        return shopRepository.getAll(lat, lon, SCALING_FACTOR);
    }
}
