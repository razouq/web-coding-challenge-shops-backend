package com.bendarsianass.shops.repository;

import com.bendarsianass.shops.entity.ShopDislike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopDislikeRepository extends JpaRepository<ShopDislike, Long> {
}
