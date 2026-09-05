package ezstub_backend.service.impl;

import ezstub_backend.payload.WorkDayDTO;
import ezstub_backend.repository.UserRepository;
import ezstub_backend.repository.WorkDayRepository;
import ezstub_backend.service.WorkDayService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.time.Duration;

import java.math.BigDecimal;
import java.util.List;

@Service
public class WorkDayServiceImpl implements WorkDayService {

    @Autowired
    private WorkDayRepository workDayRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public WorkDayDTO createWorkSchedule(WorkDayDTO dto) {
        return null;
    }

    @Override
    public List<WorkDayDTO> getByUserId(Long userId) {
        return List.of();
    }

    @Override
    public WorkDayDTO getById(Long id) {
        return null;
    }

    @Override
    public WorkDayDTO updateWorkSchedule(Long id, WorkDayDTO dto) {
        return null;
    }

    @Override
    public void deleteWorkSchedule(Long id) {

    }

    @Override
    public BigDecimal calculateExpectedGross(WorkDayDTO dto) {
        if (dto.getPunchIn() == null || dto.getPunchOut() == null || dto.getHourlyRate() == null) {
            return dto.getExpectedGrossPay();
        }

        long minutes = Duration.between(dto.getPunchIn(), dto.getPunchOut()).toMinutes();
        int breakMinutes = dto.getUnpaidBreakMinutes() == null ? 0 : dto.getUnpaidBreakMinutes();

        BigDecimal hours = BigDecimal.valueOf(Math.max(0, minutes - breakMinutes))
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

        BigDecimal expectedGross = hours.multiply(dto.getHourlyRate());
        return expectedGross;
    }
}