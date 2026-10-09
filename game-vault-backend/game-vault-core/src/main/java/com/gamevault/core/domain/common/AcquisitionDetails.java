package com.gamevault.core.domain.common;

import lombok.Builder;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

@Builder(toBuilder = true)
public record AcquisitionDetails(
        @NonNull AcquisitionType type,
        LocalDate acquiredAt,
        BigDecimal price,
        Currency currency
) {

    public AcquisitionDetails {
        validatePrice(price);
        validatePriceAndCurrency(price, currency);
        validateAcquisitionType(type, price, currency);
    }

    public static AcquisitionDetails purchased(
            LocalDate acquiredAt,
            BigDecimal price,
            Currency currency
    ) {
        return AcquisitionDetails.builder()
                .type(AcquisitionType.PURCHASED)
                .acquiredAt(acquiredAt)
                .price(price)
                .currency(currency)
                .build();
    }

    public static AcquisitionDetails gifted(
            LocalDate acquiredAt
    ) {
        return AcquisitionDetails.builder()
                .type(AcquisitionType.GIFTED)
                .acquiredAt(acquiredAt)
                .build();
    }

    public static AcquisitionDetails traded(
            LocalDate acquiredAt
    ) {
        return AcquisitionDetails.builder()
                .type(AcquisitionType.TRADED)
                .acquiredAt(acquiredAt)
                .build();
    }

    public static AcquisitionDetails unknown() {
        return AcquisitionDetails.builder()
                .type(AcquisitionType.UNKNOWN)
                .build();
    }

    private static void validatePrice(BigDecimal price) {
        if (price != null && price.signum() < 0) {
            throw new IllegalArgumentException(
                    "Acquisition price cannot be negative"
            );
        }

        if (price != null && price.scale() > 2) {
            throw new IllegalArgumentException(
                    "Acquisition price cannot have more than two decimal places"
            );
        }
    }

    private static void validatePriceAndCurrency(
            BigDecimal price,
            Currency currency
    ) {
        if (price != null && currency == null) {
            throw new IllegalArgumentException(
                    "Currency is required when a price is provided"
            );
        }

        if (price == null && currency != null) {
            throw new IllegalArgumentException(
                    "Currency cannot be provided without a price"
            );
        }
    }

    private static void validateAcquisitionType(
            AcquisitionType type,
            BigDecimal price,
            Currency currency
    ) {
        if (type == AcquisitionType.PURCHASED) {
            if (price == null || currency == null) {
                throw new IllegalArgumentException(
                        "Purchased games require a price and currency"
                );
            }
        } else if (price != null || currency != null) {
            throw new IllegalArgumentException(
                    "Price and currency are only supported for purchased games"
            );
        }
    }
}
