package com.narae.fliwith.dto.openAPI;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class DetailIntroRes {
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
        public String scale;
        public String usefee;
        public String discountinfo;
        public String spendtime;
        public String parkingfee;
        public String infocenterculture;
        public String accomcountculture;
        public String usetimeculture;
        public String restdateculture;
        public String parkingculture;
        public String chkbabycarriageculture;
        public String chkpetculture;
        public String chkcreditcardculture;
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
