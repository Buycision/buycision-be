package project.notificationservice.domain.entity;

public enum NotifiTypeEnum {
    INFO, WARNING, ERROR;

    public String getPath() {
        return "/" + this.name().toLowerCase();
    }
}
