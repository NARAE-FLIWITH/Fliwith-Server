package com.narae.fliwith.dto.openAPI;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class DetailCommonRes {
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
        public String contentid;
        public String contenttypeid;
        public String title;
        public String createdtime;
        public String modifiedtime;
        public String tel;
        public String telname;
        public String homepage;
        public String booktour;
        public String firstimage;
        public String firstimage2;
        public String cpyrhtDivCd;
        public String areacode;
        public String sigungucode;
        public String addr1;
        public String addr2;
        public String zipcode;
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
