package org.warehouse.util;

import java.util.ArrayList;
import java.util.List;

import org.warehouse.entity.Size;
import org.warehouse.entity.device.Computer;
import org.warehouse.entity.device.Fridge;
import org.warehouse.entity.device.Phone;
import org.warehouse.entity.device.TVSet;
import org.warehouse.entity.device.VacuumCleaner;
import org.warehouse.entity.devicemodel.ComputerModel;
import org.warehouse.entity.devicemodel.FridgeModel;
import org.warehouse.entity.devicemodel.PhoneModel;
import org.warehouse.entity.devicemodel.TVSetModel;
import org.warehouse.entity.devicemodel.VacuumCleanerModel;

public class CreatorTestEntities {

    public static List<Phone> createPhones() {
        List<Phone> phones = new ArrayList<>();
        Phone phone = Phone.builder()
                .withId(1)
                .withName("IPhone4")
                .withCountryOfManufacture("USA")
                .withCompany("Apple")
                .withOnlineOrder(true)
                .withInstallment(false)
                .withModels(new ArrayList<>())
                .build();
        phone.addModel(createPhoneModels().get(0));
        phone.addModel(createPhoneModels().get(1));
        phones.add(phone);
        phone = Phone.builder()
                .withId(2)
                .withName("IPhone5")
                .withCountryOfManufacture("USA")
                .withCompany("Apple")
                .withOnlineOrder(true)
                .withInstallment(false)
                .withModels(new ArrayList<>())
                .build();
        phone.addModel(createPhoneModels().get(2));
        phone.addModel(createPhoneModels().get(3));
        phones.add(phone);
        return phones;
    }    

    public static List<PhoneModel> createPhoneModels() {
        List<PhoneModel> phoneModels = new ArrayList<>();
        PhoneModel phoneModel = PhoneModel.builder()
                .withId(1)
                .withName("DA-11")
                .withSerialNumber("test number")
                .withColor("Black")
                .withCost(25000)
                .withAvailability(true)
                .withSize(createSize(100,100,25))
                .withMemoryInMb(1024)
                .withNumberCameras(1)
                .build();
        phoneModels.add(phoneModel);
        phoneModel = PhoneModel.builder()
                .withId(2)
                .withName("AAA-33")
                .withSerialNumber("test number")
                .withColor("White")
                .withCost(15000)
                .withAvailability(false)
                .withSize(createSize(100,100,25))
                .withMemoryInMb(2048)
                .withNumberCameras(4)
                .build();
        phoneModels.add(phoneModel);
        phoneModel = PhoneModel.builder()
                .withId(3)
                .withName("TT-23")
                .withSerialNumber("test number")
                .withColor("White")
                .withCost(75000)
                .withAvailability(false)
                .withSize(createSize(100,100,25))
                .withMemoryInMb(2048)
                .withNumberCameras(3)
                .build();
        phoneModels.add(phoneModel);
        phoneModel = PhoneModel.builder()
                .withId(4)
                .withName("GT-23")
                .withSerialNumber("test number")
                .withColor("Black")
                .withCost(45000)
                .withAvailability(false)
                .withSize(createSize(100,100,25))
                .withMemoryInMb(1024)
                .withNumberCameras(1)
                .build();
        phoneModels.add(phoneModel);
        return phoneModels;
    }
    
    public static List<Computer> createComputers() {
        List<Computer> computers = new ArrayList<>();
        Computer computer = Computer.builder()
                .withId(1)
                .withName("MacBook")
                .withCountryOfManufacture("USA")
                .withCompany("Apple")
                .withOnlineOrder(true)
                .withInstallment(false)
                .withModels(new ArrayList<>())
                .build();
        computer.addModel(createComputerModels().get(0));
        computer.addModel(createComputerModels().get(1));
        computers.add(computer);
        computer = Computer.builder()
                .withId(2)
                .withName("MacBook2")
                .withCountryOfManufacture("USA")
                .withCompany("Apple")
                .withOnlineOrder(true)
                .withInstallment(false)
                .withModels(new ArrayList<>())
                .build();
        computer.addModel(createComputerModels().get(2));
        computer.addModel(createComputerModels().get(3));
        computers.add(computer);
        return computers;
    }
    
    public static List<ComputerModel> createComputerModels() {
        List<ComputerModel> computerModels = new ArrayList<>();
        ComputerModel computerModel = ComputerModel.builder()
                .withId(1)
                .withName("DA-11")
                .withSerialNumber("test number")
                .withColor("Black")
                .withCost(25000)
                .withAvailability(true)
                .withSize(createSize(100,100,25))
                .withCategory("first")
                .withProcessor("atlon")
                .build();
        computerModels.add(computerModel);
        computerModel = ComputerModel.builder()
                .withId(2)
                .withName("AAA-33")
                .withSerialNumber("test number")
                .withColor("White")
                .withCost(15000)
                .withAvailability(false)
                .withSize(createSize(100,100,25))
                .withCategory("second")
                .withProcessor("iCore")
                .build();
        computerModels.add(computerModel);
        computerModel = ComputerModel.builder()
                .withId(3)
                .withName("TT-23")
                .withSerialNumber("test number")
                .withColor("White")
                .withCost(75000)
                .withAvailability(false)
                .withSize(createSize(100,100,25))
                .withCategory("first")
                .withProcessor("atlon")
                .build();
        computerModels.add(computerModel);
        computerModel = ComputerModel.builder()
                .withId(4)
                .withName("GT-23")
                .withSerialNumber("test number")
                .withColor("Black")
                .withCost(45000)
                .withAvailability(false)
                .withSize(createSize(100,100,25))
                .withCategory("second")
                .withProcessor("iCore")
                .build();
        computerModels.add(computerModel);
        return computerModels;
    }
    
    public static List<TVSet> createTVSets() {
        List<TVSet> tvSets = new ArrayList<>();
        TVSet tvSet = TVSet.builder()
                .withId(1)
                .withName("MacTV")
                .withCountryOfManufacture("USA")
                .withCompany("Apple")
                .withOnlineOrder(true)
                .withInstallment(false)
                .withModels(new ArrayList<>())
                .build();
        tvSet.addModel(createTVSetModels().get(0));
        tvSet.addModel(createTVSetModels().get(1));
        tvSets.add(tvSet);
        tvSet = TVSet.builder()
                .withId(2)
                .withName("MacTV2")
                .withCountryOfManufacture("USA")
                .withCompany("Apple")
                .withOnlineOrder(true)
                .withInstallment(false)
                .withModels(new ArrayList<>())
                .build();
        tvSet.addModel(createTVSetModels().get(2));
        tvSet.addModel(createTVSetModels().get(3));
        tvSets.add(tvSet);
        return tvSets;
    }
    
    public static List<TVSetModel> createTVSetModels() {
        List<TVSetModel> tvSetModels = new ArrayList<>();
        TVSetModel tvSetModel = TVSetModel.builder()
                .withId(1)
                .withName("DA-11")
                .withSerialNumber("test number")
                .withColor("Black")
                .withCost(25000)
                .withAvailability(true)
                .withSize(createSize(100,100,25))
                .withCategory("first")
                .withTechnology("LED")
                .build();
        tvSetModels.add(tvSetModel);
        tvSetModel = TVSetModel.builder()
                .withId(2)
                .withName("AAA-33")
                .withSerialNumber("test number")
                .withColor("White")
                .withCost(15000)
                .withAvailability(false)
                .withSize(createSize(100,100,25))
                .withCategory("second")
                .withTechnology("TFT")
                .build();
        tvSetModels.add(tvSetModel);
        tvSetModel = TVSetModel.builder()
                .withId(3)
                .withName("TT-23")
                .withSerialNumber("test number")
                .withColor("White")
                .withCost(75000)
                .withAvailability(false)
                .withSize(createSize(100,100,25))
                .withCategory("first")
                .withTechnology("LED")
                .build();
        tvSetModels.add(tvSetModel);
        tvSetModel = TVSetModel.builder()
                .withId(4)
                .withName("GT-23")
                .withSerialNumber("test number")
                .withColor("Black")
                .withCost(45000)
                .withAvailability(false)
                .withSize(createSize(100,100,25))
                .withCategory("second")
                .withTechnology("TFT")
                .build();
        tvSetModels.add(tvSetModel);
        return tvSetModels;
    }
    
    public static List<Fridge> createFridges() {
        List<Fridge> fridges = new ArrayList<>();
        Fridge fridge = Fridge.builder()
                .withId(1)
                .withName("MacFridge")
                .withCountryOfManufacture("USA")
                .withCompany("Apple")
                .withOnlineOrder(true)
                .withInstallment(false)
                .withModels(new ArrayList<>())
                .build();
        fridge.addModel(createFridgeModels().get(0));
        fridge.addModel(createFridgeModels().get(1));
        fridges.add(fridge);
        fridge = Fridge.builder()
                .withId(2)
                .withName("MacFridge2")
                .withCountryOfManufacture("USA")
                .withCompany("Apple")
                .withOnlineOrder(true)
                .withInstallment(false)
                .withModels(new ArrayList<>())
                .build();
        fridge.addModel(createFridgeModels().get(2));
        fridge.addModel(createFridgeModels().get(3));
        fridges.add(fridge);
        return fridges;
    }
    
    public static List<FridgeModel> createFridgeModels() {
        List<FridgeModel> fridgeModels = new ArrayList<>();
        FridgeModel fridgeModel = FridgeModel.builder()
                .withId(1)
                .withName("DA-11")
                .withSerialNumber("test number")
                .withColor("Black")
                .withCost(25000)
                .withAvailability(true)
                .withSize(createSize(100,100,25))
                .withNumberOfDoor(1)
                .withCompressor("INV")
                .build();
        fridgeModels.add(fridgeModel);
        fridgeModel = FridgeModel.builder()
                .withId(2)
                .withName("AAA-33")
                .withSerialNumber("test number")
                .withColor("White")
                .withCost(15000)
                .withAvailability(false)
                .withSize(createSize(100,100,25))
                .withNumberOfDoor(3)
                .withCompressor("CONV")
                .build();
        fridgeModels.add(fridgeModel);
        fridgeModel = FridgeModel.builder()
                .withId(3)
                .withName("TT-23")
                .withSerialNumber("test number")
                .withColor("White")
                .withCost(75000)
                .withAvailability(false)
                .withSize(createSize(100,100,25))
                .withNumberOfDoor(1)
                .withCompressor("INV")
                .build();
        fridgeModels.add(fridgeModel);
        fridgeModel = FridgeModel.builder()
                .withId(4)
                .withName("GT-23")
                .withSerialNumber("test number")
                .withColor("Black")
                .withCost(45000)
                .withAvailability(false)
                .withSize(createSize(100,100,25))
                .withNumberOfDoor(3)
                .withCompressor("CONV")
                .build();
        fridgeModels.add(fridgeModel);
        return fridgeModels;
    }
    
    public static List<VacuumCleaner> createVacuumCleaners() {
        List<VacuumCleaner> vacuumCleaners = new ArrayList<>();
        VacuumCleaner vacuumCleaner = VacuumCleaner.builder()
                .withId(1)
                .withName("MacVac")
                .withCountryOfManufacture("USA")
                .withCompany("Apple")
                .withOnlineOrder(true)
                .withInstallment(false)
                .withModels(new ArrayList<>())
                .build();
        vacuumCleaner.addModel(createVacuumCleanerModels().get(0));
        vacuumCleaner.addModel(createVacuumCleanerModels().get(1));
        vacuumCleaners.add(vacuumCleaner);
        vacuumCleaner = VacuumCleaner.builder()
                .withId(2)
                .withName("MacVac2")
                .withCountryOfManufacture("USA")
                .withCompany("Apple")
                .withOnlineOrder(true)
                .withInstallment(false)
                .withModels(new ArrayList<>())
                .build();
        vacuumCleaner.addModel(createVacuumCleanerModels().get(2));
        vacuumCleaner.addModel(createVacuumCleanerModels().get(3));
        vacuumCleaners.add(vacuumCleaner);
        return vacuumCleaners;
    }
    
    public static List<VacuumCleanerModel> createVacuumCleanerModels() {
        List<VacuumCleanerModel> vacuumCleanerModels = new ArrayList<>();
        VacuumCleanerModel vacuumCleanerModel = VacuumCleanerModel.builder()
                .withId(1)
                .withName("DA-11")
                .withSerialNumber("test number")
                .withColor("Black")
                .withCost(25000)
                .withAvailability(true)
                .withSize(createSize(100,100,25))
                .withAmountLitres(1)
                .withNumberModes(1)
                .build();
        vacuumCleanerModels.add(vacuumCleanerModel);
        vacuumCleanerModel = VacuumCleanerModel.builder()
                .withId(2)
                .withName("AAA-33")
                .withSerialNumber("test number")
                .withColor("White")
                .withCost(15000)
                .withAvailability(false)
                .withSize(createSize(100,100,25))
                .withAmountLitres(3)
                .withNumberModes(3)
                .build();
        vacuumCleanerModels.add(vacuumCleanerModel);
        vacuumCleanerModel = VacuumCleanerModel.builder()
                .withId(3)
                .withName("TT-23")
                .withSerialNumber("test number")
                .withColor("White")
                .withCost(75000)
                .withAvailability(false)
                .withSize(createSize(100,100,25))
                .withAmountLitres(1)
                .withNumberModes(1)
                .build();
        vacuumCleanerModels.add(vacuumCleanerModel);
        vacuumCleanerModel = VacuumCleanerModel.builder()
                .withId(4)
                .withName("GT-23")
                .withSerialNumber("test number")
                .withColor("Black")
                .withCost(45000)
                .withAvailability(false)
                .withSize(createSize(100,100,25))
                .withAmountLitres(3)
                .withNumberModes(3)
                .build();
        vacuumCleanerModels.add(vacuumCleanerModel);
        return vacuumCleanerModels;
    }
    
    public static Size createSize(int length, int width, int height) {
        return Size.builder()
                .withLengthMm(length)
                .withWidthMm(width)
                .withHeightMm(height)
                .build();
    }
}
