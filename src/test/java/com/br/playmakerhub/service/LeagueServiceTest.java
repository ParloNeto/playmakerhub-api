package com.br.playmakerhub.service;

import com.br.playmakerhub.exceptions.ObjectNotFoundException;
import com.br.playmakerhub.mocks.MockLeague;
import com.br.playmakerhub.models.Coach;
import com.br.playmakerhub.models.League;
import com.br.playmakerhub.models.Team;
import com.br.playmakerhub.repositories.LeagueRepository;
import com.br.playmakerhub.services.LeagueService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LeagueServiceTest {

    @Mock
    LeagueRepository repository;

    @InjectMocks
    LeagueService service;

    MockLeague input;

    @BeforeEach
    void setUp() {
        input = new MockLeague();
    }

    @Test
    @Order(0)
    @DisplayName("Retorna uma lista de todas as ligas")
    public void testGetAllLeagues() {
        List<League> leagues = input.mockListEntity();

        when(repository.findAll()).thenReturn(leagues);

        List<League> leagueList = service.findAllLeagues();

        assertNotNull(leagueList);
        assertFalse(leagueList.isEmpty());
        assertEquals(leagueList.get(0).getName(), "Premier League");
        assertEquals(leagueList.get(0).getTeams().get(0).getName(), "Arsenal");
        assertEquals(leagueList.get(0).getTeams().get(1).getName(), "Chelsea");
        assertEquals(leagueList.get(0).getTeams().get(2).getName(), "Manchester United");

        verify(repository).findAll();
    }

    @Test
    @Order(1)
    @DisplayName("Retorna uma liga pelo ID")
    public void testGetLeagueById() {
        League league = input.mockEntity();
        String id = league.getId();

        when(repository.findById(id)).thenReturn(Optional.of(league));

        League leagueObj = service.findLeagueById(id);

        assertNotNull(leagueObj);
        assertEquals(leagueObj.getName(), "Premier League");
        assertEquals(leagueObj.getTeams().get(0).getName(), "Arsenal");
        assertEquals(leagueObj.getTeams().get(1).getName(), "Chelsea");
        assertEquals(leagueObj.getTeams().get(2).getName(), "Manchester United");
        verify(repository).findById(id);
    }

    @Test
    @Order(2)
    @DisplayName("Cria uma liga")
    public void testCreateLeague() {
        League league = input.mockEntity();

        when(repository.save(league)).thenReturn(league);

        League leagueObj = service.createLeague(league);

        assertNotNull(leagueObj);
        assertEquals(leagueObj.getName(), "Premier League");
        assertEquals(leagueObj.getTeams().get(0).getName(), "Arsenal");
        assertEquals(leagueObj.getTeams().get(1).getName(), "Chelsea");
        assertEquals(leagueObj.getTeams().get(2).getName(), "Manchester United");
        verify(repository).save(league);
    }

    @Test
    @Order(3)
    @DisplayName("Atualiza uma liga")
    public void testUpdateLeague() {
        League leagueInitial = input.mockEntity();
        String id = leagueInitial.getId();

        League league = new League(leagueInitial.getId(), "Bundesliga", leagueInitial.getTeams());

        when(repository.findById(id)).thenReturn(Optional.of(leagueInitial));
        when(repository.save(leagueInitial)).thenReturn(league);

        League leagueObj = service.updateLeague(leagueInitial);

        assertNotNull(leagueInitial);
        assertNotEquals(leagueInitial.getName(), league.getName());
        assertEquals(leagueObj.getName(), "Bundesliga");
        assertEquals(leagueObj.getId(), league.getId());
        assertEquals(leagueObj.getTeams(), league.getTeams());

    }

    @Test
    @Order(4)
    @DisplayName("Deleta uma liga")
    public void testDeleteLeague() {
        String id = input.mockEntity().getId();
        League leagueMock = input.mockEntity();

        when(repository.findById(id)).thenReturn(Optional.of(leagueMock));

        service.deleteLeague(id);

        verify(repository).delete(ArgumentMatchers.any(League.class));
    }
}
