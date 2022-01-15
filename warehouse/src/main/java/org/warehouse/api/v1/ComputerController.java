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
import org.warehouse.dto.ComputerModelDto;
import org.warehouse.dto.DeviceDto;
import org.warehouse.entity.device.Computer;
import org.warehouse.service.ComputerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/computers")
@Tag(name = "Computers", description = "adding, search and sorting computers")
public class ComputerController {

    private final ComputerService computerService;

    @GetMapping
    @Operation(summary = "Find everything computers", description = "Return everything computers")
    public List<Computer> findAllComputers() {
        return computerService.findAllDevices();
    }

    @GetMapping("/{name}")
    @Operation(summary = "Find a computer by name ignore case", 
        description = "Return a computer with input name ignore case")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Found the computer", 
              content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = Computer.class)) }), 
            @ApiResponse(responseCode = "404", description = "Computer not found", 
              content = @Content) })
    public Computer findComputer(@PathVariable("name") String deviceName) {
        return computerService.findByName(deviceName);
    }
    
    @GetMapping(value = "/sortcolor/{color}")
    @Operation(summary = "Find computers by color", description = "Return computers with input color")
    public List<Computer> findAllComputersByColor(@PathVariable("color") String color) {
        return computerService.findAllByColor(color);
    }

    @GetMapping(value = "/sortcost")
    @Operation(summary = "Find computers by cost", 
        description = "Return ordered ascending computers with cost between input")
    public List<Computer> findAllComputersByCost(@RequestParam("minCost") @Min(0) int minCost,
            @RequestParam("maxCost") @Min(0) int maxCost) {
        return computerService.findAllByCost(minCost, maxCost);
    }
    
    @GetMapping(value = "/availability")
    @Operation(summary = "Find computers by availability", description = "Return availability computers")
    public List<Computer> findAllComputersByAvailability() {
        return computerService.findAllByAvailability();
    }
    
    @GetMapping(value = "/sortcategory/{category}")
    @Operation(summary = "Find computers by category", description = "Return computers with input category")
    public List<Computer> findAllComputersByCategory(@PathVariable("category") String category) {
        return computerService.findAllByCategory(category);
    }
    
    @GetMapping(value = "/sortprocessor/{processor}")
    @Operation(summary = "Find computers by processor", description = "Return computers with input processor")
    public List<Computer> findAllComputersByProcessor(@PathVariable("processor") String processor) {
        return computerService.findAllByProcessor(processor);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The computer added sucessful"),
            @ApiResponse(responseCode = "400", description = "The computer already exists in database, or input invalid data")})
    @Operation(summary = "Add a computer", description = "Add a computer to database")
    public void addComputer(@Valid @RequestBody DeviceDto computer) {
        computerService.addDevice(computer);
    }

    @PostMapping("/models/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The model for computer added sucessful"),
            @ApiResponse(responseCode = "400", description = "Input invalid data")})
    @Operation(summary = "Add a model for computer", description = "Add a model for computer by name ignored case")
    public void addComputerModelForDevice(@Valid @RequestBody ComputerModelDto computerModelDto,
            @PathVariable("name") String deviceName) {
        computerService.addModelForDevice(deviceName, computerModelDto);
    }
}
