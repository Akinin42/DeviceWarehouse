package org.warehouse.api.v1;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.warehouse.GlobalExceptionHandler;
import org.warehouse.dto.DeviceDto;
import org.warehouse.dto.VacuumCleanerModelDto;
import org.warehouse.entity.device.VacuumCleaner;
import org.warehouse.exception.EntityAlreadyExistException;
import org.warehouse.exception.EntityNotExistException;
import org.warehouse.service.VacuumCleanerService;
import org.warehouse.util.CreatorTestEntities;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class VacuumCleanerControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Mock
    private VacuumCleanerService fridgeServiceMock;

    private VacuumCleanerController fridgeController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        mapper = new ObjectMapper();
        fridgeController = new VacuumCleanerController(fridgeServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(fridgeController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
    }

    @Test
    void testFindAllVacuumCleaners() throws Exception {
        List<VacuumCleaner> vacuumCleaners = CreatorTestEntities.createVacuumCleaners();
        when(fridgeServiceMock.findAllDevices()).thenReturn(vacuumCleaners);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/vacuum")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacVac")))
                .andExpect(jsonPath("$[1].name", is("MacVac2")))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void testFindVacuumCleanerByName() throws Exception {
        VacuumCleaner vacuumCleaner = CreatorTestEntities.createVacuumCleaners().get(0);
        when(fridgeServiceMock.findByName("MacVac")).thenReturn(vacuumCleaner);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/vacuum/MacVac")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("MacVac")));
    }

    @Test
    void testFindVacuumCleanerByNameWhenEntityNotExist() throws Exception {
        doThrow(new EntityNotExistException("Device with name unexisteddevice not found!")).when(fridgeServiceMock)
                .findByName("unexisteddevice");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/vacuum/unexisteddevice")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isNotFound());
    }

    @Test
    void testFindAllVacuumCleanerByColor() throws Exception {
        List<VacuumCleaner> vacuumCleaners = new ArrayList<>();
        VacuumCleaner vacuumCleaner = CreatorTestEntities.createVacuumCleaners().get(0);
        vacuumCleaner.removeModel(CreatorTestEntities.createVacuumCleanerModels().get(0));
        vacuumCleaners.add(vacuumCleaner);
        when(fridgeServiceMock.findAllByColor("White")).thenReturn(vacuumCleaners);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/vacuum/sortcolor/White")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacVac")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllVacuumCleanersByCost() throws Exception {
        List<VacuumCleaner> vacuumCleaners = new ArrayList<>();
        VacuumCleaner vacuumCleaner = CreatorTestEntities.createVacuumCleaners().get(0);
        vacuumCleaner.removeModel(CreatorTestEntities.createVacuumCleanerModels().get(0));
        vacuumCleaners.add(vacuumCleaner);
        when(fridgeServiceMock.findAllByCost(0, 18000)).thenReturn(vacuumCleaners);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/vacuum/sortcost/?minCost=0&maxCost=18000")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacVac")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllVacuumCleanersByAvailability() throws Exception {
        List<VacuumCleaner> vacuumCleaners = new ArrayList<>();
        VacuumCleaner vacuumCleaner = CreatorTestEntities.createVacuumCleaners().get(0);
        vacuumCleaner.removeModel(CreatorTestEntities.createVacuumCleanerModels().get(0));
        vacuumCleaners.add(vacuumCleaner);
        when(fridgeServiceMock.findAllByAvailability()).thenReturn(vacuumCleaners);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/vacuum/availability")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacVac")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllVacuumCleanersByAmount() throws Exception {
        List<VacuumCleaner> vacuumCleaners = new ArrayList<>();
        VacuumCleaner vacuumCleaner = CreatorTestEntities.createVacuumCleaners().get(0);
        vacuumCleaner.removeModel(CreatorTestEntities.createVacuumCleanerModels().get(0));
        vacuumCleaners.add(vacuumCleaner);
        when(fridgeServiceMock.findAllByAmount(3)).thenReturn(vacuumCleaners);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/vacuum/sortamount/3")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacVac")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllVacuumCleanersByModes() throws Exception {
        List<VacuumCleaner> vacuumCleaners = new ArrayList<>();
        VacuumCleaner vacuumCleaner = CreatorTestEntities.createVacuumCleaners().get(0);
        vacuumCleaner.removeModel(CreatorTestEntities.createVacuumCleanerModels().get(0));
        vacuumCleaners.add(vacuumCleaner);
        when(fridgeServiceMock.findAllByModes(3)).thenReturn(vacuumCleaners);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/vacuum/sortmodes/3")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacVac")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testAddVacuumCleaner() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setName("test");
        device.setCountryOfManufacture("test");
        device.setCompany("test");
        device.setInstallment(true);
        device.setOnlineOrder(true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/vacuum")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(device));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isCreated());
        verify(fridgeServiceMock).addDevice(device);
    }

    @Test
    void testAddVacuumCleanerWhenNameExist() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setName("test");
        device.setCountryOfManufacture("test");
        device.setCompany("test");
        device.setInstallment(true);
        device.setOnlineOrder(true);
        doThrow(new EntityAlreadyExistException("Device witn name test already exists")).when(fridgeServiceMock)
                .addDevice(device);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/vacuum")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(device));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isBadRequest());
    }

    @Test
    void testAddVacuumCleanerWhenInvalidData() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setName("  ");
        device.setCountryOfManufacture("test");
        device.setCompany("test");
        device.setInstallment(true);
        device.setOnlineOrder(true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/vacuum")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(device));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isBadRequest());
    }

    @Test
    void testAddVacuumCleanerModelForDevice() throws Exception {
        VacuumCleanerModelDto vacuumCleanerDto = VacuumCleanerModelDto.builder()
                .withName("test")
                .withSerialNumber("test")
                .withColour("test")
                .withLengthMm(1)
                .withWidthMm(1)
                .withHeightMm(1)
                .withCost(10)
                .withAvailability(true)
                .withAmountLitres(2)
                .withNumberModes(2)
                .build();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/vacuum/models/MacVac")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(vacuumCleanerDto));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isCreated());
        verify(fridgeServiceMock).addModelForDevice("MacVac", vacuumCleanerDto);
    }

    @Test
    void testAddVacuumCleanerModelForDeviceWhenInvalidInputData() throws Exception {
        VacuumCleanerModelDto vacuumCleanerDto = VacuumCleanerModelDto.builder()
                .withName("test")
                .withSerialNumber("test")
                .withColour("  ")
                .withLengthMm(1)
                .withWidthMm(1)
                .withHeightMm(1)
                .withCost(10)
                .withAvailability(true)
                .withAmountLitres(2)
                .withNumberModes(2)
                .build();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/vacuum/models/MacVac")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(vacuumCleanerDto));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isBadRequest());
    }
}
