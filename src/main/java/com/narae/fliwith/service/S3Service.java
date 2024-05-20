package com.narae.fliwith.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.narae.fliwith.domain.User;
import com.narae.fliwith.dto.ImageReq.PresignedUrlReq;
import com.narae.fliwith.dto.ImageRes;
import com.narae.fliwith.dto.ImageRes.PresignedUrlRes;
import com.narae.fliwith.exception.user.LogInFailException;
import com.narae.fliwith.repository.UserRepository;
import jakarta.transaction.Transactional;
import java.util.Date;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class S3Service {
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${image.prefix}")
    private String prefix;

    @Value("${image.folder}")
    private String folder;

    private final AuthService authService;


    public ImageRes.PresignedUrlRes issuePresignedUrl(String email, PresignedUrlReq presignedUrlReq){
        User user = authService.authUser(email);

        String imageName = folder+UUID.randomUUID()+"."+presignedUrlReq.getImageExtension().getUploadExtension();

        GeneratePresignedUrlRequest request = generatePresignedUrlRequest(bucket, imageName);
        return PresignedUrlRes.builder()
                .presignedUrl(amazonS3.generatePresignedUrl(request).toString())
                .imageUrl(prefix+imageName)
                .build();

    }

    private GeneratePresignedUrlRequest generatePresignedUrlRequest(String bucket, String imageName){

        //만료 시간 설정
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 5; // 5분
        expiration.setTime(expTimeMillis);

        //Pre-Signed Url request 생성
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucket, imageName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);
        //request 파라미터 추가
        request.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString());

        return request;
    }

}

