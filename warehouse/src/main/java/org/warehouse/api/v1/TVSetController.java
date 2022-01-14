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
import org.warehouse.dto.TVSetModelDto;
import org.warehouse.entity.TVSet;
import org.warehouse.service.TVSetService;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/tvsets")
public class TVSetController {

    private final TVSetService tvsetService;

    @GetMapping
    public List<TVSet> findAllTVSets() {
        return tvsetService.findAllDevices();
    }

    @GetMapping("/{name}")
    public Optional<TVSet> findTVSet(@PathVariable("name") String deviceName) {
        return tvsetService.findByName(deviceName);
    }

    @GetMapping(value = "/{name}", params = { "color", "minCost", "maxCost" })
    public Optional<TVSet> findTVSetByNameAndColorAndCost(@PathVariable("name") String deviceName,
            @RequestParam("color") String colour, @RequestParam("minCost") int minCost,
            @RequestParam("maxCost") int maxCost) {
        return tvsetService.findByNameAndColorAndCost(deviceName, colour, minCost, maxCost);
    }

    @GetMapping(value = "/{name}", params = { "category", "technology", "availability" })
    public Optional<TVSet> findAvailabilityTVSetByCategoryAndTechnology(@PathVariable("name") String deviceName,
            @RequestParam("category") String category, @RequestParam("technology") String technology,
            @RequestParam("availability") boolean availability) {
        return tvsetService.findAvailabilityByNameAndCategoryAndTechnology(deviceName, category, technology,
                availability);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addTVSet(@Valid @RequestBody DeviceDto tvset) {
        tvsetService.addDevice(tvset);
    }

    @PostMapping("/models/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTVSetModelForDevice(@Valid @RequestBody TVSetModelDto tvsetModelDto,
            @PathVariable("name") String deviceName) {
        tvsetService.addModelForDevice(deviceName, tvsetModelDto);
    }
}
