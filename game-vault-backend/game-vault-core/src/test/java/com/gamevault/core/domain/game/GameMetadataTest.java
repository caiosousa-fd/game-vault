package com.gamevault.core.domain.game;

import com.gamevault.core.domain.common.ItemRegion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GameMetadataTest {

    private static final ItemRegion VALID_REGION = ItemRegion.PAL;

    @Test
    void shouldThrowExceptionWhenRegionIsNull() {
        assertThrows(
                NullPointerException.class,
                () -> new GameMetadata(null, null, null, null)
        );
    }

    @ParameterizedTest
    @MethodSource("invalidEditions")
    void shouldThrowExceptionForInvalidEdition(String edition, String reason) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new GameMetadata(VALID_REGION, edition, null, null),
                reason
        );
    }

    private static Stream<Arguments> invalidEditions() {
        return Stream.of(
                Arguments.of("   ", "Blank edition"),
                Arguments.of("", "Empty edition")
        );
    }
}
