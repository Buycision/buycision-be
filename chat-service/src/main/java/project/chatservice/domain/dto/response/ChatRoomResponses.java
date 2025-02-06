package project.chatservice.domain.dto.response;

import lombok.Getter;
import project.chatservice.domain.entity.ChatRoom;
import project.chatservice.domain.entity.UserChatRoom;
import project.chatservice.domain.service.ChatRoomService;

import java.util.List;
import java.util.Objects;

public record ChatRoomResponses(
        boolean hasNext,
        int number,
        int numberOfElements,
        List<ChatRoomResponse> chatRoomResponses
) {
    public static ChatRoomResponses from(boolean hasNext, int number, int numberOfElements, List<ChatRoom> userChatRooms) {
        List<ChatRoomResponse> roomResponses = userChatRooms.stream().map(ChatRoomResponse::from).toList();

        return new ChatRoomResponses(hasNext, number, numberOfElements, roomResponses);
    }

    public record ChatRoomResponse(Long id, String title, boolean isActivated) {
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ChatRoomResponse that = (ChatRoomResponse) o;
            return Objects.equals(id, that.id);
        }

        @Override
        public int hashCode() {
            return Objects.hashCode(id);
        }

        public static ChatRoomResponse from(ChatRoom chatRoom) {
            return new ChatRoomResponse(chatRoom.getId(), chatRoom.getName(), chatRoom.getIsActivated());
        }
    }
}
