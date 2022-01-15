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

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/fridges")
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

    @GetMapping(value = "/{name}", params = { "color", "minCost", "maxCost" })
    public Fridge findFridgeByNameAndColorAndCost(@PathVariable("name") String deviceName,
            @RequestParam("color") String colour, @RequestParam("minCost") int minCost,
            @RequestParam("maxCost") int maxCost) {
        return fridgeService.findByNameAndColorAndCost(deviceName, colour, minCost, maxCost);
    }

    @GetMapping(value = "/{name}", params = { "door", "compressor", "availability" })
    public Fridge findAvailabilityFridgeByDoorsAndCompressor(@PathVariable("name") String deviceName,
            @RequestParam("door") int numberOfDoor, @RequestParam("compressor") String compressor,
            @RequestParam("availability") boolean availability) {
        return fridgeService.findAvailabilityByNameAndDoorsAndCompressor(deviceName, numberOfDoor, compressor,
                availability);
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
