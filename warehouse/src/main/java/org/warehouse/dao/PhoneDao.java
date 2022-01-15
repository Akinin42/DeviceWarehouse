package org.warehouse.dao;

import org.springframework.stereotype.Repository;
import org.warehouse.entity.device.Phone;

@Repository
public interface PhoneDao extends DeviceDao<Phone> {

}
