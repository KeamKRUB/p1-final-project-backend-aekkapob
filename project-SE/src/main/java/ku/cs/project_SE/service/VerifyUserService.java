package ku.cs.project_SE.service;

import ku.cs.project_SE.dto.verifyUser.VerifyUserRequestDto;
import ku.cs.project_SE.dto.verifyUser.VerifyUserResponseDto;
import ku.cs.project_SE.entity.VerifyUser.VerifyUser;
import ku.cs.project_SE.entity.user.Role;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.enums.ActionType;
import ku.cs.project_SE.enums.VerifyUserStatus;
import ku.cs.project_SE.exception.InvalidRoleException;
import ku.cs.project_SE.mapper.UserMapper;
import ku.cs.project_SE.mapper.VerifyUserMapper;
import ku.cs.project_SE.repository.VerifyUserRepository;
import ku.cs.project_SE.service.notification.NotificationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VerifyUserService {
    private final VerifyUserRepository verifyUserRepository;
    private final VerifyUserMapper verifyUserMapper;
    private final UserService userService;
    private UserMapper userMapper;
    private final NotificationService notificationService;
    private final GenericLogService genericLogService;

    public VerifyUserService(VerifyUserRepository verifyUserRepository, VerifyUserMapper verifyUserMapper, UserService userService, NotificationService notificationService, GenericLogService genericLogService) {
        this.verifyUserRepository = verifyUserRepository;
        this.verifyUserMapper = verifyUserMapper;
        this.userService = userService;
        this.notificationService = notificationService;
        this.genericLogService = genericLogService;
    }

    public void createVerifyRequest(VerifyUserRequestDto verifyUserRequestDto, User user){
        if (user.getRole() == Role.INVESTOR) {
            throw new InvalidRoleException("Investors are not allowed to request verification.");
        }

        if (user.getRole() == Role.ADMIN) {
            throw new InvalidRoleException("Admin are not allowed to request verification.");
        }
        VerifyUser checkDuplicate = verifyUserRepository.findByCitizenIdAndRequestStatus(verifyUserRequestDto.citizenId(),VerifyUserStatus.APPROVED);
        if (checkDuplicate != null){
            throw new InvalidRoleException("Investors are not allowed to request verification.");
        }
        VerifyUser verifyUser = verifyUserMapper.toEntity(verifyUserRequestDto);
        verifyUser.setRequester(user);
        verifyUser.setRequestStatus(VerifyUserStatus.PENDING);
        verifyUser.setRequestDate(LocalDateTime.now());
        VerifyUser savedVerifyUser = verifyUserRepository.save(verifyUser);
        genericLogService.logAction(savedVerifyUser, ActionType.CREATED,user);
    }

    public List<VerifyUserResponseDto> getVerifyUser() {
        List<VerifyUser> verifyUsers = verifyUserRepository.findByRequestStatus(VerifyUserStatus.PENDING);
        return verifyUsers.stream()
                .map(verifyUserMapper::toDto)
                .collect(Collectors.toList());
    }

    public VerifyUserResponseDto getVerifyUserByRequestId(UUID requesterId) {
        VerifyUser verifyUsers = verifyUserRepository.findByRequestStatusAndRequestId(VerifyUserStatus.PENDING,requesterId);
        return verifyUserMapper.toDto(verifyUsers);
    }

    public void approve(UUID requestId,User user) {
        VerifyUser verifyUser = verifyUserRepository.findById(requestId).orElseThrow();
        verifyUser.setRequestStatus(VerifyUserStatus.APPROVED);
        verifyUser.setApprover(user);
        verifyUser.getRequester().setRole(Role.INVESTOR);
        VerifyUser savedVerifyUser = verifyUserRepository.save(verifyUser);
        genericLogService.logAction(savedVerifyUser, ActionType.APPROVED,user);

        notificationService.approveNotifyUserAllChannels(verifyUser.getRequester());
    }

    public void reject(UUID requestId, User user, String reason) {
        VerifyUser verifyUser = verifyUserRepository.findById(requestId).orElseThrow();
        verifyUser.setRequestStatus(VerifyUserStatus.REJECTED);
        verifyUser.setApprover(user);
        VerifyUser savedVerifyUser = verifyUserRepository.save(verifyUser);
        genericLogService.logAction(savedVerifyUser, ActionType.REJECTED,user);

        notificationService.rejectNotifyUserAllChannels(verifyUser.getRequester(),reason);
    }

    public Boolean checkIsVerifying(User user) {
        VerifyUser verifyUser = verifyUserRepository.findByRequestStatusAndRequester_UserId(VerifyUserStatus.PENDING,user.getUserId());
        return verifyUser != null;
    }
}
