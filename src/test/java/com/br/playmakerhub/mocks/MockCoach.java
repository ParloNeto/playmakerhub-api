package com.br.playmakerhub.mocks;

import com.br.playmakerhub.dto.CoachDTO;
import com.br.playmakerhub.models.Coach;

import java.util.ArrayList;
import java.util.List;

public class MockCoach {



    public Coach mockEntity() {
        Coach coach = new Coach();
        coach.setId("66e0630de36afe058170dde6");
        coach.setCoachesName("Tite");
        coach.setNationality("Brazil");
        coach.setUrlImageCoach("www.sdasdasdas.com");
        coach.setCoachesName("Tite");

        return coach;
    }

    public CoachDTO mockEntityDTO() {
        CoachDTO coachDTO = new CoachDTO();
        coachDTO.setCoachesName("Tite");
        coachDTO.setNationality("Brazil");
        coachDTO.setUrlImageCoach("www.sdasdasdas.com");
        coachDTO.setCoachesName("Tite");

        return coachDTO;
    }

    public List<Coach> mockListEntity() {
        List<Coach> list = new ArrayList<Coach>();

        Coach coachOne = new Coach();
        coachOne.setId("66e0630de36afe058170dde6");
        coachOne.setCoachesName("Tite");
        coachOne.setNationality("Brazil");
        coachOne.setUrlImageCoach("www.sdasdasdas.com");

        list.add(coachOne);

        Coach coachTwo = new Coach();
        coachTwo.setId("66e0630de36afe058170dde7");
        coachTwo.setCoachesName("Abel Ferreira");
        coachTwo.setNationality("Portugal");
        coachTwo.setUrlImageCoach("www.sdasdasdas.com");

        list.add(coachTwo);

        Coach coachThree = new Coach();
        coachThree.setId("66e0630de36afe058170dde8");
        coachThree.setCoachesName("Zidane");
        coachThree.setNationality("French");
        coachThree.setUrlImageCoach("www.sdasdasdas.com");

        list.add(coachThree);

        return list;
    }
}
