package com.bludash.emulator.repository;

import com.bludash.emulator.domain.BluFieldNumberValue;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BluFieldNumberValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BluFieldNumberValueRepository extends JpaRepository<BluFieldNumberValue, Long> {
}
