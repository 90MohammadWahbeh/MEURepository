package meu.edu.jo.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.entities.Achievements;
import meu.edu.jo.services.AchievementsService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/achievements")
public class AchievementsController {

    @Autowired
    private AchievementsService achievementsService;

    // Other autowired dependencies

    @GetMapping
    public ResponseEntity<List<Achievements>> getAllAchievements() {
        try {
            List<Achievements> achievements = achievementsService.getAllAchievements();
            return new ResponseEntity<>(achievements, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.NO_RECORDS + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getAchievementsById(@PathVariable Long id) {
        try {
            Optional<Achievements> achievements = achievementsService.getAchievementsById(id);
            return new ResponseEntity<>(achievements, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.NO_RECORDS + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Achievements> createAchievements(@RequestBody Achievements achievements) {
        try {
            Achievements createdAchievements = achievementsService.createAchievements(achievements);
            return new ResponseEntity<>(createdAchievements, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Achievements> updateAchievements(@PathVariable Long id,
                                                           @RequestBody Achievements updatedAchievements) {
        try {
            Achievements updatedAchievementsResult = achievementsService.updateAchievements(id, updatedAchievements);
            return new ResponseEntity<>(updatedAchievementsResult, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAchievements(@PathVariable Long id) {
        try {
            achievementsService.deleteAchievements(id);
            return new ResponseEntity<>(SystemMessages.DELETE_MSG, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }

    @PostMapping("/upload-file/{achievementsId}")
    public ResponseEntity<String> uploadFile(@PathVariable Long achievementsId,
                                            @RequestParam("file") MultipartFile file) {
        try {
            Optional<Achievements> achievements = achievementsService.getAchievementsById(achievementsId);

            if (achievements.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Achievements achievementsEntity = achievements.get();
            achievementsEntity.setAchievementsFile(file.getBytes());
            achievementsService.saveAchievements(achievementsEntity);
            return ResponseEntity.ok("File uploaded successfully for achievements with ID: " + achievementsId);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }
}
