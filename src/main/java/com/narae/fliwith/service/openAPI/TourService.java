package com.narae.fliwith.service.openAPI;

import com.narae.fliwith.domain.Location;
import com.narae.fliwith.domain.Spot;
import com.narae.fliwith.dto.TourRes;
import com.narae.fliwith.dto.TourRes.TourDetailRes;
import com.narae.fliwith.dto.TourRes.TourType;
import com.narae.fliwith.dto.openAPI.*;
import com.narae.fliwith.dto.openAPI.DetailWithTourRes.Item;
import com.narae.fliwith.repository.LocationRepository;
import com.narae.fliwith.repository.SpotRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.codec.DecodingException;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Transactional
@RequiredArgsConstructor
public class TourService {

    private final WebClient webClient;
    @Value("${service.key}")
    private String serviceKey;

    private final SpotRepository spotRepository;
    private final LocationRepository locationRepository;

    public Mono<Item> getDetailWithTour(String contentId) {
        return webClient.get()
                .uri(uriBuilder ->
                    uriBuilder.path("/detailWithTour1")
                            .queryParam("MobileOS", "AND")
                            .queryParam("MobileApp", "fliwith")
                            .queryParam("contentId", contentId)
                            .queryParam("_type", "json")
                            .queryParam("serviceKey", serviceKey)
                            .build()
                 )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<DetailWithTourRes.Root>() {
                })
                .map(root -> root.getResponse().getBody().getItems().getItem().get(0));

    }

    public Mono<DetailCommonRes.Item> getDetailCommon(String contentId) {
        return webClient.get()
                .uri(uriBuilder ->
                    uriBuilder.path("/detailCommon1")
                            .queryParam("MobileOS", "AND")
                            .queryParam("MobileApp", "fliwith")
                            .queryParam("contentId", contentId)
                            .queryParam("defaultYN", "Y")
                            .queryParam("firstImageYN", "Y")
                            .queryParam("areacodeYN", "Y")
                            .queryParam("addrinfoYN", "Y")
                            .queryParam("_type", "json")
                            .queryParam("serviceKey", serviceKey)
                            .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<DetailCommonRes.Root>() {
                })
                .map(root -> root.getResponse().getBody().getItems().getItem().get(0));
    }

    public Mono<DetailIntroRes.Item> getDetailIntro(String contentId, String contentTypeId) {
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/detailIntro1")
                                .queryParam("MobileOS", "AND")
                                .queryParam("MobileApp", "fliwith")
                                .queryParam("contentId", contentId)
                                .queryParam("contentTypeId", contentTypeId)
                                .queryParam("_type", "json")
                                .queryParam("serviceKey", serviceKey)
                                .build()
                )
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<DetailIntroRes.Root>() {
                })
                .map(root -> root.getResponse().getBody().getItems().getItem().get(0));
    }

    public List<TourType> getTourByType(String latitude, String longitude, String contentTypeId) {
        //lat, lon 주변 몇 km 이내  contentTypeId인 관광지 조회 return
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/locationBasedList1")
                                .queryParam("MobileOS", "AND")
                                .queryParam("MobileApp", "fliwith")
                                .queryParam("mapX", longitude)
                                .queryParam("mapY", String.valueOf(latitude))
                                .queryParam("radius", "500")
                                .queryParam("_type", "json")
                                .queryParam("contentTypeId", String.valueOf(contentTypeId))
                                .queryParam("serviceKey", serviceKey)
                                .build()
                )
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<LocationBasedListRes.Root>() {
                })
                .map(root -> root.getResponse().getBody().getItems().getItem().stream()
                        .map(item -> new TourType(Integer.parseInt(item.contenttypeid), Integer.parseInt(item.contentid)))
                        .collect(Collectors.toList()))
                .onErrorReturn(DecodingException.class, new ArrayList<>())
                .blockFirst();
        //TODO: DB에 관광지 미리 저장해두고, 조회하는 걸로 로직 변경하기

    }

    public TourDetailRes getTour(String contentTypeId, String contentId) {
        DetailWithTourRes.Item detailWithTour = getDetailWithTour(contentId).block();
        DetailIntroRes.Item detailIntro = getDetailIntro(contentId, contentTypeId).block();
        DetailCommonRes.Item detailCommon = getDetailCommon(contentId).block();

        return TourDetailRes.builder()
                .detailWithTour(detailWithTour)
                .detailIntro(detailIntro)
                .detailCommon(detailCommon)
                .build();
    }

    public List<TourRes.TourName> getSerachKeyword(String keyword) {
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/searchKeyword1")
                                .queryParam("MobileOS", "AND")
                                .queryParam("MobileApp", "fliwith")
                                .queryParam("keyword", keyword)
                                .queryParam("_type", "json")
                                .queryParam("serviceKey", serviceKey)
                                .build()
                )
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<SearchKeywordRes.Root>() {
                })
                .map(root -> root.getResponse().getBody().getItems().getItem().stream().map(item -> new TourRes.TourName(Integer.parseInt(item.contentid), item.title)).collect(Collectors.toList()))
                .blockFirst();
    }

    public AreaBasedListRes.Response getAreaBasedList(String contentTypeId, int pageNo) {
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/areaBasedList1")
                                .queryParam("pageNo", pageNo)
                                .queryParam("MobileOS", "AND")
                                .queryParam("MobileApp", "fliwith")
                                .queryParam("contentTypeId", contentTypeId)
                                .queryParam("_type", "json")
                                .queryParam("serviceKey", serviceKey)
                                .build()
                )
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<AreaBasedListRes.Root>() {
                }).map(AreaBasedListRes.Root::getResponse)
                .blockFirst();
    }



    public void saveAllSpots(String contentTypeId) {
        int pageNo = 1;
        int totalItems = 0;
        int itemsPerPage = 10;

        do {
            AreaBasedListRes.Response response = getAreaBasedList(contentTypeId, pageNo); // Fetch spots from API with paging
            totalItems = response.getBody().getTotalCount();
            saveSpotFromResponse(response);
            pageNo++;
        } while ((pageNo - 1) * itemsPerPage < totalItems);
    }

    private void saveSpotFromResponse(AreaBasedListRes.Response response) {
        for (AreaBasedListRes.Item item : response.getBody().getItems().getItem()) {
            Spot spot = Spot.builder()
                    .id(Integer.parseInt(item.getContentid()))
                    .title(item.getTitle())
                    .thumbnail(item.getFirstimage())
                    .tel(item.getTel())
                    .address(item.getAddr1() + " "+item.getAddr2())
                    .areaCode(Integer.parseInt(item.getAreacode()))
                    .contentTypeId(Integer.parseInt(item.getContenttypeid()))
                    .build();


            spot = spotRepository.save(spot);

            Location location = Location.builder()
                    .id(Integer.parseInt(item.getContentid()))
                            .spot(spot)
                                    .latitude(Double.parseDouble(item.getMapy()))
                                            .longitude(Double.parseDouble(item.getMapx())).build();
            locationRepository.save(location);
        }
    }
}
