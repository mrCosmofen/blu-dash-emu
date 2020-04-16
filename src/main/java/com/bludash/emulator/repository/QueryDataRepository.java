package com.bludash.emulator.repository;

import com.bludash.emulator.domain.QueryData;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QueryData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QueryDataRepository extends JpaRepository<QueryData, Long> {
}
