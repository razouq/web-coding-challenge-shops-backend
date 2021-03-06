package com.bendarsianass.shops.repository;

import com.bendarsianass.shops.entity.Shop;
import com.bendarsianass.shops.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {

    List<Shop> findAllByLikesIs(Pageable pageable ,UserEntity userEntity);

    @Query(
            value = "select shop.*  " +
                    "    from shop " +
                    "    left outer join " +
                    "        shop_like on shop.id=shop_like.shop_id " +
                    "    left outer join " +
                    "        user_entity userlike on shop_like.user_id=userlike.id " +
                    "    left outer join " +
                    "        shop_dislike on shop.id=shop_dislike.shop_id " +
                    "    left outer join " +
                    "        user_entity userdislike on shop_dislike.user_id=userdislike.id " +
                    "    LEFT OUTER JOIN point p ON shop.id = p.shop_id " +
                    "where (userlike.id<>:user_id or userlike.id is null) " +
                    "   and shop.id not in (select shop.id from shop left outer join shop_like on shop.id=shop_like.shop_id " +
                    "                       left outer join user_entity on shop_like.user_id=user_entity.id " +
                    "                        where user_entity.id = :user_id) " +
                    "   and ((userdislike.id<>:user_id   or userdislike.id is null) " +
                    "   or (userdislike.id=:user_id and NOW()>TIMESTAMPADD( Hour, 2, shop_dislike.created_at))) " +
                    "ORDER BY ACOS(SIN(p.lat*:sf)*SIN(:lat*:sf) + COS(p.lat*:sf)*COS(:lat*:sf)*COS((p.lon-:lon)*:sf))",
            nativeQuery = true
    )
    List<Shop> findAllNearbyNotLikedAndNotDislikedBeforeAndSorted(@Param("user_id") Long user_id,
                                                                  @Param("lat") double lat,
                                                                  @Param("lon") double lon,
                                                                  @Param("sf") double sf,
                                                                  Pageable pageable);

    /*
    Test Functions
     */
    @Query(
            value = "SELECT * FROM shop s LEFT OUTER JOIN point p ON s.id = p.shop_id " +
                    "ORDER BY ACOS(SIN(p.lat*:sf)*SIN(:lat*:sf) + COS(p.lat*:sf)*COS(:lat*:sf)*COS((p.lon-:lon)*:sf))",
            nativeQuery = true
    )
    List<Shop> getAll(Pageable pageable, @Param("lat") double lat, @Param("lon") double lon, @Param("sf") double sf);

    @Query(
            value = "select shop.* from shop " +
                    "    left outer join " +
                    "        shop_like on shop.id=shop_like.shop_id " +
                    "    left outer join " +
                    "        user_entity on shop_like.user_id=user_entity.id " +
                    "    where (user_entity.id<>:user_id or user_entity.id is null) " +
                    "            and shop.id not in ( " +
                    "                select shop.id from shop " +
                    "                    left outer join " +
                    "                        shop_like on shop.id=shop_like.shop_id " +
                    "                    left outer join " +
                    "                        user_entity on shop_like.user_id=user_entity.id " +
                    "                    where user_entity.id = :user_id " +
                    "            )",
            nativeQuery = true
    )
    List<Shop> findNotLiked(Pageable pageable, @Param("user_id") Long user_id);
}
