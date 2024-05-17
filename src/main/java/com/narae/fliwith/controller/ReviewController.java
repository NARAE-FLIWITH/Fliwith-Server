package com.narae.fliwith.controller;

import com.narae.fliwith.dto.ReviewReq;
import com.narae.fliwith.dto.base.BaseRes;
import com.narae.fliwith.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
