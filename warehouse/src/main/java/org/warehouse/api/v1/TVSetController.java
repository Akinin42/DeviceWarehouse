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
import org.warehouse.dto.TVSetModelDto;
import org.warehouse.entity.device.TVSet;
import org.warehouse.service.TVSetService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/tvsets")
@Tag(name = "TVSets", description = "adding, search and sorting TVSets")
public class TVSetController {

    private final TVSetService tvsetService;

    @GetMapping
    public List<TVSet> findAllTVSets() {
        return tvsetService.findAllDevices();
    }

    @GetMapping("/{name}")
    public TVSet findTVSet(@PathVariable("name") String deviceName) {
        return tvsetService.findByName(deviceName);
    }
    
    @GetMapping(value = "/sortcolor/{color}")
    public List<TVSet> findAllTVSetsByColor(@PathVariable("color") String color) {
        return tvsetService.findAllByColor(color);
    }

    @GetMapping(value = "/sortcost")
    public List<TVSet> findAllTVSetsByCost(@RequestParam("minCost") int minCost,
            @RequestParam("maxCost") int maxCost) {
        return tvsetService.findAllByCost(minCost, maxCost);
    }

    @GetMapping(value = "/availability")
    public List<TVSet> findAllTVSetsByAvailability() {
        return tvsetService.findAllByAvailability();
    }
    
    @GetMapping(value = "/sortcategory/{category}")
    public List<TVSet> findAllTVSetsByCategory(@PathVariable("category") String category) {
        return tvsetService.findAllByCategory(category);
    }
    
    @GetMapping(value = "/sorttechnology/{technology}")
    public List<TVSet> findAllTVSetsByTechnology(@PathVariable("technology") String technology) {
        return tvsetService.findAllByTechnology(technology);
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
