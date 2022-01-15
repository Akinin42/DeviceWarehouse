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
import org.warehouse.dto.ComputerModelDto;
import org.warehouse.dto.DeviceDto;
import org.warehouse.entity.device.Computer;
import org.warehouse.service.ComputerService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/computers")
public class ComputerController {

    private final ComputerService computerService;

    @GetMapping
    public List<Computer> findAllComputers() {
        return computerService.findAllDevices();
    }

    @GetMapping("/{name}")
    public Optional<Computer> findComputer(@PathVariable("name") String deviceName) {
        return computerService.findByName(deviceName);
    }

    @GetMapping(value = "/{name}", params = { "color", "minCost", "maxCost" })
    public Optional<Computer> findComputerByNameAndColorAndCost(@PathVariable("name") String deviceName,
            @RequestParam("color") String colour, @RequestParam("minCost") int minCost,
            @RequestParam("maxCost") int maxCost) {
        return computerService.findByNameAndColorAndCost(deviceName, colour, minCost, maxCost);
    }

    @GetMapping(value = "/{name}", params = { "category", "processor", "availability" })
    public Optional<Computer> findAvailabilityComputerByCategoryAndProcessor(@PathVariable("name") String deviceName,
            @RequestParam("category") String category, @RequestParam("processor") String processor,
            @RequestParam("availability") boolean availability) {
        return computerService.findAvailabilityByNameAndCategoryAndProcessor(deviceName, category, processor,
                availability);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addComputer(@Valid @RequestBody DeviceDto computer) {
        computerService.addDevice(computer);
    }

    @PostMapping("/models/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addComputerModelForDevice(@Valid @RequestBody ComputerModelDto computerModelDto,
            @PathVariable("name") String deviceName) {
        computerService.addModelForDevice(deviceName, computerModelDto);
    }
}
