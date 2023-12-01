package meu.edu.jo.controllers;

import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.entities.Theses;
import meu.edu.jo.entities.UniversitySociety;
import meu.edu.jo.services.UniversitySocietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/university-societies")
public class UniversitySocietyController {

    @Autowired
    private UniversitySocietyService universitySocietyService;

    // Other autowired dependencies

    @GetMapping
    public ResponseEntity<List<UniversitySociety>> getAllUniversitySocieties() {
        try {
            List<UniversitySociety> universitySocieties = universitySocietyService.getAllUniversitySocieties();
            return new ResponseEntity<>(universitySocieties, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.NO_RECORDS + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUniversitySocietyById(@PathVariable Long id) {
        try {
            Optional<UniversitySociety> universitySociety = universitySocietyService.getUniversitySocietyById(id);
            return new ResponseEntity<>(universitySociety, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.NO_RECORDS + e.getMessage());
        }
    }
    
    @GetMapping("/findByUserId/{userId}")
    public List<UniversitySociety> getPersonalInfoByUserId(@PathVariable Long userId) {
        return universitySocietyService.getUniversitySocietiesByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<UniversitySociety> createUniversitySociety(@RequestBody UniversitySociety universitySociety) {
        try {
            UniversitySociety createdUniversitySociety = universitySocietyService.createUniversitySociety(universitySociety);
            return new ResponseEntity<>(createdUniversitySociety, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<UniversitySociety> updateUniversitySociety(@PathVariable Long id,
                                                                    @RequestBody UniversitySociety updatedUniversitySociety) {
        try {
            UniversitySociety updatedUniversitySocietyResult =
                    universitySocietyService.updateUniversitySociety(id, updatedUniversitySociety);
            return new ResponseEntity<>(updatedUniversitySocietyResult, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUniversitySociety(@PathVariable Long id) {
        try {
            universitySocietyService.deleteUniversitySociety(id);
            return new ResponseEntity<>(SystemMessages.DELETE_MSG, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }

    @GetMapping("/report")
    public ResponseEntity<Resource> generatePdfReport() throws IOException {
        // Assuming you have a PDFGenerator class similar to the one in PersonalInfoController
        // pdfGenerator.generatePdfReport();
        // String fileName = pdfGenerator.getPdfNameWithDate();

        // For simplicity, returning a dummy resource
        String fileName = "dummy-report.pdf";
        Resource resource = new FileSystemResource(fileName);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
                .contentLength(resource.getFile().length())
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }

    @PostMapping("/upload-file/{universitySocietyId}")
    public ResponseEntity<String> uploadFile(@PathVariable Long universitySocietyId,
                                             @RequestParam("file") MultipartFile file) {
        try {
            Optional<UniversitySociety> universitySociety =
                    universitySocietyService.getUniversitySocietyById(universitySocietyId);

            if (universitySociety.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            UniversitySociety universitySocietyEntity = universitySociety.get();
            universitySocietyEntity.setUniversitySocietyFile(file.getBytes());
            universitySocietyService.saveUniversitySociety(universitySocietyEntity);
            return ResponseEntity.ok("File uploaded successfully for university society with ID: " + universitySocietyId);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }
}
