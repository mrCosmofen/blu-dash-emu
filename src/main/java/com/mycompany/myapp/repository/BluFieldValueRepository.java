package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BluFieldValue;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BluFieldValue entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BluFieldValueRepository extends JpaRepository<BluFieldValue, Long> {
}
