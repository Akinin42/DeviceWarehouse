package org.warehouse.dao;

import org.springframework.stereotype.Repository;
import org.warehouse.entity.Fridge;

@Repository
public interface FridgeDao extends DeviceDao<Fridge> {
}
