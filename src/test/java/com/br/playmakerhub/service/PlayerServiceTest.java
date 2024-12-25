package com.br.playmakerhub.service;

import com.br.playmakerhub.dto.PlayerDTO;
import com.br.playmakerhub.exceptions.season.DuplicateStatisticsException;
import com.br.playmakerhub.mocks.MockPlayer;
import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.models.Statistics;
import com.br.playmakerhub.repositories.PlayerRepository;
import com.br.playmakerhub.services.PlayerService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.br.playmakerhub.mocks.MockStatistics.mockStatisticsList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PlayerServiceTest {

    MockPlayer input;

    @Mock
    PlayerRepository playerRepository;

    @InjectMocks
    PlayerService playerService;


    @BeforeEach
    void setUp() {
        input = new MockPlayer();
    }

    @Test
    @Order(0)
    @DisplayName("Testa o retorno de uma lista de jogadores")
    public void testGetAllPlayers() {
        List<Player> playersMock = input.mockListEntity();

        when(playerRepository.findAll()).thenReturn((playersMock));

        List<Player> players = playerService.getAllPlayers();

        assertNotNull(players);
        verify(playerRepository).findAll();
    }

    @Test
    @Order(1)
    @DisplayName("Testa o retorno de um jogador pelo ID")
    public void testGetPlayerById() {
        String id = input.mockEntity().getId();
        Player playerMock = input.mockEntity();

        when(playerRepository.findById(id)).thenReturn(Optional.of(playerMock));

        Player player = playerService.getPlayerById(id);

        assertNotNull(player);
        assertEquals(player.getFirstName(), "Javier");
        assertEquals(player.getLastName(), "Puado");
        assertEquals(player.getNationality(), "Spain");
        assertEquals(player.getKitNumber(), 7);
        assertEquals(player.getPosition(), "MD");
        assertEquals(player.getJoined(), "2017");
        assertEquals(player.getUrlImagePlayer(), "https://cdn.sofifa.net/players/244/622/24_120.png");

        verify(playerRepository).findById(id);
    }

    @Test
    @Order(2)
    @DisplayName("Testa a criacao de um jogador")
    public void testCreatePlayer() {
        PlayerDTO playerMockDto = input.mockEntityDTO();
        Player playerMock = input.mockEntity();

        when(playerRepository.save(ArgumentMatchers.any(Player.class))).thenReturn(playerMock);

        Player player = playerService.createPlayer(playerMockDto);

        assertNotNull(player);
        assertEquals(player.getFirstName(), "Javier");
        assertEquals(player.getLastName(), "Puado");
        assertEquals(player.getNationality(), "Spain");
        assertEquals(player.getKitNumber(), 7);
        assertEquals(player.getPosition(), "MD");
        assertEquals(player.getJoined(), "2017");
        assertEquals(player.getUrlImagePlayer(), "https://cdn.sofifa.net/players/244/622/24_120.png");

        verify(playerRepository).save(ArgumentMatchers.any(Player.class));
    }

    @Test
    @Order(3)
    @DisplayName("Testa o delete de um jogador")
    public void testDeletePlayer() {
        String id = input.mockEntity().getId();
        Player playerMock = input.mockEntity();

        when(playerRepository.findById(id)).thenReturn(Optional.of(playerMock));

        playerService.deletePlayer(id);

        verify(playerRepository).delete(ArgumentMatchers.any(Player.class));
    }

    @Test
    @Order(4)
    @DisplayName("Testa a busca das estatísticas do jogador por temporada")
    public void testFindPlayerStatisticsBySeason() {
        String playerId = input.mockEntity().getId();
        String seasonName = "temporada-24-25";
        Player playerMock = input.mockEntity();

        Statistics statisticsMock = mockStatisticsList().get(0);

        playerMock.getStatisticsBySeasons().add(statisticsMock);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(playerMock));

        Statistics statistics = playerService.findPlayerStatisticsBySeason(playerId, seasonName);

        assertNotNull(statistics);
        assertEquals(seasonName, statistics.getSeason());
        assertEquals(43, statistics.getMatches());
        assertEquals(20, statistics.getGoals());
        assertEquals(10, statistics.getAssists());
        assertEquals(5, statistics.getYellowCards());
        assertEquals(1, statistics.getRedCards());
    }

    @Test
    @Order(5)
    @DisplayName("Testa a criação de estatísticas para uma nova temporada do jogador")
    public void testCreateStatisticsSeasonPlayer() {
        String playerId = input.mockEntity().getId();
        Statistics statisticsMock = new Statistics();

        statisticsMock.setSeason("temporada-25-26");
        statisticsMock.setMatches(43);
        statisticsMock.setGoals(20);
        statisticsMock.setAssists(10);
        statisticsMock.setYellowCards(5);
        statisticsMock.setRedCards(1);

        Player playerMock = input.mockEntity();

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(playerMock));
        when(playerRepository.save(ArgumentMatchers.any(Player.class))).thenReturn(playerMock);

        playerService.createStatisticsSeasonPlayer(playerId, statisticsMock);

        verify(playerRepository).save(ArgumentMatchers.any(Player.class));
        assertEquals(4, playerMock.getStatisticsBySeasons().size());
        assertEquals("temporada-22-23", playerMock.getStatisticsBySeasons().get(2).getSeason());
    }

    @Test
    @Order(6)
    @DisplayName("Testa a exceção de criação de estatísticas para uma nova temporada do jogador - DuplicateStatisticsException")
    public void testCreateStatisticsSeasonPlayerDuplicateSeason() {
        String playerId = input.mockEntity().getId();
        Statistics statisticsMock = mockStatisticsList().get(1);

        Player playerMock = input.mockEntity();

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(playerMock));

        assertThrows(DuplicateStatisticsException.class, () -> {
            playerService.createStatisticsSeasonPlayer(playerId, statisticsMock);
        });

        verify(playerRepository, never()).save(ArgumentMatchers.any(Player.class));
    }

    @Test
    @Order(7)
    @DisplayName("Testa a atualização das estatísticas de uma temporada do jogador")
    public void testUpdateStatisticsSeasonPlayer() {
        String playerId = input.mockEntity().getId();
        Statistics statisticsMock = input.mockEntity().getStatisticsBySeasons().get(0);
//        Statistics oldStatistics = statisticsMock;
//        List<Statistics> statisticsList = new ArrayList<>();

        statisticsMock.setSeason("temporada-25-26");
        statisticsMock.setMatches(43);
        statisticsMock.setGoals(20);
        statisticsMock.setAssists(10);
        statisticsMock.setYellowCards(5);
        statisticsMock.setRedCards(1);

        Player playerMock = input.mockEntity();
        playerMock.getStatisticsBySeasons().clear();

        playerMock.getStatisticsBySeasons().add(statisticsMock);

        when(playerRepository.findById(playerId)).thenReturn(Optional.of(playerMock));
        when(playerRepository.save(ArgumentMatchers.any(Player.class))).thenReturn(playerMock);

        List<Statistics> updatedStatistics = playerService.updateStatisticsSeasonPlayer(playerId, statisticsMock);

        verify(playerRepository).save(ArgumentMatchers.any(Player.class));
        assertEquals("temporada-25-26", updatedStatistics.get(0).getSeason());
        assertEquals(43, updatedStatistics.get(0).getMatches());
        assertEquals(20, updatedStatistics.get(0).getGoals());
        assertEquals(10, updatedStatistics.get(0).getAssists());
        assertEquals(5, updatedStatistics.get(0).getYellowCards());
        assertEquals(1, updatedStatistics.get(0).getRedCards());
    }



}
