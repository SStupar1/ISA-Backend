package com.example.demo.config;

import com.example.demo.dto.request.CreateUserRequest;
import com.example.demo.dto.response.UserResponse;
import com.example.demo.entity.Admin;
import com.example.demo.entity.MedicalStaff;
import com.example.demo.entity.Patient;
import com.example.demo.entity.User;
import com.example.demo.repository.IAdminRepository;
import com.example.demo.repository.IMedicalStaffRepository;
import com.example.demo.repository.IPatientRepository;
import com.example.demo.service.IUserService;
import com.example.demo.utils.enums.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class DataLoader implements ApplicationRunner {

    private List<UUID> ids = new ArrayList();

    @Autowired
    private IAdminRepository adminRepository;

    @Autowired
    private IPatientRepository patientRepository;

    @Autowired
    private IMedicalStaffRepository medicalStaffRepository;

    @Autowired
    private IUserService userService;

    private void setupIds() {
        ids.add(UUID.fromString("917a4080-375a-4da3-955f-5dfa68ed1dcb"));
        ids.add(UUID.fromString("2e1cf001-1778-4791-a95b-4928eb692729"));
        ids.add(UUID.fromString("933c01e6-2c79-401d-8ffd-125f20e3cfff"));
        ids.add(UUID.fromString("24d7671e-c8aa-47dc-8cc6-a50d3c875da1"));
        ids.add(UUID.fromString("11a66abc-f934-4d45-aaec-2060e802a431"));
        ids.add(UUID.fromString("da125e3c-0a23-479f-a270-d8c4ec283d64"));
        ids.add(UUID.fromString("df736dc5-662a-4bae-a6cf-3e85f1eeabe2"));
        ids.add(UUID.fromString("f66a7d1a-c3bc-484e-a93c-3dbad8da713a"));
        ids.add(UUID.fromString("887b6506-6998-4db7-84b9-a9a5f24fb430"));
        ids.add(UUID.fromString("e06fcaca-2ec9-4791-890d-db74a83d6102"));
        ids.add(UUID.fromString("34b105b1-0e15-4284-8489-c695085d6e96"));
        ids.add(UUID.fromString("ef639bfd-3f28-42e9-8cd4-049623ca6838"));
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        setupIds();
        for (int i = 0; i < 13; i++) {
            try {
                Admin admin = null;
                Patient patient = null;
                MedicalStaff medicalStaff = null;
                CreateUserRequest request = new CreateUserRequest();
                if (i <= 3) {
                    admin = adminRepository.findOneById(ids.get(i));
                    request.setUserType(UserType.ADMIN);
                } else if (i > 3 && i <= 7) {
                    patient = patientRepository.findOneById(ids.get(i));
                    request.setUserType(UserType.PATIENT);
                } else {
                    medicalStaff = medicalStaffRepository.findOneById(ids.get(i));
                    request.setUserType(UserType.MEDICAL);
                }
                request.setEmail(String.format("user%s@test.com", i));
                request.setSsn(String.format("123123132%s", i));
                request.setAddress(String.format("adresa %s", i));
                request.setCity(String.format("grad %s", i));
                request.setCountry(String.format("drzava %s", i));
                request.setFirstName(String.format("Ime %s", i));
                request.setLastName(String.format("Prezime %s", i));
                request.setPhone(String.format("telefon %s", i));
                request.setPassword(String.format("pass%s", i));
                request.setRePassword(String.format("pass%s", i));
                UserResponse userResponse = userService.createUser(request);
                userResponse.setDeleted(false);
                User user = new User();
                user.setId(userResponse.getId());
                if (i <= 3) {
                    admin.setUser(user);
                    adminRepository.save(admin);
                }else if (i > 3 && i <= 7) {
                    patient.setUser(user);
                    patientRepository.save(patient);
                } else {
                    medicalStaff.setUser(user);
                    medicalStaffRepository.save(medicalStaff);
                }
            } catch (Exception ex) {
                // nothing
            }
        }
    }
}