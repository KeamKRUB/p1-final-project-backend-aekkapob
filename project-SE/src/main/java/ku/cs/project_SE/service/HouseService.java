package ku.cs.project_SE.service;

import ku.cs.project_SE.dto.FilterDTO;
import ku.cs.project_SE.dto.PageResponseDto;
import ku.cs.project_SE.dto.house.HouseDetailDto;
import ku.cs.project_SE.dto.house.HouseSearchFilterRequestDto;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.factory.house_filter.HouseFilterFactory;
import ku.cs.project_SE.mapper.HouseMapper;
import ku.cs.project_SE.model.filter.specification.FilterSpecification;
import ku.cs.project_SE.repository.HouseRepository;
import org.json.JSONObject;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class HouseService {

    private final HouseRepository houseRepository;
    private final HouseMapper houseMapper;
    private final SpecificationComposer specificationComposer;
    private final HouseFilterFactory houseFilterFactory;

    public HouseService(HouseRepository houseRepository, HouseMapper houseMapper, SpecificationComposer specificationComposer, HouseFilterFactory houseFilterFactory) {
        this.houseRepository = houseRepository;
        this.houseMapper = houseMapper;
        this.specificationComposer = specificationComposer;
        this.houseFilterFactory = houseFilterFactory;
    }

    public PageResponseDto<HouseDetailDto> searchHouses(String query, int page, int pageSize) {
        return searchHouses(query, page, pageSize, null);
    }

    public PageResponseDto<HouseDetailDto> searchHouses(String query, int page, int pageSize, HouseSearchFilterRequestDto filterRequest) {
        return searchHouses(query, page, pageSize, filterRequest, null);
    }

    public PageResponseDto<HouseDetailDto> searchHouses(String query, int page, int pageSize, HouseSearchFilterRequestDto filterRequest, User user) {
        int pageIndex = Math.max(0, page - 1);
        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.by(Sort.Direction.DESC, "houseId"));

        // Compose specifications from query and filters with user validation
        Specification<House> specification = specificationComposer.composeSearchAndFilters(query, filterRequest, user);

        Page<House> resultPage;
        if (specification == null) {
            // No filters or query - return all
            resultPage = houseRepository.findAll(pageable);
        } else {
            // Apply combined specification
            resultPage = houseRepository.findAll(specification, pageable);
        }

        List<HouseDetailDto> items = resultPage.getContent()
                .stream()
                .map(houseMapper::toDetail)
                .toList();

        return new PageResponseDto<>(
                items,
                page,
                pageSize,
                resultPage.getTotalElements(),
                resultPage.getTotalPages()
        );
    }

    public HouseDetailDto getHouseById(Long id, UUID userId) {
        House house = houseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("House not found: id=" + id));
        return houseMapper.toDetail(house,userId);
    }

    public House getHouseEntityById(Long id) {
        return houseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("House not found: id=" + id));
    }

    public FilterDTO getHouseFilterSpecifications(User user){
        List<FilterSpecification> filterSpecificationList = houseFilterFactory.getHouseFilterSpecifications(user);

        List<Map<String, Object>> filters = new ArrayList<>();
        for (FilterSpecification filterSpecification : filterSpecificationList) {
            JSONObject jsonObject = filterSpecification.getFilterSpecificationJSON();
            jsonObject.put("filterName",filterSpecification.getFilterSpecificationName());
            jsonObject.put("filterType",filterSpecification.getFilterSpecificationTypeName());
            filters.add(jsonObject.toMap());
        }

        return new FilterDTO(filters.size(), filters);
    }

    public House findHouseById(Long id) {
        return houseRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("House not found: id=" + id));
    }



}
