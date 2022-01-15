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
import org.warehouse.dto.VacuumCleanerModelDto;
import org.warehouse.entity.device.VacuumCleaner;
import org.warehouse.service.VacuumCleanerService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/vacuum")
@Tag(name = "Vacuum cleaners", description = "adding, search and sorting vacuum cleaners")
public class VacuumCleanerController {

    private final VacuumCleanerService vacuumCleanerService;

    @GetMapping
    public List<VacuumCleaner> findAllVacuumCleaners() {
        return vacuumCleanerService.findAllDevices();
    }

    @GetMapping("/{name}")
    public VacuumCleaner findVacuumCleaner(@PathVariable("name") String deviceName) {
        return vacuumCleanerService.findByName(deviceName);
    }

    @GetMapping(value = "/sortcolor/{color}")
    public List<VacuumCleaner> findAllVacuumCleanersByColor(@PathVariable("color") String color) {
        return vacuumCleanerService.findAllByColor(color);
    }

    @GetMapping(value = "/sortcost")
    public List<VacuumCleaner> findAllVacuumCleanersByCost(@RequestParam("minCost") int minCost,
            @RequestParam("maxCost") int maxCost) {
        return vacuumCleanerService.findAllByCost(minCost, maxCost);
    }

    @GetMapping(value = "/availability")
    public List<VacuumCleaner> findAllVacuumCleanersByAvailability() {
        return vacuumCleanerService.findAllByAvailability();
    }
    
    @GetMapping(value = "/sortamount/{amount}")
    public List<VacuumCleaner> findAllVacuumCleanersByAmount(@PathVariable("amount") int amount) {
        return vacuumCleanerService.findAllByAmount(amount);
    }
    
    @GetMapping(value = "/sortmodes/{modes}")
    public List<VacuumCleaner> findAllVacuumCleanersByModes(@PathVariable("modes") int modes) {
        return vacuumCleanerService.findAllByModes(modes);
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
