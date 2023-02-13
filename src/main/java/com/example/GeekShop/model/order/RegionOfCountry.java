package com.example.GeekShop.model.order;

public enum RegionOfCountry {
    Crimea("Crimea"),
    Vinnytsia_region("Vinnytsia region"),
    Volyn_region("Volyn region"),
    Dnipropetrovsk_region("Dnipropetrovsk region"),
    Donetsk_region("Donetsk region"),
    Zhytomyr_region("Zhytomyr region"),
    Transcarpathian_region("Transcarpathian region"),
    Zaporizhzhia_region("Zaporizhzhia region"),
    Kirovohrad_region("Kirovohrad region"),
    Kyiv_region("Kyiv region"),
    Bukovel_resort("Bukovel resort"),
    Luhansk_region("Luhansk region"),
    Lviv_region("Lviv region"),
    Mykolaiv_region("Mykolaiv region"),
    Odesa_region("Odesa region"),
    Poltava_region("Poltava region"),
    Rivne_region("Rivne region"),
    Sumy_region("Sumy region"),
    Ternopil_region("Ternopil region"),
    Kharkiv_region("Kharkiv region"),
    Kherson_region("Kherson region"),
    Khmelnytskyi_region("Khmelnytskyi region"),
    Cherkasy_region("Cherkasy region"),
    Chernivtsi_region("Chernivtsi region"),
    Chernihiv_region("Chernihiv region"),
    ;

    private final String displayValue;

    RegionOfCountry(String displayValue) {
        this.displayValue = displayValue;
    }
    public String getDisplayValue() {
        return displayValue;
    }
}
