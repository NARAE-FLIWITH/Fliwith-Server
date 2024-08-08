package com.narae.fliwith.controller;

import com.narae.fliwith.config.security.dto.CustomUser;
import com.narae.fliwith.dto.ImageReq.PresignedUrlReq;
import com.narae.fliwith.dto.ImageRes;
import com.narae.fliwith.dto.ReviewReq;
import com.narae.fliwith.dto.ReviewRes;
import com.narae.fliwith.dto.ReviewRes.ReviewItemRes;
import com.narae.fliwith.dto.TourRes.TourName;
import com.narae.fliwith.dto.base.BaseRes;
import com.narae.fliwith.service.ReviewService;
import com.narae.fliwith.service.S3Service;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;
    private final S3Service s3Service;

    @PostMapping()
    public ResponseEntity<BaseRes<Void>> createReview(@AuthenticationPrincipal CustomUser customUser, @RequestBody ReviewReq.WriteReviewReq writeReviewReq) {
        reviewService.writeReview(customUser, writeReviewReq);
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "리뷰를 작성하는데 성공했습니다."));
    }
    @GetMapping("/{reviewId}")
    public ResponseEntity<BaseRes<ReviewRes.ReviewDetailRes>> getReview(@AuthenticationPrincipal CustomUser customUser, @PathVariable Long reviewId) {
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "리뷰 상세조회에 성공했습니다.", reviewService.getReviewDetail(customUser, reviewId)));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<BaseRes<Void>> deleteReview(@AuthenticationPrincipal CustomUser customUser, @PathVariable Long reviewId) {
        reviewService.deleteReview(customUser, reviewId);
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "리뷰 삭제에 성공했습니다."));
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<BaseRes<ReviewRes.ReviewDetailRes>> updateReview(@AuthenticationPrincipal CustomUser customUser, @PathVariable Long reviewId, @RequestBody ReviewReq.WriteReviewReq writeReviewReq) {
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "리뷰 수정에 성공했습니다.", reviewService.updateReview(customUser, reviewId, writeReviewReq)));
    }

    @GetMapping()
    public ResponseEntity<BaseRes<List<TourName>>> getSpotName(@AuthenticationPrincipal CustomUser customUser, @RequestParam String spotName) {
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "관광지 이름 목록 조회에 성공했습니다.", reviewService.getSpotName(customUser, spotName)));
    }

    @GetMapping("/list")
    public ResponseEntity<BaseRes<ReviewItemRes>> getReviewList(@AuthenticationPrincipal CustomUser customUser, @RequestParam int pageNo, @RequestParam String order){
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "리뷰 목록 조회에 성공했습니다.", reviewService.getReviewList(customUser, pageNo, order)));
    }

    @PostMapping("/presigned")
    public ResponseEntity<BaseRes<ImageRes.PresignedUrlRes>> updateReview(@AuthenticationPrincipal CustomUser customUser, @RequestBody PresignedUrlReq presignedUrlReq) {
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "presignedUrl 생성에 성공했습니다.", s3Service.issuePresignedUrl(customUser, presignedUrlReq)));
    }

    @PostMapping("/{reviewId}")
    public ResponseEntity<BaseRes<ReviewRes.LikeUnlikeRes>> likeUnlikeReview(@AuthenticationPrincipal CustomUser customUser, @PathVariable Long reviewId) {
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "리뷰 좋아요 또는 좋아요 취소 누르기에 성공했습니다.", reviewService.likeUnlikeReview(customUser, reviewId)));
    }

    @GetMapping("/list/like")
    public ResponseEntity<BaseRes<ReviewItemRes>> getReviewLikeList(@AuthenticationPrincipal CustomUser customUser, @RequestParam int pageNo){
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "내가 좋아요 한 리뷰 목록 조회에 성공했습니다.", reviewService.getReviewLikeList(customUser, pageNo)));
    }

    @GetMapping("/list/write")
    public ResponseEntity<BaseRes<ReviewItemRes>> getReviewWriteList(@AuthenticationPrincipal CustomUser customUser, @RequestParam int pageNo){
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "내가 작성한 리뷰 목록 조회에 성공했습니다.", reviewService.getReviewWriteList(customUser, pageNo)));
    }


}
