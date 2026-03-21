package com.thailandcube.models;

import java.time.LocalDateTime;

public class RegistrationInfo {
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
    private int baseEntryFee;
    private String currencyCode;
    private boolean onTheSpotRegistration;
    private boolean useWcaRegistration;

    public RegistrationInfo(LocalDateTime openTime, LocalDateTime closeTime, int baseEntryFee, String currencyCode, boolean onTheSpotRegistration, boolean useWcaRegistration) {
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.baseEntryFee = baseEntryFee;
        this.currencyCode = currencyCode;
        this.onTheSpotRegistration = onTheSpotRegistration;
        this.useWcaRegistration = useWcaRegistration;
    }

    public LocalDateTime getOpenTime() {
        return openTime;
    }

    public LocalDateTime getCloseTime() {
        return closeTime;
    }

    public int getBaseEntryFee() {
        return baseEntryFee;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public boolean isOnTheSpotRegistration() {
        return onTheSpotRegistration;
    }

    public boolean isUseWcaRegistration() {
        return useWcaRegistration;
    }
}
