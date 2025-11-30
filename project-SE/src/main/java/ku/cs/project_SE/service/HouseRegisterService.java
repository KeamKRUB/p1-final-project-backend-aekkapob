package ku.cs.project_SE.service;

import ku.cs.project_SE.dto.house.HouseDetailDto;
import ku.cs.project_SE.dto.house_register.HouseRegisterRespondDto;
import ku.cs.project_SE.dto.user.UserRespondDto;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.house_register.HouseRegister;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.enums.ActionType;
import ku.cs.project_SE.enums.HouseRegisterStatus;
import ku.cs.project_SE.mapper.HouseMapper;
import ku.cs.project_SE.mapper.UserMapper;
import ku.cs.project_SE.repository.HouseRegisterRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class HouseRegisterService {

    private final HouseRegisterRepository houseRegisterRepository;
    private final UserService userService;
    private final HouseService houseService;
    private final UserMapper userMapper;
    private final HouseMapper houseMapper;
    private final GenericLogService genericLogService;

    public HouseRegisterService(HouseRegisterRepository houseRegisterRepository, UserService userService, HouseService houseService,HouseMapper houseMapper, UserMapper userMapper, GenericLogService genericLogService) {
        this.houseRegisterRepository = houseRegisterRepository;
        this.userService = userService;
        this.houseService = houseService;
        this.houseMapper = houseMapper;
        this.userMapper = userMapper;
        this.genericLogService = genericLogService;

    }

    public HouseRegister createHouseRegister(long houseId, UUID userId) {
        User user = userService.findById(userId);
        House house = houseService.findHouseById(houseId);
        HouseRegister houseRegister = HouseRegister.builder()
                .user(user)
                .house(house)
                .date(LocalDateTime.now())
                .registerStatus(HouseRegisterStatus.PENDING)
                .build();

        HouseRegister savedHouseRegister = houseRegisterRepository.save(houseRegister);
        genericLogService.logAction(savedHouseRegister, ActionType.CREATED,user);
        return savedHouseRegister;
    }

//    public HouseRegister findHouseRegisterById(HouseRegisterId id) {
//        return houseRegisterRepository.findById(id).orElse(null);
//    }

    public List<HouseRegisterRespondDto> getAllHouseRegisterDto() {
//        List<HouseRegister> houseRegisters = houseRegisterRepository.findAll();
        List<HouseRegister> houseRegisters = houseRegisterRepository.findHouseRegistersByRegisterStatus(HouseRegisterStatus.PENDING);
        return houseRegisters.stream()
                .map(hr -> getHouseRegisterDtoById(hr.getRegisterId()))
                .toList();
    }

    public HouseRegisterRespondDto getHouseRegisterDtoById(UUID registerId) {
        HouseRegister houseRegister = houseRegisterRepository.findHouseRegistersByRegisterId(registerId);
        User user = houseRegister.getUser();
        UserRespondDto userDTO = userMapper.toDto(user);
        HouseDetailDto houseDetailDto = houseMapper.toDetail(houseRegister.getHouse());
        return new HouseRegisterRespondDto(registerId,houseDetailDto,userDTO,houseRegister.getDate(),houseRegister.getRegisterStatus());
    }

    public List<HouseRegisterRespondDto> getHouseRegisterByUserId(UUID userId) {
        List<HouseRegister> houseRegisters = houseRegisterRepository.findHouseRegistersByUser_UserIdOrderByDateAsc(userId);

        User user = userService.findById(userId);
        UserRespondDto userDTO = userMapper.toDto(user);

        return houseRegisters.stream()
                .map(register -> {
                    HouseDetailDto houseDetailDto = houseMapper.toDetail(register.getHouse());
                    return new HouseRegisterRespondDto(register.getRegisterId(),houseDetailDto, userDTO,register.getDate(),register.getRegisterStatus());
                })
                .collect(Collectors.toList());
    }

    public void approveHouseRegister(UUID requestId,User user) {
        HouseRegister houseRegister = houseRegisterRepository.findHouseRegistersByRegisterId(requestId);
        System.out.println("House register approved"+houseRegister.getRegisterId());
        houseRegister.setRegisterStatus(HouseRegisterStatus.REVIEWED);
        HouseRegister savedHouseRegister = houseRegisterRepository.save(houseRegister);
        genericLogService.logAction(savedHouseRegister, ActionType.APPROVED,user);
    }

    public Boolean isUserRegisterToThisHouse(long houseId, UUID userId) {
           List<HouseRegister> houseRegister = houseRegisterRepository.findHouseRegisterByHouse_HouseIdAndUser_UserId_AndRegisterStatus(houseId, userId,HouseRegisterStatus.PENDING);
           return !houseRegister.isEmpty();
    }
}
