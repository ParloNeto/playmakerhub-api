package com.br.playmakerhub.mocks;

import com.br.playmakerhub.dto.CareerDTO;
import com.br.playmakerhub.models.Career;

import java.util.ArrayList;
import java.util.List;

import static com.br.playmakerhub.mocks.MockStatistics.mockCareerHistory;

public class MockCareer {

    private final MockCoach mockCoach = new MockCoach();
    private final MockSeason mockSeason = new MockSeason();
    private final MockPlayer mockPlayer = new MockPlayer();


    public Career mockEntity() {
        Career career = new Career();
        career.setId("673153326f1c766801b16d13");
        career.setCoach(mockCoach.mockEntity());
        career.setFifaCareer("FIFA 23");
        career.setLeagueCareer("La Liga");
        career.setTeamCareer("Barcelona");
        career.setSeasons(mockSeason.mockListEntity());
        career.setPlayers(mockPlayer.mockListEntity());
        career.setCareerHistory(mockCareerHistory());
        return career;
    }

    public CareerDTO mockEntityDTO() {
        CareerDTO careerDTO = new CareerDTO();
        careerDTO.setCoach(mockCoach.mockEntityDTO());
        careerDTO.setFifaCareer("FIFA 23");
        careerDTO.setLeagueCareer("La Liga");
        careerDTO.setTeamCareer("Barcelona");

        return careerDTO;
    }

    public List<Career> mockListEntity() {
        List<Career> list = new ArrayList<Career>();

        Career careerOne = mockEntity();

        list.add(careerOne);

        return list;
    }
}
