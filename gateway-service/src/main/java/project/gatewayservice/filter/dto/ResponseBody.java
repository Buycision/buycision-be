package project.gatewayservice.filter.dto;

public record ResponseBody(String body) {
    public static ResponseBody of(String body) {
        return new ResponseBody(body);
    }
}
