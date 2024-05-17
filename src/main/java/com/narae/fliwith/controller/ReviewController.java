package com.narae.fliwith.controller;

import com.narae.fliwith.dto.ReviewReq;
import com.narae.fliwith.dto.ReviewRes;
import com.narae.fliwith.dto.TourRes.TourName;
import com.narae.fliwith.dto.base.BaseRes;
import com.narae.fliwith.service.ReviewService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;

    @PostMapping()
    public ResponseEntity<BaseRes<Void>> createReview(Principal principal, @RequestBody ReviewReq.WriteReviewReq writeReviewReq) {
        reviewService.writeReview(principal, writeReviewReq);
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "리뷰를 작성하는데 성공했습니다."));
    }
    @GetMapping("/{reviewId}")
    public ResponseEntity<BaseRes<ReviewRes.ReviewDetailRes>> getReview(Principal principal, @PathVariable Long reviewId) {
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "리뷰 상세조회에 성공했습니다.", reviewService.getReviewDetail(principal, reviewId)));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<BaseRes<Void>> deleteReview(Principal principal, @PathVariable Long reviewId) {
        reviewService.deleteReview(principal, reviewId);
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "리뷰 삭제에 성공했습니다."));
    }

    @PatchMapping("/{reviewId}")
    public ResponseEntity<BaseRes<ReviewRes.ReviewDetailRes>> updateReview(Principal principal, @PathVariable Long reviewId, @RequestBody ReviewReq.WriteReviewReq writeReviewReq) {
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "리뷰 수정에 성공했습니다.", reviewService.updateReview(principal, reviewId, writeReviewReq)));
    }

    @GetMapping()
    public ResponseEntity<BaseRes<List<TourName>>> getSpotName(Principal principal, @RequestParam String spotName) {
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "관광지 이름 목록 조회에 성공했습니다.", reviewService.getSpotName(principal, spotName)));
    }

    //TODO: 리뷰 이미지 첨부 이후에 주석 해제
//    @GetMapping()
//    public ResponseEntity<BaseRes<ReviewItemRes>> getReviewList(Principal principal,@RequestParam int pageNo, @RequestParam String order){
//        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "리뷰 목록 조회에 성공했습니다.", reviewService.getReviewList(principal, pageNo, order)));
//
//    }
}
