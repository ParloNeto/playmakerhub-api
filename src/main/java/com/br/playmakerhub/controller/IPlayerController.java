package com.br.playmakerhub.controller;

import com.br.playmakerhub.models.Player;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "Player", description = "Endpoints for Managing Players")
public interface IPlayerController {

    @GetMapping("/{id}")
    @Operation(summary = "Finds a Player", description = "Finds a Player",
            tags = {"Player"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = Player.class))
                            }),
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            }
    )
    public ResponseEntity<Player> getPlayerById(@PathVariable String id);

    @GetMapping
    @Operation(summary = "Find all Players", description = "Find all Players",
            tags = {"Player"},
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
    public ResponseEntity<List<Player>> getAllPlayers();


    @PostMapping
    @Operation(summary = "Create a new Player",
            description = "Create a new Player",
            tags = {"Player"},
            responses = {
                    @ApiResponse(description = "Success", responseCode = "200",
                            content = {
                                    @Content(schema = @Schema(implementation = Player.class))
                            }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Player> createPlayer(@RequestBody Player player) throws IllegalAccessException;

//    @PostMapping("/addToCoach/{coachId}")
//    @Operation(
//            summary = "Add player to a coach",
//            description = "Adds a player to the coach specified by ID",
//            responses = {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "Success",
//                            content = @Content(
//                                    mediaType = "application/json",
//                                    schema = @Schema(implementation = Coach.class)
//                            )
//                    ),
//                    @ApiResponse(
//                            responseCode = "400",
//                            description = "Bad Request",
//                            content = @Content
//                    ),
//                    @ApiResponse(
//                            responseCode = "404",
//                            description = "Not Found",
//                            content = @Content
//                    )
//            }
//    )
//    public ResponseEntity<Coach> addPlayerToCoach(@PathVariable String coachId, @io.swagger.v3.oas.annotations.parameters.RequestBody(
//            description = "Data of the player to be added",
//            required = true,
//            content = @Content(
//                    schema = @Schema(implementation = Player.class)
//            )
//    ) @RequestBody Player player);


    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a Player",
            description = "Delete a Player",
            tags = {"Player"},
            method = "DELETE",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
                    @ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),
            })
    public ResponseEntity<Void> deletePlayer(@PathVariable String id);
}
