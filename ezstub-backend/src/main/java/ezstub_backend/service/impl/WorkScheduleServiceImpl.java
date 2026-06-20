package ezstub_backend.service.impl;

import ezstub_backend.dto.WorkScheduleDTO;
import ezstub_backend.mapper.WorkScheduleMapper;
import ezstub_backend.model.User;
import ezstub_backend.model.WorkSchedule;
import ezstub_backend.repository.UserRepository;
import ezstub_backend.repository.WorkScheduleRepository;
import ezstub_backend.service.WorkScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkScheduleServiceImpl implements WorkScheduleService {

    private final WorkScheduleRepository workScheduleRepository;
    private final UserRepository userRepository;

    public WorkScheduleServiceImpl(
            WorkScheduleRepository workScheduleRepository,
            UserRepository userRepository
    ) {
        this.workScheduleRepository = workScheduleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public WorkScheduleDTO createWorkSchedule(WorkScheduleDTO dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        WorkSchedule workSchedule =
                WorkScheduleMapper.toEntity(dto, user);

        WorkSchedule saved =
                workScheduleRepository.save(workSchedule);

        return WorkScheduleMapper.toDTO(saved);
    }

    @Override
    public List<WorkScheduleDTO> getByUserId(Long userId) {

        return workScheduleRepository.findByUserId(userId)
                .stream()
                .map(WorkScheduleMapper::toDTO)
                .toList();
    }

    @Override
    public WorkScheduleDTO getById(Long id) {

        WorkSchedule workSchedule = workScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Work schedule not found"));

        return WorkScheduleMapper.toDTO(workSchedule);
    }

    @Override
    public WorkScheduleDTO updateWorkSchedule(Long id, WorkScheduleDTO dto) {

        WorkSchedule workSchedule = workScheduleRepository.findById(id)
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

        WorkSchedule updated = workScheduleRepository.save(workSchedule);

        return WorkScheduleMapper.toDTO(updated);
    }

    @Override
    public void deleteWorkSchedule(Long id) {

        workScheduleRepository.deleteById(id);
    }
}