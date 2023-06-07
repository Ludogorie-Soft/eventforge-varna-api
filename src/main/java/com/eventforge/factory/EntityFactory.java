package com.eventforge.factory;

import com.eventforge.constants.OrganisationPriorityCategory;
import com.eventforge.constants.Role;

import com.eventforge.dto.EventRequest;
import com.eventforge.dto.RegistrationRequest;
import com.eventforge.exception.EmailAlreadyTakenException;
import com.eventforge.model.Event;
import com.eventforge.model.Organisation;
import com.eventforge.model.OrganisationPriority;
import com.eventforge.model.User;
import com.eventforge.service.OrganisationPriorityService;
import com.eventforge.service.OrganisationService;
import com.eventforge.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class EntityFactory {
    private final OrganisationService organisationService;

    private final OrganisationPriorityService organisationPriorityService;

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    public Event createEvent(EventRequest eventRequest, String authHeader) {
        User user = userService.getLoggedUserByToken(authHeader);
        Organisation organisation = organisationService.getOrganisationByUserId(user.getId());
        return Event.builder()
                .name(eventRequest.getName())
                .description(eventRequest.getDescription())
                .address(eventRequest.getAddress())
                .eventCategories(eventRequest.getEventCategories())
                .organisation(organisation)
                .isOnline(eventRequest.getIsOnline())
                .startsAt(eventRequest.getStartsAt())
                .endsAt(eventRequest.getEndsAt())
                .build();

    }


    public User createOrganisation(RegistrationRequest request) {
        User user = createUser(request);
        Set<OrganisationPriority> organisationPriorities =
                assignOrganisationPrioritiesToOrganisation(request.getOrganisationPriorities(), request.getOptionalCategory());

        Organisation org = Organisation.builder()
                .name(request.getName())
                .bullstat(request.getBullstat())
                .user(user)
                .address(request.getAddress())
                .organisationPriorities(organisationPriorities)
                .website(request.getWebsite())
                .facebookLink(request.getFacebookLink())
                .charityOption(request.getCharityOption())
                .organisationPurpose(request.getOrganisationPurpose())
                .build();

        organisationService.saveOrganisationInDb(org);
        return user;
    }


    public User createUser(RegistrationRequest request) {
        User user = userService.getUserByEmail(request.getUsername());
        if (user == null) {
            User user1 = User.builder()
                    .username(request.getUsername())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(Role.ORGANISATION.toString())
                    .phoneNumber(request.getPhoneNumber())
                    .fullName(request.getFullName())
                    .isEnabled(false)
                    .isNonLocked(true)
                    .build();
            userService.saveUserInDb(user1);
            return user1;
        } else {
            log.warn("Неуспешна регистрация");
            throw new EmailAlreadyTakenException();
        }

    }


    private Set<OrganisationPriority> assignOrganisationPrioritiesToOrganisation(Set<String> priorityCategories, String optionalCategory) {
        Set<OrganisationPriority> organisationPriorities = new HashSet<>();
        OrganisationPriority newOrganisationPriority = null;
        if (optionalCategory != null) {
            newOrganisationPriority = createOrganisationPriority(optionalCategory);
        }
        if (newOrganisationPriority != null) {
            organisationPriorities.add(newOrganisationPriority);
        }

        if (priorityCategories != null) {
            for (String category : priorityCategories) {
                OrganisationPriority organisationPriority = organisationPriorityService.getOrganisationPriorityByCategory(category);
                if (organisationPriority != null) {
                    organisationPriorities.add(organisationPriority);
                }
            }
        }
        return organisationPriorities;
    }

    private OrganisationPriority createOrganisationPriority(String priority) {
        OrganisationPriority organisationPriority = null;
        if (organisationPriorityService.getOrganisationPriorityByCategory(priority) == null) {
            organisationPriority = new OrganisationPriority(priority);
            organisationPriorityService.saveOrganisationPriority(organisationPriority);
            OrganisationPriorityCategory.addNewOrganisationPriorityCategory(priority);

        }
        return organisationPriority;
    }

}