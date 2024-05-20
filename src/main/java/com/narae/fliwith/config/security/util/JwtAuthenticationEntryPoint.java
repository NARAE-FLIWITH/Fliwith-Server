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
        if(exception.equals(SecurityExceptionList.UNKNOWN_ERROR.getErrorCode()))
            setResponse(response, SecurityExceptionList.UNKNOWN_ERROR);

        else if(exception.equals(SecurityExceptionList.MALFORMED_TOKEN_ERROR.getErrorCode()))
            setResponse(response, SecurityExceptionList.MALFORMED_TOKEN_ERROR);

        else if(exception.equals(SecurityExceptionList.ILLEGAL_TOKEN_ERROR.getErrorCode()))
            setResponse(response, SecurityExceptionList.ILLEGAL_TOKEN_ERROR);

        else if(exception.equals(SecurityExceptionList.EXPIRED_TOKEN_ERROR.getErrorCode()))
            setResponse(response, SecurityExceptionList.EXPIRED_TOKEN_ERROR);

        else if(exception.equals(SecurityExceptionList.UNSUPPORTED_TOKEN_ERROR.getErrorCode()))
            setResponse(response, SecurityExceptionList.UNSUPPORTED_TOKEN_ERROR);

        else setResponse(response, SecurityExceptionList.ACCESS_DENIED);

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
