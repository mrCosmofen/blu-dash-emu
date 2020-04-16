package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.BluForm;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the BluForm entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BluFormRepository extends JpaRepository<BluForm, Long> {
}
