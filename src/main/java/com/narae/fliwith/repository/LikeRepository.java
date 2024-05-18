package com.narae.fliwith.repository;

import com.narae.fliwith.domain.Like;
import com.narae.fliwith.domain.Review;
import com.narae.fliwith.domain.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Optional<Like> findByLikerAndReview(User liker, Review review);
}
