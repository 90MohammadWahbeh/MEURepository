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
import meu.edu.jo.entities.Theses;
import meu.edu.jo.services.ThesesService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/theses")
public class ThesesController {

    @Autowired
    private ThesesService thesesService;

    // Other autowired dependencies

    @GetMapping
    public ResponseEntity<List<Theses>> getAllTheses() {
        try {
            List<Theses> theses = thesesService.getAllTheses();
            return new ResponseEntity<>(theses, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.NO_RECORDS + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getThesesById(@PathVariable Long id) {
        try {
            Optional<Theses> theses = thesesService.getThesesById(id);
            return new ResponseEntity<>(theses, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.NO_RECORDS + e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Theses> createTheses(@RequestBody Theses theses) {
        try {
            Theses createdTheses = thesesService.createTheses(theses);
            return new ResponseEntity<>(createdTheses, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Theses> updateTheses(@PathVariable Long id,
                                               @RequestBody Theses updatedTheses) {
        try {
            Theses updatedThesesResult = thesesService.updateTheses(id, updatedTheses);
            return new ResponseEntity<>(updatedThesesResult, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTheses(@PathVariable Long id) {
        try {
            thesesService.deleteTheses(id);
            return new ResponseEntity<>(SystemMessages.DELETE_MSG, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }

    @PostMapping("/upload-file/{thesesId}")
    public ResponseEntity<String> uploadFile(@PathVariable Long thesesId,
                                             @RequestParam("file") MultipartFile file) {
        try {
            Optional<Theses> theses = thesesService.getThesesById(thesesId);

            if (theses.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Theses thesesEntity = theses.get();
            thesesEntity.setThesesFile(file.getBytes());
            thesesService.saveTheses(thesesEntity);
            return ResponseEntity.ok("File uploaded successfully for theses with ID: " + thesesId);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }
}
