package com.publicpmr.repository;
import com.publicpmr.domain.Rates;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Rates entity.
 */
@Repository
public interface RatesRepository extends JpaRepository<Rates, Long> {

    @Query(value = "select distinct rates from Rates rates left join fetch rates.curs",
        countQuery = "select count(distinct rates) from Rates rates")
    Page<Rates> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct rates from Rates rates left join fetch rates.curs")
    List<Rates> findAllWithEagerRelationships();

    @Query("select rates from Rates rates left join fetch rates.curs where rates.id =:id")
    Optional<Rates> findOneWithEagerRelationships(@Param("id") Long id);

}
