package com.narae.fliwith.controller.openAPI;

import com.narae.fliwith.config.security.dto.CustomUser;
import com.narae.fliwith.dto.ReviewRes.ReviewContentRes;
import com.narae.fliwith.dto.TourReq.AiTourReq;
import com.narae.fliwith.dto.TourRes.TourDetailRes;
import com.narae.fliwith.dto.TourRes.TourType;
import com.narae.fliwith.dto.base.BaseRes;
import com.narae.fliwith.service.openAPI.TourService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;
//    @GetMapping("/tour")
//    public ResponseEntity<BaseRes<List<TourType>>> getTourByType(@AuthenticationPrincipal CustomUser customUser, @RequestParam String latitude, @RequestParam String longitude, @RequestParam String contentTypeId){
//        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "관광지 목록 조회에 성공했습니다.", tourService.getTourByType(
//                customUser.getEmail(), latitude, longitude, contentTypeId)));
//    }

    @GetMapping("/tour")
    public ResponseEntity<BaseRes<List<TourType>>> getTourByType(@AuthenticationPrincipal CustomUser customUser, @RequestParam double latitude, @RequestParam double longitude){
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "위치기반 모든 유형의 관광지 목록 조회에 성공했습니다.", tourService.getNearEveryTourType(
                customUser, latitude, longitude)));
    }

    @GetMapping("/tour/{contentTypeId}/{contentId}")
    public ResponseEntity<BaseRes<TourDetailRes>> getTour(@AuthenticationPrincipal CustomUser customUser, @PathVariable(value = "contentTypeId")String contentTypeId, @PathVariable(value = "contentId") String contentId){
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "관광지 상세 조회에 성공했습니다.", tourService.getTour(
                customUser, contentTypeId, contentId)));

    }

    @GetMapping("/tour/review/{contentId}")
    public ResponseEntity<BaseRes<ReviewContentRes>> getTourReviewContentPageList(@AuthenticationPrincipal CustomUser customUser, @PathVariable(value = "contentId") String contentId, @RequestParam int pageNo){
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "관광지 리뷰 목록 조회에 성공했습니다.", tourService.getTourReviewContentPageList(customUser, contentId, pageNo)));

    }

    @GetMapping("/admin/save")
    public ResponseEntity<BaseRes<Void>> saveTour(@RequestParam String contentTypeId){
        tourService.saveAllSpots(contentTypeId);
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "관광지 타입 목록을 저장하는데 성공했습니다."));
    }

    @PostMapping("/tour/ai")
    public ResponseEntity<BaseRes<TourDetailRes>> getAiTour(@AuthenticationPrincipal CustomUser customUser,  @RequestBody AiTourReq aiTourReq){
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "추천 관광지를 찾는데 성공했습니다.", tourService.getAiTour(customUser, aiTourReq)));

    }

}
