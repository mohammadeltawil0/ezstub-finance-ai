package ezstub_backend.service.impl;

import ezstub_backend.dto.WorkScheduleDTO;
import ezstub_backend.mapper.WorkDayMapper;
import ezstub_backend.model.User;
import ezstub_backend.model.WorkDay;
import ezstub_backend.repository.UserRepository;
import ezstub_backend.repository.WorkDayRepository;
import ezstub_backend.service.WorkDayService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkScheduleServiceImpl implements WorkDayService {

    private final WorkDayRepository workScheduleRepository;
    private final UserRepository userRepository;

    public WorkScheduleServiceImpl(
            WorkDayRepository workScheduleRepository,
            UserRepository userRepository
    ) {
        this.workScheduleRepository = workScheduleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public WorkScheduleDTO createWorkSchedule(WorkScheduleDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        WorkDay workSchedule =
                WorkDayMapper.toEntity(dto, user);

        WorkDay saved =
                workScheduleRepository.save(workSchedule);

        return WorkDayMapper.toDTO(saved);
    }

    @Override
    public List<WorkScheduleDTO> getByUserId(Long userId) {

        return workScheduleRepository.findByUserId(userId)
                .stream()
                .map(WorkDayMapper::toDTO)
                .toList();
    }

    @Override
    public WorkScheduleDTO getById(Long id) {

        WorkDay workSchedule = workScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Work schedule not found"));

        return WorkDayMapper.toDTO(workSchedule);
    }

    @Override
    public WorkScheduleDTO updateWorkSchedule(Long id, WorkScheduleDTO dto) {

        WorkDay workSchedule = workScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Work schedule not found"));

        workSchedule.setWorkDate(dto.getWorkDate());

        workSchedule.setStartTime(
                dto.getStartTime()
        );

        workSchedule.setEndTime(
                dto.getEndTime()
        );

        workSchedule.setUnpaidBreakMinutes(
                dto.getUnpaidBreakMinutes()
        );

        workSchedule.setHourlyRate(
                dto.getHourlyRate()
        );

        workSchedule.setExpectedGrossPay(
                dto.getExpectedGrossPay()
        );

        workSchedule.setType(
                dto.getType()
        );

        workSchedule.setNotes(
                dto.getNotes()
        );

        workSchedule.setEmployer(
                dto.getEmployer()
        );

        WorkDay updated = workScheduleRepository.save(workSchedule);

        return WorkDayMapper.toDTO(updated);
    }

    @Override
    public void deleteWorkSchedule(Long id) {

        workScheduleRepository.deleteById(id);
    }
}