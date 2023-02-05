package com.tzs.marshall.bean.distnaceMatrix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DistanceMatrix {
    private List<String> destination_addresses;
    private List<String> origin_addresses;
    private List<Elements> rows;
    private String status;


}

