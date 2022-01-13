package org.warehouse.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.warehouse.entity.Model;

@Repository
public interface ModelDao <E extends Model> extends JpaRepository<E, Integer> {
}
