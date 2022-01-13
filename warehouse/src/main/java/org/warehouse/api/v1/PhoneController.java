package org.warehouse.api.v1;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addPhone(@Valid @RequestBody DeviceDto phone) {
        phoneService.addDevice(phone);
    }

    @GetMapping("/models")
    public List<PhoneModel> findAllModels() {
        return phoneService.findAllModels();
    }

    @GetMapping("/models/{name}")
    public List<PhoneModel> findAllModelsForDevice(@PathVariable("name") String deviceName) {
        return phoneService.findAllModelsForDevice(deviceName);
    }

    @PostMapping("/models/{name}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addPhoneModelForDevice(@Valid @RequestBody PhoneModelDto phoneModelDto,
            @PathVariable("name") String deviceName) {
        phoneService.addModelForDevice(deviceName, phoneModelDto);
    }
}
