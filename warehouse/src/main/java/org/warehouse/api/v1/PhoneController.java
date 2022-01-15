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
import org.warehouse.dto.PhoneModelDto;
import org.warehouse.entity.device.Phone;
import org.warehouse.service.PhoneService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/phones")
@Tag(name = "Phones", description = "adding, search and sorting phones")
public class PhoneController {

    private final PhoneService phoneService;

    @GetMapping
    public List<Phone> findAllPhones() {
        return phoneService.findAllDevices();
    }

    @GetMapping("/{name}")
    public Phone findPhoneByName(@PathVariable("name") String deviceName) {
        return phoneService.findByName(deviceName);
    }

    @GetMapping(value = "/sortcolor/{color}")
    public List<Phone> findAllPhonesByColor(@PathVariable("color") String color) {
        return phoneService.findAllByColor(color);
    }

    @GetMapping(value = "/sortcost")
    public List<Phone> findAllPhonesByCost(@RequestParam("minCost") int minCost,
            @RequestParam("maxCost") int maxCost) {
        return phoneService.findAllByCost(minCost, maxCost);
    }
    
    @GetMapping(value = "/availability")
    public List<Phone> findAllPhonesByAvailability() {
        return phoneService.findAllByAvailability();
    }
    
    @GetMapping(value = "/sortmemory/{memory}")
    public List<Phone> findAllPhonesByMemory(@PathVariable("memory") int memory) {
        return phoneService.findAllByMemory(memory);
    }
    
    @GetMapping(value = "/sortcameras/{cameras}")
    public List<Phone> findAllPhonesByCameras(@PathVariable("cameras") int numberOfCameras) {
        return phoneService.findAllByCameras(numberOfCameras);
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
}
