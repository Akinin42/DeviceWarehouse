package org.warehouse.api.v1;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

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
import org.warehouse.dto.VacuumCleanerModelDto;
import org.warehouse.entity.device.VacuumCleaner;
import org.warehouse.service.VacuumCleanerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/vacuum")
@Tag(name = "Vacuum cleaners", description = "adding, search and sorting vacuum cleaners")
public class VacuumCleanerController {

    private final VacuumCleanerService vacuumCleanerService;

    @GetMapping
    @Operation(summary = "Find everything vacuum cleaners", description = "Return everything vacuum cleaners")
    public List<VacuumCleaner> findAllVacuumCleaners() {
        return vacuumCleanerService.findAllDevices();
    }

    @GetMapping("/{name}")
    @Operation(summary = "Find a vacuum cleaner by name ignore case", 
        description = "Return a vacuum cleaner with input name ignore case")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Found the vacuum cleaner", 
          content = { @Content(mediaType = "application/json", 
            schema = @Schema(implementation = VacuumCleaner.class)) }), 
        @ApiResponse(responseCode = "404", description = "Vacuum cleaner not found", 
          content = @Content) })
    public VacuumCleaner findVacuumCleaner(@PathVariable("name") String deviceName) {
        return vacuumCleanerService.findByName(deviceName);
    }

    @GetMapping(value = "/sortcolor/{color}")
    @Operation(summary = "Find vacuum cleaners by color", description = "Return vacuum cleaners with input color")
    public List<VacuumCleaner> findAllVacuumCleanersByColor(@PathVariable("color") String color) {
        return vacuumCleanerService.findAllByColor(color);
    }

    @GetMapping(value = "/sortcost")
    @Operation(summary = "Find vacuum cleaners by cost", 
        description = "Return ordered ascending vacuum cleaners with cost between input")
    public List<VacuumCleaner> findAllVacuumCleanersByCost(@RequestParam("minCost") @Min(0) int minCost,
            @RequestParam("maxCost") @Min(0) int maxCost) {
        return vacuumCleanerService.findAllByCost(minCost, maxCost);
    }

    @GetMapping(value = "/availability")
    @Operation(summary = "Find vacuum cleaners by availability", description = "Return availability vacuum cleaners")
    public List<VacuumCleaner> findAllVacuumCleanersByAvailability() {
        return vacuumCleanerService.findAllByAvailability();
    }
    
    @GetMapping(value = "/sortamount/{amount}")
    @Operation(summary = "Find vacuum cleaners by amount", 
        description = "Return vacuum cleaners with amount bigger or equals input")
    public List<VacuumCleaner> findAllVacuumCleanersByAmount(@PathVariable("amount") @Positive int amount) {
        return vacuumCleanerService.findAllByAmount(amount);
    }
    
    @GetMapping(value = "/sortmodes/{modes}")
    @Operation(summary = "Find vacuum cleaners by modes", 
        description = "Return TVSets with number of modes bigger or equals input")
    public List<VacuumCleaner> findAllVacuumCleanersByModes(@PathVariable("modes") @Positive int modes) {
        return vacuumCleanerService.findAllByModes(modes);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "The vacuum cleaner added sucessful"),
        @ApiResponse(responseCode = "400", description = "The vacuum cleaner already exists in database, or input invalid data")})
    @Operation(summary = "Add a vacuum cleaner", description = "Add a vacuum cleaner to database")
    public void addVacuumCleaner(@Valid @RequestBody DeviceDto vacuumCleaner) {
        vacuumCleanerService.addDevice(vacuumCleaner);
    }

    @PostMapping("/models/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The model for vacuum cleaner added sucessful"),
            @ApiResponse(responseCode = "400", description = "Input invalid data")})
    @Operation(summary = "Add a model for vacuum cleaner", 
        description = "Add a model for vacuum cleaner by name ignored case")
    public void addVacuumCleanerModelForDevice(@Valid @RequestBody VacuumCleanerModelDto vacuumCleanerModelDto,
            @PathVariable("name") String deviceName) {
        vacuumCleanerService.addModelForDevice(deviceName, vacuumCleanerModelDto);
    }
}
