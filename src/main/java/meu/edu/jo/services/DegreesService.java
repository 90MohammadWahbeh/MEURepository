package meu.edu.jo.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.entities.Degrees;
import meu.edu.jo.repositories.DegreesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DegreesService {

    @Autowired
    private DegreesRepository degreesRepository;

    public List<Degrees> getAllDegrees() {
        try {
            return degreesRepository.findAll();
        } catch (Exception e) {
            throw new CustomException(SystemMessages.NO_RECORDS + e.getMessage());
        }
    }

    public Optional<Degrees> getDegreesById(Long id) {
        try {
            return degreesRepository.findById(id);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.NO_RECORDS + e.getMessage());
        }
    }

    public Degrees saveDegrees(Degrees degrees) {
        try {
            return degreesRepository.save(degrees);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }

    public Degrees updateDegrees(Long id, Degrees updatedDegrees) {
        try {
            Optional<Degrees> optionalDegrees = degreesRepository.findById(id);

            if (optionalDegrees.isPresent()) {
                Degrees existingDegrees = optionalDegrees.get();
                // Copy non-null properties from updatedDegrees to existingDegrees
                BeanUtils.copyProperties(updatedDegrees, existingDegrees, "id");
                return degreesRepository.save(existingDegrees);
            } else {
                throw new CustomException(SystemMessages.NO_RECORDS + id);
            }
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }

    public void deleteDegrees(Long id) {
        try {
            degreesRepository.deleteById(id);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }
}
