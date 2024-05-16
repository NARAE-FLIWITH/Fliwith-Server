package com.narae.fliwith.dto;

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
}
