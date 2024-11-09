package com.ic.catalogservice.serializer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;
import java.time.LocalDateTime;

public abstract class CustomResponseMixin<T> {

    @JsonCreator
    public CustomResponseMixin(
            @JsonProperty("time") LocalDateTime time,
            @JsonProperty("httpStatus") HttpStatus httpStatus,
            @JsonProperty("isSuccess") Boolean isSuccess,
            @JsonProperty("response") T response
    ) {}
}

