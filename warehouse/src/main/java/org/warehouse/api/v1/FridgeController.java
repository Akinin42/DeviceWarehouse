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
import org.warehouse.dto.FridgeModelDto;
import org.warehouse.entity.device.Fridge;
import org.warehouse.service.FridgeService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/fridges")
@Tag(name = "Fridges", description = "adding, search and sorting fridges")
public class FridgeController {

    private final FridgeService fridgeService;

    @GetMapping
    @Operation(summary = "Find everything fridges", description = "Return everything fridges")
    public List<Fridge> findAllFridges() {
        return fridgeService.findAllDevices();
    }

    @GetMapping("/{name}")
    @Operation(summary = "Find a fridge by name ignore case", 
        description = "Return a fridge with input name ignore case")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Found the fridge", 
              content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = Fridge.class)) }), 
            @ApiResponse(responseCode = "404", description = "Fridge not found", 
              content = @Content) })
    public Fridge findFridge(@PathVariable("name") String deviceName) {
        return fridgeService.findByName(deviceName);
    }

    @GetMapping(value = "/sortcolor/{color}")
    @Operation(summary = "Find fridges by color", description = "Return fridges with input color")
    public List<Fridge> findAllFridgesByColor(@PathVariable("color") String color) {
        return fridgeService.findAllByColor(color);
    }

    @GetMapping(value = "/sortcost")
    @Operation(summary = "Find fridges by cost", 
        description = "Return ordered ascending fridges with cost between input")
    public List<Fridge> findAllFridgesByCost(@RequestParam("minCost") @Min(0) int minCost,
            @RequestParam("maxCost") @Min(0) int maxCost) {
        return fridgeService.findAllByCost(minCost, maxCost);
    }

    @GetMapping(value = "/availability")
    @Operation(summary = "Find fridges by availability", description = "Return availability fridges")
    public List<Fridge> findAllFridgesByAvailability() {
        return fridgeService.findAllByAvailability();
    }
    
    @GetMapping(value = "/sortdoor/{door}")
    @Operation(summary = "Find fridges by number of doors", 
        description = "Return fridges with number of doors bigger or equals input")
    public List<Fridge> findAllFridgesByDoors(@PathVariable("door") @Positive int numberOfDoor) {
        return fridgeService.findAllByDoors(numberOfDoor);
    }
    
    @GetMapping(value = "/sortcompressor/{compressor}")
    @Operation(summary = "Find fridges by compressor", description = "Return fridges with input compressor")
    public List<Fridge> findAllFridgesByCompressor(@PathVariable("compressor") String compressor) {
        return fridgeService.findAllByCompressor(compressor);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The fridge added sucessful"),
            @ApiResponse(responseCode = "400", description = "The fridge already exists in database, or input invalid data")})
    @Operation(summary = "Add a fridge", description = "Add a fridge to database")
    public void addFridge(@Valid @RequestBody DeviceDto fridge) {
        fridgeService.addDevice(fridge);
    }

    @PostMapping("/models/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The model for fridge added sucessful"),
            @ApiResponse(responseCode = "400", description = "Input invalid data")})
    @Operation(summary = "Add a model for fridge", description = "Add a model for fridge by name ignored case")
    public void addFridgeModelForDevice(@Valid @RequestBody FridgeModelDto fridgeModelDto,
            @PathVariable("name") String deviceName) {
        fridgeService.addModelForDevice(deviceName, fridgeModelDto);
    }
}
