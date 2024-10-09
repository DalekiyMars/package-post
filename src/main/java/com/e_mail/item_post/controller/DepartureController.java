package com.e_mail.item_post.controller;

import com.e_mail.item_post.db.service.DepartureService;
import com.e_mail.item_post.dto.DepartureDto;
import com.e_mail.item_post.entity.Departure;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping("/departures")
@Tag(name = "Departures", description = "Departure API to manipulate it")
public class DepartureController {
    private final DepartureService departureService;
    private final ModelMapper modelMapper;

    @Operation(
            summary = "Get all departures",
            description = "Returns all departures from server",
            responses = {
                    @ApiResponse(responseCode = "200",description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = DepartureDto.class),
                                    mediaType = "JSON"
                            )
                    )
            }
    )
    @GetMapping()
    public List<DepartureDto> getAllDepartures() {
        return departureService.findAll().stream()
                .map(departure ->  modelMapper.map(departure, DepartureDto.class))
                .collect(Collectors.toList());
    }

    @Operation(
            summary = "Get departure by ID",
            description = "Returns departure with given ID",
            parameters = {
                    @Parameter(name = "id", description = "Departure ID")
            },
            responses = {
                    @ApiResponse(responseCode = "200",description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = DepartureDto.class),
                                    mediaType = "JSON"
                            )
                    ),
                    @ApiResponse(responseCode = "400",description = "Departure not exists",
                    content = @Content)
            }
    )
    @GetMapping("/{id}")
    public DepartureDto getDeparture(@PathVariable("id") UUID id) {
        return modelMapper.map(departureService.findOne(id), DepartureDto.class);
    }

    @Operation(
            summary = "Create new departure",
            description = "Validate new departure and puts it to db if everything OK",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = DepartureDto.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "200",description = "OK",
                            content = @Content(
                                    schema = @Schema(implementation = DepartureDto.class),
                                    mediaType = "JSON"
                            )
                    ),
                    @ApiResponse(responseCode = "400",description = "Given data not valid",
                            content = @Content)
            }
    )
    @PostMapping("/new")
    public ResponseEntity<HttpStatus> registerDeparture(@RequestBody @Validated DepartureDto departureDTO) {
        var temp = departureService.save(modelMapper.map(departureDTO, Departure.class));
        log.info("Departure с id " + temp.getId() + "  сохранен");
        return ResponseEntity.ok(HttpStatus.OK);

    }

    @Operation(
            summary = "Update departure",
            description = "Searching departure with this ID, validates new info and update it if exists",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            schema = @Schema(implementation = DepartureDto.class)
                    )
            ),
            parameters = @Parameter(name = "id", description = "Departure ID"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "OK",
                            content = @Content
                    ),
                    @ApiResponse(responseCode = "400",description = "Departure with this ID not exists or given data not valid",
                            content = @Content)
            }
    )
    @PostMapping("/update/{id}")
    public ResponseEntity<HttpStatus> updateDataAboutCurrentDeparture(@PathVariable("id") UUID id, @RequestBody @Validated DepartureDto departureDto){
        departureService.updateDepartureInfo(id, modelMapper.map(departureDto, Departure.class));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @Operation(
            summary = "Delete departure",
            description = "Searching departure with this ID and delete it from db if it exists",
            parameters = @Parameter(name = "id", description = "Departure ID"),
            responses = {
                    @ApiResponse(responseCode = "200",description = "OK", content = @Content),
                    @ApiResponse(responseCode = "400",description = "Departure not exists", content = @Content)
            }
    )
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HttpStatus> deleteDeparture(@PathVariable("id") UUID id){
        departureService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
