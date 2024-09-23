package com.br.playmakerhub.controller;

import com.br.playmakerhub.dto.CoachDTO;
import com.br.playmakerhub.models.Coach;
import com.br.playmakerhub.services.CoachService;
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
@RequestMapping(value = "/coaches")
@Tag(name = "Coach", description = "Endpoints for Managing Coaches")
public class CoachController {

    @Autowired
    CoachService service;

    private static final Logger logger = LogManager.getLogger(IPlayerController.class);
    @GetMapping("/{coachId}")
    @Operation(summary = "Finds a Coach", description = "Finds a Coach",
            tags = {"Coach"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = Coach.class))
                            }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<Coach> getCoachById(@PathVariable String coachId) {
        Coach coach = service.getCoachById(coachId);
        return ResponseEntity.ok().body(coach);
    }

    @GetMapping
    @Operation(summary = "Find all Coaches", description = "Find all Coaches",
            tags = {"Coach"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(
                                            mediaType = "application/json",
                                            array = @ArraySchema(schema = @Schema(implementation = Coach.class))
                                    )
                            }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<List<Coach>> getAllCoaches() {
        List<Coach> coachs = service.getAllCoaches();
        return ResponseEntity.ok().body(coachs);
    }


    @PostMapping
    @Operation(summary = "Create a new Coach",
            description = "Create a new Coach",
            tags = {"Coach"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = Coach.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Coach> createCoach(@RequestBody CoachDTO coachDTO) {
        Coach coachCreated = service.createCoach(coachDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(coachCreated);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Coach",
            description = "Delete a Coach",
            tags = {"Coach"},
            method = "DELETE",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Void> deleteCoach(@PathVariable String id) {
        service.deleteCoach(id);
        return ResponseEntity.noContent().build();
    }
}
