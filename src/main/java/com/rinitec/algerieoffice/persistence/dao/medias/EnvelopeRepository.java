package com.rinitec.algerieoffice.persistence.dao.medias;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rinitec.algerieoffice.persistence.modal.medias.Envelope;

public interface EnvelopeRepository extends JpaRepository<Envelope, UUID> {
}
