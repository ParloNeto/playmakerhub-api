package com.br.playmakerhub.mocks;

import com.br.playmakerhub.dto.CoachDTO;
import com.br.playmakerhub.dto.PlayerDTO;
import com.br.playmakerhub.models.Coach;
import com.br.playmakerhub.models.Player;

import java.util.ArrayList;
import java.util.List;

import static com.br.playmakerhub.mocks.MockStatistics.mockStatisticsHistory;
import static com.br.playmakerhub.mocks.MockStatistics.mockStatisticsList;

public class MockPlayer {



    public Player mockEntity() {
        Player player = new Player();
        player.setId("6731584f42ae2864e855d5e1");
        player.setFirstName("Javier");
        player.setLastName("Puado");
        player.setKitNumber(7);
        player.setNationality("Spain");
        player.setPosition("MD");
        player.setJoined("2017");
        player.setUrlImagePlayer("https://cdn.sofifa.net/players/244/622/24_120.png");
        player.setStatisticsBySeasons(mockStatisticsList());
        player.setStatisticsHistory(mockStatisticsHistory());

        return player;
    }

    public PlayerDTO mockEntityDTO() {
        PlayerDTO playerDTO = new PlayerDTO();
        playerDTO.setIdCareer("1");
        playerDTO.setFirstName("Javier");
        playerDTO.setLastName("Puado");
        playerDTO.setKitNumber(7);
        playerDTO.setNationality("Spain");
        playerDTO.setPosition("MD");
        playerDTO.setJoined("2017");
        playerDTO.setUrlImagePlayer("https://cdn.sofifa.net/players/244/622/24_120.png");
        playerDTO.setStatisticsBySeasons(mockStatisticsList());
        playerDTO.setStatisticsHistory(mockStatisticsHistory());

        return playerDTO;
    }

    public List<Player> mockListEntity() {
        List<Player> players = new ArrayList<>();

        Player playerOne = mockEntity();

        players.add(playerOne);

        Player playerTwo = new Player();
        playerTwo.setId("2");
        playerTwo.setFirstName("Lionel");
        playerTwo.setLastName("Messi");
        playerTwo.setKitNumber(10);
        playerTwo.setNationality("Argentina");
        playerTwo.setPosition("FW");
        playerTwo.setJoined("2021");
        playerTwo.setUrlImagePlayer("https://cdn.sofifa.net/players/158/023/23_120.png");
        playerTwo.setStatisticsBySeasons(mockStatisticsList());
        playerTwo.setStatisticsHistory(mockStatisticsHistory());

        players.add(playerTwo);

        Player playerThree = new Player();
        playerTwo.setId("3");
        playerTwo.setFirstName("Martin");
        playerTwo.setLastName("Braithwaite");
        playerTwo.setKitNumber(22);
        playerTwo.setNationality("Denmark");
        playerTwo.setPosition("ATA");
        playerTwo.setJoined("2022");
        playerTwo.setUrlImagePlayer("https://cdn.sofifa.net/players/158/023/23_120.png");
        playerTwo.setStatisticsBySeasons(mockStatisticsList());
        playerTwo.setStatisticsHistory(mockStatisticsHistory());

        players.add(playerThree);

        return players;
    }

    public List<PlayerDTO> mockEntityListDTO() {
        List<PlayerDTO> playerDTOs = new ArrayList<>();
        playerDTOs.add(mockEntityDTO());
        return playerDTOs;
    }
}
