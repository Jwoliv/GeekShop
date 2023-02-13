package com.example.GeekShop.model.order;

public enum TypePostOffice {
    NovaPoshta("Nova Poshta"),
    UkrPoshta("Ukr poshta");
    private final String displayValue;

    TypePostOffice(String displayValue) {
        this.displayValue = displayValue;
    }
    public String getDisplayValue() {
        return displayValue;
    }
}
