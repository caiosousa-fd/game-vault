package com.gamevault.api;

import com.gamevault.api.openapi.GamesApiDelegate;
import com.gamevault.api.openapi.model.GameDetails;
import com.gamevault.api.openapi.model.GameFormat;
import com.gamevault.api.openapi.model.GameStatus;
import com.gamevault.api.openapi.model.GameSummaryPage;
import com.gamevault.api.openapi.model.GameUpsertRequest;
import com.gamevault.api.openapi.model.ItemCondition;
import com.gamevault.api.openapi.model.PlatformSummary;
import com.gamevault.api.openapi.model.Region;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

/**
 * Mocked {@link GamesApiDelegate} implementation.
 *
 * <p>Returns static sample data that mirrors the examples in the OpenAPI
 * specification so the API can be exercised end-to-end before a real
 * persistence layer exists.</p>
 */
@Service
public class SpringGamesApiDelegate implements GamesApiDelegate {

    @Override
    public ResponseEntity<Void> addGame(GameUpsertRequest gameUpsertRequest) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteGame(UUID id) {
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<GameDetails> getGame(UUID id) {
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<GameSummaryPage> getGames(
            Optional<String> query,
            Optional<UUID> platformId,
            Optional<GameFormat> format,
            Optional<GameStatus> status,
            Optional<Region> region,
            Optional<ItemCondition> condition,
            Optional<Boolean> hasBox,
            Optional<Boolean> hasManual,
            Optional<Integer> page,
            Optional<Integer> size,
            Optional<String> sort
    ) {
        GameSummaryPage response = new GameSummaryPage()
                .content(Collections.emptyList())
                .page(1)
                .size(1)
                .totalElements(0L)
                .totalPages(1)
                .first(true)
                .last(true);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<GameDetails> updateGame(UUID id, GameUpsertRequest gameUpsertRequest) {
        GameDetails game = new GameDetails()
                .id(id)
                .title("The Legend of Zelda: Ocarina of Time")
                .platform(new PlatformSummary()
                        .id(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                        .name("Nintendo 64"))
                .edition("Player's Choice")
                .region(Region.PAL)
                .format(GameFormat.PHYSICAL)
                .condition(ItemCondition.GOOD)
                .hasBox(true)
                .hasManual(true)
                .status(GameStatus.COMPLETED)
                .purchaseDate(LocalDate.parse("2001-03-15"))
                .purchasePrice(Float.parseFloat("29.99"))
                .currency("EUR")
                .coverUrl(URI.create("https://example.com/covers/zelda-oot.jpg"))
                .notes("Complete in box, cartridge in excellent working order.")
                .createdAt(OffsetDateTime.parse("2024-01-23T04:56:07Z"))
                .updatedAt(OffsetDateTime.parse("2024-01-23T04:56:07Z"));
        return ResponseEntity.ok(game);
    }
}
