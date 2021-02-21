package ru.geekbrains.javabackendat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;

@JsonPropertyOrder({
        "data",
        "success",
        "status"
})

@Data
public class CommonResponse <MethodData>{

    @JsonProperty("data")
    private MethodData data;
    private Boolean success;
    private Integer status;
}
