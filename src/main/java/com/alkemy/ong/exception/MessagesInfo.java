package com.alkemy.ong.exception;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
public class MessagesInfo {
    @JsonProperty("messages")
    private Map<String, String> messages;
    @JsonProperty("status_code")
    private int statusCode;
    @JsonProperty("path")
    private String path;
}

