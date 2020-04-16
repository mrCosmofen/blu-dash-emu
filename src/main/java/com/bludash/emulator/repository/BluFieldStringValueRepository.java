package com.bludash.emulator.repository;

import com.bludash.emulator.domain.BluFieldStringValue;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BluFieldStringValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BluFieldStringValueRepository extends JpaRepository<BluFieldStringValue, Long> {
}
