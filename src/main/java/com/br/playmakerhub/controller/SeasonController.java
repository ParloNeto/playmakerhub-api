package com.br.playmakerhub.controller;

import com.br.playmakerhub.models.Season;
import com.br.playmakerhub.services.SeasonService;
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
@RequestMapping(value = "/seasons")
@Tag(name = "Season", description = "Endpoints for Managing Seasons")
public class SeasonController {

    @Autowired
    SeasonService service;

    private static final Logger logger = LogManager.getLogger(IPlayerController.class);
    @GetMapping("/{seasonId}")
    @Operation(summary = "Finds a Season", description = "Finds a Season",
            tags = {"Season"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = Season.class))
                            }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<Season> getSeasonById(@PathVariable String seasonId) {
        logger.info("getSeasonById() is called.");
        Season season = service.getSeasonById(seasonId);
        logger.info("getSeasonById() is finished.");
        return ResponseEntity.ok().body(season);
    }

    @GetMapping
    @Operation(summary = "Find all Seasons", description = "Find all Seasons",
            tags = {"Season"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Season.class))
                                    )
                            }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<Season>> getAllSeasons() {
        logger.info("getAllSeasons() is called.");
        List<Season> seasons = service.getAllSeasons();
        logger.info("getAllSeasons() is finished.");
        return ResponseEntity.ok().body(seasons);
    }


    @PostMapping
    @Operation(summary = "Create a new Season",
            description = "Create a new Season",
            tags = {"Season"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = Season.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Season> createSeason(@RequestBody Season season) {
        Season seasonCreated = service.createSeason(season);
        return ResponseEntity.status(HttpStatus.CREATED).body(seasonCreated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Season",
            description = "Delete a Season",
            tags = {"Season"},
            method = "DELETE",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Void> deleteSeason(@PathVariable String id) {
        logger.info("deleteSeason() is called.");
        service.deleteSeason(id);
        logger.info("deleteSeason() is finished.");
        return ResponseEntity.noContent().build();
    }
}
