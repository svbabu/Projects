package com.thoughtprocessing.enums;

public enum HomeCenterShop {
    Furniture("F01","Furniture and Decor"),
    Electrical("E01","Electric Fittings"),
    Garden("G01","Gardening Tools"),
    Paint("P01","Painting and Coats"),
    Plumbing("PL01","Plumbing Supplies");
    private String code;
    private String description;
    HomeCenterShop(String code, String description) {
        this.code = code;
        this.description = description;
    }
    public String getCode() {
        return code;
    }
    public String getDescription() {
        return description; }
    public String toString(){
        return name()+"("+code+"):"+description;

    }
}
