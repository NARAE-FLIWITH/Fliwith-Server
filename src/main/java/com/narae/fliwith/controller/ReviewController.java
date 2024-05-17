package com.narae.fliwith.controller;

import com.narae.fliwith.dto.ReviewReq;
import com.narae.fliwith.dto.ReviewRes;
import com.narae.fliwith.dto.base.BaseRes;
import com.narae.fliwith.service.ReviewService;
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
}
