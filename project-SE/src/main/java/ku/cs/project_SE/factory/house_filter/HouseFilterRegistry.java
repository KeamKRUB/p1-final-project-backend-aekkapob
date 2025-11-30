package ku.cs.project_SE.factory.house_filter;

import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.user.Role;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.model.filter.action.house.*;
import ku.cs.project_SE.model.filter.specification.*;
import ku.cs.project_SE.repository.HouseRepository;
import ku.cs.project_SE.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HouseFilterRegistry {
    private final List<FilterRegistryEntry<House, ?>> filterEntries;
    private final Map<String, FilterRegistryEntry<House, ?>> filterMap;
    private final HouseRepository houseRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public HouseFilterRegistry(HouseRepository houseRepository, ProjectRepository projectRepository) {
        this.houseRepository = houseRepository;
        this.projectRepository = projectRepository;
        this.filterEntries = new ArrayList<>();
        this.filterMap = new HashMap<>();
        initializeFilters();
    }

    private void initializeFilters() {
        // Fetch dynamic values from database
        List<String> areas = houseRepository.findDistinctAreas();
        List<String> houseTypes = houseRepository.findDistinctHouseTypes();
        List<String> provinces = houseRepository.findDistinctProvinces();
        List<String> projects = projectRepository.findDistinctProjectName();

        addFilter(
                "ประเภทบ้าน (รีโนเวท)",
                new DropdownFilterSpecification("ประเภทบ้าน (รีโนเวท)", List.of("ไม่ต้องรีโนเวท","รีโนเวท"),"ไม่ต้องรีโนเวท"),
                new HouseBigTypeFilter(),
                DropdownFilterDTO.class
        );


        addFilter(
                "โครงการ",
                new DropdownFilterSpecification("โครงการ", projects),
                new ProjectFilter(),
                DropdownFilterDTO.class
        );

        addFilter(
                "พื้นที่",
                new MultiSelectionFilterSpecification("พื้นที่", areas, List.of()),
                new AreaFilter(),
                MultiSelectionDTO.class
        );

        addFilter(
                "ประเภทบ้าน",
                new SingleSelectionFilterSpecification("ประเภทบ้าน", houseTypes),
                new HouseTypeFilter(),
                SingleSelectionFilterDTO.class
        );

        addFilter(
                "ความใหม่",
                new DropdownFilterSpecification("ความใหม่", List.of("มือหนึ่ง", "มือสอง")),
                new OldNewFilter(),
                DropdownFilterDTO.class
        );

        addFilter(
                "เรียงตาม",
                new SingleSelectionFilterSpecification("เรียงตาม", List.of("ใหม่สุดก่อน", "เก่าสุดก่อน")),
                new SortByFilter(),
                SingleSelectionFilterDTO.class
        );

        addFilter(
                "ห้องนอน",
                new DropdownFilterSpecification("ห้องนอน", List.of("1", "2", "3", "4", "5", "6+")),
                new BedRoomFilter(),
                DropdownFilterDTO.class
        );

        addFilter(
                "ห้องน้ำ",
                new DropdownFilterSpecification("ห้องน้ำ", List.of("1", "2", "3", "4", "5", "6+")),
                new BathroomFilter(),
                DropdownFilterDTO.class
        );

        addFilter(
                "ที่จอดรถ",
                new DropdownFilterSpecification("ที่จอดรถ", List.of("1", "2", "3", "4+")),
                new GarageSpacesFilter(),
                DropdownFilterDTO.class
        );

        addFilter(
                "ขนาดพื้นที่",
                new IntegerNumberRangeSpecification("ขนาดพื้นที่", 0, 10000, 0, 10000, "พื้นที่ต่ำสุด", "พื้นที่สูงสุด", "ตร.ม."),
                new AreaSizeFilter(),
                IntegerNumberRangeDTO.class
        );

        addFilter(
                "ช่วงราคา",
                new IntegerNumberRangeSpecification("ช่วงราคา", 0, 100000000, 0, 100000000, "ราคาต่ำสุด", "ราคาสูงสุด", "บาท"),
                new PriceRangeFilter(),
                IntegerNumberRangeDTO.class
        );

        addFilter(
                "จังหวัด",
                new MultiSelectionFilterSpecification("จังหวัด", provinces, List.of()),
                new ProvinceFilter(),
                MultiSelectionDTO.class
        );
    }

    public <D extends SpecificationDataDTO> void addFilter(
            String filterName,
            FilterSpecification filterSpecification,
            ku.cs.project_SE.model.filter.action.EntityFilter<House, D> entityFilter,
            Class<D> dtoClass
    ) {
        FilterRegistryEntry<House, D> entry = new FilterRegistryEntry<>(
                filterName,
                filterSpecification,
                entityFilter,
                dtoClass
        );
        filterEntries.add(entry);
        filterMap.put(filterName, entry);
    }

    public <D extends SpecificationDataDTO> void addFilterAtBeginning(
            String filterName,
            FilterSpecification filterSpecification,
            ku.cs.project_SE.model.filter.action.EntityFilter<House, D> entityFilter,
            Class<D> dtoClass
    ){
        FilterRegistryEntry<House, D> entry = new FilterRegistryEntry<>(
                filterName,
                filterSpecification,
                entityFilter,
                dtoClass
        );
        filterEntries.addFirst(entry);
        filterMap.put(filterName, entry);
    }

    public List<FilterSpecification> getAllFilterSpecifications() {
        return filterEntries.stream()
                .map(FilterRegistryEntry::getFilterSpecification)
                .toList();
    }

    public List<FilterSpecification> getFilters(User user) {
        // Create empty list
        List<FilterSpecification> resultFilters = new ArrayList<>();

        // Determine which filters to include based on user role
        boolean isBusinessUser = user != null &&
            (user.getRole() == Role.ADMIN || user.getRole() == Role.INVESTOR);

        // Filter from the existing filterMap based on user role
        for (FilterRegistryEntry<House, ?> entry : filterEntries) {
            // Skip renovatable filter for non-business users
            if (!isBusinessUser && entry.getFilterName().equals("ประเภทบ้าน (รีโนเวท)")) {
                continue;
            }

            // Add filter to result list
            resultFilters.add(entry.getFilterSpecification());
        }

        return resultFilters;
    }

    public FilterRegistryEntry<House, ?> getFilterEntry(String filterName) {
        return filterMap.get(filterName);
    }

}
