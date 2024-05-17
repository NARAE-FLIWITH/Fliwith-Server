package com.narae.fliwith.repository;

import com.narae.fliwith.domain.Spot;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpotRepository extends JpaRepository<Spot, Integer> {
    List<Spot> findAllByTitleContains(String title);

}
