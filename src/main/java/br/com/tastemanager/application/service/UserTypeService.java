package br.com.tastemanager.application.service;

import br.com.tastemanager.domain.entity.UserType;
import br.com.tastemanager.domain.repository.UserTypeRepository;
import br.com.tastemanager.shared.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.shared.dto.response.UserTypeResponseDTO;
import br.com.tastemanager.shared.mapper.UserTypeMapper;
import br.com.tastemanager.shared.validator.UserTypeValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserTypeService {

    private final UserTypeRepository userTypeRepository;
    private final UserTypeMapper userTypeMapper;
    private final UserTypeValidator userTypeValidator;

    public UserTypeService(UserTypeRepository userTypeRepository, UserTypeMapper userTypeMapper, UserTypeValidator userTypeValidator) {
        this.userTypeRepository = userTypeRepository;
        this.userTypeMapper = userTypeMapper;
        this.userTypeValidator = userTypeValidator;
    }

    public UserTypeResponseDTO createUserType(UserTypeRequestDTO userTypeRequestDTO) {
        userTypeValidator.validateUserTypeName(userTypeRequestDTO.getName());
        UserType userType = userTypeMapper.toEntity(userTypeRequestDTO);
        userTypeRepository.save(userType);
        return userTypeMapper.toResponseDTO(userType);
    }

    public List<UserType> findAllUserTypes(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return userTypeRepository.findAll(pageable).getContent();
    }

    public UserTypeResponseDTO updateUserType(Long id, UserTypeRequestDTO userTypeRequestDTO) {
        UserType userType = userTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserType not found"));
        userType.setName(userTypeRequestDTO.getName());
        userTypeRepository.save(userType);
        return userTypeMapper.toResponseDTO(userType);
    }

    public String deleteUserType(Long id) {
        userTypeValidator.validateUserTypeExistsById(id);
        userTypeValidator.validateUserTypeIsInUse(id);
        userTypeRepository.deleteById(id);
        return "User type deleted successfully";
    }

    public UserTypeResponseDTO findUserTypeById(Long id) {
        UserType userType = userTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserType not found"));
        return userTypeMapper.toResponseDTO(userType);
    }
}