package meu.edu.jo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import meu.edu.jo.entities.Degrees;
import meu.edu.jo.services.DegreesService;
import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/degrees")
public class DegreesController {

    @Autowired
    private DegreesService degreesService;

    @GetMapping
    public ResponseEntity<?> getAllDegrees() {
        try {
            List<Degrees> degrees = degreesService.getAllDegrees();
            return ResponseEntity.ok(degrees);
        } catch (CustomException e) {
            throw e;
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDegreesById(@PathVariable Long id) {
        try {
            Optional<Degrees> degrees = degreesService.getDegreesById(id);
            if(degrees.isPresent()) {
                return ResponseEntity.ok(degrees.orElse(null));
                
            }else {
                return new ResponseEntity<String>(SystemMessages.NO_RECORDS,HttpStatus.OK);
            }
        
        } catch (CustomException e) {
            throw e;
        }
    }

    @PostMapping
    public ResponseEntity<?> createDegrees(@RequestBody Degrees degrees) {
        try {
            Degrees createdDegrees = degreesService.saveDegrees(degrees);
            return ResponseEntity.ok(createdDegrees);
        } catch (CustomException e) {
            throw e;
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDegrees(@PathVariable Long id, @RequestBody Degrees updatedDegrees) {
        try {
            Degrees updatedDegree = degreesService.updateDegrees(id, updatedDegrees);
            return ResponseEntity.ok(updatedDegree);
        } catch (CustomException e) {
            throw e;
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteDegrees(@PathVariable Long id) {
        try {
            degreesService.deleteDegrees(id);
            return new ResponseEntity<String>(SystemMessages.DELETE_MSG,HttpStatus.OK);
        } catch (CustomException e) {
            throw e;
        }
    }
}
