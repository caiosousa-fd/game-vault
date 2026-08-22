package com.gamevault.api;

import com.gamevault.api.openapi.model.GameDetails;
import com.gamevault.api.openapi.model.GameFormat;
import com.gamevault.api.openapi.model.GameStatus;
import com.gamevault.api.openapi.model.GameSummaryPage;
import com.gamevault.api.openapi.model.GameUpsertRequest;
import com.gamevault.api.openapi.model.ItemCondition;
import com.gamevault.api.openapi.model.Region;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class SpringGamesApiDelegateTest {

    @InjectMocks
    private SpringGamesApiDelegate delegate;

    @Test
    void shouldReturnOkWhenAddingGame() {
        ResponseEntity<Void> response = delegate.addGame(new GameUpsertRequest());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void shouldReturnNoContentWhenDeletingGame() {
        ResponseEntity<Void> response = delegate.deleteGame(UUID.randomUUID());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void shouldReturnNotFoundWhenGettingSingleGame() {
        ResponseEntity<GameDetails> response = delegate.getGame(UUID.randomUUID());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }

    @Test
    void shouldReturnOkWithEmptyPageWhenGettingGames() {
        ResponseEntity<GameSummaryPage> response = delegate.getGames(
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.empty()
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldReturnEmptyContentPageWhenGettingGames() {
        GameSummaryPage page = delegate.getGames(
                Optional.of("zelda"),
                Optional.of(UUID.randomUUID()),
                Optional.of(GameFormat.PHYSICAL),
                Optional.of(GameStatus.COMPLETED),
                Optional.of(Region.PAL),
                Optional.of(ItemCondition.GOOD),
                Optional.of(true),
                Optional.of(true),
                Optional.of(1),
                Optional.of(1),
                Optional.of("title,asc")
        ).getBody();

        assertThat(page).isNotNull();
        assertThat(page.getContent()).isEmpty();
        assertThat(page.getPage()).isEqualTo(1);
        assertThat(page.getSize()).isEqualTo(1);
        assertThat(page.getTotalElements()).isEqualTo(0L);
        assertThat(page.getTotalPages()).isEqualTo(1);
        assertThat(page.getFirst()).isTrue();
        assertThat(page.getLast()).isTrue();
    }

    @Test
    void shouldReturnOkWhenUpdatingGame() {
        ResponseEntity<GameDetails> response = delegate.updateGame(UUID.randomUUID(), new GameUpsertRequest());

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
    }

    @Test
    void shouldEchoRequestedIdWhenUpdatingGame() {
        UUID id = UUID.randomUUID();

        GameDetails game = delegate.updateGame(id, new GameUpsertRequest()).getBody();

        assertThat(game).isNotNull();
        assertThat(game.getId()).isEqualTo(id);
    }

    @Test
    void shouldReturnSampleGameDetailsWhenUpdatingGame() {
        UUID id = UUID.randomUUID();

        GameDetails game = delegate.updateGame(id, new GameUpsertRequest()).getBody();

        assertThat(game).isNotNull();
        assertThat(game.getTitle()).isEqualTo("The Legend of Zelda: Ocarina of Time");
        assertThat(game.getPlatform()).isNotNull();
        assertThat(game.getPlatform().getName()).isEqualTo("Nintendo 64");
        assertThat(game.getEdition().get()).isEqualTo("Player's Choice");
        assertThat(game.getRegion()).isEqualTo(Region.PAL);
        assertThat(game.getFormat()).isEqualTo(GameFormat.PHYSICAL);
        assertThat(game.getCondition().get()).isEqualTo(ItemCondition.GOOD);
        assertThat(game.getHasBox()).isTrue();
        assertThat(game.getHasManual()).isTrue();
        assertThat(game.getStatus()).isEqualTo(GameStatus.COMPLETED);
        assertThat(game.getCurrency()).isEqualTo("EUR");
    }
}
