package com.narae.fliwith.repository;

import com.narae.fliwith.domain.Review;
import com.narae.fliwith.domain.Spot;
import com.narae.fliwith.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findAllByUserOrderByCreatedAtDesc(User user, Pageable pageable);

    void deleteAllByUser(User user);

    @Query("SELECT r FROM Review r WHERE r.id NOT IN (SELECT b.review.id FROM Block b WHERE b.blocker = :blocker) ORDER BY r.createdAt DESC")
    Page<Review> findAllOrderByCreatedAtDescExcludingBlocked(User blocker,  Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.id NOT IN (SELECT b.review.id FROM Block b WHERE b.blocker = :blocker) ORDER BY SIZE(r.likes) DESC")
    Page<Review> findAllOrderByLikesDescExcludingBlocked(User blocker,  Pageable pageable);


    @Query("SELECT r FROM Review r WHERE r.spot = :spot AND r.id NOT IN (SELECT b.review.id FROM Block b WHERE b.blocker = :blocker) ORDER BY r.createdAt DESC")
    Page<Review> findAllBySpotOrderByCreatedAtDescExcludingBlocked(Spot spot, User blocker, Pageable pageable);

}
