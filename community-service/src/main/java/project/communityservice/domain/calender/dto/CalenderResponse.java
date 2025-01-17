package project.communityservice.domain.calender.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Calendar;

@Getter
@AllArgsConstructor
public class CalenderResponse {
    private Calendar calendar;
}
