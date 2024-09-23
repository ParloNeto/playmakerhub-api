package com.br.playmakerhub.service;

import com.br.playmakerhub.dto.CoachDTO;
import com.br.playmakerhub.mocks.MockCoach;
import com.br.playmakerhub.models.Coach;
import com.br.playmakerhub.repositories.CoachRepository;
import com.br.playmakerhub.services.CoachService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CoachServiceTest {

    MockCoach input;

    @Mock
    CoachRepository coachRepository;

    @InjectMocks
    CoachService coachService;


    @BeforeEach
    void setUp() {
        input = new MockCoach();
    }

    @Test
    @Order(0)
    @DisplayName("Testa o retorno de uma lista de técnicos")
    public void testGetAllCoaches() {
        List<Coach> coachesMock = input.mockListEntity();

        when(coachRepository.findAll()).thenReturn((coachesMock));

        List<Coach> coaches = coachService.getAllCoaches();

        assertNotNull(coaches);
        verify(coachRepository).findAll();
    }

    @Test
    @Order(1)
    @DisplayName("Testa o retorno de um técnico pelo ID")
    public void testGetCoachById() {
        String id = input.mockEntity().getId();
        Coach coachMock = input.mockEntity();

        when(coachRepository.findById(id)).thenReturn(Optional.of(coachMock));

        Coach coach = coachService.getCoachById(id);

        assertNotNull(coach);
        assertEquals(coach.getCoachesName(), "Tite");
        assertEquals(coach.getNationality(), "Brazil");
        assertEquals(coach.getUrlImageCoach(), "www.sdasdasdas.com");
        verify(coachRepository).findById(id);
    }

    @Test
    @Order(2)
    @DisplayName("Testa a criacao de um técnico")
    public void testCreateCoach() {
        CoachDTO coachMockDto = input.mockEntityDTO();
        Coach coachMock = input.mockEntity();

        when(coachRepository.save(ArgumentMatchers.any(Coach.class))).thenReturn(coachMock);

        Coach coach = coachService.createCoach(coachMockDto);

        assertNotNull(coach);
        assertEquals(coach.getCoachesName(), "Tite");
        assertEquals(coach.getNationality(), "Brazil");
        assertEquals(coach.getUrlImageCoach(), "www.sdasdasdas.com");
        verify(coachRepository).save(ArgumentMatchers.any(Coach.class));
    }

    @Test
    @Order(3)
    @DisplayName("Testa o delete de um técnico")
    public void testDeleteCoach() {
        String id = input.mockEntity().getId();
        Coach coachMock = input.mockEntity();

        when(coachRepository.findById(id)).thenReturn(Optional.of(coachMock));

        coachService.deleteCoach(id);

        verify(coachRepository).delete(ArgumentMatchers.any(Coach.class));
    }


}
