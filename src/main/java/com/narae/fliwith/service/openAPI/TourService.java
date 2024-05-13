package com.narae.fliwith.service.openAPI;

import com.narae.fliwith.dto.openAPI.DetailWithTourRes;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

@Service
@Transactional
@RequiredArgsConstructor
public class TourService {

    private final WebClient webClient;
    @Value("${service.key}")
    private String serviceKey;

    public DetailWithTourRes.Response getDetailWithTour(String contentId){
        return webClient.get()
                .uri(uriBuilder -> {
                    uriBuilder.path("/detailWithTour1")
                            .queryParam("MobileOS", "AND")
                            .queryParam("MobileApp", "fliwith")
                            .queryParam("contentId", contentId)
                            .queryParam("_type", "json")
                            .queryParam("serviceKey", serviceKey)
                            .build();
                  System.out.println(uriBuilder.toUriString());
                    return URI.create(uriBuilder.toUriString());
                })
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<DetailWithTourRes.Root>() {
                })
                .map(DetailWithTourRes.Root::getResponse)
                .block();
    }
}
