package ku.cs.project_SE.controller;

import jakarta.validation.Valid;
import ku.cs.project_SE.dto.PageResponseDto;
import ku.cs.project_SE.dto.promotion.CreatePromotionDto;
import ku.cs.project_SE.dto.promotion.PromotionResponseDto;
import ku.cs.project_SE.dto.promotion.UpdatePromotionDto;
import ku.cs.project_SE.dto.promotion.action.SetActionRequest;
import ku.cs.project_SE.dto.promotion.condition.AddCompositeConditionRequest;
import ku.cs.project_SE.service.PromotionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/promotions")
public class PromotionController {

    private final PromotionService service;

    public PromotionController(PromotionService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<PromotionResponseDto>> list(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String filter,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "6") int pageSize
    ) {
        return ResponseEntity.ok(service.search(query, filter, page, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PromotionResponseDto> get(@PathVariable String id) {
        var dto = service.getById(id);
        if (dto == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/create")
    public ResponseEntity<PromotionResponseDto> create(@Valid @RequestBody CreatePromotionDto req) {
        return ResponseEntity.ok(service.create(req));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PromotionResponseDto> update(
            @PathVariable String id,
            @Valid @RequestBody UpdatePromotionDto req
    ) {
        return ResponseEntity.ok(service.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/add-conditions")
    public ResponseEntity<Long> addCondition(
            @PathVariable String id,
            @RequestBody AddCompositeConditionRequest req
    ) {
        return ResponseEntity.ok(service.addCompositeCondition(id, req));
    }

    @GetMapping("/{id}/check")
    public ResponseEntity<Boolean> check(
            @PathVariable String id,
            @RequestParam("houseId") Long houseId
    ) {
        return ResponseEntity.ok(service.check(id, houseId));
    }

    @PostMapping("/{id}/action")
    public ResponseEntity<Void> setAction(@PathVariable String id, @RequestBody SetActionRequest req) {
        service.setAction(id, req);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/get-conditions")
    public ResponseEntity<?> getConditions(@PathVariable String id) {
        return ResponseEntity.ok(service.getConditions(id));
    }

    @GetMapping("/{id}/get-action")
    public ResponseEntity<?> getAction(@PathVariable String id) {
        return ResponseEntity.ok(service.getAction(id));
    }

    @GetMapping("/validate")
    public ResponseEntity<?> validatePromotion(
            @RequestParam("houseId") Long houseId,
            @RequestParam("code") String code
    ) {
        var result = service.validatePromotion(houseId, code);
        if (result == null) return ResponseEntity.badRequest().body("Invalid promotion code");
        return ResponseEntity.ok(result);
    }
}


