package com.br.playmakerhub.controller;

import com.br.playmakerhub.models.Career;
import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.models.Season;
import com.br.playmakerhub.services.CareerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
        logger.info("getCareerById() is called.");
        Career career = service.getCareerById(careerId);
        logger.info("getCareerById() is finished.");
        return ResponseEntity.ok().body(career);
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
        logger.info("getAllCareers() is called.");
        List<Career> careers = service.getAllCareers();
        logger.info("getAllCareers() is finished.");
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
    public ResponseEntity<Career> createCareer(@RequestBody Career career) {
        Career careerCreated = service.createCareer(career);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(careerCreated.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PostMapping("/{careerId}/{typeSeason}")
    public ResponseEntity<Career> createPlayerToCareer(@RequestBody Player player, @PathVariable String typeSeason, @PathVariable String careerId) {
        Career careerAtuc = service.addPlayerToCareer(careerId, player, typeSeason);
        return ResponseEntity.status(HttpStatus.CREATED).body(careerAtuc);
    }

    @PostMapping("/{careerId}/seasons")
    public ResponseEntity<Career> addSeasonToCareer(@RequestBody Season season, @PathVariable String careerId) {
        Career careerAtuc = service.addSeasonToCareer(careerId, season);
        return ResponseEntity.status(HttpStatus.CREATED).body(careerAtuc);
    }

    @GetMapping("/{careerId}/players")
    public ResponseEntity<List<Player>> getPlayerOfCareer(@PathVariable String careerId) {
        List<Player> playersList = service.getPlayersOfCareer(careerId);
        return ResponseEntity.status(HttpStatus.CREATED).body(playersList);
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
        logger.info("deleteCareer() is called.");
        service.deleteCareer(id);
        logger.info("deleteCareer() is finished.");
        return ResponseEntity.noContent().build();
    }
}
