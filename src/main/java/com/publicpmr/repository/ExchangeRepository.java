package com.publicpmr.repository;
import com.publicpmr.domain.Exchange;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the Exchange entity.
 */
@Repository
public interface ExchangeRepository extends JpaRepository<Exchange, Long> {

    @Query(value = "select distinct exchange from Exchange exchange left join fetch exchange.rates",
        countQuery = "select count(distinct exchange) from Exchange exchange")
    Page<Exchange> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct exchange from Exchange exchange left join fetch exchange.rates")
    List<Exchange> findAllWithEagerRelationships();

    @Query("select exchange from Exchange exchange left join fetch exchange.rates where exchange.id =:id")
    Optional<Exchange> findOneWithEagerRelationships(@Param("id") Long id);

}
