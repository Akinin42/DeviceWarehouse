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
import org.warehouse.dto.FridgeModelDto;
import org.warehouse.entity.device.Fridge;
import org.warehouse.exception.EntityAlreadyExistException;
import org.warehouse.exception.EntityNotExistException;
import org.warehouse.service.FridgeService;
import org.warehouse.util.CreatorTestEntities;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class FridgeControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Mock
    private FridgeService fridgeServiceMock;

    private FridgeController fridgeController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        mapper = new ObjectMapper();
        fridgeController = new FridgeController(fridgeServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(fridgeController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
    }

    @Test
    void testFindAllFridges() throws Exception {
        List<Fridge> fridges = CreatorTestEntities.createFridges();
        when(fridgeServiceMock.findAllDevices()).thenReturn(fridges);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/fridges")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacFridge")))
                .andExpect(jsonPath("$[1].name", is("MacFridge2")))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void testFindFridgeByName() throws Exception {
        Fridge fridge = CreatorTestEntities.createFridges().get(0);
        when(fridgeServiceMock.findByName("MacFridge")).thenReturn(fridge);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/fridges/MacFridge")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("MacFridge")));
    }

    @Test
    void testFindFridgeByNameWhenEntityNotExist() throws Exception {
        doThrow(new EntityNotExistException("Device with name unexisteddevice not found!")).when(fridgeServiceMock)
                .findByName("unexisteddevice");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/fridges/unexisteddevice")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isNotFound());
    }

    @Test
    void testFindAllFridgesByColor() throws Exception {
        List<Fridge> fridges = new ArrayList<>();
        Fridge fridge = CreatorTestEntities.createFridges().get(0);
        fridge.removeModel(CreatorTestEntities.createFridgeModels().get(0));
        fridges.add(fridge);
        when(fridgeServiceMock.findAllByColor("White")).thenReturn(fridges);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/fridges/sortcolor/White")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacFridge")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllFridgesByCost() throws Exception {
        List<Fridge> fridges = new ArrayList<>();
        Fridge fridge = CreatorTestEntities.createFridges().get(0);
        fridge.removeModel(CreatorTestEntities.createFridgeModels().get(0));
        fridges.add(fridge);
        when(fridgeServiceMock.findAllByCost(0, 18000)).thenReturn(fridges);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/fridges/sortcost/?minCost=0&maxCost=18000")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacFridge")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllFridgesByAvailability() throws Exception {
        List<Fridge> fridges = new ArrayList<>();
        Fridge fridge = CreatorTestEntities.createFridges().get(0);
        fridge.removeModel(CreatorTestEntities.createFridgeModels().get(0));
        fridges.add(fridge);
        when(fridgeServiceMock.findAllByAvailability()).thenReturn(fridges);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/fridges/availability")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacFridge")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllFridgesByDoors() throws Exception {
        List<Fridge> fridges = new ArrayList<>();
        Fridge fridge = CreatorTestEntities.createFridges().get(0);
        fridge.removeModel(CreatorTestEntities.createFridgeModels().get(0));
        fridges.add(fridge);
        when(fridgeServiceMock.findAllByDoors(3)).thenReturn(fridges);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/fridges/sortdoor/3")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacFridge")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllFridgesByCompressor() throws Exception {
        List<Fridge> fridges = new ArrayList<>();
        Fridge fridge = CreatorTestEntities.createFridges().get(0);
        fridge.removeModel(CreatorTestEntities.createFridgeModels().get(0));
        fridges.add(fridge);
        when(fridgeServiceMock.findAllByCompressor("CONV")).thenReturn(fridges);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/fridges/sortcompressor/CONV")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("MacFridge")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testAddFridge() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setName("test");
        device.setCountryOfManufacture("test");
        device.setCompany("test");
        device.setInstallment(true);
        device.setOnlineOrder(true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/fridges")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(device));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isCreated());
        verify(fridgeServiceMock).addDevice(device);
    }

    @Test
    void testAddFridgeWhenNameExist() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setName("test");
        device.setCountryOfManufacture("test");
        device.setCompany("test");
        device.setInstallment(true);
        device.setOnlineOrder(true);
        doThrow(new EntityAlreadyExistException("Device witn name test already exists")).when(fridgeServiceMock)
                .addDevice(device);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/fridges")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(device));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isBadRequest());
    }

    @Test
    void testAddFridgeWhenInvalidData() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setName("  ");
        device.setCountryOfManufacture("test");
        device.setCompany("test");
        device.setInstallment(true);
        device.setOnlineOrder(true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/fridges")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(device));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isBadRequest());
    }

    @Test
    void testAddFridgeModelForDevice() throws Exception {
        FridgeModelDto fridgeDto = FridgeModelDto.builder()
                .withName("test")
                .withSerialNumber("test")
                .withColour("test")
                .withLengthMm(1)
                .withWidthMm(1)
                .withHeightMm(1)
                .withCost(10)
                .withAvailability(true)
                .withNumberOfDoor(2)
                .withCompressor("test")
                .build();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/fridges/models/MacFridge")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(fridgeDto));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isCreated());
        verify(fridgeServiceMock).addModelForDevice("MacFridge", fridgeDto);
    }

    @Test
    void testAddFridgeModelForDeviceWhenInvalidInputData() throws Exception {
        FridgeModelDto fridgeDto = FridgeModelDto.builder()
                .withName("test")
                .withSerialNumber("test")
                .withColour("  ")
                .withLengthMm(1)
                .withWidthMm(1)
                .withHeightMm(1)
                .withCost(10)
                .withAvailability(true)
                .withNumberOfDoor(2)
                .withCompressor("test")
                .build();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/fridges/models/MacFridge")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(fridgeDto));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isBadRequest());
    }
}
