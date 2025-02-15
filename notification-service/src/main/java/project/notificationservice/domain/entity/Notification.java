package project.notificationservice.domain.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer senderNo;

    private String senderName;

    private Integer receiverNo;

    @Enumerated(EnumType.STRING)
    private NotifiTypeEnum typeEnum;

    private String url;

    private String content;

    private Boolean isRead;

    private Boolean isDel;

    @Builder
    public Notification(Integer senderNo, String senderName, Integer receiverNo, NotifiTypeEnum typeEnum, String url, String content, Boolean isRead, Boolean isDel) {
        this.senderNo = senderNo;
        this.senderName = senderName;
        this.receiverNo = receiverNo;
        this.typeEnum = typeEnum;
        this.url = url;
        this.content = content;
        this.isRead = isRead;
        this.isDel = isDel;
    }
}
