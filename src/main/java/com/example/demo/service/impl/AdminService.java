package com.example.demo.service.impl;


import com.example.demo.dto.request.CreateAdminRequest;
import com.example.demo.dto.request.CreateUserRequest;
import com.example.demo.dto.request.UpdateAdminRequest;
import com.example.demo.dto.response.AdminResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.Admin;
import com.example.demo.entity.User;
import com.example.demo.repository.IAdminRepository;
import com.example.demo.repository.IClinicRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IAdminService;
import com.example.demo.service.IUserService;
import com.example.demo.utils.enums.AdminType;
import com.example.demo.utils.enums.UserType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminService implements IAdminService {

    private final IAdminRepository _adminRepository;

    private final IUserService _userService;

    private final IUserRepository _userRepository;

    private final IClinicRepository _clinicRepository;

    public AdminService(IAdminRepository adminRepository, IUserService userService, IUserRepository userRepository, IClinicRepository clinicRepository) {
        _adminRepository = adminRepository;
        _userService = userService;
        _userRepository = userRepository;
        _clinicRepository = clinicRepository;
    }

    @Override
    public AdminResponse
    createAdmin(CreateAdminRequest genericRequest) throws Exception {
        CreateUserRequest userRequest = new CreateUserRequest();

        userRequest.setPassword(genericRequest.getPassword());
        userRequest.setRePassword(genericRequest.getPassword());
        userRequest.setAddress(genericRequest.getAddress());
        userRequest.setCity(genericRequest.getCity());
        userRequest.setEmail(genericRequest.getEmail());
        userRequest.setCountry(genericRequest.getCountry());
        userRequest.setFirstName(genericRequest.getFirstName());
        userRequest.setLastName(genericRequest.getLastName());
        userRequest.setSsn(genericRequest.getSsn());
        userRequest.setPhone(genericRequest.getPhone());
        userRequest.setUserType(UserType.ADMIN);
        // Save user to database and gets NOT User Entity, we get UserResponse
        UserResponse userResponse = _userService.createUser(userRequest);
        userResponse.setDeleted(false);
        // Transform to user entity
        User user = _userRepository.findOneById(userResponse.getId());
        //user.setId(userResponse.getId());

        Admin admin = new Admin();
        admin.setUser(user);
        admin.setAdminType(AdminType.ADMIN);
//        admin.setClinic(_clinicRepository.findOneById(genericRequest.getClinicId()));


        Admin savedAdmin = _adminRepository.save(admin);

        return mapAdminToAdminResponse(savedAdmin);
    }

    @Override
    public AdminResponse updateAdmin(UUID id, UpdateAdminRequest request) throws Exception {
        Admin admin = _adminRepository.findOneById(id);

        if (admin == null) {
            throw new Exception(String.format("Admin with %s id is not found", id));
        }

        // Set values for patient
        // there is no patient fields

        User user = admin.getUser();
        user.setAddress(request.getAddress());
        user.setCity(request.getCity());
        user.setCountry(request.getCountry());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());

        Admin savedAdmin = _adminRepository.save(admin);

        return mapAdminToAdminResponse(savedAdmin);
    }

    @Override
    public AdminResponse getAdmin(UUID id) throws Exception {
        Admin admin = _adminRepository.findOneById(id);

        if (admin == null) {
            throw new Exception(String.format("Admin with %s id is not found", id));
        }

        return mapAdminToAdminResponse(admin);
    }

    @Override
    public List<AdminResponse> getAdmins() {
        List<Admin> admins = _adminRepository.findAllByUser_IsDeleted(false);

        return admins
                .stream()
                .map(a -> mapAdminToAdminResponse(a))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAdmin(UUID id) {
        Admin admin = _adminRepository.findOneById(id);
        User user = admin.getUser();
        user.setDeleted(true);
        _adminRepository.save(admin);
    }

    private AdminResponse mapAdminToAdminResponse(Admin admin) {
        AdminResponse adminResponse = new AdminResponse();
        User user = admin.getUser();
        adminResponse.setAddress(user.getAddress());
        adminResponse.setCity(user.getCity());
        adminResponse.setCountry(user.getCountry());
        adminResponse.setEmail(user.getEmail());
        adminResponse.setFirstName(user.getFirstName());
        adminResponse.setLastName(user.getLastName());
        adminResponse.setId(admin.getId());
        adminResponse.setPhone(user.getPhone());
        adminResponse.setSsn(user.getSsn());
        adminResponse.setAdminType(admin.getAdminType());

        return adminResponse;
    }
}