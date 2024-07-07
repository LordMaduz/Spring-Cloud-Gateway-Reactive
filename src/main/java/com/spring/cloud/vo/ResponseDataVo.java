package com.spring.cloud.vo;

import java.util.Map;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDataVo {

    private boolean allow;
    private String reason;
    private Map<String,Object> headers;
    private Integer status_code;
}
