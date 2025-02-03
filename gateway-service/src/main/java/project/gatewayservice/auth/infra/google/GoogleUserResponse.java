package project.gatewayservice.auth.infra.google;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class GoogleUserResponse {

    private String email;
    private String name;
    private String picture;

}