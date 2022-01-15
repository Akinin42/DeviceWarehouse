package org.warehouse.api.v1;

import java.util.List;

import javax.validation.Valid;

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

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/fridges")
@Tag(name = "Fridges", description = "adding, search and sorting fridges")
public class FridgeController {

    private final FridgeService fridgeService;

    @GetMapping
    public List<Fridge> findAllFridges() {
        return fridgeService.findAllDevices();
    }

    @GetMapping("/{name}")
    public Fridge findFridge(@PathVariable("name") String deviceName) {
        return fridgeService.findByName(deviceName);
    }

    @GetMapping(value = "/sortcolor/{color}")
    public List<Fridge> findAllFridgesByColor(@PathVariable("color") String color) {
        return fridgeService.findAllByColor(color);
    }

    @GetMapping(value = "/sortcost")
    public List<Fridge> findAllFridgesByCost(@RequestParam("minCost") int minCost,
            @RequestParam("maxCost") int maxCost) {
        return fridgeService.findAllByCost(minCost, maxCost);
    }

    @GetMapping(value = "/availability")
    public List<Fridge> findAllFridgesByAvailability() {
        return fridgeService.findAllByAvailability();
    }
    
    @GetMapping(value = "/sortdoor/{door}")
    public List<Fridge> findAllFridgesByDoors(@PathVariable("door") int numberOfDoor) {
        return fridgeService.findAllByDoors(numberOfDoor);
    }
    
    @GetMapping(value = "/sortcompressor/{compressor}")
    public List<Fridge> findAllFridgesByCompressor(@PathVariable("compressor") String compressor) {
        return fridgeService.findAllByCompressor(compressor);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addFridge(@Valid @RequestBody DeviceDto fridge) {
        fridgeService.addDevice(fridge);
    }

    @PostMapping("/models/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addFridgeModelForDevice(@Valid @RequestBody FridgeModelDto fridgeModelDto,
            @PathVariable("name") String deviceName) {
        fridgeService.addModelForDevice(deviceName, fridgeModelDto);
    }
}
