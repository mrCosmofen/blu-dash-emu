package com.bludash.emulator.repository;

import com.bludash.emulator.domain.BluField;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BluField entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BluFieldRepository extends JpaRepository<BluField, Long> {
}
