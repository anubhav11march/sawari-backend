package com.tzs.marshall.bean.distnaceMatrix;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Element {
    Map<String, Object> distance;
    Map<String, Object> duration;
    String status;
}
