package com.narae.fliwith.controller.openAPI;

import com.narae.fliwith.dto.TourRes.TourDetailRes;
import com.narae.fliwith.dto.TourRes.TourType;
import com.narae.fliwith.dto.base.BaseRes;
import com.narae.fliwith.service.openAPI.TourService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;
    @GetMapping("/tour")
    public ResponseEntity<BaseRes<List<TourType>>> getTourByType(@RequestParam String latitude, @RequestParam String longitude, @RequestParam String contentTypeId){
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "관광지 목록 조회에 성공했습니다.", tourService.getTourByType(latitude, longitude, contentTypeId)));
    }

    @GetMapping("/tour/{contentTypeId}/{contentId}")
    public ResponseEntity<BaseRes<TourDetailRes>> getTour(@PathVariable(value = "contentTypeId")String contentTypeId, @PathVariable(value = "contentId") String contentId){
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "관광지 상세 조회에 성공했습니다.", tourService.getTour(contentTypeId, contentId)));

    }

    @GetMapping("/admin/save")
    public ResponseEntity<BaseRes<Void>> saveTour(@RequestParam String contentTypeId){
        tourService.saveAllSpots(contentTypeId);
        return ResponseEntity.ok(BaseRes.create(HttpStatus.OK.value(), "관광지 타입 목록을 저장하는데 성공했습니다."));
    }

}
