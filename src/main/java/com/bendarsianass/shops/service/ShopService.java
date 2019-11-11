package com.bendarsianass.shops.service;

import com.bendarsianass.shops.entity.Point;
import com.bendarsianass.shops.entity.Shop;
import com.bendarsianass.shops.entity.UserEntity;
import com.bendarsianass.shops.model.UserLocation;
import com.bendarsianass.shops.repository.ShopRepository;
import com.bendarsianass.shops.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    public final double SCALING_FACTOR = 3.14159 / 180;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    public Shop saveShopAndPoint(Shop shop, Point point) {
        shop.setLocation(point);
        point.setShop(shop);
        return shopRepository.save(shop);
    }

    public List<Shop> getAllShops(UserLocation userLocation, int page) {
        return shopRepository.getAll(PageRequest.of(page,12), userLocation.getLat(), userLocation.getLon(), SCALING_FACTOR);
    }

    public void like(Long userId, Long shopId) {
        UserEntity user = userRepository.findById(userId).get();
        Shop shop = shopRepository.findById(shopId).get();
        user.getLikedShops().add(shop);
        userRepository.save(user);
    }

    public List<Shop> getPreferred(Long userId, int page) {
        UserEntity userEntity = userRepository.findById(userId).get();
        return shopRepository.findAllByLikesIs(PageRequest.of(page, 12) ,userEntity);
    }

    public List<Shop> getNearby(Long userId, int page) {
        return shopRepository.findNotLiked(PageRequest.of(page, 12), userId);
    }

    public void removeLikedShop(Long userId, Long shopId) {
        UserEntity userEntity = userRepository.findById(userId).get();
        Shop shop = shopRepository.findById(shopId).get();
        userEntity.getLikedShops().remove(shop);
        userRepository.save(userEntity);
    }
}
