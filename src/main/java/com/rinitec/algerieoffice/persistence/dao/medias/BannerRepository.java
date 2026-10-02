package com.rinitec.algerieoffice.persistence.dao.medias;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rinitec.algerieoffice.persistence.modal.medias.Banner;

public interface BannerRepository extends JpaRepository<Banner, UUID> {
}
