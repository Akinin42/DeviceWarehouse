package org.warehouse.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.warehouse.dao.TVSetDao;
import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.ModelDto;
import org.warehouse.dto.TVSetModelDto;
import org.warehouse.entity.Size;
import org.warehouse.entity.device.TVSet;
import org.warehouse.entity.devicemodel.Model;
import org.warehouse.entity.devicemodel.TVSetModel;
import org.warehouse.service.TVSetService;

@Service
@Transactional
public class TVSetServiceImpl extends DeviceServiceImpl<TVSet, TVSetModel> implements TVSetService {

    protected final TVSetDao tvsetDao;

    public TVSetServiceImpl(TVSetDao tvsetDao) {
        super(tvsetDao);
        this.tvsetDao = tvsetDao;
    }

    @Override
    public TVSet findAvailabilityByNameAndCategoryAndTechnology(String deviceName, String category,
            String technology, boolean availability) {
        checkDeviceExists(deviceName);
        List<Model> models = new ArrayList<>(getModelsForDevice(deviceName));
        filterByCategory(models, category);
        filterByTechnology(models, technology);
        if (Boolean.TRUE.equals(availability)) {
            filterByAvailability(models);
        }
        return createResultDevice(tvsetDao.findByNameIgnoreCase(deviceName).get(), models);
    }

    private void filterByCategory(List<Model> models, String category) {
        if (category != null && !category.isEmpty()) {
            for (Model model : new ArrayList<>(models)) {
                TVSetModel tvsetModel = (TVSetModel) model;
                if (!tvsetModel.getCategory().equals(category)) {
                    models.remove(model);
                }
            }
        }
    }

    private void filterByTechnology(List<Model> models, String technology) {
        if (technology != null && !technology.isEmpty()) {
            for (Model model : new ArrayList<>(models)) {
                TVSetModel tvsetModel = (TVSetModel) model;
                if (!tvsetModel.getTechnology().equals(technology)) {
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
    protected TVSet mapDtoToEntity(DeviceDto deviceDto) {
        return TVSet.builder()
                .withId(deviceDto.getId())
                .withName(deviceDto.getName())
                .withCountryOfManufacture(deviceDto.getCountryOfManufacture())
                .withCompany(deviceDto.getCompany())
                .withOnlineOrder(deviceDto.getOnlineOrder())
                .withInstallment(deviceDto.getInstallment())
                .build();
    }

    @Override
    protected TVSetModel mapModelDtoToEntity(ModelDto modelDto) {
        TVSetModelDto tvsetModelDto = (TVSetModelDto) modelDto;
        Size size = Size.builder()
                .withLengthMm(modelDto.getLengthMm())
                .withWidthMm(modelDto.getWidthMm())
                .withHeightMm(modelDto.getHeightMm())
                .build();
        return TVSetModel.builder()
                .withId(tvsetModelDto.getId())
                .withName(tvsetModelDto.getName())
                .withSerialNumber(tvsetModelDto.getSerialNumber())
                .withColor(tvsetModelDto.getColour())
                .withSize(size)
                .withCost(tvsetModelDto.getCost())
                .withAvailability(tvsetModelDto.getAvailability())
                .withCategory(tvsetModelDto.getCategory())
                .withTechnology(tvsetModelDto.getTechnology())
                .build();
    }

    @Override
    protected TVSet createResultDevice(TVSet device, List<Model> models) {
        return TVSet.builder()
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
