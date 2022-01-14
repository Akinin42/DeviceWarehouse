package org.warehouse.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.warehouse.dao.FridgeDao;
import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.FridgeModelDto;
import org.warehouse.dto.ModelDto;
import org.warehouse.entity.Fridge;
import org.warehouse.entity.FridgeModel;
import org.warehouse.entity.Model;
import org.warehouse.entity.Size;
import org.warehouse.service.FridgeService;

@Service
@Transactional
public class FridgeServiceImpl extends DeviceServiceImpl<Fridge, FridgeModel> implements FridgeService {

    protected final FridgeDao fridgeDao;

    public FridgeServiceImpl(FridgeDao fridgeDao) {
        super(fridgeDao);
        this.fridgeDao = fridgeDao;
    }

    @Override
    public Optional<Fridge> findAvailabilityByNameAndDoorsAndCompressor(String deviceName, int numberOfDoor,
            String compressor, boolean availability) {
        Fridge result = null;
        if (fridgeDao.findByNameIgnoreCase(deviceName).isPresent()) {
            List<Model> models = new ArrayList<>(fridgeDao.findByNameIgnoreCase(deviceName).get().getModels());
            filterByDoors(models, numberOfDoor);
            filterByCompressor(models, compressor);
            if (Boolean.TRUE.equals(availability)) {
                filterByAvailability(models);
            }
            result = createResultDevice(fridgeDao.findByNameIgnoreCase(deviceName).get(), models);
        }
        return Optional.ofNullable(result);
    }

    private void filterByDoors(List<Model> models, int numberOfDoor) {
        if (numberOfDoor != 0) {
            for (Model model : new ArrayList<>(models)) {
                FridgeModel fridgeModel = (FridgeModel) model;
                if (fridgeModel.getNumberOfDoor() < numberOfDoor) {
                    models.remove(model);
                }
            }
        }
    }

    private void filterByCompressor(List<Model> models, String compressor) {
        if (compressor != null && !compressor.isEmpty()) {
            for (Model model : new ArrayList<>(models)) {
                FridgeModel fridgeModel = (FridgeModel) model;
                if (!fridgeModel.getCompressor().equals(compressor)) {
                    models.remove(model);
                }
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
    protected Fridge mapDtoToEntity(DeviceDto deviceDto) {
        return Fridge.builder()
                .withId(deviceDto.getId())
                .withName(deviceDto.getName())
                .withCountryOfManufacture(deviceDto.getCountryOfManufacture())
                .withCompany(deviceDto.getCompany())
                .withOnlineOrder(deviceDto.getOnlineOrder())
                .withInstallment(deviceDto.getInstallment())
                .build();
    }

    @Override
    protected FridgeModel mapModelDtoToEntity(ModelDto modelDto) {
        FridgeModelDto fridgeModelDto = (FridgeModelDto) modelDto;
        Size size = Size.builder()
                .withLengthMm(modelDto.getLengthMm())
                .withWidthMm(modelDto.getWidthMm())
                .withHeightMm(modelDto.getHeightMm())
                .build();
        return FridgeModel.builder()
                .withId(fridgeModelDto.getId())
                .withName(fridgeModelDto.getName())
                .withSerialNumber(fridgeModelDto.getSerialNumber())
                .withColor(fridgeModelDto.getColour())
                .withSize(size)
                .withCost(fridgeModelDto.getCost())
                .withAvailability(fridgeModelDto.getAvailability())
                .withNumberOfDoor(fridgeModelDto.getNumberOfDoor())
                .withCompressor(fridgeModelDto.getCompressor())
                .build();
    }

    @Override
    protected Fridge createResultDevice(Fridge device, List<Model> models) {
        return Fridge.builder()
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
