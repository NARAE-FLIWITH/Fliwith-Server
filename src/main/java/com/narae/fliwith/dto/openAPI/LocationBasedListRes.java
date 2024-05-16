package com.narae.fliwith.dto.openAPI;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class LocationBasedListRes {
    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Body{
        public Items items;
        public int numOfRows;
        public int pageNo;
        public int totalCount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Header{
        public String resultCode;
        public String resultMsg;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item{
        public String addr1;
        public String addr2;
        public String areacode;
        public String booktour;
        public String cat1;
        public String cat2;
        public String cat3;
        public String contentid;
        public String contenttypeid;
        public String createdtime;
        public String dist;
        public String firstimage;
        public String firstimage2;
        public String cpyrhtDivCd;
        public String mapx;
        public String mapy;
        public String mlevel;
        public String modifiedtime;
        public String sigungucode;
        public String tel;
        public String title;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Items{
        public List<Item> item;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response{
        public Header header;
        public Body body;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Root{
        public Response response;
    }


}
