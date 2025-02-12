package project.buysellservice.domain.article.entity;

public enum Category {
    ELECTRONICS,
    FURNITURE,
    CLOTHING,
    TOYS,
    BOOKS,
    VEHICLES,
    REAL_ESTATE;

    public static Category fromString(String category) {
        return Category.valueOf(category.toUpperCase());
    }
}
