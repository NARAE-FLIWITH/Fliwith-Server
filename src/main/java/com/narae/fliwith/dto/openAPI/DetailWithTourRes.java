package com.narae.fliwith.dto.openAPI;

import lombok.*;

import java.util.List;

@NoArgsConstructor
public class DetailWithTourRes {
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
    public static class Items{
        public List<Item> item;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Item{
        public String contentid;
        public String parking;
        public String route;
        public String publictransport;
        public String ticketoffice;
        public String promotion;
        public String wheelchair;
        public String exit;
        public String elevator;
        public String restroom;
        public String auditorium;
        public String room;
        public String handicapetc;
        public String braileblock;
        public String helpdog;
        public String guidehuman;
        public String audioguide;
        public String bigprint;
        public String brailepromotion;
        public String guidesystem;
        public String blindhandicapetc;
        public String signguide;
        public String videoguide;
        public String hearingroom;
        public String hearinghandicapetc;
        public String stroller;
        public String lactationroom;
        public String babysparechair;
        public String infantsfamilyetc;
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
