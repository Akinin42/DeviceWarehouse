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
import org.warehouse.dto.ComputerModelDto;
import org.warehouse.dto.DeviceDto;
import org.warehouse.entity.device.Computer;
import org.warehouse.service.ComputerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/computers")
@Tag(name = "Computers", description = "adding, search and sorting computers")
public class ComputerController {

    private final ComputerService computerService;

    @GetMapping
    @Operation(summary = "Find everything computers", description = "Return everything computers")
    public List<Computer> findAllComputers() {
        return computerService.findAllDevices();
    }

    @GetMapping("/{name}")
    @Operation(summary = "Find a computer by name ignore case", description = "Return a computer by name ignore case")
    public Computer findComputer(@PathVariable("name") String deviceName) {
        return computerService.findByName(deviceName);
    }
    
    @GetMapping(value = "/sortcolor/{color}")
    public List<Computer> findAllComputersByColor(@PathVariable("color") String color) {
        return computerService.findAllByColor(color);
    }

    @GetMapping(value = "/sortcost")
    public List<Computer> findAllComputersByCost(@RequestParam("minCost") int minCost,
            @RequestParam("maxCost") int maxCost) {
        return computerService.findAllByCost(minCost, maxCost);
    }
    
    @GetMapping(value = "/availability")
    public List<Computer> findAllComputersByAvailability() {
        return computerService.findAllByAvailability();
    }
    
    @GetMapping(value = "/sortcategory/{category}")
    public List<Computer> findAllComputersByCategory(@PathVariable("category") String category) {
        return computerService.findAllByCategory(category);
    }
    
    @GetMapping(value = "/sortprocessor/{processor}")
    public List<Computer> findAllComputersByProcessor(@PathVariable("processor") String processor) {
        return computerService.findAllByProcessor(processor);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a computer", description = "Add a computer to database")
    public void addComputer(@Valid @RequestBody DeviceDto computer) {
        computerService.addDevice(computer);
    }

    @PostMapping("/models/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a model for computer", description = "Add a model for computer by name ignored case")
    public void addComputerModelForDevice(@Valid @RequestBody ComputerModelDto computerModelDto,
            @PathVariable("name") String deviceName) {
        computerService.addModelForDevice(deviceName, computerModelDto);
    }
}
