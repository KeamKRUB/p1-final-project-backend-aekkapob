## 1. Basic Tests (No Filters)

### Test 1: Get all houses without filters
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 6
  }'
```

### Test 2: Text search only
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "query": "luxury",
    "page": 1,
    "pageSize": 6
  }'
```

---

## 2. Single Filter Tests

### Test 3: Filter by bedroom count (3 bedrooms)
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 6,
    "filterRequest": {
      "filters": [
        {
          "filterName": "ห้องนอน",
          "filterData": {
            "selectedChoice": "3"
          }
        }
      ]
    }
  }'
```

### Test 4: Filter by area (เกษตร)
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 6,
    "filterRequest": {
      "filters": [
        {
          "filterName": "พื้นที่",
          "filterData": {
            "selectedChoices": [
              {"value": "เกษตร"}
            ]
          }
        }
      ]
    }
  }'
```

### Test 5: Filter by house type (บ้านเดี่ยว)
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 6,
    "filterRequest": {
      "filters": [
        {
          "filterName": "ประเภทบ้าน",
          "filterData": {
            "selectedChoices": "บ้านเดี่ยว"
          }
        }
      ]
    }
  }'
```

### Test 6: Filter by new/used (มือหนึ่ง - new)
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 6,
    "filterRequest": {
      "filters": [
        {
          "filterName": "ความใหม่",
          "filterData": {
            "selectedChoice": "มือหนึ่ง"
          }
        }
      ]
    }
  }'
```

### Test 7: Filter by new/used (มือสอง - used)
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 6,
    "filterRequest": {
      "filters": [
        {
          "filterName": "ความใหม่",
          "filterData": {
            "selectedChoice": "มือสอง"
          }
        }
      ]
    }
  }'
```

### Test 8: Filter by area size (minimum 100 sqm)
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 6,
    "filterRequest": {
      "filters": [
        {
          "filterName": "ขนาดพื้นที่",
          "filterData": {
            "value": 100
          }
        }
      ]
    }
  }'
```

---

## 3. Multiple Filters (AND Logic)

### Test 9: Area + Bedrooms
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 6,
    "filterRequest": {
      "filters": [
        {
          "filterName": "พื้นที่",
          "filterData": {
            "selectedChoices": [
              {"value": "เกษตร"}
            ]
          }
        },
        {
          "filterName": "ห้องนอน",
          "filterData": {
            "selectedChoice": "3"
          }
        }
      ]
    }
  }'
```

### Test 10: House type + New/Used + Bedrooms
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 6,
    "filterRequest": {
      "filters": [
        {
          "filterName": "ประเภทบ้าน",
          "filterData": {
            "selectedChoices": "บ้านเดี่ยว"
          }
        },
        {
          "filterName": "ความใหม่",
          "filterData": {
            "selectedChoice": "มือหนึ่ง"
          }
        },
        {
          "filterName": "ห้องนอน",
          "filterData": {
            "selectedChoice": "4"
          }
        }
      ]
    }
  }'
```

### Test 11: All filters combined
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 10,
    "filterRequest": {
      "filters": [
        {
          "filterName": "พื้นที่",
          "filterData": {
            "selectedChoices": [
              {"value": "เกษตร"}
            ]
          }
        },
        {
          "filterName": "ประเภทบ้าน",
          "filterData": {
            "selectedChoices": "บ้านเดี่ยว"
          }
        },
        {
          "filterName": "ความใหม่",
          "filterData": {
            "selectedChoice": "มือหนึ่ง"
          }
        },
        {
          "filterName": "ห้องนอน",
          "filterData": {
            "selectedChoice": "3"
          }
        },
        {
          "filterName": "ขนาดพื้นที่",
          "filterData": {
            "value": 100
          }
        }
      ]
    }
  }'
```

---

## 4. Text Search + Filters (AND Logic)

### Test 12: Text search + single filter
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "query": "luxury",
    "page": 1,
    "pageSize": 6,
    "filterRequest": {
      "filters": [
        {
          "filterName": "ห้องนอน",
          "filterData": {
            "selectedChoice": "3"
          }
        }
      ]
    }
  }'
```

### Test 13: Text search + multiple filters
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "query": "modern",
    "page": 1,
    "pageSize": 6,
    "filterRequest": {
      "filters": [
        {
          "filterName": "พื้นที่",
          "filterData": {
            "selectedChoices": [
              {"value": "เกษตร"}
            ]
          }
        },
        {
          "filterName": "ห้องนอน",
          "filterData": {
            "selectedChoice": "4"
          }
        }
      ]
    }
  }'
```

---

## 5. Multi-selection Area Filter

### Test 14: Multiple areas selected (เกษตร OR พหลโยธิน)
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 6,
    "filterRequest": {
      "filters": [
        {
          "filterName": "พื้นที่",
          "filterData": {
            "selectedChoices": [
              {"value": "เกษตร"},
              {"value": "พหลโยธิน"}
            ]
          }
        }
      ]
    }
  }'
```

---

## 6. Edge Cases & Special Tests

### Test 15: 6+ bedrooms
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 6,
    "filterRequest": {
      "filters": [
        {
          "filterName": "ห้องนอน",
          "filterData": {
            "selectedChoice": "6+"
          }
        }
      ]
    }
  }'
```

### Test 16: All bedrooms (ทั้งหมด)
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 6,
    "filterRequest": {
      "filters": [
        {
          "filterName": "ห้องนอน",
          "filterData": {
            "selectedChoice": "ทั้งหมด"
          }
        }
      ]
    }
  }'
```

### Test 17: Empty filters array
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 6,
    "filterRequest": {
      "filters": []
    }
  }'
```

### Test 18: Large page size
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 50,
    "filterRequest": {
      "filters": [
        {
          "filterName": "ห้องนอน",
          "filterData": {
            "selectedChoice": "3"
          }
        }
      ]
    }
  }'
```

---

## 7. Pagination Tests

### Test 19: Page 2
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 2,
    "pageSize": 6,
    "filterRequest": {
      "filters": [
        {
          "filterName": "ห้องนอน",
          "filterData": {
            "selectedChoice": "3"
          }
        }
      ]
    }
  }'
```

### Test 20: Different page sizes
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 12
  }'
```

---

## 8. Response Format Tests with Pretty Print

### Test 21: With pretty print (using jq)
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "query": "luxury",
    "page": 1,
    "pageSize": 6,
    "filterRequest": {
      "filters": [
        {
          "filterName": "ห้องนอน",
          "filterData": {
            "selectedChoice": "3"
          }
        }
      ]
    }
  }' | jq '.'
```

### Test 22: Show only total count
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "page": 1,
    "pageSize": 6,
    "filterRequest": {
      "filters": [
        {
          "filterName": "ห้องนอน",
          "filterData": {
            "selectedChoice": "3"
          }
        }
      ]
    }
  }' | jq '.totalElements'
```

---

## 9. One-liner Tests (for quick testing)

```bash
# Quick test 1: No filters
curl -X POST http://localhost:8888/houses/search -H "Content-Type: application/json" -d '{"page":1,"pageSize":6}'

# Quick test 2: With query
curl -X POST http://localhost:8888/houses/search -H "Content-Type: application/json" -d '{"query":"luxury","page":1,"pageSize":6}'

# Quick test 3: With single filter
curl -X POST http://localhost:8888/houses/search -H "Content-Type: application/json" -d '{"page":1,"pageSize":6,"filterRequest":{"filters":[{"filterName":"ห้องนอน","filterData":{"selectedChoice":"3"}}]}}'

# Quick test 4: Query + filter
curl -X POST http://localhost:8888/houses/search -H "Content-Type: application/json" -d '{"query":"modern","page":1,"pageSize":6,"filterRequest":{"filters":[{"filterName":"ห้องนอน","filterData":{"selectedChoice":"3"}}]}}'
```

---

## 10. Comparison: GET vs POST

### GET endpoint (with filters as query param)
```bash
curl -X GET "http://localhost:8888/houses?query=luxury&page=1&pageSize=6&filters=%7B%22filters%22%3A%5B%7B%22filterName%22%3A%22%E0%B8%AB%E0%B9%89%E0%B8%AD%E0%B8%87%E0%B8%99%E0%B8%AD%E0%B8%99%22%2C%22filterData%22%3A%7B%22selectedChoice%22%3A%223%22%7D%7D%5D%7D"
```

### POST endpoint (same query, cleaner)
```bash
curl -X POST http://localhost:8888/houses/search \
  -H "Content-Type: application/json" \
  -d '{
    "query": "luxury",
    "page": 1,
    "pageSize": 6,
    "filterRequest": {
      "filters": [
        {
          "filterName": "ห้องนอน",
          "filterData": {
            "selectedChoice": "3"
          }
        }
      ]
    }
  }'
```

---

## Notes

1. **Content-Type Header**: Always include `-H "Content-Type: application/json"`
2. **Pretty Print**: Add `| jq '.'` at the end for formatted JSON output (requires jq)
3. **Save Response**: Add `-o output.json` to save response to file
4. **Verbose Mode**: Add `-v` flag to see full request/response headers
5. **Default Values**: If page/pageSize are not provided, they default to 1 and 6 respectively

## Filter Names Reference

- `พื้นที่` - Area (multi-selection)
- `ประเภทบ้าน` - House Type (single selection)
- `ความใหม่` - New/Used (dropdown: มือหนึ่ง, มือสอง)
- `ห้องนอน` - Bedrooms (dropdown: ทั้งหมด, 1, 2, 3, 4, 5, 6+)
- `ขนาดพื้นที่` - Area Size (integer range)
