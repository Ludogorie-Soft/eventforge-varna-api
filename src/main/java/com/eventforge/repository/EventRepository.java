package com.eventforge.repository;

import com.eventforge.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
      String LEGAL_USER_CONDITION ="e.organisation.user.isNonLocked = true AND e.organisation.user.isApprovedByAdmin = true";
      String UNEXPIRED_CONDITION = "e.endsAt >= ?1";
      String EXPIRED_CONDITION = "e.endsAt < ?1";

      //queries that find organisation events by active , expired and upcoming

    @Query("SELECT e FROM Event e WHERE e.isEvent = true AND e.organisation.id = :orgId AND e.endsAt < :now  ORDER BY e.startsAt ASC")
    List<Event> findAllExpiredEvents(Long orgId , LocalDateTime now);

    @Query("SELECT e FROM Event e WHERE e.isEvent = true AND e.organisation.id = :orgId AND e.startsAt < :now AND e.endsAt >= :now ORDER BY e.startsAt ASC")
    List<Event> findAllActiveEvents (Long orgId , LocalDateTime now);

    @Query("SELECT e FROM Event e WHERE e.isEvent= true AND e.organisation.id = :orgId AND e.startsAt > :now ORDER BY e.startsAt ASC")
    List<Event> findAllUpcomingEvents(Long orgId , LocalDateTime now);

    // queries accessible for everyone!

    @Query("SELECT e FROM Event e WHERE e.isEvent = true AND "+LEGAL_USER_CONDITION + " AND e.startsAt > :now ORDER BY e.startsAt ASC LIMIT 3")
    List<Event> findThreeUpcomingEvents(LocalDateTime now);
    @Query("SELECT e FROM Event e WHERE e.id = :eventId AND "+LEGAL_USER_CONDITION)
    Event findEventByIdWithCondition(Long eventId);

    @Query("SELECT e FROM Event e WHERE e.isEvent = true AND "+ LEGAL_USER_CONDITION+" AND "+UNEXPIRED_CONDITION)
    Page<Event> findAllActiveEvents(LocalDateTime date ,
                                    Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.isEvent = false AND "+ LEGAL_USER_CONDITION+" AND "+UNEXPIRED_CONDITION)
    Page<Event> findAllActiveAds(LocalDateTime date , Pageable pageable);

    @Query("SELECT e FROM Event e WHERE e.isEvent = true AND "+LEGAL_USER_CONDITION+ " AND "+EXPIRED_CONDITION)
    Page<Event> findAllExpiredEvents(LocalDateTime passedDate , Pageable pageable);
    @Query("SELECT e FROM Event e WHERE e.isEvent = false AND "+LEGAL_USER_CONDITION+ " AND "+EXPIRED_CONDITION)
    Page<Event> findAllExpiredAds(LocalDateTime passedDate , Pageable pageable);

    // queries accessible for organisations!
    @Query("SELECT e FROM Event e WHERE e.organisation.user.id = :userId AND e.organisation.user.isNonLocked = true ORDER BY e.startsAt ASC")
    List<Event> findAllEventsAndAdsForOrganisationByUserId(Long userId);

    @Query("SELECT e FROM Event e WHERE e.isEvent = true AND e.organisation.user.id = :userId AND e.id = :eventId")
    Event findEventByIdAndUserId(Long userId , Long eventId);
}