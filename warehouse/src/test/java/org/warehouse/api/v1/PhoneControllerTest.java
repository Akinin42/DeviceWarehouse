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
import org.warehouse.dto.PhoneModelDto;
import org.warehouse.entity.device.Phone;
import org.warehouse.exception.EntityAlreadyExistException;
import org.warehouse.exception.EntityNotExistException;
import org.warehouse.service.PhoneService;
import org.warehouse.util.CreatorTestEntities;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
class PhoneControllerTest {

    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @Mock
    private PhoneService phoneServiceMock;

    private PhoneController phoneController;

    @BeforeEach
    public void setUpBeforeClass() throws Exception {
        mapper = new ObjectMapper();
        phoneController = new PhoneController(phoneServiceMock);
        mockMvc = MockMvcBuilders.standaloneSetup(phoneController)
                    .setControllerAdvice(new GlobalExceptionHandler())
                    .build();
    }

    @Test
    void testFindAllPhones() throws Exception {
        List<Phone> phones = CreatorTestEntities.createPhones();
        when(phoneServiceMock.findAllDevices()).thenReturn(phones);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/phones")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("IPhone4")))
                .andExpect(jsonPath("$[1].name", is("IPhone5")))
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    @Test
    void testFindPhoneByName() throws Exception {
        Phone phone = CreatorTestEntities.createPhones().get(0);
        when(phoneServiceMock.findByName("IPhone4")).thenReturn(phone);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/phones/IPhone4")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("IPhone4")));
    }

    @Test
    void testFindPhoneByNameWhenEntityNotExist() throws Exception {
        doThrow(new EntityNotExistException("Device with name unexisteddevice not found!")).when(phoneServiceMock)
                .findByName("unexisteddevice");
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/phones/unexisteddevice")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isNotFound());
    }

    @Test
    void testFindAllPhonesByColor() throws Exception {
        List<Phone> phones = new ArrayList<>();
        Phone phone = CreatorTestEntities.createPhones().get(0);
        phone.removeModel(CreatorTestEntities.createPhoneModels().get(0));
        phones.add(phone);
        when(phoneServiceMock.findAllByColor("White")).thenReturn(phones);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/phones/sortcolor/White")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("IPhone4")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllPhonesByCost() throws Exception {
        List<Phone> phones = new ArrayList<>();
        Phone phone = CreatorTestEntities.createPhones().get(0);
        phone.removeModel(CreatorTestEntities.createPhoneModels().get(0));
        phones.add(phone);
        when(phoneServiceMock.findAllByCost(0, 18000)).thenReturn(phones);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get("/api/v1/phones/sortcost/?minCost=0&maxCost=18000").contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("IPhone4")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllPhonesByAvailability() throws Exception {
        List<Phone> phones = new ArrayList<>();
        Phone phone = CreatorTestEntities.createPhones().get(0);
        phone.removeModel(CreatorTestEntities.createPhoneModels().get(0));
        phones.add(phone);
        when(phoneServiceMock.findAllByAvailability()).thenReturn(phones);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/phones/availability")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("IPhone4")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllPhonesByMemory() throws Exception {
        List<Phone> phones = new ArrayList<>();
        Phone phone = CreatorTestEntities.createPhones().get(0);
        phone.removeModel(CreatorTestEntities.createPhoneModels().get(0));
        phones.add(phone);
        when(phoneServiceMock.findAllByMemory(1000)).thenReturn(phones);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/phones/sortmemory/1000")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("IPhone4")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testFindAllPhonesByCameras() throws Exception {
        List<Phone> phones = new ArrayList<>();
        Phone phone = CreatorTestEntities.createPhones().get(0);
        phone.removeModel(CreatorTestEntities.createPhoneModels().get(0));
        phones.add(phone);
        when(phoneServiceMock.findAllByCameras(1)).thenReturn(phones);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/api/v1/phones/sortcameras/1")
                .contentType(MediaType.APPLICATION_JSON);
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name", is("IPhone4")))
                .andExpect(jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    void testAddPhone() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setName("test");
        device.setCountryOfManufacture("test");
        device.setCompany("test");
        device.setInstallment(true);
        device.setOnlineOrder(true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/phones")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(device));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isCreated());
        verify(phoneServiceMock).addDevice(device);
    }

    @Test
    void testAddPhoneWhenPhoneWithNameExist() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setName("test");
        device.setCountryOfManufacture("test");
        device.setCompany("test");
        device.setInstallment(true);
        device.setOnlineOrder(true);
        doThrow(new EntityAlreadyExistException("Device witn name test already exists")).when(phoneServiceMock)
                .addDevice(device);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/phones")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(device));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isBadRequest());
    }

    @Test
    void testAddPhoneWhenPhoneWithInvalidData() throws Exception {
        DeviceDto device = new DeviceDto();
        device.setName("  ");
        device.setCountryOfManufacture("  ");
        device.setCompany("  ");
        device.setInstallment(true);
        device.setOnlineOrder(true);
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/phones")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(device));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isBadRequest());
    }

    @Test
    void testAddPhoneModelForDevice() throws Exception {
        PhoneModelDto phoneDto = PhoneModelDto.builder()
                .withName("test")
                .withSerialNumber("test")
                .withColour("test")
                .withLengthMm(1)
                .withWidthMm(1)
                .withHeightMm(1)
                .withCost(10)
                .withAvailability(true)
                .withMemoryInMb(1)
                .withNumberOfCameras(1)
                .build();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/phones/models/IPhone")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(phoneDto));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isCreated());
        verify(phoneServiceMock).addModelForDevice("IPhone", phoneDto);
    }

    @Test
    void testAddPhoneModelForDeviceWhenInvalidInputData() throws Exception {
        PhoneModelDto phoneDto = PhoneModelDto.builder()
                .withName("  ")
                .withSerialNumber("test")
                .withColour("test")
                .withLengthMm(1)
                .withWidthMm(1)
                .withHeightMm(1)
                .withCost(10)
                .withAvailability(true)
                .withMemoryInMb(1)
                .withNumberOfCameras(1)
                .build();
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/api/v1/phones/models/IPhone")
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(phoneDto));
        ResultActions result = mockMvc.perform(request);
        result.andExpect(status().isBadRequest());
    }
}
