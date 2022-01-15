package org.warehouse.api.v1;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.TVSetModelDto;
import org.warehouse.entity.device.TVSet;
import org.warehouse.service.TVSetService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/tvsets")
@Tag(name = "TVSets", description = "adding, search and sorting TVSets")
public class TVSetController {

    private final TVSetService tvsetService;

    @GetMapping
    @Operation(summary = "Find everything TVSets", description = "Return everything TVSets")
    public List<TVSet> findAllTVSets() {
        return tvsetService.findAllDevices();
    }

    @GetMapping("/{name}")
    @Operation(summary = "Find a TVSet by name ignore case", description = "Return a TVSet with input name ignore case")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Found the TVSet", 
          content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = TVSet.class)) }), 
        @ApiResponse(responseCode = "404", description = "TVSet not found", 
          content = @Content) })
    public TVSet findTVSet(@PathVariable("name") String deviceName) {
        return tvsetService.findByName(deviceName);
    }
    
    @GetMapping(value = "/sortcolor/{color}")
    @Operation(summary = "Find TVSets by color", description = "Return TVSets with input color")
    public List<TVSet> findAllTVSetsByColor(@PathVariable("color") String color) {
        return tvsetService.findAllByColor(color);
    }

    @GetMapping(value = "/sortcost")
    @Operation(summary = "Find TVSets by cost", description = "Return ordered ascending TVSets with cost between input")
    public List<TVSet> findAllTVSetsByCost(@RequestParam("minCost") @Min(0) int minCost,
            @RequestParam("maxCost") @Min(0) int maxCost) {
        return tvsetService.findAllByCost(minCost, maxCost);
    }

    @GetMapping(value = "/availability")
    @Operation(summary = "Find TVSets by availability", description = "Return availability TVSets")
    public List<TVSet> findAllTVSetsByAvailability() {
        return tvsetService.findAllByAvailability();
    }
    
    @GetMapping(value = "/sortcategory/{category}")
    @Operation(summary = "Find TVSets by category", description = "Return TVSets with input category")
    public List<TVSet> findAllTVSetsByCategory(@PathVariable("category") String category) {
        return tvsetService.findAllByCategory(category);
    }
    
    @GetMapping(value = "/sorttechnology/{technology}")
    @Operation(summary = "Find TVSets by technology", description = "Return TVSets with input technology")
    public List<TVSet> findAllTVSetsByTechnology(@PathVariable("technology") String technology) {
        return tvsetService.findAllByTechnology(technology);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "The TVSet added sucessful"),
        @ApiResponse(responseCode = "400", description = "The TVSet already exists in database, or input invalid data")})
    @Operation(summary = "Add a TVSet", description = "Add a TVSet to database")
    public void addTVSet(@Valid @RequestBody DeviceDto tvset) {
        tvsetService.addDevice(tvset);
    }

    @PostMapping("/models/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "The model for TVSet added sucessful"),
        @ApiResponse(responseCode = "400", description = "Input invalid data")})
    @Operation(summary = "Add a model for TVSet", description = "Add a model for TVSet by name ignored case")
    public void addTVSetModelForDevice(@Valid @RequestBody TVSetModelDto tvsetModelDto,
            @PathVariable("name") String deviceName) {
        tvsetService.addModelForDevice(deviceName, tvsetModelDto);
    }
}
