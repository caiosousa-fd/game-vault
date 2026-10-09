package com.gamevault.core.domain.game;

import com.gamevault.core.domain.common.AcquisitionDetails;
import com.gamevault.core.domain.common.AcquisitionType;
import com.gamevault.core.domain.common.ItemRegion;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;

class GameTest {
    private static final UUID VALID_ID = UUID.randomUUID();
    private static final UUID VALID_PLATFORM_ID = UUID.randomUUID();
    private static final GameMetadata VALID_METADATA = new GameMetadata(ItemRegion.PAL, null, null, null);
    private static final GameCopy VALID_COPY = new PhysicalGameCopy(null, false, false);
    private static final GameStatus VALID_STATUS = GameStatus.BACKLOG;
    private static final AcquisitionDetails VALID_ACQUISITION_DETAILS = new AcquisitionDetails(
            AcquisitionType.UNKNOWN,
            null,
            null,
            null
    );

    @ParameterizedTest
    @MethodSource("nullFieldArguments")
    void shouldThrowExceptionWhenRequiredFieldIsNull(String fieldName, GameConstructorArguments args) {
        assertThrows(
                NullPointerException.class,
                args::construct,
                fieldName + " should not be null"
        );
    }

    private static Stream<Arguments> nullFieldArguments() {
        return Stream.of(
                Arguments.of(
                        "id",
                        new GameConstructorArguments(
                                null,
                                "Title",
                                VALID_PLATFORM_ID,
                                VALID_METADATA,
                                VALID_COPY,
                                VALID_STATUS,
                                VALID_ACQUISITION_DETAILS
                        )
                ),
                Arguments.of(
                        "title",
                        new GameConstructorArguments(
                                VALID_ID,
                                null,
                                VALID_PLATFORM_ID,
                                VALID_METADATA,
                                VALID_COPY,
                                VALID_STATUS,
                                VALID_ACQUISITION_DETAILS
                        )
                ),
                Arguments.of(
                        "platformId",
                        new GameConstructorArguments(
                                VALID_ID,
                                "Title",
                                null,
                                VALID_METADATA,
                                VALID_COPY,
                                VALID_STATUS,
                                VALID_ACQUISITION_DETAILS
                        )
                ),
                Arguments.of(
                        "metadata",
                        new GameConstructorArguments(
                                VALID_ID,
                                "Title",
                                VALID_PLATFORM_ID,
                                null,
                                VALID_COPY,
                                VALID_STATUS,
                                VALID_ACQUISITION_DETAILS
                        )
                ),
                Arguments.of(
                        "copy",
                        new GameConstructorArguments(
                                VALID_ID,
                                "Title",
                                VALID_PLATFORM_ID,
                                VALID_METADATA,
                                null,
                                VALID_STATUS,
                                VALID_ACQUISITION_DETAILS
                        )
                ),
                Arguments.of(
                        "status",
                        new GameConstructorArguments(
                                VALID_ID,
                                "Title",
                                VALID_PLATFORM_ID,
                                VALID_METADATA,
                                VALID_COPY,
                                null,
                                VALID_ACQUISITION_DETAILS
                        )
                ),
                Arguments.of(
                        "acquisitionDetails",
                        new GameConstructorArguments(
                                VALID_ID,
                                "Title",
                                VALID_PLATFORM_ID,
                                VALID_METADATA,
                                VALID_COPY,
                                VALID_STATUS,
                                null
                        )
                )
        );
    }

    @ParameterizedTest
    @MethodSource("invalidTitles")
    void shouldThrowExceptionForInvalidTitle(String title, String reason) {
        assertThrows(
                IllegalArgumentException.class,
                () -> new Game(
                        VALID_ID,
                        title,
                        VALID_PLATFORM_ID,
                        VALID_METADATA,
                        VALID_COPY,
                        VALID_STATUS,
                        VALID_ACQUISITION_DETAILS
                ),
                reason
        );
    }

    private static Stream<Arguments> invalidTitles() {
        return Stream.of(
                Arguments.of("   ", "Blank title"),
                Arguments.of("", "Empty title")
        );
    }

    private record GameConstructorArguments(
            UUID id,
            String title,
            UUID platformId,
            GameMetadata metadata,
            GameCopy copy,
            GameStatus status,
            AcquisitionDetails acquisitionDetails
    ) {
        void construct() {
            new Game(id, title, platformId, metadata, copy, status, acquisitionDetails);
        }
    }
}
