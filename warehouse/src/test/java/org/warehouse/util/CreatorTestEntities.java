package org.warehouse.util;

import java.util.ArrayList;
import java.util.List;

import org.warehouse.entity.Size;
import org.warehouse.entity.device.Phone;
import org.warehouse.entity.devicemodel.PhoneModel;

public class CreatorTestEntities {

    public static List<Phone> createPhones() {
        List<Phone> phones = new ArrayList<>();
        Phone phone = Phone.builder().withId(1).withName("IPhone4").withCountryOfManufacture("USA").withCompany("Apple")
                .withOnlineOrder(true).withInstallment(false).withModels(new ArrayList<>()).build();
        phone.addModel(createPhoneModels().get(0));
        phone.addModel(createPhoneModels().get(1));
        phones.add(phone);
        phone = Phone.builder().withId(2).withName("IPhone5").withCountryOfManufacture("USA").withCompany("Apple")
                .withOnlineOrder(true).withInstallment(false).withModels(new ArrayList<>()).build();
        phone.addModel(createPhoneModels().get(2));
        phone.addModel(createPhoneModels().get(3));
        phones.add(phone);
        return phones;

    }    

    public static List<PhoneModel> createPhoneModels() {
        List<PhoneModel> phoneModels = new ArrayList<>();
        PhoneModel phoneModel = PhoneModel.builder().withId(1).withName("DA-11").withSerialNumber("test number")
                .withColor("Black").withCost(25000).withAvailability(true).withSize(createSize(100,100,25)).withMemoryInMb(1024).withNumberCameras(1)
                .build();
        phoneModels.add(phoneModel);
        phoneModel = PhoneModel.builder().withId(2).withName("AAA-33").withSerialNumber("test number")
                .withColor("White").withCost(15000).withAvailability(false).withSize(createSize(100,100,25)).withMemoryInMb(2048).withNumberCameras(4)
                .build();
        phoneModels.add(phoneModel);
        phoneModel = PhoneModel.builder().withId(3).withName("TT-23").withSerialNumber("test number")
                .withColor("White").withCost(75000).withAvailability(false).withSize(createSize(100,100,25)).withMemoryInMb(2048).withNumberCameras(3)
                .build();
        phoneModels.add(phoneModel);
        phoneModel = PhoneModel.builder().withId(4).withName("GT-23").withSerialNumber("test number")
                .withColor("Black").withCost(45000).withAvailability(false).withSize(createSize(100,100,25)).withMemoryInMb(1024).withNumberCameras(1)
                .build();
        phoneModels.add(phoneModel);
        return phoneModels;
    }
    
    public static Size createSize(int length, int width, int height) {
        return Size.builder().withLengthMm(length).withWidthMm(width).withHeightMm(height).build();
    }

}
