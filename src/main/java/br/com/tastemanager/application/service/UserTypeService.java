package br.com.tastemanager.application.service;

import br.com.tastemanager.domain.entity.UserType;
import br.com.tastemanager.domain.repository.UserTypeRepository;
import br.com.tastemanager.shared.dto.request.UserTypeRequestDTO;
import br.com.tastemanager.shared.dto.response.UserTypeResponseDTO;
import br.com.tastemanager.shared.mapper.UserTypeMapper;
import br.com.tastemanager.shared.validator.UserTypeValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        String upperCaseName = userTypeRequestDTO.getName().toUpperCase();
        userTypeValidator.validateUserTypeName(upperCaseName);
        UserType userType = userTypeMapper.toEntity(userTypeRequestDTO);
        userType.setName(upperCaseName);
        userTypeRepository.save(userType);
        return userTypeMapper.toResponseDTO(userType);
    }

    public List<UserTypeResponseDTO> findAllUserTypes(int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id"));
        return userTypeRepository.findAll(pageable).getContent().stream()
                .map(userTypeMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    public UserTypeResponseDTO updateUserType(Long id, UserTypeRequestDTO userTypeRequestDTO) {
        UserType userType = userTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("UserType not found"));
        String upperCaseName = userTypeRequestDTO.getName().toUpperCase();
        userType.setName(upperCaseName);
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