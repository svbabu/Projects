package com.thoughtprocessing.controller;

import com.thoughtprocessing.dto.ProfileDTO;
import com.thoughtprocessing.repository.ProfileRepository;
import com.thoughtprocessing.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin("origins=http://localhost:8080")
public class ProfileController {
    @Autowired
    private ProfileService profileService;
   @Autowired
   private ProfileRepository profileRepository;

   @Autowired
   public ProfileController(ProfileService profileService, ProfileRepository profileRepository) {
       this.profileService = profileService;
       this.profileRepository = profileRepository;

   }
    // ✅ Update or create profile
    @PutMapping("/update")
     public ResponseEntity<ProfileDTO> updateProfile(@RequestBody ProfileDTO dto) {
        ProfileDTO updatedProfile = profileService.updateProfile(dto);
        return ResponseEntity.ok(updatedProfile);

    }
    // ✅ Get all profiles by userId
    @GetMapping("/{userId}")
    public ResponseEntity<List<ProfileDTO>> getProfilesByUserId(@PathVariable String userId) {
        List<ProfileDTO> profiles = profileService.findAllByUserprofileId(userId);
        return ResponseEntity.ok(profiles);
     }
}
