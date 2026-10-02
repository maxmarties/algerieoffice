package com.rinitec.algerieoffice.persistence.dao.medias;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rinitec.algerieoffice.persistence.modal.medias.Screenshot;

public interface ScreenshotRepository extends JpaRepository<Screenshot, UUID> {
}
