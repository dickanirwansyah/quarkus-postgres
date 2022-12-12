package com.rnd;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.ws.rs.core.Response;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse {
    private Date timestamp;
    private String message;
    private Integer status;
    private Object data;

    public static final RestResponse ok(Object data){
        return RestResponse.builder()
                .data(data)
                .timestamp(new Date())
                .status(Response.Status.OK.getStatusCode())
                .message(Constant.SUCCESS_MESSAGE)
                .build();
    }

    public static final RestResponse failed(){
        return RestResponse.builder()
                .message(Constant.FAILED_MESSAGE)
                .status(Response.Status.BAD_REQUEST.getStatusCode())
                .timestamp(new Date())
                .build();
    }

    public static final RestResponse notfound(){
        return RestResponse.builder()
                .message(Constant.NOTFOUND_MESSAGE)
                .status(Response.Status.NOT_FOUND.getStatusCode())
                .timestamp(new Date())
                .build();
    }
}
