package com.narae.fliwith.controller.openAPI;

import com.narae.fliwith.dto.openAPI.DetailCommonRes;
import com.narae.fliwith.dto.openAPI.DetailWithTourRes;
import com.narae.fliwith.service.openAPI.TourService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class TourController {

    private final TourService tourService;

    @GetMapping("/tour/{contentId}")
    public DetailWithTourRes.Response getTour(@PathVariable(value = "contentId") String contentId){
        return tourService.getDetailWithTour(contentId);
    }

    @GetMapping("/tour/common/{contentId}")
    public DetailCommonRes.Response getDetailCommon(@PathVariable(value = "contentId") String contentId){
        return tourService.getDetailCommon(contentId);
    }

}
