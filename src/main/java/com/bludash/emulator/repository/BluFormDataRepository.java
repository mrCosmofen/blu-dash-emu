package com.bludash.emulator.repository;

import com.bludash.emulator.domain.BluFormData;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BluFormData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BluFormDataRepository extends JpaRepository<BluFormData, Long> {
}
