package org.warehouse.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.warehouse.dao.PhoneDao;
import org.warehouse.dao.PhoneModelDao;
import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.ModelDto;
import org.warehouse.dto.PhoneModelDto;
import org.warehouse.entity.Phone;
import org.warehouse.entity.PhoneModel;
import org.warehouse.service.PhoneService;

@Service
@Transactional
public class PhoneServiceImpl extends DeviceServiceImpl<Phone, PhoneModel> implements PhoneService {

    private final PhoneDao phoneDao;
    private final PhoneModelDao phoneModelDao;

    public PhoneServiceImpl(PhoneDao phoneDao, PhoneModelDao phoneModelDao) {
        super(phoneDao, phoneModelDao);        
        this.phoneDao = phoneDao;
        this.phoneModelDao = phoneModelDao;
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
        return PhoneModel.builder()
                .withId(phoneModelDto.getId())
                .withName(phoneModelDto.getName())
                .withSerialNumber(phoneModelDto.getSerialNumber())
                .withColour(phoneModelDto.getColour())
                .withCost(phoneModelDto.getCost())
                .withAvailability(phoneModelDto.getAvailability())
                .withMemoryInMb(phoneModelDto.getMemoryInMb())
                .withNumberOfCameras(phoneModelDto.getNumberOfCameras())
                .build();
    }
}
