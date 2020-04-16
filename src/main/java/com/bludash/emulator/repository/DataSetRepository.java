package com.bludash.emulator.repository;

import com.bludash.emulator.domain.DataSet;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DataSet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DataSetRepository extends JpaRepository<DataSet, Long> {
}
