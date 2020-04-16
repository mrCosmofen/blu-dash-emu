package com.bludash.emulator.repository;

import com.bludash.emulator.domain.DataModel;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DataModel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataModelRepository extends JpaRepository<DataModel, Long> {
}
