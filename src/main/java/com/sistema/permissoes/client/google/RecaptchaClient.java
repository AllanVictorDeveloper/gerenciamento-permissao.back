package com.sistema.permissoes.client.google;

import com.sistema.permissoes.dto.response.auth.RecaptchaResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@FeignClient(name = "recaptcha-client", url = "https://www.google.com/recaptcha/api")
public interface RecaptchaClient {

    @PostMapping(value = "/siteverify", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    RecaptchaResponseDTO verificar(@RequestBody Map<String, String> form);
}
