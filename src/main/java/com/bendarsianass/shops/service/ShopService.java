package com.bendarsianass.shops.service;

import com.bendarsianass.shops.entity.Point;
import com.bendarsianass.shops.entity.Shop;
import com.bendarsianass.shops.entity.ShopDislike;
import com.bendarsianass.shops.entity.UserEntity;
import com.bendarsianass.shops.model.UserLocation;
import com.bendarsianass.shops.repository.ShopDislikeRepository;
import com.bendarsianass.shops.repository.ShopRepository;
import com.bendarsianass.shops.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public void like(Long shopId, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        Long userId = userService.findUserEntityIdFromToken(token);
        UserEntity user = userRepository.findById(userId).get();
        Shop shop = shopRepository.findById(shopId).get();
        user.getLikedShops().add(shop);
        userRepository.save(user);
    }

    public List<Shop> getPreferred(int page, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        Long userId = userService.findUserEntityIdFromToken(token);
        UserEntity userEntity = userRepository.findById(userId).get();
        return shopRepository.findAllByLikesIs(PageRequest.of(page, 12) ,userEntity);
    }

    public List<Shop> getNearby(int page, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        Long userId = userService.findUserEntityIdFromToken(token);
        return shopRepository.findNotLiked(PageRequest.of(page, 12), userId);
    }

    public void removeLikedShop(Long shopId, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        Long userId = userService.findUserEntityIdFromToken(token);
        UserEntity userEntity = userRepository.findById(userId).get();
        Shop shop = shopRepository.findById(shopId).get();
        userEntity.getLikedShops().remove(shop);
        userRepository.save(userEntity);
    }

    public void dislike(Long shopId, HttpServletRequest request) {

        String token = request.getHeader("Authorization").substring("Bearer ".length());
        Long userId = userService.findUserEntityIdFromToken(token);

        ShopDislike shopDislike = new ShopDislike();

        UserEntity userEntity = userRepository.findById(userId).get();
        Shop shop = shopRepository.findById(shopId).get();

        shopDislike.setUser(userEntity);
        shopDislike.setShop(shop);

        shopDislikeRepository.save(shopDislike);

        userEntity.getShopDislikes().add(shopDislike);
        shop.getDislikes().add(shopDislike);

        userRepository.save(userEntity);
        shopRepository.save(shop);
    }

    public List<Shop> getDisliked(Long userId) {
        List<ShopDislike> shopDislikes = shopDislikeRepository.findByUserIdAndCreatedAtAfter(userId, new Date(System.currentTimeMillis()-10*1000));
        List<Shop> dislikedShops = new ArrayList<>();
        for (ShopDislike shopDislike:shopDislikes) {
            dislikedShops.add(shopDislike.getShop());
        }
        return dislikedShops;
    }

    public List<Shop> getAllNearbyNotDislikedBeforeAndSorted(UserLocation userLocation, int page, HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring("Bearer ".length());
        Long id = userService.findUserEntityIdFromToken(token);
        System.out.println("id : " + id);
        return shopRepository.findAllNearbyNotLikedAndNotDislikedBeforeAndSorted(
                id,
                userLocation.getLat(),
                userLocation.getLon(),
                SCALING_FACTOR,
                PageRequest.of(page, 12));
    }
}
