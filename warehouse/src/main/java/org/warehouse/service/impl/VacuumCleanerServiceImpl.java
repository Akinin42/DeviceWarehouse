package org.warehouse.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.warehouse.dao.VacuumCleanerDao;
import org.warehouse.dao.VacuumCleanerModelDao;
import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.ModelDto;
import org.warehouse.dto.VacuumCleanerModelDto;
import org.warehouse.entity.Model;
import org.warehouse.entity.Size;
import org.warehouse.entity.VacuumCleaner;
import org.warehouse.entity.VacuumCleanerModel;
import org.warehouse.service.VacuumCleanerService;

@Service
@Transactional
public class VacuumCleanerServiceImpl extends DeviceServiceImpl<VacuumCleaner, VacuumCleanerModel> implements VacuumCleanerService {

    protected final VacuumCleanerDao vacuumCleanerDao;

    public VacuumCleanerServiceImpl(VacuumCleanerDao vacuumCleanerDao, VacuumCleanerModelDao vacuumCleanerModelDao) {
        super(vacuumCleanerDao, null);
        this.vacuumCleanerDao = vacuumCleanerDao;
    }

    @Override
    protected VacuumCleaner mapDtoToEntity(DeviceDto deviceDto) {
        return VacuumCleaner.builder()
                .withId(deviceDto.getId())
                .withName(deviceDto.getName())
                .withCountryOfManufacture(deviceDto.getCountryOfManufacture())
                .withCompany(deviceDto.getCompany())
                .withOnlineOrder(deviceDto.getOnlineOrder())
                .withInstallment(deviceDto.getInstallment())
                .build();
    }

    @Override
    protected VacuumCleanerModel mapModelDtoToEntity(ModelDto modelDto) {
        VacuumCleanerModelDto vacuumCleanerModelDto = (VacuumCleanerModelDto) modelDto;
        Size size = Size.builder()
                .withLengthMm(modelDto.getLengthMm())
                .withWidthMm(modelDto.getWidthMm())
                .withHeightMm(modelDto.getHeightMm())
                .build();
        return VacuumCleanerModel.builder()
                .withId(vacuumCleanerModelDto.getId())
                .withName(vacuumCleanerModelDto.getName())
                .withSerialNumber(vacuumCleanerModelDto.getSerialNumber())
                .withColor(vacuumCleanerModelDto.getColour())
                .withSize(size)
                .withCost(vacuumCleanerModelDto.getCost())
                .withAvailability(vacuumCleanerModelDto.getAvailability())
                .withAmountLitres(vacuumCleanerModelDto.getAmountLitres())
                .withNumberModes(vacuumCleanerModelDto.getNumberModes())
                .build();
    }

    @Override
    protected VacuumCleaner createResultDevice(VacuumCleaner device, List<Model> models) {
        return VacuumCleaner.builder()
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
