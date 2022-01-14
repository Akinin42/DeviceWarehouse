package org.warehouse.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.warehouse.dao.PhoneDao;
import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.ModelDto;
import org.warehouse.dto.PhoneModelDto;
import org.warehouse.entity.Model;
import org.warehouse.entity.Phone;
import org.warehouse.entity.PhoneModel;
import org.warehouse.entity.Size;
import org.warehouse.service.PhoneService;

@Service
@Transactional
public class PhoneServiceImpl extends DeviceServiceImpl<Phone, PhoneModel> implements PhoneService {

    protected final PhoneDao phoneDao;

    public PhoneServiceImpl(PhoneDao phoneDao) {
        super(phoneDao);
        this.phoneDao = phoneDao;
    }

    @Override
    public Optional<Phone> findAvailabilityByNameAndMemoryAndCameras(String deviceName, int memory, int numberOfCameras,
            boolean availability) {
        Phone result = null;
        if (phoneDao.findByNameIgnoreCase(deviceName).isPresent()) {
            List<Model> models = new ArrayList<>(phoneDao.findByNameIgnoreCase(deviceName).get().getModels());
            filterByMemory(models, memory);
            filterByCameras(models, numberOfCameras);
            if (Boolean.TRUE.equals(availability)) {
                filterByAvailability(models);
            }
            result = createResultDevice(phoneDao.findByNameIgnoreCase(deviceName).get(), models);
        }
        return Optional.ofNullable(result);
    }

    private void filterByMemory(List<Model> models, int memory) {
        if (memory != 0) {
            for (Model model : new ArrayList<>(models)) {
                PhoneModel phoneModel = (PhoneModel) model;
                if (phoneModel.getMemoryInMb() < memory) {
                    models.remove(model);
                }
            }
        }
    }

    private void filterByCameras(List<Model> models, int numberOfCameras) {
        for (Model model : new ArrayList<>(models)) {
            PhoneModel phoneModel = (PhoneModel) model;
            if (phoneModel.getNumberCameras() < numberOfCameras) {
                models.remove(model);
            }
        }
    }

    private void filterByAvailability(List<Model> models) {
        for (Model model : new ArrayList<>(models)) {
            if (Boolean.FALSE.equals(model.getAvailability())) {
                models.remove(model);
            }
        }
    }

    @Override
    protected Phone mapDtoToEntity(DeviceDto deviceDto) {
        return Phone.builder()
                .withId(deviceDto.getId())
                .withName(deviceDto.getName())
                .withCountryOfManufacture(deviceDto.getCountryOfManufacture())
                .withCompany(deviceDto.getCompany())
                .withOnlineOrder(deviceDto.getOnlineOrder())
                .withInstallment(deviceDto.getInstallment())
                .build();
    }

    @Override
    protected PhoneModel mapModelDtoToEntity(ModelDto modelDto) {
        PhoneModelDto phoneModelDto = (PhoneModelDto) modelDto;
        Size size = Size.builder()
                .withLengthMm(modelDto.getLengthMm())
                .withWidthMm(modelDto.getWidthMm())
                .withHeightMm(modelDto.getHeightMm())
                .build();
        return PhoneModel.builder()
                .withId(phoneModelDto.getId())
                .withName(phoneModelDto.getName())
                .withSerialNumber(phoneModelDto.getSerialNumber())
                .withColor(phoneModelDto.getColour())
                .withSize(size)
                .withCost(phoneModelDto.getCost())
                .withAvailability(phoneModelDto.getAvailability())
                .withMemoryInMb(phoneModelDto.getMemoryInMb())
                .withNumberCameras(phoneModelDto.getNumberOfCameras())
                .build();
    }

    @Override
    protected Phone createResultDevice(Phone device, List<Model> models) {
        return Phone.builder()
                .withId(device.getId())
                .withName(device.getName())
                .withCountryOfManufacture(device.getCountryOfManufacture())
                .withCompany(device.getCompany())
                .withOnlineOrder(device.getOnlineOrder())
                .withInstallment(device.getInstallment())
                .withModels(models)
                .build();
    }
}
