package com.gamevault.core.domain.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AcquisitionDetailsTest {
    private static final AcquisitionType VALID_TYPE = AcquisitionType.PURCHASED;
    private static final BigDecimal VALID_PRICE = new BigDecimal("10.00");
    private static final Currency VALID_CURRENCY = Currency.getInstance("USD");

    @ParameterizedTest
    @MethodSource("nullTypeArguments")
    void shouldThrowExceptionWhenTypeIsNull(AcquisitionType type, String reason) {
        assertThrows(
                NullPointerException.class,
                () -> new AcquisitionDetails(type, null, VALID_PRICE, VALID_CURRENCY),
                reason
        );
    }

    private static Stream<Arguments> nullTypeArguments() {
        return Stream.of(
                Arguments.of(null, "Type should not be null")
        );
    }

    @ParameterizedTest
    @MethodSource("invalidPrices")
    void shouldThrowExceptionForInvalidPrice(BigDecimal price, String reason) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AcquisitionDetails(
                        AcquisitionType.PURCHASED,
                        null,
                        price,
                        VALID_CURRENCY
                ),
                reason
        );
    }

    private static Stream<Arguments> invalidPrices() {
        return Stream.of(
                Arguments.of(new BigDecimal("-10.00"), "Negative price"),
                Arguments.of(new BigDecimal("10.999"), "More than two decimal places")
        );
    }

    @ParameterizedTest
    @MethodSource("priceCurrencyMismatches")
    void shouldThrowExceptionForPriceCurrencyMismatch(
            AcquisitionType type,
            BigDecimal price,
            Currency currency,
            String reason
    ) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new AcquisitionDetails(type, null, price, currency),
                reason
        );
    }

    private static Stream<Arguments> priceCurrencyMismatches() {
        return Stream.of(
                Arguments.of(
                        AcquisitionType.PURCHASED,
                        VALID_PRICE,
                        null,
                        "Purchased without currency"
                ),
                Arguments.of(
                        AcquisitionType.PURCHASED,
                        null,
                        null,
                        "Purchased without price and currency"
                ),
                Arguments.of(
                        AcquisitionType.GIFTED,
                        VALID_PRICE,
                        VALID_CURRENCY,
                        "Non-purchased type with price and currency"
                ),
                Arguments.of(
                        AcquisitionType.TRADED,
                        null,
                        VALID_CURRENCY,
                        "Non-purchased type with currency"
                ),
                Arguments.of(
                        AcquisitionType.GIFTED,
                        null,
                        VALID_CURRENCY,
                        "Currency provided without price"
                )
        );
    }

    @Test
    void shouldCreateValidPurchasedAcquisition() {
        LocalDate acquiredAt = LocalDate.of(2024, 1, 15);
        BigDecimal price = new BigDecimal("59.99");
        Currency currency = Currency.getInstance("USD");

        AcquisitionDetails details = new AcquisitionDetails(
                AcquisitionType.PURCHASED,
                acquiredAt,
                price,
                currency
        );

        assertEquals(AcquisitionType.PURCHASED, details.type());
        assertEquals(acquiredAt, details.acquiredAt());
        assertEquals(price, details.price());
        assertEquals(currency, details.currency());
    }

    @Test
    void shouldCreateValidGiftedAcquisition() {
        LocalDate acquiredAt = LocalDate.of(2024, 1, 15);

        AcquisitionDetails details = new AcquisitionDetails(
                AcquisitionType.GIFTED,
                acquiredAt,
                null,
                null
        );

        assertEquals(AcquisitionType.GIFTED, details.type());
        assertEquals(acquiredAt, details.acquiredAt());
        assertNull(details.price());
        assertNull(details.currency());
    }

    @Test
    void shouldCreateValidTradedAcquisition() {
        LocalDate acquiredAt = LocalDate.of(2024, 1, 15);

        AcquisitionDetails details = new AcquisitionDetails(
                AcquisitionType.TRADED,
                acquiredAt,
                null,
                null
        );

        assertEquals(AcquisitionType.TRADED, details.type());
        assertEquals(acquiredAt, details.acquiredAt());
        assertNull(details.price());
        assertNull(details.currency());
    }

    @Test
    void shouldCreateValidUnknownAcquisition() {
        AcquisitionDetails details = new AcquisitionDetails(
                AcquisitionType.UNKNOWN,
                null,
                null,
                null
        );

        assertEquals(AcquisitionType.UNKNOWN, details.type());
        assertNull(details.acquiredAt());
        assertNull(details.price());
        assertNull(details.currency());
    }

    @Test
    void shouldCreateValidZeroPriceAcquisition() {
        BigDecimal price = new BigDecimal("0.00");
        Currency currency = Currency.getInstance("USD");

        AcquisitionDetails details = new AcquisitionDetails(
                AcquisitionType.PURCHASED,
                null,
                price,
                currency
        );

        assertEquals(price, details.price());
        assertEquals(currency, details.currency());
    }

    @Test
    void shouldCreateValidPriceWithOneDecimal() {
        BigDecimal price = new BigDecimal("10.5");
        Currency currency = Currency.getInstance("USD");

        AcquisitionDetails details = new AcquisitionDetails(
                AcquisitionType.PURCHASED,
                null,
                price,
                currency
        );

        assertEquals(price, details.price());
    }

    @Test
    void shouldCreatePurchasedUsingFactory() {
        LocalDate acquiredAt = LocalDate.of(2024, 1, 15);
        BigDecimal price = new BigDecimal("49.99");
        Currency currency = Currency.getInstance("USD");

        AcquisitionDetails details = AcquisitionDetails.purchased(acquiredAt, price, currency);

        assertEquals(AcquisitionType.PURCHASED, details.type());
        assertEquals(acquiredAt, details.acquiredAt());
        assertEquals(price, details.price());
        assertEquals(currency, details.currency());
    }

    @Test
    void shouldCreateGiftedUsingFactory() {
        LocalDate acquiredAt = LocalDate.of(2024, 1, 15);

        AcquisitionDetails details = AcquisitionDetails.gifted(acquiredAt);

        assertEquals(AcquisitionType.GIFTED, details.type());
        assertEquals(acquiredAt, details.acquiredAt());
        assertNull(details.price());
        assertNull(details.currency());
    }

    @Test
    void shouldCreateTradedUsingFactory() {
        LocalDate acquiredAt = LocalDate.of(2024, 1, 15);

        AcquisitionDetails details = AcquisitionDetails.traded(acquiredAt);

        assertEquals(AcquisitionType.TRADED, details.type());
        assertEquals(acquiredAt, details.acquiredAt());
        assertNull(details.price());
        assertNull(details.currency());
    }

    @Test
    void shouldCreateUnknownUsingFactory() {
        AcquisitionDetails details = AcquisitionDetails.unknown();

        assertEquals(AcquisitionType.UNKNOWN, details.type());
        assertNull(details.acquiredAt());
        assertNull(details.price());
        assertNull(details.currency());
    }

    @Test
    void shouldCreateBuilderAndModifyWithToBuilder() {
        LocalDate originalDate = LocalDate.of(2024, 1, 15);
        BigDecimal originalPrice = new BigDecimal("59.99");
        Currency originalCurrency = Currency.getInstance("USD");

        AcquisitionDetails original = new AcquisitionDetails(
                AcquisitionType.PURCHASED,
                originalDate,
                originalPrice,
                originalCurrency
        );

        LocalDate newDate = LocalDate.of(2024, 2, 20);
        AcquisitionDetails modified = original.toBuilder()
                .acquiredAt(newDate)
                .build();

        assertEquals(newDate, modified.acquiredAt());
        assertEquals(AcquisitionType.PURCHASED, modified.type());
        assertEquals(originalPrice, modified.price());
        assertEquals(originalCurrency, modified.currency());
    }
}
