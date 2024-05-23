package com.narae.fliwith.repository;

import com.narae.fliwith.domain.Like;
import com.narae.fliwith.domain.Review;
import com.narae.fliwith.domain.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByLikerAndReview(User liker, Review review);

    @Query("SELECT l.review FROM Like l WHERE l.liker = :user ORDER BY l.review.createdAt desc")
    Page<Review> findLikedReviewsByUserOrderByCreatedAtDesc(User user, Pageable pageable);

    boolean existsByLikerAndReview(User liker, Review review);
}
