package com.bendarsianass.shops.service;

import com.bendarsianass.shops.entity.Point;
import com.bendarsianass.shops.entity.Shop;
import com.bendarsianass.shops.entity.ShopDislike;
import com.bendarsianass.shops.entity.UserEntity;
import com.bendarsianass.shops.exception.AccountServiceException;
import com.bendarsianass.shops.model.UserLocation;
import com.bendarsianass.shops.repository.ShopDislikeRepository;
import com.bendarsianass.shops.repository.ShopRepository;
import com.bendarsianass.shops.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class ShopService {

    public final double SCALING_FACTOR = 3.14159 / 180;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShopDislikeRepository shopDislikeRepository;

    @Autowired
    private UserService userService;

    public Shop saveShopAndPoint(Shop shop, Point point) {
        shop.setLocation(point);
        point.setShop(shop);
        return shopRepository.save(shop);
    }

    public List<Shop> getAllShops(UserLocation userLocation, int page) {
        return shopRepository.getAll(PageRequest.of(page,12), userLocation.getLat(), userLocation.getLon(), SCALING_FACTOR);
    }

    public void like(Long shopId, UserEntity userEntity) {
        Shop shop = shopRepository.findById(shopId).get();
        // validation
        Map<String, String> errors = new HashMap<>();
        if(userEntity.getLikedShops().contains(shop)) {
            errors.put("like", "already liked shop");
            throw new AccountServiceException(errors);
        }
        if(this.getDisliked(userEntity.getId()).contains(shop)) {
            errors.put("like", "user can't like a disliked shop");
            throw new AccountServiceException(errors);
        }
        userEntity.getLikedShops().add(shop);
        userRepository.save(userEntity);
    }

    public List<Shop> getPreferred(int page, UserEntity userEntity) {
        return shopRepository.findAllByLikesIs(PageRequest.of(page, 12) ,userEntity);
    }

    public List<Shop> getNearby(int page, UserEntity userEntity) {
        return shopRepository.findNotLiked(PageRequest.of(page, 12), userEntity.getId());
    }

    public void removeLikedShop(Long shopId, UserEntity userEntity) {
        Shop shop = shopRepository.findById(shopId).get();
        userEntity.getLikedShops().remove(shop);
        userRepository.save(userEntity);
    }

    public void dislike(Long shopId, UserEntity userEntity) {
        ShopDislike shopDislike = new ShopDislike();
        Shop shop = shopRepository.findById(shopId).get();
        // validation

        Map<String, String> errors = new HashMap<>();
        if(this.getDisliked(userEntity.getId()).contains(shop)) {
            errors.put("dislike", "already disliked shop");
            throw new AccountServiceException(errors);
        }
        if(userEntity.getLikedShops().contains(shop)) {
            errors.put("dislike", "user can't dislike a preferred shop");
            throw new AccountServiceException(errors);
        }

        shopDislike.setUser(userEntity);
        shopDislike.setShop(shop);

        shopDislikeRepository.save(shopDislike);

        userEntity.getShopDislikes().add(shopDislike);
        shop.getDislikes().add(shopDislike);

        userRepository.save(userEntity);
        shopRepository.save(shop);
    }

    public List<Shop> getDisliked(Long userId) {
        List<ShopDislike> shopDislikes = shopDislikeRepository.findAllByUserIdAndCreatedAtAfter(userId, new Date(System.currentTimeMillis()-2*60*60*1000));
        List<Shop> dislikedShops = new ArrayList<>();
        for (ShopDislike shopDislike:shopDislikes) {
            dislikedShops.add(shopDislike.getShop());
        }
        return dislikedShops;
    }

    public List<Shop> getAllNearbyNotDislikedBeforeAndSorted(UserLocation userLocation, int page, UserEntity userEntity) {

        return shopRepository.findAllNearbyNotLikedAndNotDislikedBeforeAndSorted(
                userEntity.getId(),
                userLocation.getLat(),
                userLocation.getLon(),
                SCALING_FACTOR,
                PageRequest.of(page, 12));
    }
}
