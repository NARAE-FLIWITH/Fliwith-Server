package com.narae.fliwith.service;

import com.narae.fliwith.domain.Image;
import com.narae.fliwith.domain.Review;
import com.narae.fliwith.domain.Spot;
import com.narae.fliwith.domain.User;
import com.narae.fliwith.dto.ReviewReq;
import com.narae.fliwith.dto.ReviewRes;
import com.narae.fliwith.dto.ReviewRes.ReviewItem;
import com.narae.fliwith.dto.ReviewRes.ReviewItemRes;
import com.narae.fliwith.dto.TourRes.TourName;
import com.narae.fliwith.exception.review.ReviewAccessFailException;
import com.narae.fliwith.exception.review.ReviewFindFailException;
import com.narae.fliwith.exception.review.ReviewListException;
import com.narae.fliwith.exception.spot.SpotFindFailException;
import com.narae.fliwith.exception.user.LogInFailException;
import com.narae.fliwith.repository.ReviewRepository;
import com.narae.fliwith.repository.SpotRepository;
import com.narae.fliwith.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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


    public ReviewItemRes getReviewList(Principal principal, int pageNo, String order) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(LogInFailException::new);

        Pageable pageable = PageRequest.of(pageNo, 10); // 0은 페이지 번호, 10은 페이지 크기

        Page<Review> reviewsPage;
        List<Review> reviews;
        //최신순 recent
        if("recent".equals(order)){
            reviewsPage = reviewRepository.findAllByOrderByCreatedAtDesc(pageable);
            reviews = reviewsPage.getContent();
            return ReviewItemRes.builder()
                    .reviews(reviews.stream().map(review -> new ReviewItem(review.getImages().get(0).getUrl(), review.getUser().getNickname(), review.getUser().getDisability(), review.getLikes())).collect(
                            Collectors.toList()))
                    .pageNo(pageNo)
                    .lastPageNo(pageable.getPageSize())
                    .build();


        }

        //인기순 like
        if("like".equals(order)){
            reviewsPage = reviewRepository.findAllByOrderByLikesDesc(pageable);
            reviews = reviewsPage.getContent();
            return ReviewItemRes.builder()
                    .reviews(reviews.stream().map(review -> new ReviewItem(review.getImages().get(0).getUrl(), review.getUser().getNickname(), review.getUser().getDisability(), review.getLikes())).collect(
                            Collectors.toList()))
                    .pageNo(pageNo)
                    .lastPageNo(pageable.getPageSize())
                    .build();
        }

        throw new ReviewListException();
    }

    public List<TourName> getSpotName(Principal principal, String spotName) {
        User user = userRepository.findByEmail(principal.getName()).orElseThrow(LogInFailException::new);

        List<Spot> spots = spotRepository.findAllByTitleContains(spotName);

        return spots.stream().map(spot-> TourName.builder().name(spot.getTitle()).contentId(spot.getId()).build()).collect(
                Collectors.toList());


    }
}
