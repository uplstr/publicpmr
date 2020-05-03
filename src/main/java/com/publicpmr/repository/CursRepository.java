package com.publicpmr.repository;
import com.publicpmr.domain.Curs;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Curs entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CursRepository extends JpaRepository<Curs, Long> {

}
