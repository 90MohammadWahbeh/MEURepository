package meu.edu.jo.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.entities.Achievements;
import meu.edu.jo.entities.Conference;
import meu.edu.jo.services.ConferenceService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/conferences")
public class ConferenceController {

    @Autowired
    private ConferenceService conferenceService;

    @GetMapping
    public ResponseEntity<List<Conference>> getAllConferences() {
        try {
            List<Conference> conferences = conferenceService.getAllConferences();
            return new ResponseEntity<>(conferences, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.NO_RECORDS + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getConferenceById(@PathVariable Long id) {
        try {
            Optional<Conference> conference = conferenceService.getConferenceById(id);
            return new ResponseEntity<>(conference, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.NO_RECORDS + e.getMessage());
        }
    }
    @GetMapping("/findByUserId/{userId}")
    public List<Conference> getPersonalInfoByUserId(@PathVariable Long userId) {
        return conferenceService.getConferencesByUserId(userId);
    }
	
    @PostMapping
    public ResponseEntity<Conference> createConference(@RequestBody Conference conference) {
        try {
            Conference createdConference = conferenceService.createConference(conference);
            return new ResponseEntity<>(createdConference, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conference> updateConference(@PathVariable Long id, @RequestBody Conference conference) {
        try {
            Conference updatedConference = conferenceService.updateConference(id, conference);
            return new ResponseEntity<>(updatedConference, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteConference(@PathVariable Long id) {
        try {
            conferenceService.deleteConference(id);
            return new ResponseEntity<>(SystemMessages.DELETE_MSG, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }


}
