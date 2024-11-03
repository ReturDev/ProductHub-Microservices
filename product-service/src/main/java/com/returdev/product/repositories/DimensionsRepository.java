package com.returdev.product.repositories;

import com.returdev.product.entities.DimensionsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DimensionsRepository extends JpaRepository<DimensionsEntity, Long> {}