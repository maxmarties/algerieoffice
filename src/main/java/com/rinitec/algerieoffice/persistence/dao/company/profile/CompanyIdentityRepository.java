package com.rinitec.algerieoffice.persistence.dao.company.profile;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.company.profile.CompanyIdentity;

public interface CompanyIdentityRepository extends JpaRepository<CompanyIdentity, Long>, CompanyIdentityRepositoryCustom {

	@Modifying
	@Query("update CompanyIdentity c set c.consulted = true where c.companyId = ?1")
	void updateCosultedByCompanyId(Long companyId);
	
	@Query("select c.userId from CompanyIdentity c where c.companyId = ?1")
	Optional<Long> findUserIdByCompanyId(Long companyId);
	
	@Modifying
	@Query("delete from CompanyIdentity c where c.companyId in :lines")
	void deleteIdentities(@Param("lines") List<Long> lines);
	
}
