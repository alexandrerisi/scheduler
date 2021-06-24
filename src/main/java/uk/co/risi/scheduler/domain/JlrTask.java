package uk.co.risi.scheduler.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.http.HttpMethod;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;

@Data
public class JlrTask implements Serializable {

    @Id
    private String id;
    //Endpoint Trigger
    private URI uri;
    private HttpMethod httpMethod;
    private Map<String, String> headers;
    private String body;
}
