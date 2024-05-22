package com.narae.fliwith.service.openAPI;

import com.narae.fliwith.domain.Disability;
import com.narae.fliwith.domain.Location;
import com.narae.fliwith.domain.Review;
import com.narae.fliwith.domain.Spot;
import com.narae.fliwith.domain.User;
import com.narae.fliwith.dto.ReviewRes.ReviewItem;
import com.narae.fliwith.dto.TourReq.AiTourParams;
import com.narae.fliwith.dto.TourReq.AiTourReq;
import com.narae.fliwith.dto.TourRes.TourAsk;
import com.narae.fliwith.dto.TourRes.TourDetailRes;
import com.narae.fliwith.dto.TourRes.TourType;
import com.narae.fliwith.dto.openAPI.*;
import com.narae.fliwith.exception.spot.SpotFindFailException;
import com.narae.fliwith.exception.tour.NotFoundAiTourException;
import com.narae.fliwith.repository.LocationRepository;
import com.narae.fliwith.repository.ReviewRepository;
import com.narae.fliwith.repository.SpotRepository;
import com.narae.fliwith.service.AuthService;
import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.service.OpenAiService;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.codec.DecodingException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    private static final String MODEL = "gpt-3.5-turbo";

    private OpenAiService openAiService;

    @Value("${chatgpt.key}")
    private String gptKey;


    private final SpotRepository spotRepository;
    private final LocationRepository locationRepository;
    private final ReviewRepository reviewRepository;
    private final AuthService authService;

    public Mono<DetailWithTourRes.Item> getDetailWithTour(String contentId) {
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
                .map(root -> root.getResponse().getBody().getItems().getItem().get(0))
                .onErrorReturn(DecodingException.class, new DetailWithTourRes.Item());

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
                .map(root -> root.getResponse().getBody().getItems().getItem().get(0))
                .onErrorReturn(DecodingException.class, new DetailCommonRes.Item());
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
                .map(root -> root.getResponse().getBody().getItems().getItem().get(0))
                .onErrorReturn(DecodingException.class, new DetailIntroRes.Item());
    }

    public List<TourType> getNearEveryTourType(String email, double latitude, double longitude) {
        User user = authService.authUser(email);
        return locationRepository.findNearEverySpotType(latitude, longitude).stream()
                .map(location -> TourType.builder()
                        .contentTypeId(location.getSpot().getContentTypeId())
                        .contentId(location.getSpot().getId())
                        .latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build())
                .collect(Collectors.toList());
    }

    public TourDetailRes getTour(String email, String contentTypeId, String contentId) {
        User user = authService.authUser(email);

        DetailWithTourRes.Item detailWithTour = getDetailWithTour(contentId).block();
        DetailIntroRes.Item detailIntro = getDetailIntro(contentId, contentTypeId).block();
        DetailCommonRes.Item detailCommon = getDetailCommon(contentId).block();

        Pageable pageable = PageRequest.of(0, 10); // 0은 페이지 번호, 10은 페이지 크기
        Spot spot = spotRepository.findById(Integer.parseInt(contentId)).orElseThrow(SpotFindFailException::new);

        Page<Review> reviewsPage = reviewRepository.findAllBySpotOrderByCreatedAtDesc(spot, pageable);
        List<Review> reviews = reviewsPage.getContent();

        return TourDetailRes.builder()
                .detailWithTour(detailWithTour)
                .detailIntro(detailIntro)
                .detailCommon(detailCommon)
                .reviews(reviews.stream().map(review -> new ReviewItem(review.getId(), review.getImages().get(0).getUrl(), review.getUser().getNickname(), review.getUser().getDisability(), (long) review.getLikes().size())).collect(
                        Collectors.toList()))
                .build();
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
                    .areaCode(item.getAreacode())
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

    private AreaBasedListRes.Body getAreaBasedListWithAreaCode(String contentTypeId, String areaCode, int pageNo) {
        return webClient.get()
                .uri(uriBuilder ->
                        uriBuilder.path("/areaBasedList1")
                                .queryParam("pageNo", pageNo)
                                .queryParam("MobileOS", "AND")
                                .queryParam("MobileApp", "fliwith")
                                .queryParam("contentTypeId", contentTypeId)
                                .queryParam("areaCode", areaCode)
                                .queryParam("_type", "json")
                                .queryParam("serviceKey", serviceKey)
                                .build()
                )
                .retrieve()
                .bodyToFlux(new ParameterizedTypeReference<AreaBasedListRes.Root>() {
                }).map(root -> root.getResponse().getBody())
                .blockFirst();
    }

    public TourDetailRes getAiTour(String email, AiTourReq aiTourReq) {
        User user = authService.authUser(email);

        AiTourParams params = AiTourParams.from(aiTourReq);
        List<TourAsk> askList = new ArrayList<>();
        int pageNo = 0;
        int totalItems = 0;
        int itemsPerPage = 10;
        List<TourAsk> spotList;

        List<String> contentTypeIds;
        if (params.getContentTypeId().equals("0")) {
            contentTypeIds = List.of("12", "14", "32", "38", "39");
        } else {
            contentTypeIds = Collections.singletonList(params.getContentTypeId());
        }

        for (String contentTypeId : contentTypeIds) {
            do {
                AreaBasedListRes.Body body = getAreaBasedListWithAreaCode(contentTypeId, params.getAreaCode(), pageNo);
                totalItems = body.getTotalCount();
                spotList = body.getItems().getItem().stream().map(item -> TourAsk.builder().name(item.getTitle()).contentId(item.getContentid()).contentTypeId(item.getContenttypeid()).build()).collect(Collectors.toList());

                for (TourAsk tourAsk : spotList) {
                    String contentId = String.valueOf(tourAsk.getContentId());
                    DetailWithTourRes.Item detailWithTour = getDetailWithTour(contentId).block();
                    if (isServiceAvailable(params.getDisability(), detailWithTour)) {
                        askList.add(tourAsk);
                    }
                }

                pageNo++;
                if(askList.size()>=10){
                    break;
                }

            } while ((pageNo - 1) * itemsPerPage < totalItems);
            if(askList.size()>=10){
                break;
            }
        }

        //gpt
        if(!askList.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for (TourAsk tourAsk : askList) {
                sb.append(tourAsk.getName()).append(", ");
            }
            sb.delete(sb.length() - 2, sb.length());
            String gptRecommend = createGptRecommend(sb.toString(), params);
//            System.out.println(gptAnswer);
            TourAsk gptChoice = askList.stream().filter(tourAsk -> gptRecommend.contains(tourAsk.getName())).findFirst().get();
            //TODO: auth 중복 로직 해결
            return getTour(email, gptChoice.getContentTypeId(), gptChoice.getContentId());
        }


        throw new NotFoundAiTourException();
    }

    private boolean isServiceAvailable(Disability disability, DetailWithTourRes.Item detailWithTour) {
        switch (disability) {
            case PHYSICAL:
                return detailWithTour.getPublictransport() != null && !detailWithTour.getPublictransport().isEmpty();
            case VISUAL:
                return detailWithTour.getBraileblock() != null && !detailWithTour.getBraileblock().isEmpty();
            case HEARING:
                return detailWithTour.getAudioguide() != null && !detailWithTour.getAudioguide().isEmpty();
            default:
                return true;
        }
    }

    private String createGptRecommend(String content, AiTourParams params){
        this.openAiService = new OpenAiService(gptKey);

        String prompt = content + "중에 갈 만한 관광지를 추천해줘. "+params.getDisability().getDisability()+ "을 가진 사람과 함께 "+params.getVisitedDate()+ "에 여행할 거고, 총 여행 인원은 "+params.getPeopleNum()+ "명 이야. 내가 말한 것 중에서 1개의 관광지 이름만 말해줘.";
//        System.out.println("prompt: "+prompt);
        ChatCompletionRequest requester = ChatCompletionRequest.builder()
                .model(MODEL)
                .maxTokens(2048)
                .messages(List.of(
                        new ChatMessage("user", prompt)
                )).build();
        return openAiService.createChatCompletion(requester).getChoices().get(0).getMessage().getContent();
    }
}
