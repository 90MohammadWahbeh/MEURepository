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
import meu.edu.jo.entities.Activities;
import meu.edu.jo.services.ActivitiesService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/activities")
public class ActivitiesController {

	@Autowired
	private ActivitiesService activitiesService;

	@GetMapping
	public ResponseEntity<?> getAllActivities() {
		try {
			List<Activities> activities = activitiesService.getAllActivities();
			return ResponseEntity.ok(activities);
		} catch (CustomException e) {
			throw e;
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getActivitiesById(@PathVariable Long id) {
		try {
			Optional<Activities> activities = activitiesService.getActivitiesById(id);
			if (activities.isPresent()) {
				return ResponseEntity.ok(activities.orElse(null));

			} else {
				return new ResponseEntity<String>(SystemMessages.NO_RECORDS, HttpStatus.OK);
			}

		} catch (CustomException e) {
			throw e;
		}
	}

	@PostMapping
	public ResponseEntity<?> createActivities(@RequestBody Activities activities) {
		try {
			Activities createdActivities = activitiesService.createActivity(activities);
			return ResponseEntity.ok(createdActivities);
		} catch (CustomException e) {
			throw e;
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateActivities(@PathVariable Long id,
			@RequestBody Activities updatedActivities) {
		try {
			Activities updatedActivity = activitiesService.updateActivity(id, updatedActivities);
			return ResponseEntity.ok(updatedActivity);
		} catch (CustomException e) {
			throw e;
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteActivities(@PathVariable Long id) {
		try {
			activitiesService.deleteActivities(id);
			return new ResponseEntity<String>(SystemMessages.DELETE_MSG, HttpStatus.OK);
		} catch (CustomException e) {
			throw e;
		}
	}
}
