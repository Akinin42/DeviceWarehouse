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
import org.warehouse.dto.PhoneModelDto;
import org.warehouse.entity.device.Phone;
import org.warehouse.service.PhoneService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/phones")
@Tag(name = "Phones", description = "adding, search and sorting phones")
public class PhoneController {

    private final PhoneService phoneService;

    @GetMapping
    @Operation(summary = "Find everything phones", description = "Return everything phones")
    public List<Phone> findAllPhones() {
        return phoneService.findAllDevices();
    }

    @GetMapping("/{name}")
    @Operation(summary = "Find a phone by name ignore case", 
        description = "Return a phone with input name ignore case")
    @ApiResponses(value = { 
            @ApiResponse(responseCode = "200", description = "Found the phone", 
              content = { @Content(mediaType = "application/json", 
                schema = @Schema(implementation = Phone.class)) }), 
            @ApiResponse(responseCode = "404", description = "Phone not found", 
              content = @Content) })
    public Phone findPhoneByName(@PathVariable("name") String deviceName) {
        return phoneService.findByName(deviceName);
    }

    @GetMapping(value = "/sortcolor/{color}")
    @Operation(summary = "Find phones by color", description = "Return phones with input color")
    public List<Phone> findAllPhonesByColor(@PathVariable("color") String color) {
        return phoneService.findAllByColor(color);
    }

    @GetMapping(value = "/sortcost")
    @Operation(summary = "Find phones by cost", 
        description = "Return ordered ascending phones with cost between input")
    public List<Phone> findAllPhonesByCost(@RequestParam("minCost") @Min(0) int minCost,
            @RequestParam("maxCost") @Min(0) int maxCost) {
        return phoneService.findAllByCost(minCost, maxCost);
    }
    
    @GetMapping(value = "/availability")
    @Operation(summary = "Find phones by availability", description = "Return availability phones")
    public List<Phone> findAllPhonesByAvailability() {
        return phoneService.findAllByAvailability();
    }
    
    @GetMapping(value = "/sortmemory/{memory}")
    @Operation(summary = "Find phones by memory", 
        description = "Return phones with a memory in Mb bigger or equals input")
    public List<Phone> findAllPhonesByMemory(@PathVariable("memory") @Positive int memory) {
        return phoneService.findAllByMemory(memory);
    }
    
    @GetMapping(value = "/sortcameras/{cameras}")
    @Operation(summary = "Find phones by number of cameras", 
        description = "Return phones with number of cameras bigger or equals input")
    public List<Phone> findAllPhonesByCameras(@PathVariable("cameras") @Min(0) int numberOfCameras) {
        return phoneService.findAllByCameras(numberOfCameras);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The phone added sucessful"),
            @ApiResponse(responseCode = "400", description = "The phone already exists in database, or input invalid data")})
    @Operation(summary = "Add a phone", description = "Add a phone to database")    
    public void addPhone(@Valid @RequestBody DeviceDto phone) {
        phoneService.addDevice(phone);
    }

    @PostMapping("/models/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "The model for phone added sucessful"),
            @ApiResponse(responseCode = "400", description = "Input invalid data")})
    @Operation(summary = "Add a model for phone", description = "Add a model for phone by name ignored case")
    public void addPhoneModelForDevice(@Valid @RequestBody PhoneModelDto phoneModelDto,
            @PathVariable("name") String deviceName) {
        phoneService.addModelForDevice(deviceName, phoneModelDto);
    }
}
