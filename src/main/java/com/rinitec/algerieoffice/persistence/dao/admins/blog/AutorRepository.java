package com.rinitec.algerieoffice.persistence.dao.admins.blog;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rinitec.algerieoffice.persistence.modal.admins.blog.Autor;

public interface AutorRepository extends JpaRepository<Autor, Long>, AutorRepositoryCustom {

	boolean existsByIdentify(String identify);
	
	boolean existsByEmail(String email);
	
	boolean existsByFacebook(String facebook);
	
	boolean existsByTwitter(String twitter);
	
	boolean existsByLinkedin(String linkedin);
	
	Autor findByIdentify(String identify);
	
	@Query("select count(a.id) from Autor a where a.id in :lines")
	long countAutors(@Param("lines") List<Long> lines);
	
	@Modifying
	@Query("delete from Autor a where a.id in :lines")
	void deleteAutors(@Param("lines") List<Long> lines);
	
}
