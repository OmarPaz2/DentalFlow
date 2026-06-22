package com.dentalflow.material_service.data.repositories;

import com.dentalflow.material_service.data.entities.MaterialEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IMaterialRepository extends JpaRepository<MaterialEntity, Integer> {

    List<MaterialEntity> findByStockLessThanEqual(Integer stock);
}
