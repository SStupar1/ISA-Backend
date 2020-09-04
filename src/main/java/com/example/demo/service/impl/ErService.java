package com.example.demo.service.impl;

import com.example.demo.dto.request.CreateErRequest;
import com.example.demo.dto.request.ErFilterRequest;
import com.example.demo.dto.request.UpdateErRequest;
import com.example.demo.dto.response.ErResponse;
import com.example.demo.entity.Clinic;
import com.example.demo.entity.Er;
import com.example.demo.entity.ErAppointmentPeriod;
import com.example.demo.repository.IClinicRepository;
import com.example.demo.repository.IErAppointmentPeriodRepository;
import com.example.demo.repository.IErRepository;
import com.example.demo.service.IErService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ErService implements IErService {

    private final IErRepository _erRepository;

    private final IClinicRepository _clinicRepository;

    private final IErAppointmentPeriodRepository _erAppointmentPeriodRepository;

    public ErService(IErRepository erRepository, IClinicRepository clinicRepository, IErAppointmentPeriodRepository erAppointmentPeriodRepository) {
        _erRepository = erRepository;
        _clinicRepository = clinicRepository;
        _erAppointmentPeriodRepository = erAppointmentPeriodRepository;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public List<ErResponse> getAllErs() {
        List<Er> ers = _erRepository.findAllByIsDeleted(false);

        return ers.stream().map(er -> mapErToErResponse(er)).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public List<ErResponse> getAllErsByClinic(UUID id) throws Exception {
        List<Er> ers = _erRepository.findAllByClinic_IdAndIsDeleted(id, false);

        return ers.stream().map(er -> mapErToErResponse(er)).collect(Collectors.toList());
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public ErResponse updateEr(UUID id, UpdateErRequest request) throws Exception {
        Er er = _erRepository.findOneById(id);

        if (er == null) {
            throw new Exception(String.format("Medical with %s id is not found", id));
        }

        List <ErAppointmentPeriod> erAppointmentPeriods = _erAppointmentPeriodRepository.findAll();
        for(ErAppointmentPeriod eap: erAppointmentPeriods) {
            if(eap.getEr().equals(er)) {
                return mapErToErResponse(er);
            }
        }

        er.setName(request.getName());
        er.setNumber(request.getNumber());
        Er savedEr = _erRepository.save(er);

        return mapErToErResponse(savedEr);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public ErResponse createEr(CreateErRequest request, UUID clinicId) {
        Er er = new Er();
        Clinic clinic = _clinicRepository.findOneById(clinicId);

        er.setName(request.getName());
        er.setNumber(request.getNumber());
        er.setClinic(clinic);
        er.setDeleted(false);
        Er savedEr = _erRepository.save(er);

        return mapErToErResponse(savedEr);
    }

    @Override
    public ErResponse getEr(UUID id) {
        Er er = _erRepository.findOneById(id);
        return mapErToErResponse(er);
    }

    @Override
    public void deleteEr(UUID id) {
        Er er = _erRepository.findOneById(id);
//        if(!_erAppointmentPeriodRepository.findAnyByEr(er)) {
//            er.setDeleted(true);
//            _erRepository.save(er);
//        }
        List <ErAppointmentPeriod> erAppointmentPeriods = _erAppointmentPeriodRepository.findAll();
        for(ErAppointmentPeriod eap: erAppointmentPeriods) {
            if(eap.getEr().equals(er)) {
                return;
            }
        }
        er.setDeleted(true);
        _erRepository.save(er);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Override
    public List<ErResponse> getAllErsByClinic(UUID id, ErFilterRequest request) {
        List<Er> ers = _erRepository.findAllByClinic_IdAndIsDeleted(id, false);

        return ers
                .stream()
                .filter(er -> {
                    if(er.getName() != null) {
                        return er.getName().toLowerCase().contains(request.getName().toLowerCase());
                    } else {
                        return true;
                    }
                })
                .filter(er -> {
                    String temp = er.getNumber().toString();
                    if(request.getNumber() != null) {
                        return temp.toLowerCase().contains(request.getName().toLowerCase());
                    } else {
                        return true;
                    }
                })
                .map(er -> mapErToErResponse(er))
                .collect(Collectors.toList());

    }

    private ErResponse mapErToErResponse(Er er) {
        ErResponse erResponse = new ErResponse();
        erResponse.setId(er.getId());
        erResponse.setName(er.getName());
        erResponse.setNumber(er.getNumber());

        return erResponse;
    }
}
