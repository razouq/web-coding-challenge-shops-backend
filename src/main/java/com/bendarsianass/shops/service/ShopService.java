package com.bendarsianass.shops.service;

import com.bendarsianass.shops.model.Point;
import com.bendarsianass.shops.model.Shop;
import com.bendarsianass.shops.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopService {

    @Autowired
    private ShopRepository shopRepository;

    public Shop saveShopAndPoint(Shop shop, Point point) {
        shop.setLocation(point);
        point.setShop(shop);
        return shopRepository.save(shop);
    }
}
