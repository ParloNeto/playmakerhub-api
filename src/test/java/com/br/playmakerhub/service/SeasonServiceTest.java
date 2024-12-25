package com.br.playmakerhub.service;

import com.br.playmakerhub.dto.SeasonDTO;
import com.br.playmakerhub.exceptions.ObjectNotFoundException;
import com.br.playmakerhub.mocks.MockPlayer;
import com.br.playmakerhub.mocks.MockSeason;
import com.br.playmakerhub.models.Career;
import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.models.Season;
import com.br.playmakerhub.repositories.CareerRepository;
import com.br.playmakerhub.repositories.SeasonRepository;
import com.br.playmakerhub.services.CareerService;
import com.br.playmakerhub.services.PlayerService;
import com.br.playmakerhub.services.SeasonService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SeasonServiceTest {

    MockSeason input;

    MockPlayer mockPlayer;

    @InjectMocks
    SeasonService seasonService;

    @Mock
    SeasonRepository seasonRepository;

    @Mock
    CareerRepository careerRepository;

    @Mock
    PlayerService playerService;

    @Mock
    CareerService careerService;

    @BeforeEach
    void setUp() {
        input = new MockSeason();
        mockPlayer = new MockPlayer();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(0)
    @DisplayName("Testa o retorno de uma lista de temporadas")
    public void testGetAllSeasones() {
        List<Season> seasonsMock = input.mockListEntity();

        when(seasonRepository.findAll()).thenReturn((seasonsMock));

        List<Season> seasones = seasonService.getAllSeasons();

        assertNotNull(seasones);
        verify(seasonRepository).findAll();
    }

    @Test
    @Order(1)
    @DisplayName("Testa o retorno de uma temporada pelo ID")
    public void testGetSeasonById() {
        String id = input.mockEntity().getId();
        Season seasonMock = input.mockEntity();

        when(seasonRepository.findById(id)).thenReturn(Optional.of(seasonMock));

        Season season = seasonService.getSeasonById(id);

        assertNotNull(season);
        assertEquals(season.getSeasonName(), "temporada-24-25");
        assertEquals(season.getGames(), 43);
        assertEquals(season.getWins(), 24);
        assertEquals(season.getDraws(), 8);
        assertEquals(season.getLosses(), 11);
        assertEquals(season.getGoalsScored(), 98);
        assertEquals(season.getGoalsConceded(), 68);
        assertNotNull(season.getPlayers());

        verify(seasonRepository).findById(id);
    }

    @Test
    @Order(2)
    @DisplayName("Testa a criacao de uma temporada")
    public void testCreateSeason() {
        SeasonDTO seasonMockDto = input.mockEntityDTO();
        Season seasonMock = input.mockEntity();

        when(seasonRepository.save(ArgumentMatchers.any(Season.class))).thenReturn(seasonMock);

        Season season = seasonService.createSeason(seasonMockDto);

        assertNotNull(season);
        assertEquals(season.getSeasonName(), "temporada-24-25");
        assertEquals(season.getGames(), 43);
        assertEquals(season.getWins(), 24);
        assertEquals(season.getDraws(), 8);
        assertEquals(season.getLosses(), 11);
        assertEquals(season.getGoalsScored(), 98);
        assertEquals(season.getGoalsConceded(), 68);
        assertNotNull(season.getPlayers());

        verify(seasonRepository).save(ArgumentMatchers.any(Season.class));
    }

    @Test
    @Order(3)
    @DisplayName("Testa o delete de uma temporada")
    public void testDeleteSeason() {
        String id = input.mockEntity().getId();
        Season seasonMock = input.mockEntity();

        when(seasonRepository.findById(id)).thenReturn(Optional.of(seasonMock));

        seasonService.deleteSeason(id);

        verify(seasonRepository).delete(ArgumentMatchers.any(Season.class));
    }

    @Test
    @Order(4)
    @DisplayName("Testa a atualização das estatísticas de uma temporada")
    public void testUpdateStatisticsSeason() {
        String seasonId = input.mockEntity().getId();
        Season seasonMock = input.mockEntity();
        Season updatedSeason = new Season();
        updatedSeason.setGames(50);
        updatedSeason.setWins(30);
        updatedSeason.setDraws(10);
        updatedSeason.setLosses(10);
        updatedSeason.setGoalsScored(120);
        updatedSeason.setGoalsConceded(70);

        when(seasonRepository.findById(seasonId)).thenReturn(Optional.of(seasonMock));
        when(seasonRepository.save(seasonMock)).thenReturn(updatedSeason);

        Season result = seasonService.updateStatisticsSeason(seasonId, updatedSeason);

        assertNotNull(result);
        assertEquals(50, result.getGames());
        assertEquals(30, result.getWins());
        assertEquals(10, result.getDraws());
        assertEquals(10, result.getLosses());
        assertEquals(120, result.getGoalsScored());
        assertEquals(70, result.getGoalsConceded());

        verify(seasonRepository).findById(seasonId);
        verify(seasonRepository).save(seasonMock);
    }

    @Test
    @Order(5)
    @DisplayName("Testa a atribuição de jogadores a uma temporada")
    public void testSetPlayersToSeason() {
        String seasonId = input.mockEntity().getId();
        Season seasonMock = input.mockEntity();

        List<Player> players = mockPlayer.mockListEntity();

        when(seasonRepository.findById(seasonId)).thenReturn(Optional.of(seasonMock));
        when(seasonRepository.save(seasonMock)).thenReturn(seasonMock);

        Season result = seasonService.setPlayersToSeason(seasonId, players);

        assertNotNull(result);
        assertEquals(players.size(), result.getPlayers().size());
        verify(seasonRepository).findById(seasonId);
        verify(seasonRepository).save(seasonMock);
    }


    @Test
    @Order(6)
    @DisplayName("Testa a remoção de um jogador de uma temporada")
    public void testRemovePlayerFromSeason() {
        String seasonId = "season123";
        String playerId = "player123";

        Season seasonMock = new Season();
        seasonMock.setId(seasonId);

        Player playerMock = mockPlayer.mockListEntity().get(0);
        playerMock.setId(playerId);

        List<Player> players = new ArrayList<>();
        players.add(playerMock);
        seasonMock.setPlayers(players);

        when(seasonRepository.findById(seasonId)).thenReturn(Optional.of(seasonMock));
        when(seasonRepository.save(seasonMock)).thenReturn(seasonMock);

        Season result = seasonService.removePlayerFromSeason(seasonId, playerId);

        assertNotNull(result);
        assertEquals(0, result.getPlayers().size(), "O jogador deveria ter sido removido.");
        assertFalse(result.getPlayers().stream().anyMatch(player -> player.getId().equals(playerId)));

        verify(seasonRepository).findById(seasonId);
        verify(seasonRepository).save(seasonMock);
        verify(playerService).updateStatisticsHistory(playerMock, playerMock.getStatisticsBySeasons());
    }

}
