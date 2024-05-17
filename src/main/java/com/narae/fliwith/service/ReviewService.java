package com.narae.fliwith.service;

import com.narae.fliwith.domain.Image;
import com.narae.fliwith.domain.Review;
import com.narae.fliwith.domain.Spot;
import com.narae.fliwith.domain.User;
import com.narae.fliwith.dto.ReviewReq;
import com.narae.fliwith.dto.ReviewRes;
import com.narae.fliwith.exception.review.ReviewAccessFailException;
import com.narae.fliwith.exception.review.ReviewFindFailException;
import com.narae.fliwith.exception.spot.SpotFindFailException;
import com.narae.fliwith.exception.user.LogInFailException;
import com.narae.fliwith.repository.ReviewRepository;
import com.narae.fliwith.repository.SpotRepository;
import com.narae.fliwith.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.stream.Collectors;

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

    public ReviewRes.ReviewDetailRes getReviewDetail(Principal principal, Long reviewId) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(LogInFailException::new);

        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewFindFailException::new);

        boolean isMine = user.getId().equals(review.getUser().getId());

        return ReviewRes.ReviewDetailRes.builder()
                .spotName(review.getSpot().getTitle())
                .content(review.getContent())
                .disability(review.getUser().getDisability())
                .createdAt(review.getCreatedAt())
                .images(review.getImages().stream().map(Image::getUrl).collect(Collectors.toList()))
                .nickname(review.getUser().getNickname())
                .isMine(isMine)
                .likes(review.getLikes())
                .build();
    }

    public void deleteReview(Principal principal, Long reviewId) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(LogInFailException::new);

        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewFindFailException::new);

        boolean isMine = user.getId().equals(review.getUser().getId());
        if(!isMine){
            throw new ReviewAccessFailException();
        }

        reviewRepository.delete(review);

    }

    public ReviewRes.ReviewDetailRes updateReview(Principal principal, Long reviewId, ReviewReq.WriteReviewReq req) {

        User user = userRepository.findByEmail(principal.getName()).orElseThrow(LogInFailException::new);

        Review review = reviewRepository.findById(reviewId).orElseThrow(ReviewFindFailException::new);

        boolean isMine = user.getId().equals(review.getUser().getId());
        if(!isMine){
            throw new ReviewAccessFailException();
        }

        ReviewReq.UpdateReviewReq updateReviewReq =
                ReviewReq.UpdateReviewReq.builder()
                        .content(req.getContent())
                        .spot(spotRepository.findById(req.getContentId()).orElseThrow(SpotFindFailException::new))
                        .build();

        review.updateReview(updateReviewReq);
        return ReviewRes.ReviewDetailRes.builder()
                .spotName(review.getSpot().getTitle())
                .content(review.getContent())
                .disability(review.getUser().getDisability())
                .createdAt(review.getCreatedAt())
                .images(review.getImages().stream().map(Image::getUrl).collect(Collectors.toList()))
                .nickname(review.getUser().getNickname())
                .isMine(isMine)
                .likes(review.getLikes())
                .build();

    }


}
