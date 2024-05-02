package com.eventforge.model;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private String address;
    private String facebookLink;
    private String eventCategories;
    private double price;
    private Integer minAge;
    private Integer maxAge;
    @OneToOne(mappedBy = "event" , cascade = CascadeType.REMOVE)
    private Image eventImage;
    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;
    private Boolean isOnline;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
    private Boolean isEvent;
    @DateTimeFormat(pattern ="dd-MM-yyyy HH:mm" ,iso = DateTimeFormat.ISO.DATE_TIME)

    private LocalDateTime startsAt;
    @DateTimeFormat(pattern ="dd-MM-yyyy HH:mm" ,iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endsAt;

    //for recurrence events
    private String recurrenceDetails;
}
