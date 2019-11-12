package com.bendarsianass.shops.repository;

import com.bendarsianass.shops.entity.ShopDislike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface ShopDislikeRepository extends JpaRepository<ShopDislike, Long> {
    List<ShopDislike> findByUserIdAndCreatedAtAfter(Long userId, Date date);
}
