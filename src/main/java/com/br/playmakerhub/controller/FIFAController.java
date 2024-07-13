package com.br.playmakerhub.controller;

import com.br.playmakerhub.models.Player;
import com.br.playmakerhub.models.response.SeasonResponse;
import com.br.playmakerhub.services.FIFAService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/fifaVersion")
@Tag(name = "FifaVersion", description = "Endpoints for Managing Players")
public class FIFAController {

    @Autowired
    private FIFAService fifaService;

    @GetMapping("/initialSeason")
    @Operation(summary = "Find InitialSeason by FifaVersion", description = "Find InitialSeason by FifaVersion",
            tags = {"FifaVersion"},
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = @Content),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            }
    )
    public ResponseEntity<SeasonResponse> getInitialSeasonByFIFAVersion(@RequestParam String version) {
        String filteredVersion = fifaService.getInitialSeasonByFIFAVersion(version);
        SeasonResponse response = new SeasonResponse(filteredVersion);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping
    @Operation(summary = "Find all FifaVersion", description = "Find all FifaVersion",
            tags = {"FifaVersion"},
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = {
                            @Content(schema = @Schema(name = "FifaVersion"))
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            }
    )
    public ResponseEntity<List<String>> getVersionsFifa() {
        return ResponseEntity.status(HttpStatus.OK).body(fifaService.getFifaVersions());
    }

    @GetMapping("/allSeasons")
    @Operation(summary = "Find all Seasons", description = "Find all Seasons",
            tags = {"FifaVersion"},
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200", content = {
                            @Content(schema = @Schema(name = "FifaVersion"))
                    }),
                    @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                    @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                    @ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
            }
    )
    public ResponseEntity<List<String>> getAllSeasons() {
        return ResponseEntity.status(HttpStatus.OK).body(fifaService.getFifaSeasons());
    }

}
