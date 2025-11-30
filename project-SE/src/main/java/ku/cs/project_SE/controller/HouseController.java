// keam
package ku.cs.project_SE.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ku.cs.project_SE.dto.FilterDTO;
import ku.cs.project_SE.dto.PageResponseDto;
import ku.cs.project_SE.dto.house.HouseDetailDto;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.dto.house.HouseSearchFilterRequestDto;
import ku.cs.project_SE.dto.house.HouseSearchRequestDto;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.factory.house_render_spec.HouseRenderSpecFactory;
import ku.cs.project_SE.model.filter.house_detail_renderer.RenderSpec;
import ku.cs.project_SE.service.HouseRegisterService;
import ku.cs.project_SE.service.HouseService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/houses")
public class HouseController {

    private final HouseService houseService;
    private final ObjectMapper objectMapper;
    private final HouseRegisterService  houseRegisterService;

    public HouseController(HouseService houseService, ObjectMapper objectMapper, HouseRegisterService houseRegisterService) {
        this.houseService = houseService;
        this.objectMapper = objectMapper;
        this.houseRegisterService = houseRegisterService;
    }

    @GetMapping
    public PageResponseDto<HouseDetailDto> listHouses(
            @RequestParam(required = false) String query,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int pageSize,
            @RequestParam(required = false) String filters,
            @AuthenticationPrincipal User user
    ) {
        HouseSearchFilterRequestDto filterRequest = null;

        if (filters != null && !filters.isBlank()) {
            try {
                filterRequest = objectMapper.readValue(filters, HouseSearchFilterRequestDto.class);
            } catch (JsonProcessingException e) {
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST,
                        "Invalid filters JSON format: " + e.getMessage()
                );
            }
        }

        return houseService.searchHouses(query, page, pageSize, filterRequest, user);
    }

    @GetMapping("/filter/specification")
    public FilterDTO getHouseFilterSpecification(@AuthenticationPrincipal User user){
        return houseService.getHouseFilterSpecifications(user);
    }

    @GetMapping("/{id}/v2")
    public RenderSpec getHouseSpec(@PathVariable("id") Long id, @AuthenticationPrincipal User user){
        System.out.println("ASDJNASDUINJ "+user);
        House house =  houseService.getHouseEntityById(id);
        HouseRenderSpecFactory specFactory = new HouseRenderSpecFactory(houseRegisterService);
        return specFactory.getRenderSpec(house,user);
    }

    @PostMapping("/search")
    public PageResponseDto<HouseDetailDto> searchHouses(@RequestBody HouseSearchRequestDto searchRequest, @AuthenticationPrincipal User user) {
        Integer page = searchRequest.getPage() != null ? searchRequest.getPage() : 1;
        Integer pageSize = searchRequest.getPageSize() != null ? searchRequest.getPageSize() : 6;

        return houseService.searchHouses(
                searchRequest.getQuery(),
                page,
                pageSize,
                searchRequest.getFilterRequest(),
                user
        );
    }

    @GetMapping("/{id}")
    public HouseDetailDto getHouse(@PathVariable("id") Long id,@AuthenticationPrincipal User user) {
        UUID userId = (user != null) ? user.getUserId() : null;
        return houseService.getHouseById(id,userId);
    }
}
