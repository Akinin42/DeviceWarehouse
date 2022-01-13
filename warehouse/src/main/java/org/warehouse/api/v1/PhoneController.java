package org.warehouse.api.v1;

import java.util.List;
import java.util.Optional;

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
import org.warehouse.dto.PhoneModelDto;
import org.warehouse.entity.Phone;
import org.warehouse.entity.PhoneModel;
import org.warehouse.service.PhoneService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/phones")
public class PhoneController {

    private final PhoneService phoneService;

    @GetMapping
    public List<Phone> findAllPhones() {
        return phoneService.findAllDevices();
    }

    @GetMapping("/{name}")
    public Optional<Phone> findPhone(@PathVariable("name") String deviceName) {
        return phoneService.findByName(deviceName);
    }

    @GetMapping(value = "/{name}", params = { "color", "minCost", "maxCost" })
    public Optional<Phone> findPhoneByNameAndColorAndCost(@PathVariable("name") String deviceName,
            @RequestParam("color") String colour, @RequestParam("minCost") int minCost,
            @RequestParam("maxCost") int maxCost) {
        return phoneService.findByNameAndColorAndCost(deviceName, colour, minCost, maxCost);
    }

    @GetMapping(value = "/{name}", params = { "memory", "cameras", "availability" })
    public Optional<Phone> findAvailabilityPhoneByMemoryAndCameras(@PathVariable("name") String deviceName,
            @RequestParam("memory") int memory, @RequestParam("cameras") int numberOfCameras,
            @RequestParam("availability") boolean availability) {
        return phoneService.findAvailabilityByNameAndMemoryAndCameras(deviceName, memory, numberOfCameras,
                availability);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPhone(@Valid @RequestBody DeviceDto phone) {
        phoneService.addDevice(phone);
    }

    @PostMapping("/models/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPhoneModelForDevice(@Valid @RequestBody PhoneModelDto phoneModelDto,
            @PathVariable("name") String deviceName) {
        phoneService.addModelForDevice(deviceName, phoneModelDto);
    }

    @GetMapping("/models")
    public List<PhoneModel> findAllModels() {
        return phoneService.findAllModels();
    }

    @GetMapping("/models/{name}")
    public List<PhoneModel> findAllModelsForDevice(@PathVariable("name") String deviceName) {
        return phoneService.findAllModelsForDevice(deviceName);
    }
}
