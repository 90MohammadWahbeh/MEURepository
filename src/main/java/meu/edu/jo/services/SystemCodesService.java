package meu.edu.jo.services;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import meu.edu.jo.common.SystemMessages;
import meu.edu.jo.common.exceptions.CustomException;
import meu.edu.jo.entities.SystemCodes;
import meu.edu.jo.repositories.SystemCodesRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SystemCodesService {

    @Autowired
    private SystemCodesRepository systemCodesRepository;

    public List<SystemCodes> getAllSystemCodes() {
        List<SystemCodes> systemCodesList = systemCodesRepository.findAll();

        if (!systemCodesList.isEmpty()) {
            return systemCodesList;
        } else {
            throw new CustomException(SystemMessages.NO_RECORDS);
        }
    }

    

    public Optional<SystemCodes> getSystemCodesById(Long id) {
        Optional<SystemCodes> systemCodes = systemCodesRepository.findById(id);
        if (systemCodes.isPresent()) {
            return systemCodes;
        } else {
            throw new CustomException(SystemMessages.NO_RECORDS + id);
        }
    }


    public SystemCodes saveSystemCodes(SystemCodes systemCodes) {
        try {
            return systemCodesRepository.save(systemCodes);
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }

    public SystemCodes updateSystemCodes(Long id, SystemCodes updatedSystemCodes) {
        try {
            Optional<SystemCodes> optionalSystemCodes = systemCodesRepository.findById(id);

            if (optionalSystemCodes.isPresent()) {
                SystemCodes existingSystemCodes = optionalSystemCodes.get();
                BeanUtils.copyProperties(updatedSystemCodes, existingSystemCodes, "id");
                return systemCodesRepository.save(existingSystemCodes);
            } else {
                throw new CustomException(SystemMessages.NO_RECORDS + id);
            }
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }

    public void deleteSystemCodes(Long id) {
        try {
            systemCodesRepository.deleteById(id);
            
        } catch (Exception e) {
            throw new CustomException(SystemMessages.OPERATION_FAILED + e.getMessage());
        }
    }
}
