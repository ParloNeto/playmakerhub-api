package com.br.playmakerhub.controller;

import com.br.playmakerhub.dto.CareerDTO;
import com.br.playmakerhub.dto.PlayerDTO;
import com.br.playmakerhub.dto.PlayerStatsDTO;
import com.br.playmakerhub.dto.SeasonDTO;
import com.br.playmakerhub.models.Career;
import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.models.Season;
import com.br.playmakerhub.services.CareerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/careers")
@Tag(name = "Career", description = "Endpoints for Managing Careers")
public class CareerController {

    @Autowired
    CareerService service;

    private static final Logger logger = LogManager.getLogger(IPlayerController.class);
    @GetMapping("/{careerId}")
    @Operation(summary = "Finds a Career", description = "Finds a Career",
            tags = {"Career"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = Career.class))
                            }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<Career> getCareerById(@PathVariable String careerId) {
        Career career = service.getCareerById(careerId);
        return ResponseEntity.ok().body(career);
    }

    @GetMapping("/{careerId}/{typeSeason}")
    @Operation(summary = "Find all Players by Season of Career", description = "Find all Players by Season of Career",
            tags = {"Career"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Player.class))
                                    )
                            }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<Player>> getAllPlayersBySeasonAndCareer(@PathVariable String careerId, @PathVariable String typeSeason) {
        List<Player> playersList = service.getAllPlayersBySeasonAndCareer(careerId, typeSeason);
        return ResponseEntity.status(HttpStatus.OK).body(playersList);
    }

    @GetMapping("/{careerId}/seasons/{typeSeason}/players/positions/{positionPlayer}")
    @Operation(summary = "Find all Players by Season of Career", description = "Find all Players by Season of Career",
            tags = {"Career"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Player.class))
                                    )
                            }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<Player>> getPlayersBySeasonAndPosition(@PathVariable String careerId, @PathVariable String typeSeason, @PathVariable String positionPlayer) {
        List<Player> playersListFiltered = service.getPlayersBySeasonAndPosition(careerId, typeSeason, positionPlayer);
        return ResponseEntity.status(HttpStatus.OK).body(playersListFiltered);
    }

    @GetMapping("/{careerId}/statistics/goals")
    @Operation(summary = "Find all Players sorted by goals in career", description = "Find all Players by Season of Career",
            tags = {"Career"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Player.class))
                                    )
                            }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<Page<PlayerStatsDTO>> getAllPlayersSortedByGoals(@PathVariable String careerId, Pageable pageable) {
        Page<PlayerStatsDTO> playersListFiltered = service.getStatisticsPlayersCareerByGoals(careerId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(playersListFiltered);
    }

    @GetMapping("/{careerId}/statistics/assists")
    @Operation(summary = "Find all Players sorted by assists in career", description = "Find all Players by Season of Career",
            tags = {"Career"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Player.class))
                                    )
                            }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<Page<PlayerStatsDTO>> getAllPlayersSortedByAssists(@PathVariable String careerId, Pageable pageable) {
        Page<PlayerStatsDTO> playersListFiltered = service.getStatisticsPlayersCareerByAssists(careerId, pageable);
        return ResponseEntity.status(HttpStatus.OK).body(playersListFiltered);
    }

    @GetMapping
    @Operation(summary = "Find all Careers", description = "Find all Careers",
            tags = {"Career"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Career.class))
                                    )
                            }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<Career>> getAllCareers() {
        List<Career> careers = service.getAllCareers();
        return ResponseEntity.ok().body(careers);
    }


    @PostMapping
    @Operation(summary = "Create a new Career",
            description = "Create a new Career",
            tags = {"Career"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = Career.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Career> createCareer(@RequestBody CareerDTO careerDTO) {
        Career careerCreated = service.createCareer(careerDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(careerCreated.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/{careerId}/{typeSeason}")
    @Operation(summary = "Add a player to a career",
            description = "Adds a player to the specified career for a given season type.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Player added to career successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Career not found")
    })
    public ResponseEntity<Career> createPlayerToCareer(@RequestBody PlayerDTO playerDto, @PathVariable String typeSeason, @PathVariable String careerId) {
        Career careerAtuc = service.addPlayerToCareer(careerId, playerDto, typeSeason);
        return ResponseEntity.status(HttpStatus.CREATED).body(careerAtuc);
    }



    @GetMapping("/{careerId}/seasons")
    @Operation(summary = "Get seasons by career",
            description = "Retrieves the list of seasons associated with a specific career.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seasons retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Career not found")
    })
    public ResponseEntity<List<Map<String, String>>> getSeasonsByCareer(@PathVariable String careerId) {
        List<Map<String, String>> seasonList = service.getSeasonsByCareer(careerId);
        return ResponseEntity.status(HttpStatus.OK).body(seasonList);
    }

    @GetMapping("/{careerId}/seasons/{typeSeason}")
    @Operation(summary = "Get specific seasons by career",
            description = "Retrieves the specific season associated with a specific career.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Seasons retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Career not found")
    })
    public ResponseEntity<Season> getSeasonByCareer(@PathVariable String careerId, @PathVariable String typeSeason) {
        Season season = service.getSeasonByCareer(careerId, typeSeason);
        return ResponseEntity.status(HttpStatus.OK).body(season);
    }

    @PostMapping("/{careerId}/seasons")
    @Operation(summary = "Add a season to a career",
            description = "Adds a new season to the specified career.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Season added to career successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid season data"),
            @ApiResponse(responseCode = "404", description = "Career not found")
    })
    public ResponseEntity<Career> addSeasonToCareer(@RequestBody SeasonDTO seasonDTO, @PathVariable String careerId) {
        Career careerAtuc = service.addSeasonToCareer(careerId, seasonDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(careerAtuc);
    }

    @GetMapping("/{careerId}/players")
    @Operation(summary = "Get players of a career",
            description = "Retrieves the list of players associated with a specific career.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Players retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Career not found")
    })
    public ResponseEntity<List<Player>> getPlayerOfCareer(@PathVariable String careerId) {
        List<Player> playersList = service.getPlayersOfCareer(careerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(playersList);
    }

    @GetMapping("/{careerId}/{seasonName}/available")
    @Operation(summary = "Get available players for a season",
            description = "Retrieves a list of available players for a specific season in a career.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Available players retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "Career or season not found")
    })
    public ResponseEntity<List<Player>> availablePlayersForSeason(@PathVariable String careerId, @PathVariable String seasonName) {
        List<Player> availablePlayersForSeason = service.getAvailablePlayersForSeason(careerId, seasonName);
        return ResponseEntity.status(HttpStatus.OK).body(availablePlayersForSeason);
    }

    @PutMapping("/{careerId}/{playerId}")
    @Operation(summary = "Update a player for a specific season",
            description = "Updates a player's association with a specific season in a career.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Player updated successfully"),
            @ApiResponse(responseCode = "404", description = "Career or player not found"),
            @ApiResponse(responseCode = "400", description = "Invalid season data")
    })
    public ResponseEntity<Career> updatePlayerToSpecificSeason(@PathVariable String careerId, @PathVariable String playerId, @RequestBody Season season) {
        service.updatePlayerToSeason(careerId, playerId, season);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Career",
            description = "Delete a Career",
            tags = {"Career"},
            method = "DELETE",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Void> deleteCareer(@PathVariable String id) {
        service.deleteCareer(id);
        return ResponseEntity.noContent().build();
    }
}
