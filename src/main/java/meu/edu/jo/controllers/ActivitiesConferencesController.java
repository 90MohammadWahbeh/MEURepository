package meu.edu.jo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import meu.edu.jo.entities.ActivitiesConferences;
import meu.edu.jo.entities.Degrees;
import meu.edu.jo.services.ActivitiesConferencesService;
import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/activitiesconferences")
public class ActivitiesConferencesController {

	@Autowired
	private ActivitiesConferencesService activitiesConferencesService;

	@GetMapping
	public ResponseEntity<?> getAllActivitiesConferences() {
		try {
			List<ActivitiesConferences> activitiesConferences = activitiesConferencesService
					.getAllActivitiesConferences();
			return ResponseEntity.ok(activitiesConferences);
		} catch (CustomException e) {
			throw e;
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getActivitiesConferencesById(@PathVariable Long id) {
		try {
			Optional<ActivitiesConferences> activitiesConferences = activitiesConferencesService
					.getActivitiesConferencesById(id);
			if (activitiesConferences.isPresent()) {
				return ResponseEntity.ok(activitiesConferences.orElse(null));

			} else {
				return new ResponseEntity<String>(SystemMessages.NO_RECORDS, HttpStatus.OK);
			}

		} catch (CustomException e) {
			throw e;
		}
	}

	@PostMapping
	public ResponseEntity<?> createActivitiesConferences(@RequestBody ActivitiesConferences activitiesConferences) {
		try {
			ActivitiesConferences createdActivitiesConferences = activitiesConferencesService
					.createActivityConference(activitiesConferences);
			return ResponseEntity.ok(createdActivitiesConferences);
		} catch (CustomException e) {
			throw e;
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateActivitiesConferences(@PathVariable Long id,
			@RequestBody ActivitiesConferences updatedActivitiesConferences) {
		try {
			ActivitiesConferences updatedActivity = activitiesConferencesService.updateActivityConference(id,
					updatedActivitiesConferences);
			return ResponseEntity.ok(updatedActivity);
		} catch (CustomException e) {
			throw e;
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteActivitiesConferences(@PathVariable Long id) {
		try {
			activitiesConferencesService.deleteActivitiesConferences(id);
			return new ResponseEntity<String>(SystemMessages.DELETE_MSG, HttpStatus.OK);
		} catch (CustomException e) {
			throw e;
		}
	}
}
