package com.narae.fliwith.dto;

import com.narae.fliwith.config.util.AreaCodeUtil;
import com.narae.fliwith.config.util.ContentTypeIdUtil;
import com.narae.fliwith.domain.Disability;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class TourReq {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AiTourReq{
        private String area;
        private String contentType;
        private Disability disability;
        private int peopleNum;
        private LocalDate visitedDate;
    }

    @Getter
    @Builder
    public static class AiTourParams{
        private String areaCode;
        private String contentTypeId;
        private Disability disability;
        private int peopleNum;
        private LocalDate visitedDate;

        public static AiTourParams from(AiTourReq req){
            return AiTourParams.builder()
                    .areaCode(AreaCodeUtil.convert(req.getArea()))
                    .contentTypeId(ContentTypeIdUtil.convert(req.getContentType()))
                    .disability(req.getDisability())
                    .peopleNum(req.getPeopleNum())
                    .visitedDate(req.getVisitedDate())
                    .build();
        }
    }
}
