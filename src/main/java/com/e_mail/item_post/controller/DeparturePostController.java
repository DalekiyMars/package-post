package com.e_mail.item_post.controller;

import com.e_mail.item_post.db.service.DeparturePostService;
import com.e_mail.item_post.dto.DeparturePostDto;
import com.e_mail.item_post.dto.DeparturePostEnterprise;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/update-status")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Departure-Post",
    description = "Accumulates Post and Departure entities to write information when current departure changed its status or was sent to another post")
    public class DeparturePostController {
    private final DeparturePostService departurePostService;

    @Operation(
            summary = "Creates note",
            description = "Search post and departure in database by their id and creates new record about it",
            parameters = {
                    @Parameter(name = "id", description = "Post ID")
            },
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = DeparturePostDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Record created",
                            content = @Content
                    ),
                    @ApiResponse(responseCode = "400", description = "Incorrect id or data",
                            content = @Content
                    )
            }
    )
    @PostMapping("/new")
    public ResponseEntity<HttpStatus> saveDeparturePost(@RequestBody
                                                        @Validated DeparturePostDto departurePostDto){
        departurePostService.saveDepartureAndUpdatedPost(departurePostDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Returns all history about departure",
            description = "Validates departure with given UUID and returns its history about post transaction if exists",
            parameters = {
                    @Parameter(name = "id", description = "Departure ID")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Records found",
                            content = @Content(schema = @Schema(implementation = DeparturePostEnterprise.class),
                            mediaType = "JSON")
                    ),
                    @ApiResponse(responseCode = "400", description = "Incorrect id",
                            content = @Content
                    )
            }
    )
    @GetMapping("/{departure_id}")
    public List<DeparturePostEnterprise> searchDeparturePost(@PathVariable("departure_id")
                                                             @NotNull UUID departureId){
        return departurePostService.getHistoryAboutDeparture(departureId);
    }

    @Operation(
            summary = "Delete records about departures",
            description = "Validates departure with given UUID and deletes its history about post transaction if exists",
            parameters = {
                    @Parameter(name = "id", description = "Departure ID")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Records deleted",
                            content = @Content
                    ),
                    @ApiResponse(responseCode = "400", description = "Incorrect id",
                            content = @Content
                    )
            }
    )
    @DeleteMapping("/deleteHistory/{departure_id}")
    public ResponseEntity<HttpStatus> deleteCurrentHistory(@PathVariable("departure_id")
                                                           @NotNull UUID departureId){
        departurePostService.deleteDepartureAndPostHistory(departureId);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
