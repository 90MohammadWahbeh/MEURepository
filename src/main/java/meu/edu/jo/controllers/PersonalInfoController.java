package meu.edu.jo.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import meu.edu.jo.common.PDFGenerator;
import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.entities.PersonalInfo;
import meu.edu.jo.services.PersonalInfoService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/personalInfo")
public class PersonalInfoController {

	@Autowired
	private PDFGenerator pdfGenerator;

	private final PersonalInfoService personalInfoService;

	@Autowired
	public PersonalInfoController(PersonalInfoService userProfileService) {
		this.personalInfoService = userProfileService;
	}

	@GetMapping
	public ResponseEntity<List<PersonalInfo>> getAllUserProfiles() {
		try {
			List<PersonalInfo> userProfiles = personalInfoService.getAllUserProfile();
			return new ResponseEntity<>(userProfiles, HttpStatus.OK);
		} catch (Exception e) {
			throw new CustomException(SystemMessages.NO_RECORDS + e.getMessage());
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> getUserProfileById(@PathVariable Long id) {
		try {
			Optional<PersonalInfo> userProfile = personalInfoService.getUserProfileById(id);
			return new ResponseEntity<>(userProfile, HttpStatus.OK);
		} catch (Exception e) {
			throw new CustomException(SystemMessages.NO_RECORDS + e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity<PersonalInfo> createUserProfile(@RequestBody PersonalInfo userProfile) {
		try {
			PersonalInfo createdUserProfile = personalInfoService.createUserProfile(userProfile);
			return new ResponseEntity<>(createdUserProfile, HttpStatus.OK);
		} catch (Exception e) {
			throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<PersonalInfo> updateUserProfile(@PathVariable Long id,
			@RequestBody PersonalInfo updatedUserProfile) {
		try {
			PersonalInfo updatedUserProfileResult = personalInfoService.updatePersonalInfo(id, updatedUserProfile);
			return new ResponseEntity<>(updatedUserProfileResult, HttpStatus.OK);
		} catch (Exception e) {
			throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUserProfile(@PathVariable Long id) {
		try {
			personalInfoService.deleteUserProfile(id);
			return new ResponseEntity<String>(SystemMessages.DELETE_MSG, HttpStatus.OK);
		} catch (Exception e) {
			throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
		}
	}

	@GetMapping("/report")
	public ResponseEntity<Resource> generatePdfReport() throws IOException {
		pdfGenerator.generatePdfReport();
		String fileName = pdfGenerator.getPdfNameWithDate();
		Resource resource = new FileSystemResource(fileName);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
				.contentLength(resource.getFile().length()).contentType(MediaType.APPLICATION_PDF).body(resource);
	}

	@PostMapping("/upload-image/{userId}")
	public ResponseEntity<String> uploadImage(@PathVariable Long userId, @RequestParam("file") MultipartFile file) {
		try {
			Optional<PersonalInfo> userProfile = personalInfoService.getUserProfileById(userId);

			if (userProfile.isEmpty()) {
				return ResponseEntity.notFound().build();
			}

			PersonalInfo userProfileEntity = userProfile.get();
			userProfileEntity.setImage(file.getBytes());
			personalInfoService.saveUserProfile(userProfileEntity);
			return ResponseEntity.ok("Image uploaded successfully for user with ID: " + userId);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Image upload failed");
		}
	}

}
