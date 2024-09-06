package com.narae.fliwith.repository;

import com.narae.fliwith.domain.Block;
import com.narae.fliwith.domain.Review;
import com.narae.fliwith.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {
    boolean existsByBlockerAndReview(User blocker, Review review);
}
