package com.narae.fliwith.repository;

import com.narae.fliwith.domain.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByOrderByCreatedAtDesc(Pageable pageable);
    Page<Review> findAllByOrderByLikesDescCreatedAtDesc(Pageable pageable);
}
