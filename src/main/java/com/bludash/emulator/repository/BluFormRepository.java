package com.bludash.emulator.repository;

import com.bludash.emulator.domain.BluForm;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BluForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BluFormRepository extends JpaRepository<BluForm, Long> {
}
