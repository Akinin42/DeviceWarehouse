package org.warehouse.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.warehouse.entity.Device;

@Repository
public interface DeviceDao <E extends Device> extends JpaRepository<E, Integer> {

    Optional<E> findByNameIgnoreCase(String deviceName);
    
    @Query("SELECT d FROM Device d")
    List<Device> findAllWarehouse();
    
    @Query("SELECT d FROM Device d ORDER BY name")
    List<Device> findAllOrderedWarehouseByName();
}
