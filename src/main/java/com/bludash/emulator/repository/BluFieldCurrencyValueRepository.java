package com.bludash.emulator.repository;

import com.bludash.emulator.domain.BluFieldCurrencyValue;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BluFieldCurrencyValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BluFieldCurrencyValueRepository extends JpaRepository<BluFieldCurrencyValue, Long> {
}
