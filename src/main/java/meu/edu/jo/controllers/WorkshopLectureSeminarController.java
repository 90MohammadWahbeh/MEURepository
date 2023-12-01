package meu.edu.jo.controllers;

import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.entities.WorkshopLectureSeminar;
import meu.edu.jo.services.WorkshopLectureSeminarService;
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
@RequestMapping("/workshops-lectures-seminars")
public class WorkshopLectureSeminarController {

    @Autowired
    private WorkshopLectureSeminarService workshopService;

    // Other autowired dependencies

    @GetMapping
    public ResponseEntity<List<WorkshopLectureSeminar>> getAllWorkshops() {
        try {
            List<WorkshopLectureSeminar> workshops = workshopService.getAllWorkshops();
            return new ResponseEntity<>(workshops, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.NO_RECORDS + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getWorkshopById(@PathVariable Long id) {
        try {
            Optional<WorkshopLectureSeminar> workshop = workshopService.getWorkshopById(id);
            return new ResponseEntity<>(workshop, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.NO_RECORDS + e.getMessage());
        }
    }
    
    @GetMapping("/findByUserId/{userId}")
    public List<WorkshopLectureSeminar> getWorkshopsByUserId(@PathVariable Long userId) {
        return workshopService.getWorkshopsByUserId(userId);
    }

    @PostMapping
    public ResponseEntity<WorkshopLectureSeminar> createWorkshop(@RequestBody WorkshopLectureSeminar workshop) {
        try {
            WorkshopLectureSeminar createdWorkshop = workshopService.createWorkshop(workshop);
            return new ResponseEntity<>(createdWorkshop, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkshopLectureSeminar> updateWorkshop(@PathVariable Long id,
                                                                @RequestBody WorkshopLectureSeminar updatedWorkshop) {
        try {
            WorkshopLectureSeminar updatedWorkshopResult = workshopService.updateWorkshop(id, updatedWorkshop);
            return new ResponseEntity<>(updatedWorkshopResult, HttpStatus.OK);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteWorkshop(@PathVariable Long id) {
        try {
            workshopService.deleteWorkshop(id);
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

    @PostMapping("/upload-file/{workshopId}")
    public ResponseEntity<String> uploadFile(@PathVariable Long workshopId, @RequestParam("file") MultipartFile file) {
        try {
            Optional<WorkshopLectureSeminar> workshop = workshopService.getWorkshopById(workshopId);

            if (workshop.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            WorkshopLectureSeminar workshopEntity = workshop.get();
            workshopEntity.setTheFile(file.getBytes());
            workshopService.saveWorkshop(workshopEntity);
            return ResponseEntity.ok("File uploaded successfully for workshop with ID: " + workshopId);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed");
        }
    }
}
