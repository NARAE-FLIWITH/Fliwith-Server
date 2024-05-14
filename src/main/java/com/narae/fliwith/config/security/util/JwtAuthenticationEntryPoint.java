package com.narae.fliwith.config.security.util;

import com.narae.fliwith.exception.security.constants.SecurityExceptionList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = String.valueOf(request.getAttribute("exception"));

        //TODO: 조건별 예외 처리
        setResponse(response, SecurityExceptionList.ACCESS_DENIED);
    }

    private void setResponse(HttpServletResponse response, SecurityExceptionList exceptionCode) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("timestamp", LocalDateTime.now().withNano(0).toString());
        responseJson.put("message", exceptionCode.getMessage());
        responseJson.put("errorCode", exceptionCode.getErrorCode());

        response.getWriter().print(responseJson);
    }
}
