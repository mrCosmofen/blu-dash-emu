package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.QueryData;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the QueryData entity.
 */
@SuppressWarnings("unused")
@Repository
public interface QueryDataRepository extends JpaRepository<QueryData, Long> {
}
