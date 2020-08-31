package com.example.demo.service.impl;

import com.example.demo.dto.request.LoginRequest;
import com.example.demo.dto.response.ClinicResponse;
import com.example.demo.dto.response.LoginResponse;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.Admin;
import com.example.demo.entity.MedicalStaff;
import com.example.demo.entity.User;
import com.example.demo.repository.IAdminRepository;
import com.example.demo.repository.IMedicalStaffRepository;
import com.example.demo.repository.IPatientRepository;
import com.example.demo.repository.IUserRepository;
import com.example.demo.security.DomainUserDetailsService;
import com.example.demo.service.IAuthService;
import com.example.demo.service.ITokenProvider;
import com.example.demo.utils.enums.UserType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthService implements IAuthService {

    private final PasswordEncoder _passwordEncoder;

    private final IUserRepository _userRepository;

    private final IMedicalStaffRepository _medicalStaffRepository;

    private final IAdminRepository _adminRepository;

    private final AuthenticationManager _authenticationManager;

    private final ITokenProvider _tokenProvider;

    private final IPatientRepository _patientRepository;

    private final DomainUserDetailsService _domainUserDetailsService;

    public AuthService(PasswordEncoder passwordEncoder, IUserRepository userRepository,
                       IPatientRepository patientRepository,
                       DomainUserDetailsService domainUserDetailsService,
                       AuthenticationManager authenticationManager, ITokenProvider tokenProvider,
                       IMedicalStaffRepository medicalStaffRepository,
                       IAdminRepository adminRepository) {
        _passwordEncoder = passwordEncoder;
        _userRepository = userRepository;
        _medicalStaffRepository = medicalStaffRepository;
        _adminRepository = adminRepository;
        _patientRepository = patientRepository;
        _domainUserDetailsService = domainUserDetailsService;
        _authenticationManager = authenticationManager;
        _tokenProvider = tokenProvider;
    }

    @Override
    public LoginResponse login(LoginRequest request) throws Exception {
        _domainUserDetailsService.loadUserByUsername(request.getEmail());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword());
        Authentication authentication = _authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        User user = _userRepository.findOneByEmail(request.getEmail());

        if (user == null) {
            throw new Exception(String.format("Uneli ste nepostojeci email!"));
        }

        if (!_passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new Exception("Pogresna lozinka!");
        }

        if (user.getUserType().equals(UserType.PATIENT)) {
            user.setFirstTimeLoggedIn(new Date());
            _userRepository.save(user);
        }

        if (user.getUserType().equals(UserType.PATIENT) && !user.getPatient().getActive()) {
            throw new Exception("Your account is not active");
        }

        UserResponse userResponse = mapUserToUserResponse(user);

        String token = _tokenProvider.generateToken(user);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUser(userResponse);
        loginResponse.setToken(token);

        return loginResponse;
    }

//    @Override
//    public LoginResponse setNewPasswordOnFirstLoginMedical(UUID id, FirstLoginPasswordRequest request) throws Exception {
//        if (!request.getPassword().equals(request.getRePassword())) {
//            throw new Exception("Lozinke koje ste uneli se ne podudaraju!");
//        }
//
//        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(id);
//        User user = medicalStaff.getUser();
//
//        user.setPassword(_passwordEncoder.encode(request.getPassword()));
//        user.setFirstTimeLoggedIn(new Date());
//
//        _medicalStaffRepository.save(medicalStaff);
//
//
//        UserResponse userResponse = mapUserToUserResponse(user);
//
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setUser(userResponse);
//
//        return loginResponse;
//    }
//
//    @Override
//    public LoginResponse setNewPasswordOnFirstLoginAdmin(UUID id, FirstLoginPasswordRequest request) throws Exception {
//        if (!request.getPassword().equals(request.getRePassword())) {
//            throw new Exception("Lozinke koje ste uneli se ne podudaraju!");
//        }
//
//        Admin admin = _adminRepository.findOneById(id);
//        User user = admin.getUser();
//
//        user.setPassword(_passwordEncoder.encode(request.getPassword()));
//        user.setFirstTimeLoggedIn(new Date());
//
//        _adminRepository.save(admin);
//
//
//        UserResponse userResponse = mapUserToUserResponse(user);
//
//        LoginResponse loginResponse = new LoginResponse();
//        loginResponse.setUser(userResponse);
//
//        return loginResponse;
//    }
//
//    @Override
//    public void updatePasswordMedicalStaff(UUID id, UpdatePasswordRequest request) throws Exception {
//        if (!request.getPassword().equals(request.getRePassword())) {
//            throw new Exception("Lozinke koje ste uneli se ne podudaraju!");
//        }
//
//        MedicalStaff medicalStaff = _medicalStaffRepository.findOneById(id);
//
//        User user = medicalStaff.getUser();
//
//        user.setPassword(_passwordEncoder.encode(request.getPassword()));
//
//        _userRepository.save(user);
//    }
//
//    @Override
//    public void updatePasswordAdmin(UUID id, UpdatePasswordRequest request) throws Exception {
//        if (!request.getPassword().equals(request.getRePassword())) {
//            throw new Exception("Lozinke koje ste uneli se ne podudaraju!");
//        }
//
//        Admin admin = _adminRepository.findOneById(id);
//
//        User user = admin.getUser();
//
//        user.setPassword(_passwordEncoder.encode(request.getPassword()));
//
//        _userRepository.save(user);
//    }
//
    private UserResponse mapUserToUserResponse(User user) throws Exception {
        UserResponse userResponse = new UserResponse();
        userResponse.setEmail(user.getEmail());
        UUID id = null;
        UUID clinicId = null;
        if (user.getUserType().equals(UserType.PATIENT)) {
            id = user.getPatient().getId();
        } else if (user.getUserType().equals(UserType.MEDICAL)) {
             id = user.getMedicalStaff().getId();
             clinicId = user.getMedicalStaff().getClinic().getId();
        } else if (user.getUserType().equals(UserType.ADMIN)) {
            id = user.getAdmin().getId();
            clinicId = user.getAdmin().getClinic().getId();
            userResponse.setAdminType(user.getAdmin().getAdminType());
        }
        if(user.getUserType().equals(UserType.ADMIN) || user.getUserType().equals(UserType.MEDICAL)) {
//            ClinicResponse clinicResponse = _clinicService.getClinic(clinicId);
//            userResponse.setMyClinic(clinicResponse);
        }
        userResponse.setUserId(user.getId());
        userResponse.setId(id);
        userResponse.setAddress(user.getAddress());
        userResponse.setCity(user.getCity());
        userResponse.setCountry(user.getCountry());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setPhone(user.getPhone());
        userResponse.setSsn(user.getSsn());
        userResponse.setUserType(user.getUserType());

        // only on login
        userResponse.setSetNewPassword(user.getFirstTimeLoggedIn() == null);

        return userResponse;
    }
}
