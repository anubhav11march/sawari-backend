package com.tzs.marshall.author.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AESHServicePlan {
    private Long planId;
    private String name;
    private String description;
    private Double price;
    private String currency;
    private int validity;
}
