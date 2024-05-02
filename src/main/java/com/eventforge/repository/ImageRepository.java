package com.eventforge.repository;

import com.eventforge.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image, Long> {
    @Query("select i from Image i where i.url= :imageUrl AND i.organisation.id = :orgId AND i.type = 'LOGO'")
    Image findLogoByUrlAndOrgId(String imageUrl , Long orgId);
    @Query("select i from Image i where i.url= :imageUrl AND i.organisation.id = :orgId AND i.type = 'COVER'")
    Image findCoverByUrlAndOrgId(String imageUrl , Long orgId);
    @Query("SELECT i FROM Image i WHERE i.url = :imageUrl AND i.event.id = :eventId")
    Image findEventImageByUrlAndEventId(String imageUrl , Long eventId);

    @Query("SELECT i FROM Image i WHERE i.organisation.id = :id AND i.type = 'LOGO' ")
     Image findOrganisationLogoByOrgId(Long id);

    @Query("SELECT i FROM Image i WHERE i.organisation.id = :id AND i.type = 'COVER'")
     Image findOrganisationCoverPictureByOrgId(Long id);

    @Query("SELECT i FROM Image i WHERE i.event.id = :eventId AND i.type = 'EVENT_PICTURE'")
    Image findEventPicture(Long eventId);
}
