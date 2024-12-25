package com.br.playmakerhub.service;

import com.br.playmakerhub.dto.CareerDTO;
import com.br.playmakerhub.dto.PlayerDTO;
import com.br.playmakerhub.exceptions.career.CareerNotFoundException;
import com.br.playmakerhub.mocks.MockCareer;
import com.br.playmakerhub.models.Career;
import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.models.Season;
import com.br.playmakerhub.repositories.CareerRepository;
import com.br.playmakerhub.repositories.SeasonRepository;
import com.br.playmakerhub.services.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CareerServiceTest {

    MockCareer input;

    @Mock
    CareerRepository careerRepository;

    @Mock
    SeasonRepository seasonRepository;

    @Mock
    CoachService coachService;

    @Mock
    FIFAService fifaService;

    @Mock
    SeasonService seasonService;

    @Mock
    PlayerService playerService;

    @InjectMocks
    CareerService careerService;


    @BeforeEach
    void setUp() {
        input = new MockCareer();
    }

    @Test
    @Order(0)
    @DisplayName("Testa o retorno de uma lista de carreira")
    public void testGetAllCareers() {
        List<Career> careersMock = input.mockListEntity();

        when(careerRepository.findAll()).thenReturn((careersMock));

        List<Career> careeres = careerService.getAllCareers();

        assertNotNull(careeres);
        verify(careerRepository).findAll();
    }

    @Test
    @Order(1)
    @DisplayName("Testa o retorno de uma carreira pelo ID")
    public void testGetCareerById() {
        String id = input.mockEntity().getId();
        Career careerMock = input.mockEntity();

        when(careerRepository.findById(id)).thenReturn(Optional.of(careerMock));

        Career career = careerService.getCareerById(id);

        assertNotNull(career);
        assertEquals(career.getCoach().getCoachesName(), "Tite");
        assertEquals(career.getFifaCareer(), "FIFA 23");
        assertEquals(career.getLeagueCareer(), "La Liga");
        assertEquals(career.getTeamCareer(), "Barcelona");

        verify(careerRepository).findById(id);
    }

    @Test
    @Order(2)
    @DisplayName("Testa a criacao de uma carreira")
    public void testCreateCareer() {
        CareerDTO careerMockDto = input.mockEntityDTO();
        Career careerMock = input.mockEntity();

        when(careerRepository.save(any(Career.class))).thenReturn(careerMock);

        Career career = careerService.createCareer(careerMockDto);

        assertNotNull(career);
        assertEquals(career.getCoach().getCoachesName(), "Tite");
        assertEquals(career.getFifaCareer(), "FIFA 23");
        assertEquals(career.getLeagueCareer(), "La Liga");
        assertEquals(career.getTeamCareer(), "Barcelona");

        verify(careerRepository).save(any(Career.class));
    }

    @Test
    @Order(3)
    @DisplayName("Testa o delete de uma carreira")
    public void testDeleteCareer() {
        String id = input.mockEntity().getId();
        Career careerMock = input.mockEntity();

        when(careerRepository.findById(id)).thenReturn(Optional.of(careerMock));

        careerService.deleteCareer(id);

        verify(careerRepository).delete(any(Career.class));
    }

    @Test
    @Order(4)
    @DisplayName("Testa a exceção ao tentar obter uma carreira com ID inválido")
    public void testGetCareerByIdNotFound() {
        String id = "invalidId";
        when(careerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CareerNotFoundException.class, () -> {
            careerService.getCareerById(id);
        });

        verify(careerRepository).findById(id);
    }

    @Test
    @Order(5)
    @DisplayName("Testa a adição de um jogador à carreira")
    public void testAddPlayerToCareer() {
        String careerId = input.mockEntity().getId();
        String typeSeason = input.mockEntity().getSeasons().get(2).getSeasonName(); // Nome de temporada mockado

        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setFirstName("Rony");
        playerDTO.setLastName("Rustico");
        playerDTO.setNationality("Brazil");
        playerDTO.setPosition("ATA");
        playerDTO.setKitNumber(75);

        Career careerMock = input.mockEntity();
        Player playerMock = new Player();
        playerMock.setIdCareer("5");
        playerMock.setFirstName("Rony");
        playerMock.setLastName("Rustico");
        playerMock.setNationality("Brazil");
        playerMock.setPosition("ATA");
        playerMock.setKitNumber(75);

        when(careerRepository.findById(careerId)).thenReturn(Optional.of(careerMock));
        when(playerService.createPlayer(playerDTO)).thenReturn(playerMock);
        when(seasonRepository.save(ArgumentMatchers.any(Season.class))).thenReturn(careerMock.getSeasons().get(2)); // Simula a temporada sendo salva

        careerService.addPlayerToCareer(careerId, playerDTO, typeSeason);

        verify(careerRepository).save(careerMock);
    }

    @Test
    @Order(6)
    @DisplayName("Testa a exceção ao tentar deletar uma carreira que não existe")
    public void testDeleteCareerNotFound() {
        String id = "invalidId";
        when(careerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(CareerNotFoundException.class, () -> {
            careerService.deleteCareer(id);
        });

        verify(careerRepository).findById(id);
    }



}
