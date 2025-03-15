package com.is.customerfinance.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BaseBadException {
    private final String error;
    @JsonProperty("error_description")
    private final String errorDescription;
}
