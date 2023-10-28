package meu.edu.jo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import meu.edu.jo.entities.SystemCodes;
import meu.edu.jo.services.SystemCodesService;
import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/systemcodes")
public class SystemCodesController {

    @Autowired
    private SystemCodesService systemCodesService;

    @GetMapping
    public ResponseEntity<?> getAllSystemCodes() {
        try {
            List<SystemCodes> systemCodes = systemCodesService.getAllSystemCodes();
            return ResponseEntity.ok(systemCodes);
        } catch (CustomException e) {
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSystemCodesById(@PathVariable Long id) {
        try {
            Optional<SystemCodes> systemCodes = systemCodesService.getSystemCodesById(id);
            return ResponseEntity.ok(systemCodes.orElse(null));
        } catch (CustomException e) {
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<?> createSystemCodes(@RequestBody SystemCodes systemCodes) {
        try {
            SystemCodes createdSystemCodes = systemCodesService.saveSystemCodes(systemCodes);
            return ResponseEntity.ok(createdSystemCodes);
        } catch (CustomException e) {
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSystemCodes(@PathVariable Long id, @RequestBody SystemCodes updatedSystemCodes) {
        try {
            SystemCodes updatedSystemCode = systemCodesService.updateSystemCodes(id, updatedSystemCodes);
            return ResponseEntity.ok(updatedSystemCode);
        } catch (CustomException e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSystemCodes(@PathVariable Long id) {
        try {
        	systemCodesService.deleteSystemCodes(id);
            return new ResponseEntity<String>(SystemMessages.DELETE_MSG,HttpStatus.OK);
        } catch (CustomException e) {
            throw e;
        }
    }
}
