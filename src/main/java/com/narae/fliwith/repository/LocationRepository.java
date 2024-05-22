package com.narae.fliwith.repository;

import com.narae.fliwith.domain.Location;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {
    @Query(value = "select * from location l " +
            "where (6371 * acos(cos(radians(:curLat)) * cos(radians(l.latitude)) " +
            "* cos(radians(l.longitude) - radians(:curLong)) " +
            "+ sin(radians(:curLat)) * sin(radians(l.latitude)))) <= 3", nativeQuery = true)
    List<Location> findNearEverySpotType(@Param("curLat")Double curLatitude, @Param("curLong")Double curLongitude);

}
