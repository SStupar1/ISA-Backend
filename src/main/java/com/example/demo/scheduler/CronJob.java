package com.example.demo.scheduler;

import com.example.demo.dto.request.SuggestErRequest;
import com.example.demo.entity.AppointmentRequest;
import com.example.demo.repository.IAppointmentRequestRepository;
import com.example.demo.service.IAppointmentService;
import com.example.demo.util.enums.RequestStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CronJob {
    private final IAppointmentRequestRepository _repo;

    private final IAppointmentService _service;

    public CronJob(IAppointmentRequestRepository repo, IAppointmentService service) {
        _repo = repo;
        _service = service;
    }

    @Scheduled(cron="0 0 0 * * ?")
    public void cronJobSch() {
        List<AppointmentRequest> result = _repo.findAllByStatus(RequestStatus.PENDING);
        System.out.println("Java cron job expression:: " + result);
        result.forEach(x -> {
            SuggestErRequest request = new SuggestErRequest();
            request.setId(x.getId());
            _service.suggestEr(request);
        });
    }
}
