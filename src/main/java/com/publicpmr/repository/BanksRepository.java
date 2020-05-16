package com.publicpmr.repository;
import com.publicpmr.domain.Banks;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Banks entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BanksRepository extends JpaRepository<Banks, Long> {

}
