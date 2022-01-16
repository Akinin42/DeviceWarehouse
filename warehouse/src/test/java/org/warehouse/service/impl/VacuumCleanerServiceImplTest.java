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
import org.warehouse.dao.VacuumCleanerDao;
import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.VacuumCleanerModelDto;
import org.warehouse.entity.device.VacuumCleaner;
import org.warehouse.entity.devicemodel.VacuumCleanerModel;
import org.warehouse.exception.EntityAlreadyExistException;
import org.warehouse.exception.EntityNotExistException;
import org.warehouse.service.DeviceSorter;
import org.warehouse.service.VacuumCleanerService;
import org.warehouse.util.CreatorTestEntities;

class VacuumCleanerServiceImplTest {

    private static VacuumCleanerService vacuumCleanerService;
    private static VacuumCleanerDao vacuumCleanerDaoMock;
    private static DeviceSorter<VacuumCleaner> deviceSorter;

    @BeforeAll
    static void init() {
        vacuumCleanerDaoMock = createVacuumCleanerDaoMock();
        deviceSorter = new DeviceSorter<VacuumCleaner>();
        vacuumCleanerService = new VacuumCleanerServiceImpl(vacuumCleanerDaoMock, deviceSorter);
    }

    @Test
    void addDeviceShoulSaveDeviceToDatabaseWhenItNotExistYet() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("test");
        deviceDto.setCountryOfManufacture("test");
        deviceDto.setCompany("test");
        deviceDto.setInstallment(true);
        deviceDto.setOnlineOrder(true);
        VacuumCleaner vacuumCleanerFromDto = VacuumCleaner.builder()                                
                                .withName(deviceDto.getName())
                                .withCountryOfManufacture(deviceDto.getCountryOfManufacture())
                                .withCompany(deviceDto.getCompany())
                                .withOnlineOrder(deviceDto.getOnlineOrder())
                                .withInstallment(deviceDto.getInstallment())
                                .build();
        vacuumCleanerService.addDevice(deviceDto);
        verify(vacuumCleanerDaoMock).save(vacuumCleanerFromDto);
    }
    
    @Test
    void addDeviceShouldThrowEntityAlreadyExistExceptionWhenInputDeviceExistInDatabase() {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setName("MacVac");
        assertThatThrownBy(() -> vacuumCleanerService.addDevice(deviceDto))
                .isInstanceOf(EntityAlreadyExistException.class);
    }
    
    @Test
    void findByNameShouldThrowEntityNotExistExceptionWhenDeviceNotExistInDatabase() {
        assertThatThrownBy(() -> vacuumCleanerService.findByName("notexistVacuumCleaner"))
            .isInstanceOf(EntityNotExistException.class).hasMessage("Device with name notexistVacuumCleaner not found!");
    }
    
    @Test
    void findByNameShouldReturnExpectedVacuumCleanerWhenExist() {
        VacuumCleaner expected = CreatorTestEntities.createVacuumCleaners().get(0);
        assertThat(vacuumCleanerService.findByName("MacVac")).isEqualTo(expected);
    }
    
    @Test
    void findAllDevicesShouldReturnExpectedVacuumCleaners() {
        List<VacuumCleaner> expected = CreatorTestEntities.createVacuumCleaners();
        assertThat(vacuumCleanerService.findAllDevices()).isEqualTo(expected);
    }
    
    @Test
    void findAllByColorShouldReturnExpectedVacuumCleaners() {        
        List<VacuumCleaner> expected = new ArrayList<>();
        VacuumCleaner vacuumCleaner = CreatorTestEntities.createVacuumCleaners().get(0);
        vacuumCleaner.removeModel(CreatorTestEntities.createVacuumCleanerModels().get(0));
        expected.add(vacuumCleaner);
        vacuumCleaner = CreatorTestEntities.createVacuumCleaners().get(1);
        vacuumCleaner.removeModel(CreatorTestEntities.createVacuumCleanerModels().get(3));
        expected.add(vacuumCleaner);
        assertThat(vacuumCleanerService.findAllByColor("White")).isEqualTo(expected);
    }
    
    @Test
    void findAllByColorShouldReturnEmptyListWhenColorNotExist() {
        assertThat(vacuumCleanerService.findAllByColor("Green")).isEmpty();
    }
    
    @Test
    void findAllByColorShouldReturnAllVacuumCleanersWhenColorNull() {
        List<VacuumCleaner> expected = CreatorTestEntities.createVacuumCleaners();
        assertThat(vacuumCleanerService.findAllByColor(null)).isEqualTo(expected);
    }
    
    @Test
    void findAllByColorShouldReturnAllVacuumCleanersWhenColorEmpty() {
        List<VacuumCleaner> expected = CreatorTestEntities.createVacuumCleaners();
        assertThat(vacuumCleanerService.findAllByColor("")).isEqualTo(expected);
    }
    
    @Test
    void findAllByCostShouldReturnExpectedVacuumCleaners() {        
        List<VacuumCleaner> expected = new ArrayList<>();
        VacuumCleaner vacuumCleaner = CreatorTestEntities.createVacuumCleaners().get(0);
        vacuumCleaner.removeModel(CreatorTestEntities.createVacuumCleanerModels().get(1));
        expected.add(vacuumCleaner);
        vacuumCleaner = CreatorTestEntities.createVacuumCleaners().get(1);
        vacuumCleaner.removeModel(CreatorTestEntities.createVacuumCleanerModels().get(2));
        expected.add(vacuumCleaner);
        assertThat(vacuumCleanerService.findAllByCost(25000, 50000)).isEqualTo(expected);
    }
    
    @Test
    void findAllByCostShouldReturnEmptyListWhenCostNotExists() {
        assertThat(vacuumCleanerService.findAllByCost(2000000, 0)).isEmpty();
    }
    
    @Test
    void findAllByCostShouldReturnEmptyListWhenTheirNot() {
        assertThat(vacuumCleanerService.findAllByCost(0, 10)).isEmpty();
    }
    
    @Test
    void findAllByAvailabilityShouldReturnExpectedVacuumCleaners() {        
        List<VacuumCleaner> expected = new ArrayList<>();
        VacuumCleaner vacuumCleaner = CreatorTestEntities.createVacuumCleaners().get(0);
        vacuumCleaner.removeModel(CreatorTestEntities.createVacuumCleanerModels().get(1));
        expected.add(vacuumCleaner);
        assertThat(vacuumCleanerService.findAllByAvailability()).isEqualTo(expected);
    }
    
    @Test
    void findAllByAmountShouldReturnExpectedVacuumCleaners() {        
        List<VacuumCleaner> expected = new ArrayList<>();
        VacuumCleaner vacuumCleaner = CreatorTestEntities.createVacuumCleaners().get(0);
        vacuumCleaner.removeModel(CreatorTestEntities.createVacuumCleanerModels().get(0));
        expected.add(vacuumCleaner);
        vacuumCleaner = CreatorTestEntities.createVacuumCleaners().get(1);
        vacuumCleaner.removeModel(CreatorTestEntities.createVacuumCleanerModels().get(2));
        expected.add(vacuumCleaner);
        assertThat(vacuumCleanerService.findAllByAmount(2)).isEqualTo(expected);
    }
    
    @Test
    void findAllByAmountShouldReturnAllVacuumCleanersWhenInputZero() {        
        List<VacuumCleaner> expected = CreatorTestEntities.createVacuumCleaners();
        assertThat(vacuumCleanerService.findAllByAmount(0)).isEqualTo(expected);
    }
    
    @Test
    void findAllByAmountShouldReturnEmptyListWhenInputNotExist() {
        assertThat(vacuumCleanerService.findAllByAmount(8)).isEmpty();
    }
    
    @Test
    void findAllByModesShouldReturnExpectedVacuumCleaners() {        
        List<VacuumCleaner> expected = new ArrayList<>();
        VacuumCleaner vacuumCleaner = CreatorTestEntities.createVacuumCleaners().get(0);
        vacuumCleaner.removeModel(CreatorTestEntities.createVacuumCleanerModels().get(0));
        expected.add(vacuumCleaner);
        vacuumCleaner = CreatorTestEntities.createVacuumCleaners().get(1);
        vacuumCleaner.removeModel(CreatorTestEntities.createVacuumCleanerModels().get(2));
        expected.add(vacuumCleaner);
        assertThat(vacuumCleanerService.findAllByModes(2)).isEqualTo(expected);
    }
    
    @Test
    void findAllByModesShouldReturnAllVacuumCleanersWhenInputZero() {        
        List<VacuumCleaner> expected = CreatorTestEntities.createVacuumCleaners();
        assertThat(vacuumCleanerService.findAllByModes(0)).isEqualTo(expected);
    }
    
    @Test
    void findAllByModesShouldReturnEmptyListWhenInputNotExist() {
        assertThat(vacuumCleanerService.findAllByModes(8)).isEmpty();
    }
    
    @Test
    void addModelToDeviceShoulSaveModelToDatabase() {
        VacuumCleanerDao vacuumCleanerDaoMock = createVacuumCleanerDaoMock();
        deviceSorter = new DeviceSorter<VacuumCleaner>();
        VacuumCleanerService vacuumCleanerService = new VacuumCleanerServiceImpl(vacuumCleanerDaoMock, deviceSorter);
        VacuumCleanerModelDto modelDto = VacuumCleanerModelDto.builder()
                    .withName("testModel")
                    .withLengthMm(100)
                    .withWidthMm(100)
                    .withHeightMm(100)
                    .build();
        VacuumCleanerModel vacuumCleanerModelFromDto = VacuumCleanerModel.builder()                                
                                .withName(modelDto.getName())
                                .withSize(CreatorTestEntities.createSize(100, 100, 100))
                                .build();
        VacuumCleaner vacuumCleaner = CreatorTestEntities.createVacuumCleaners().get(0);
        vacuumCleaner.addModel(vacuumCleanerModelFromDto);
        vacuumCleanerService.addModelForDevice("MacVac", modelDto);
        verify(vacuumCleanerDaoMock).save(vacuumCleaner);
    }
    
    private static VacuumCleanerDao createVacuumCleanerDaoMock() {
        VacuumCleanerDao vacuumCleanerDaoMock = mock(VacuumCleanerDao.class);
        when(vacuumCleanerDaoMock.findAll()).thenReturn(CreatorTestEntities.createVacuumCleaners());
        when(vacuumCleanerDaoMock.findByNameIgnoreCase("MacVac")).thenReturn(Optional.ofNullable(CreatorTestEntities.createVacuumCleaners().get(0)));
        when(vacuumCleanerDaoMock.findByNameIgnoreCase("MacVac2")).thenReturn(Optional.ofNullable(CreatorTestEntities.createVacuumCleaners().get(1)));
        return vacuumCleanerDaoMock;
    }
}
