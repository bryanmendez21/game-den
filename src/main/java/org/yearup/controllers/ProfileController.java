package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.yearup.models.Profile;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;
import org.yearup.repository.ProfileRepository;
import org.yearup.service.ProfileService;
import org.yearup.service.UserService;

import java.security.Principal;

@RestController
@RequestMapping("profile")
@CrossOrigin

public class ProfileController {

    private ProfileService profileService;
    private UserService userService;

    public ProfileController(ProfileService profileService, UserService userService) {
        this.profileService = profileService;
        this.userService = userService;
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public Profile getProfile (Principal principal){
        // get the currently logged-in username
        String userName = principal.getName();
        // find database user by username
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        return profileService.getProfileByUserId(userId);
    }

    @PutMapping()
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    public ResponseEntity<Profile> updateProfile(Principal principal, @RequestBody Profile profile)
    {
        String userName = principal.getName();

        User user = userService.getByUserName(userName);
        int userId = user.getId();

        return ResponseEntity.status(200).body(profileService.updateProfile(userId,profile));
    }
}
