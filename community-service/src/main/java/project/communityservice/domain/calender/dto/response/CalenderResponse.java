package project.communityservice.domain.calender.dto.response;

import project.communityservice.domain.calender.entity.Calender;

import java.util.List;
import java.util.stream.Collectors;

public record CalenderResponse(
        Long id,
        String title,
        String content
) {

    public static CalenderResponse of(Calender calender) {
        return new CalenderResponse(
                calender.getId(),
                calender.getTitle(),
                calender.getContent());
    }

    public static List<CalenderResponse> listOf(List<Calender> calenders) {
        return calenders.stream()
                .map(CalenderResponse::of)
                .collect(Collectors.toList());
    }

}
