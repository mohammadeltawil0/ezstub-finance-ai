package ezstub_backend.service.impl;

import ezstub_backend.exception.ResourceNotFoundException;
import ezstub_backend.model.User;
import ezstub_backend.model.WorkDay;
import ezstub_backend.payload.modelDTOs.WorkDayDTO;
import ezstub_backend.repository.PayPeriodRepository;
import ezstub_backend.repository.UserRepository;
import ezstub_backend.repository.WorkDayRepository;
import ezstub_backend.service.WorkDayService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.time.Duration;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class WorkDayServiceImpl implements WorkDayService {

    @Autowired
    private WorkDayRepository workDayRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PayPeriodRepository payPeriodRepository;

    @Override
    public WorkDayDTO createWorkDay(WorkDayDTO dto, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "userId", userId));

        WorkDay workDay = modelMapper.map(dto, WorkDay.class);
        workDay.setUser(user);
        workDay.setExpectedGrossPay(calculateExpectedGross(dto));

        // Bind to pay period
        if (workDay.getPunchIn() != null) {
            LocalDate shiftDate = workDay.getWorkDate();
            payPeriodRepository.findByUser_UserIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(userId, shiftDate, shiftDate)
                    .ifPresent(workDay::setPayPeriod);
            // If not found, leave it null or throw an exception if payperiods must exist first
        }

        WorkDay saved = workDayRepository.save(workDay);
        WorkDayDTO response = modelMapper.map(saved, WorkDayDTO.class);
        response.setUserId(userId);

        if (saved.getPayPeriod() != null) {
            response.setPayPeriodId(saved.getPayPeriod().getPayPeriodId());
        }
        return response;

    }

    @Override
    public List<WorkDayDTO> getByUserId(Long userId) {
        return workDayRepository
                .findByUser_UserId(userId)
                .stream()
                .map(workDay -> {
                    WorkDayDTO dto = modelMapper.map(workDay, WorkDayDTO.class);
                    dto.setUserId(userId);
                    return dto;
                }).toList();
    }

    @Override
    public WorkDayDTO getById(Long id) {
        WorkDay workDay = workDayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkDay", "workDayId", id));
        WorkDayDTO dto = modelMapper.map(workDay, WorkDayDTO.class);
        if (workDay.getUser() != null) {
            dto.setUserId(workDay.getUser().getUserId());
        }
        return dto;
    }

    @Override
    public WorkDayDTO updateWorkDay(Long id, WorkDayDTO dto) {
        WorkDay existing = workDayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkDay", "workDayId", id));
        existing.setPunchIn(dto.getPunchIn());
        existing.setPunchOut(dto.getPunchOut());
        existing.setUnpaidBreakMinutes(dto.getUnpaidBreakMinutes());
        existing.setHourlyRate(dto.getHourlyRate());
        existing.setExpectedGrossPay(calculateExpectedGross(dto));
        existing.setScheduleType(dto.getScheduleType());
        existing.setEmployer(dto.getEmployer());

        WorkDay saved = workDayRepository.save(existing);
        WorkDayDTO response = modelMapper.map(saved, WorkDayDTO.class);
        response.setWorkDayId(saved.getWorkDayId());
        if (saved.getUser() != null) {
            response.setUserId(saved.getUser().getUserId());
        }
        return response;
    }

    @Override
    public void deleteWorkDay(Long id) {
        WorkDay workDay = workDayRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkDay", "workDay", id));
        workDayRepository.delete(workDay);
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