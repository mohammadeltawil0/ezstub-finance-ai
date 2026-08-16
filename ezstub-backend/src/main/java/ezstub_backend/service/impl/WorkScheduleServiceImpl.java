package ezstub_backend.service.impl;

import ezstub_backend.payload.WorkScheduleDTO;
import ezstub_backend.model.User;
import ezstub_backend.model.WorkDay;
import ezstub_backend.repository.UserRepository;
import ezstub_backend.repository.WorkDayRepository;
import ezstub_backend.service.WorkDayService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkScheduleServiceImpl implements WorkDayService {

    @Autowired
    private WorkDayRepository workDayRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WorkScheduleDTO createWorkSchedule(WorkScheduleDTO dto) {
        return null;
    }

    @Override
    public List<WorkScheduleDTO> getByUserId(Long userId) {
        return List.of();
    }

    @Override
    public WorkScheduleDTO getById(Long id) {
        return null;
    }

    @Override
    public WorkScheduleDTO updateWorkSchedule(Long id, WorkScheduleDTO dto) {
        return null;
    }

    @Override
    public void deleteWorkSchedule(Long id) {

    }
}