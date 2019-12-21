package com.publicpmr.repository;
import com.publicpmr.domain.Taxi;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Taxi entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Long> {

}
