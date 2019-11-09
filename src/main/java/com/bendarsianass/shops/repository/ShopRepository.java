package com.bendarsianass.shops.repository;

import com.bendarsianass.shops.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShopRepository extends JpaRepository<Shop, Long> {
    @Query(
            value = "SELECT * FROM shop s LEFT OUTER JOIN point p ON s.id = p.shop_id " +
                    "ORDER BY ACOS(SIN(p.lat*:sf)*SIN(:lat*:sf) + COS(p.lat*:sf)*COS(:lat*:sf)*COS((p.lon-:lon)*:sf))",
            nativeQuery = true
    )
    List<Shop> getAll(@Param("lat") double lat, @Param("lon") double lon, @Param("sf") double sf);
}
