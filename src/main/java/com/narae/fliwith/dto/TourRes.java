package com.narae.fliwith.dto;

import com.narae.fliwith.dto.ReviewRes.ReviewItem;
import com.narae.fliwith.dto.openAPI.DetailCommonRes;
import com.narae.fliwith.dto.openAPI.DetailIntroRes;
import com.narae.fliwith.dto.openAPI.DetailWithTourRes;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TourRes {

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TourListByTypeRes {
        List<TourType> tourList;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TourType{
        int contentTypeId;
        int contentId;
        double latitude;
        double longitude;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TourDetailRes{
        DetailWithTourRes.Item detailWithTour;
        DetailIntroRes.Item detailIntro;
        DetailCommonRes.Item detailCommon;

    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TourName{
        int contentId;
        String name;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class TourAsk{
        String contentTypeId;
        String contentId;
        String name;
    }
}
