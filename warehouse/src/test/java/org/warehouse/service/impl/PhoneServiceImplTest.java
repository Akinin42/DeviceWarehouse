package org.warehouse.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.warehouse.dao.PhoneDao;
import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.PhoneModelDto;
import org.warehouse.entity.device.Phone;
import org.warehouse.entity.devicemodel.PhoneModel;
import org.warehouse.exception.EntityAlreadyExistException;
import org.warehouse.exception.EntityNotExistException;
import org.warehouse.service.DeviceSorter;
import org.warehouse.service.PhoneService;
import org.warehouse.util.CreatorTestEntities;

class PhoneServiceImplTest {

    private static PhoneService phoneService;
    private static PhoneDao phoneDaoMock;
    private static DeviceSorter<Phone> deviceSorter;

    @BeforeAll
    static void init() {
        phoneDaoMock = createPhoneDaoMock();
        deviceSorter = new DeviceSorter<Phone>();
        phoneService = new PhoneServiceImpl(phoneDaoMock, deviceSorter);
    }

    @Test
    void addDeviceShoulSaveDeviceToDatabaseWhenItNotExistYet() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("test");
        deviceDto.setCountryOfManufacture("test");
        deviceDto.setCompany("test");
        deviceDto.setInstallment(true);
        deviceDto.setOnlineOrder(true);
        Phone phoneFromDto = Phone.builder()
                                .withId(deviceDto.getId())
                                .withName(deviceDto.getName())
                                .withCountryOfManufacture(deviceDto.getCountryOfManufacture())
                                .withCompany(deviceDto.getCompany())
                                .withOnlineOrder(deviceDto.getOnlineOrder())
                                .withInstallment(deviceDto.getInstallment())
                                .build();
        phoneService.addDevice(deviceDto);
        verify(phoneDaoMock).save(phoneFromDto);
    }
    
    @Test
    void addDeviceShouldThrowEntityAlreadyExistExceptionWhenInputDeviceExistInDatabase() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("IPhone4");
        assertThatThrownBy(() -> phoneService.addDevice(deviceDto))
                .isInstanceOf(EntityAlreadyExistException.class);
    }
    
    @Test
    void findByNameShouldThrowEntityNotExistExceptionWhenDeviceNotExistInDatabase() {
        assertThatThrownBy(() -> phoneService.findByName("notexistphone"))
            .isInstanceOf(EntityNotExistException.class).hasMessage("Device with name notexistphone not found!");
    }
    
    @Test
    void findByNameShouldReturnExpectedPhoneWhenExist() {
        Phone expected = CreatorTestEntities.createPhones().get(0);
        assertThat(phoneService.findByName("IPhone4")).isEqualTo(expected);
    }
    
    @Test
    void findAllDevicesShouldReturnExpectedPhones() {
        List<Phone> expected = CreatorTestEntities.createPhones();
        assertThat(phoneService.findAllDevices()).isEqualTo(expected);
    }
    
    @Test
    void findAllByColorShouldReturnExpectedPhones() {        
        List<Phone> expected = new ArrayList<>();
        Phone phone = CreatorTestEntities.createPhones().get(0);
        phone.removeModel(CreatorTestEntities.createPhoneModels().get(0));
        expected.add(phone);
        phone = CreatorTestEntities.createPhones().get(1);
        phone.removeModel(CreatorTestEntities.createPhoneModels().get(3));
        expected.add(phone);
        assertThat(phoneService.findAllByColor("White")).isEqualTo(expected);
    }
    
    @Test
    void findAllByCostShouldReturnExpectedPhones() {        
        List<Phone> expected = new ArrayList<>();
        Phone phone = CreatorTestEntities.createPhones().get(1);
        phone.removeModel(CreatorTestEntities.createPhoneModels().get(3));
        expected.add(phone);
        assertThat(phoneService.findAllByCost(50000, 0)).isEqualTo(expected);
    }
    
    @Test
    void findAllByCostShouldReturnEmptyListWhenTheirNot() {        
        List<Phone> expected = new ArrayList<>();
        assertThat(phoneService.findAllByCost(0, 10)).isEqualTo(expected);
    }
    
    @Test
    void findAllByAvailabilityShouldReturnExpectedPhones() {        
        List<Phone> expected = new ArrayList<>();
        Phone phone = CreatorTestEntities.createPhones().get(0);
        phone.removeModel(CreatorTestEntities.createPhoneModels().get(1));
        expected.add(phone);
        assertThat(phoneService.findAllByAvailability()).isEqualTo(expected);
    }
    
    @Test
    void findAllByMemoryShouldReturnExpectedPhones() {        
        List<Phone> expected = new ArrayList<>();
        Phone phone = CreatorTestEntities.createPhones().get(0);
        phone.removeModel(CreatorTestEntities.createPhoneModels().get(0));
        expected.add(phone);
        phone = CreatorTestEntities.createPhones().get(1);
        phone.removeModel(CreatorTestEntities.createPhoneModels().get(3));
        expected.add(phone);
        assertThat(phoneService.findAllByMemory(1500)).isEqualTo(expected);
    }
    
    @Test
    void findAllByCamerasShouldReturnExpectedPhones() {        
        List<Phone> expected = new ArrayList<>();
        Phone phone = CreatorTestEntities.createPhones().get(0);
        phone.removeModel(CreatorTestEntities.createPhoneModels().get(0));
        expected.add(phone);
        assertThat(phoneService.findAllByCameras(4)).isEqualTo(expected);
    }
    
    @Test
    void addModelToDeviceShoulSaveModelToDatabase() {
        PhoneDao phoneDaoMock = createPhoneDaoMock();
        deviceSorter = new DeviceSorter<Phone>();
        PhoneService phoneService = new PhoneServiceImpl(phoneDaoMock, deviceSorter);
        PhoneModelDto modelDto = PhoneModelDto.builder()
                    .withName("testModel")
                    .withLengthMm(100)
                    .withWidthMm(100)
                    .withHeightMm(100)
                    .build();
        PhoneModel phoneModelFromDto = PhoneModel.builder()                                
                                .withName(modelDto.getName())
                                .withSize(CreatorTestEntities.createSize(100, 100, 100))
                                .build();
        Phone phone = CreatorTestEntities.createPhones().get(0);
        phone.addModel(phoneModelFromDto);
        phoneService.addModelForDevice("IPhone4", modelDto);
        verify(phoneDaoMock).save(phone);
    }
    
    private static PhoneDao createPhoneDaoMock() {
        PhoneDao phoneDaoMock = mock(PhoneDao.class);
        when(phoneDaoMock.findAll()).thenReturn(CreatorTestEntities.createPhones());
        when(phoneDaoMock.findByNameIgnoreCase("IPhone4")).thenReturn(Optional.ofNullable(CreatorTestEntities.createPhones().get(0)));
        when(phoneDaoMock.findByNameIgnoreCase("IPhone5")).thenReturn(Optional.ofNullable(CreatorTestEntities.createPhones().get(1)));
        return phoneDaoMock;
    }
}
