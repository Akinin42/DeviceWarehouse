package org.warehouse.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.warehouse.entity.Device;

@Repository
public interface DeviceDao <E extends Device> extends JpaRepository<E, Integer> {

    Optional<E> findByNameIgnoreCase(String deviceName);
}
