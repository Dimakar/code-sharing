package com.dimakar.codesharing.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@With
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetSnippetsResponse {
    private String code;
    private String date;
    private Long time;
    private Integer views;
    @JsonIgnore
    private Boolean hasTimeRestriction;
    @JsonIgnore
    private Boolean hasViewsRestriction;
}
