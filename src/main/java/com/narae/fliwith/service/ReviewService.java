package com.narae.fliwith.service;

import com.narae.fliwith.domain.Review;
import com.narae.fliwith.domain.Spot;
import com.narae.fliwith.domain.User;
import com.narae.fliwith.dto.ReviewReq;
import com.narae.fliwith.exception.spot.SpotFindFailException;
import com.narae.fliwith.exception.user.LogInFailException;
import com.narae.fliwith.repository.ReviewRepository;
import com.narae.fliwith.repository.SpotRepository;
import com.narae.fliwith.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final SpotRepository spotRepository;


    public void writeReview(Principal principal, ReviewReq.WriteReviewReq req) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(LogInFailException::new);
        Spot spot = spotRepository.findById(req.getContentId()).orElseThrow(SpotFindFailException::new);
        Review review = Review.builder().likes(0L).content(req.getContent()).user(user).spot(spot).build();
        reviewRepository.save(review);
    }
}
