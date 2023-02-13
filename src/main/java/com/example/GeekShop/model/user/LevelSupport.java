package com.example.GeekShop.model.user;

public enum LevelSupport {
    Start("Start"),
    Standard("Standard"),
    Ultra("Ultra"),
    UltraPlus("Ultra+");

    private final String displayValue;

    LevelSupport(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }
}
