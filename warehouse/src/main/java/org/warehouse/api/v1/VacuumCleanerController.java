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
import org.warehouse.dto.VacuumCleanerModelDto;
import org.warehouse.entity.VacuumCleaner;
import org.warehouse.service.VacuumCleanerService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/vacuum")
public class VacuumCleanerController {

    private final VacuumCleanerService vacuumCleanerService;

    @GetMapping
    public List<VacuumCleaner> findAllVacuumCleaners() {
        return vacuumCleanerService.findAllDevices();
    }

    @GetMapping("/{name}")
    public Optional<VacuumCleaner> findVacuumCleaner(@PathVariable("name") String deviceName) {
        return vacuumCleanerService.findByName(deviceName);
    }

    @GetMapping(value = "/{name}", params = { "color", "minCost", "maxCost" })
    public Optional<VacuumCleaner> findVacuumCleanerByNameAndColorAndCost(@PathVariable("name") String deviceName,
            @RequestParam("color") String colour, @RequestParam("minCost") int minCost,
            @RequestParam("maxCost") int maxCost) {
        return vacuumCleanerService.findByNameAndColorAndCost(deviceName, colour, minCost, maxCost);
    }
    
    @GetMapping(value = "/{name}", params = { "amount", "modes", "availability" })
    public Optional<VacuumCleaner> findAvailabilityVacuumCleanerByAmountAndModes(@PathVariable("name") String deviceName,
            @RequestParam("amount") int amount, @RequestParam("modes") int numberOfModes,
            @RequestParam("availability") boolean availability) {
        return vacuumCleanerService.findAvailabilityByNameAndAmountAndModes(deviceName, amount, numberOfModes,
                availability);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addVacuumCleaner(@Valid @RequestBody DeviceDto vacuumCleaner) {
        vacuumCleanerService.addDevice(vacuumCleaner);
    }

    @PostMapping("/models/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addVacuumCleanerModelForDevice(@Valid @RequestBody VacuumCleanerModelDto vacuumCleanerModelDto,
            @PathVariable("name") String deviceName) {
        vacuumCleanerService.addModelForDevice(deviceName, vacuumCleanerModelDto);
    }
}
