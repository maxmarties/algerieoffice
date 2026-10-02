package com.rinitec.algerieoffice.persistence.dao.admins.realtime;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rinitec.algerieoffice.persistence.modal.admins.realtime.Deactivate;

public interface DeactivateRepository extends JpaRepository<Deactivate, UUID>, DeactivateRepositoryCustom {

}
