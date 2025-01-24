package project.chatservice.domain.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "ChatRoom", description = "채팅방 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class ChatRoomController {

}
