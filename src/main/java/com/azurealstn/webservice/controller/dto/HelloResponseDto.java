package com.azurealstn.webservice.controller.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@RequiredArgsConstructor
public class HelloResponseDto {

    private final String name;
    private final int amount;

}
