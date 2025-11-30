# Render Specification Frontend Integration Guide

## Table of Contents
1. [Overview](#overview)
2. [Architecture](#architecture)
3. [API Endpoint](#api-endpoint)
4. [JSON Structure](#json-structure)
5. [Component Types Reference](#component-types-reference)
6. [TypeScript Type Definitions](#typescript-type-definitions)
7. [Implementation Guide](#implementation-guide)
8. [Real-world Examples](#real-world-examples)
9. [Best Practices](#best-practices)

---

## Overview

The Render Specification system is a **backend-driven UI rendering architecture** that allows the backend to control how house details are displayed on the frontend. Instead of the frontend hard-coding the layout, the backend sends a structured JSON specification that describes the UI components and their hierarchy.

### Benefits
- **Flexibility**: Change UI layouts without frontend code changes
- **Consistency**: Backend ensures consistent data presentation
- **Dynamic Content**: Different house types can have different layouts
- **Reusability**: Common UI patterns defined once, reused everywhere

---

## Architecture

The system follows a **Composite Design Pattern**:

```
RenderSpec (interface)
├── LeafRenderSpec (atomic components - no children)
│   ├── TextRenderSpec
│   ├── TitleRenderSpec
│   ├── PriceRenderSpec
│   ├── BadgeRenderSpec
│   ├── KeyValueRenderSpec
│   ├── ImageRenderSpec
│   ├── MapRenderSpec
│   ├── IconTextRenderSpec
│   ├── ListItemRenderSpec
│   └── DividerRenderSpec
│
└── CompositeRenderSpec (container components - has children)
    ├── PageRenderSpec
    ├── SectionRenderSpec
    ├── CardRenderSpec
    ├── RowRenderSpec
    ├── ColumnRenderSpec
    ├── GridRenderSpec
    ├── ImageGalleryRenderSpec
    └── TabRenderSpec
```

---

## API Endpoint

### Fetch House Render Specification

**Endpoint:** `GET /houses/{id}/v2`

**Response:** Returns a `RenderSpec` JSON object

**Example Request:**
```bash
curl -X GET http://localhost:8080/houses/123/v2
```

**Response Structure:**
```json
{
  "type": "tab",
  "isComposite": true,
  "value": [
    { "type": "page", "isComposite": true, "value": [...] },
    { "type": "page", "isComposite": true, "value": [...] }
  ]
}
```

---

## JSON Structure

Every render spec object has three core fields:

```typescript
{
  type: string;        // Component type identifier
  isComposite: boolean; // true = has children, false = leaf node
  value: any;          // Content (varies by type)
}
```

### Leaf Components (isComposite: false)
- `value` contains the actual data (string, number, object)

### Composite Components (isComposite: true)
- `value` contains an **array of child RenderSpec objects**

---

## Component Types Reference

### Leaf Components (Atomic/Simple)

#### 1. **text** - Plain Text Display
```json
{
  "type": "text",
  "isComposite": false,
  "value": "This is a description of the house."
}
```
- **Value Type:** `string`
- **Use Case:** Paragraphs, descriptions, captions

---

#### 2. **title** - Heading/Title
```json
{
  "type": "title",
  "isComposite": false,
  "value": "Beautiful Modern House"
}
```
- **Value Type:** `string`
- **Use Case:** Section headings, property names
- **Rendering Hint:** Use `<h1>`, `<h2>`, or `<h3>` tags

---

#### 3. **price** - Price Display
```json
{
  "type": "price",
  "isComposite": false,
  "value": {
    "amount": 5000000.0,
    "currency": "THB"
  }
}
```
- **Value Type:** `{ amount: number, currency: string }`
- **Use Case:** Property prices, rental rates
- **Rendering Hint:** Format with thousand separators, currency symbol

---

#### 4. **badge** - Status Badge/Tag
```json
{
  "type": "badge",
  "isComposite": false,
  "value": {
    "label": "For Sale",
    "variant": "success"
  }
}
```
- **Value Type:** `{ label: string, variant: string }`
- **Variants:** `"default"`, `"success"`, `"warning"`, `"danger"`, `"info"`
- **Use Case:** Status indicators, tags, labels

---

#### 5. **keyValue** - Key-Value Pair
```json
{
  "type": "keyValue",
  "isComposite": false,
  "value": {
    "key": "Bedrooms",
    "value": "3"
  }
}
```
- **Value Type:** `{ key: string, value: any }`
- **Use Case:** Property details, specifications
- **Rendering Hint:** Display as `Label: Value` or in a definition list

---

#### 6. **image** - Image Display
```json
{
  "type": "image",
  "isComposite": false,
  "value": {
    "imageId": 456,
    "alt": "House exterior view"
  }
}
```
- **Value Type:** `{ imageId: number, alt: string }`
- **Use Case:** Property photos
- **Note:** You'll need to construct the image URL from `imageId`, e.g., `/images/{imageId}`

---

#### 7. **map** - Map/Location Display
```json
{
  "type": "map",
  "isComposite": false,
  "value": {
    "latitude": 13.7563,
    "longitude": 100.5018,
    "label": "Location"
  }
}
```
- **Value Type:** `{ latitude: number, longitude: number, label: string }`
- **Use Case:** Property location on map
- **Rendering Hint:** Integrate with Google Maps, Mapbox, or similar

---

#### 8. **iconText** - Icon with Text
```json
{
  "type": "iconText",
  "isComposite": false,
  "value": {
    "icon": "bed",
    "text": "3 Bedrooms"
  }
}
```
- **Value Type:** `{ icon: string, text: string }`
- **Use Case:** Feature lists with icons
- **Icon Values:** `"bed"`, `"bath"`, `"car"`, `"home"`, `"location"`, etc.
- **Rendering Hint:** Use icon library (FontAwesome, Material Icons, etc.)

---

#### 9. **listItem** - List Item
```json
{
  "type": "listItem",
  "isComposite": false,
  "value": "Swimming pool"
}
```
- **Value Type:** `string`
- **Use Case:** Bullet points in amenities/features lists
- **Rendering Hint:** Render as `<li>` element

---

#### 10. **divider** - Visual Separator
```json
{
  "type": "divider",
  "isComposite": false,
  "value": null
}
```
- **Value Type:** `null`
- **Use Case:** Visual separation between sections
- **Rendering Hint:** Render as `<hr>` or styled divider

---

### Composite Components (Containers)

#### 11. **page** - Page Container
```json
{
  "type": "page",
  "isComposite": true,
  "value": [
    { "type": "section", ... },
    { "type": "card", ... }
  ]
}
```
- **Children:** Array of `RenderSpec` objects
- **Use Case:** Tab content, full page layouts
- **Rendering Hint:** Stack children vertically with spacing

---

#### 12. **section** - Section with Title
```json
{
  "type": "section",
  "isComposite": true,
  "value": [
    { "type": "title", "value": "Property Details" },
    { "type": "card", ... }
  ]
}
```
- **Children:** Array of `RenderSpec` objects (first child is typically a title)
- **Use Case:** Grouped content with a heading
- **Note:** Backend automatically prepends a `title` if section title is provided

---

#### 13. **card** - Card Container
```json
{
  "type": "card",
  "isComposite": true,
  "value": [
    { "type": "text", ... },
    { "type": "keyValue", ... }
  ]
}
```
- **Children:** Array of `RenderSpec` objects
- **Use Case:** Contained content with background/border
- **Rendering Hint:** Apply card styling (shadow, border, padding)

---

#### 14. **row** - Horizontal Layout
```json
{
  "type": "row",
  "isComposite": true,
  "value": [
    { "type": "column", ... },
    { "type": "column", ... }
  ]
}
```
- **Children:** Array of `RenderSpec` objects
- **Use Case:** Side-by-side layout
- **Rendering Hint:** Use flexbox/grid with `flex-direction: row`

---

#### 15. **column** - Vertical Layout
```json
{
  "type": "column",
  "isComposite": true,
  "value": [
    { "type": "title", ... },
    { "type": "text", ... }
  ]
}
```
- **Children:** Array of `RenderSpec` objects
- **Use Case:** Stacked content
- **Rendering Hint:** Use flexbox/grid with `flex-direction: column`

---

#### 16. **grid** - Grid Layout
```json
{
  "type": "grid",
  "isComposite": true,
  "value": [
    { "type": "keyValue", ... },
    { "type": "keyValue", ... },
    { "type": "keyValue", ... }
  ]
}
```
- **Children:** Array of `RenderSpec` objects
- **Additional Field:** `columns` (number) - grid column count
- **Use Case:** Multi-column grid of items
- **Rendering Hint:** Use CSS Grid with `grid-template-columns: repeat({columns}, 1fr)`

**Note:** Grid specs include an extra `columns` property in the JSON:
```json
{
  "type": "grid",
  "isComposite": true,
  "columns": 2,
  "value": [...]
}
```

---

#### 17. **imageGallery** - Image Gallery
```json
{
  "type": "imageGallery",
  "isComposite": true,
  "value": [
    { "type": "image", "value": { "imageId": 1, "alt": "..." } },
    { "type": "image", "value": { "imageId": 2, "alt": "..." } }
  ]
}
```
- **Children:** Array of `ImageRenderSpec` objects
- **Use Case:** Photo galleries, image carousels
- **Rendering Hint:** Use carousel library or grid gallery

---

#### 18. **tab** - Tab Container
```json
{
  "type": "tab",
  "isComposite": true,
  "value": [
    { "type": "page", ... },
    { "type": "page", ... }
  ]
}
```
- **Children:** Array of `PageRenderSpec` objects
- **Use Case:** Tabbed interface (Overview, Details, Location, etc.)
- **Rendering Hint:** Implement tab navigation with page switching

---

## TypeScript Type Definitions

```typescript
// Base interface
interface RenderSpec {
  type: string;
  isComposite: boolean;
  value: any;
}

// Leaf types
interface TextRenderSpec extends RenderSpec {
  type: 'text';
  isComposite: false;
  value: string;
}

interface TitleRenderSpec extends RenderSpec {
  type: 'title';
  isComposite: false;
  value: string;
}

interface PriceRenderSpec extends RenderSpec {
  type: 'price';
  isComposite: false;
  value: {
    amount: number;
    currency: string;
  };
}

interface BadgeRenderSpec extends RenderSpec {
  type: 'badge';
  isComposite: false;
  value: {
    label: string;
    variant: 'default' | 'success' | 'warning' | 'danger' | 'info';
  };
}

interface KeyValueRenderSpec extends RenderSpec {
  type: 'keyValue';
  isComposite: false;
  value: {
    key: string;
    value: any;
  };
}

interface ImageRenderSpec extends RenderSpec {
  type: 'image';
  isComposite: false;
  value: {
    imageId: number;
    alt: string;
  };
}

interface MapRenderSpec extends RenderSpec {
  type: 'map';
  isComposite: false;
  value: {
    latitude: number;
    longitude: number;
    label: string;
  };
}

interface IconTextRenderSpec extends RenderSpec {
  type: 'iconText';
  isComposite: false;
  value: {
    icon: string;
    text: string;
  };
}

interface ListItemRenderSpec extends RenderSpec {
  type: 'listItem';
  isComposite: false;
  value: string;
}

interface DividerRenderSpec extends RenderSpec {
  type: 'divider';
  isComposite: false;
  value: null;
}

// Composite types
interface CompositeRenderSpec extends RenderSpec {
  isComposite: true;
  value: RenderSpec[];
}

interface PageRenderSpec extends CompositeRenderSpec {
  type: 'page';
}

interface SectionRenderSpec extends CompositeRenderSpec {
  type: 'section';
}

interface CardRenderSpec extends CompositeRenderSpec {
  type: 'card';
}

interface RowRenderSpec extends CompositeRenderSpec {
  type: 'row';
}

interface ColumnRenderSpec extends CompositeRenderSpec {
  type: 'column';
}

interface GridRenderSpec extends CompositeRenderSpec {
  type: 'grid';
  columns: number;
}

interface ImageGalleryRenderSpec extends CompositeRenderSpec {
  type: 'imageGallery';
  value: ImageRenderSpec[];
}

interface TabRenderSpec extends CompositeRenderSpec {
  type: 'tab';
  value: PageRenderSpec[];
}
```

---

## Implementation Guide

### React Example (Recursive Renderer)

```tsx
import React from 'react';
import { RenderSpec } from './types';

// Main recursive renderer component
const RenderSpecComponent: React.FC<{ spec: RenderSpec }> = ({ spec }) => {
  // Handle leaf components
  if (!spec.isComposite) {
    switch (spec.type) {
      case 'text':
        return <p className="text">{spec.value}</p>;

      case 'title':
        return <h2 className="title">{spec.value}</h2>;

      case 'price':
        return (
          <div className="price">
            {spec.value.amount.toLocaleString()} {spec.value.currency}
          </div>
        );

      case 'badge':
        return (
          <span className={`badge badge-${spec.value.variant}`}>
            {spec.value.label}
          </span>
        );

      case 'keyValue':
        return (
          <div className="key-value">
            <span className="key">{spec.value.key}:</span>
            <span className="value">{spec.value.value}</span>
          </div>
        );

      case 'image':
        return (
          <img
            src={`/api/images/${spec.value.imageId}`}
            alt={spec.value.alt}
            className="image"
          />
        );

      case 'map':
        return (
          <div className="map">
            {/* Integrate with map library */}
            <MapComponent
              lat={spec.value.latitude}
              lng={spec.value.longitude}
              label={spec.value.label}
            />
          </div>
        );

      case 'iconText':
        return (
          <div className="icon-text">
            <Icon name={spec.value.icon} />
            <span>{spec.value.text}</span>
          </div>
        );

      case 'listItem':
        return <li>{spec.value}</li>;

      case 'divider':
        return <hr className="divider" />;

      default:
        console.warn(`Unknown leaf type: ${spec.type}`);
        return null;
    }
  }

  // Handle composite components
  const children = spec.value as RenderSpec[];

  switch (spec.type) {
    case 'page':
      return (
        <div className="page">
          {children.map((child, index) => (
            <RenderSpecComponent key={index} spec={child} />
          ))}
        </div>
      );

    case 'section':
      return (
        <section className="section">
          {children.map((child, index) => (
            <RenderSpecComponent key={index} spec={child} />
          ))}
        </section>
      );

    case 'card':
      return (
        <div className="card">
          {children.map((child, index) => (
            <RenderSpecComponent key={index} spec={child} />
          ))}
        </div>
      );

    case 'row':
      return (
        <div className="row">
          {children.map((child, index) => (
            <RenderSpecComponent key={index} spec={child} />
          ))}
        </div>
      );

    case 'column':
      return (
        <div className="column">
          {children.map((child, index) => (
            <RenderSpecComponent key={index} spec={child} />
          ))}
        </div>
      );

    case 'grid':
      const gridSpec = spec as any;
      return (
        <div
          className="grid"
          style={{
            gridTemplateColumns: `repeat(${gridSpec.columns || 2}, 1fr)`
          }}
        >
          {children.map((child, index) => (
            <RenderSpecComponent key={index} spec={child} />
          ))}
        </div>
      );

    case 'imageGallery':
      return (
        <div className="image-gallery">
          {children.map((child, index) => (
            <RenderSpecComponent key={index} spec={child} />
          ))}
        </div>
      );

    case 'tab':
      return <TabContainer pages={children} />;

    default:
      console.warn(`Unknown composite type: ${spec.type}`);
      return null;
  }
};

// Tab container with state
const TabContainer: React.FC<{ pages: RenderSpec[] }> = ({ pages }) => {
  const [activeTab, setActiveTab] = React.useState(0);

  return (
    <div className="tabs">
      <div className="tab-headers">
        {pages.map((_, index) => (
          <button
            key={index}
            className={activeTab === index ? 'active' : ''}
            onClick={() => setActiveTab(index)}
          >
            Tab {index + 1}
          </button>
        ))}
      </div>
      <div className="tab-content">
        <RenderSpecComponent spec={pages[activeTab]} />
      </div>
    </div>
  );
};

export default RenderSpecComponent;
```

### Usage in Your App

```tsx
import React, { useEffect, useState } from 'react';
import RenderSpecComponent from './RenderSpecComponent';
import { RenderSpec } from './types';

const HouseDetailPage: React.FC<{ houseId: number }> = ({ houseId }) => {
  const [renderSpec, setRenderSpec] = useState<RenderSpec | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    fetch(`/api/houses/${houseId}/v2`)
      .then(res => res.json())
      .then(data => {
        setRenderSpec(data);
        setLoading(false);
      })
      .catch(err => {
        console.error('Failed to load house details:', err);
        setLoading(false);
      });
  }, [houseId]);

  if (loading) return <div>Loading...</div>;
  if (!renderSpec) return <div>Failed to load house details</div>;

  return (
    <div className="house-detail">
      <RenderSpecComponent spec={renderSpec} />
    </div>
  );
};

export default HouseDetailPage;
```

---

## Real-world Examples

### Example 1: Simple House Overview

**API Response:**
```json
{
  "type": "page",
  "isComposite": true,
  "value": [
    {
      "type": "section",
      "isComposite": true,
      "value": [
        {
          "type": "title",
          "isComposite": false,
          "value": "Modern Villa in Bangkok"
        },
        {
          "type": "card",
          "isComposite": true,
          "value": [
            {
              "type": "row",
              "isComposite": true,
              "value": [
                {
                  "type": "column",
                  "isComposite": true,
                  "value": [
                    {
                      "type": "price",
                      "isComposite": false,
                      "value": {
                        "amount": 8500000,
                        "currency": "THB"
                      }
                    },
                    {
                      "type": "badge",
                      "isComposite": false,
                      "value": {
                        "label": "Available",
                        "variant": "success"
                      }
                    }
                  ]
                },
                {
                  "type": "column",
                  "isComposite": true,
                  "value": [
                    {
                      "type": "iconText",
                      "isComposite": false,
                      "value": {
                        "icon": "bed",
                        "text": "4 Bedrooms"
                      }
                    },
                    {
                      "type": "iconText",
                      "isComposite": false,
                      "value": {
                        "icon": "bath",
                        "text": "3 Bathrooms"
                      }
                    }
                  ]
                }
              ]
            }
          ]
        }
      ]
    }
  ]
}
```

### Example 2: Property Details with Grid

```json
{
  "type": "section",
  "isComposite": true,
  "value": [
    {
      "type": "title",
      "isComposite": false,
      "value": "Property Information"
    },
    {
      "type": "card",
      "isComposite": true,
      "value": [
        {
          "type": "grid",
          "isComposite": true,
          "columns": 2,
          "value": [
            {
              "type": "keyValue",
              "isComposite": false,
              "value": {
                "key": "Property Type",
                "value": "Villa"
              }
            },
            {
              "type": "keyValue",
              "isComposite": false,
              "value": {
                "key": "Land Size",
                "value": "500 sqm"
              }
            },
            {
              "type": "keyValue",
              "isComposite": false,
              "value": {
                "key": "Year Built",
                "value": "2020"
              }
            },
            {
              "type": "keyValue",
              "isComposite": false,
              "value": {
                "key": "Parking",
                "value": "2 cars"
              }
            }
          ]
        }
      ]
    }
  ]
}
```

### Example 3: Tabbed Interface (Top Level)

```json
{
  "type": "tab",
  "isComposite": true,
  "value": [
    {
      "type": "page",
      "isComposite": true,
      "value": [
        {
          "type": "section",
          "isComposite": true,
          "value": [
            {
              "type": "title",
              "isComposite": false,
              "value": "Overview"
            }
          ]
        }
      ]
    },
    {
      "type": "page",
      "isComposite": true,
      "value": [
        {
          "type": "section",
          "isComposite": true,
          "value": [
            {
              "type": "title",
              "isComposite": false,
              "value": "Details"
            }
          ]
        }
      ]
    },
    {
      "type": "page",
      "isComposite": true,
      "value": [
        {
          "type": "section",
          "isComposite": true,
          "value": [
            {
              "type": "title",
              "isComposite": false,
              "value": "Location"
            },
            {
              "type": "map",
              "isComposite": false,
              "value": {
                "latitude": 13.7563,
                "longitude": 100.5018,
                "label": "Property Location"
              }
            }
          ]
        }
      ]
    }
  ]
}
```

---

## Best Practices

### 1. Error Handling
Always handle unknown component types gracefully:
```typescript
default:
  console.warn(`Unknown component type: ${spec.type}`);
  return null; // or return a fallback component
```

### 2. Type Safety
Use TypeScript discriminated unions for type-safe rendering:
```typescript
type RenderSpecTypes =
  | TextRenderSpec
  | TitleRenderSpec
  | PriceRenderSpec
  // ... etc
```

### 3. Performance Optimization
- Use `React.memo()` for component memoization
- Add unique `key` props based on content, not just index when possible
- Lazy load heavy components (maps, galleries)

### 4. Responsive Design
- Grid components should respond to screen size
- Row components might stack vertically on mobile
- Image galleries should be touch-friendly

### 5. Accessibility
- Ensure proper heading hierarchy with title components
- Add ARIA labels to badges and icons
- Make interactive tabs keyboard-navigable

### 6. Null/Undefined Handling
Always check for null/undefined values:
```typescript
{spec.value?.text || 'N/A'}
```

### 7. Image Loading
- Show loading placeholders while images load
- Handle image load errors with fallback images
- Consider lazy loading for image galleries

### 8. Map Integration
- Load map libraries asynchronously
- Cache map instances for performance
- Handle missing coordinates gracefully

---

## Summary

This render specification system provides a powerful, flexible way to build dynamic UIs driven by backend logic. The key points to remember:

1. **Two component types**: Leaf (atomic) and Composite (containers)
2. **Recursive rendering**: Composite components contain other components
3. **Type-driven rendering**: The `type` field determines which component to render
4. **Flexible structure**: Backend controls the entire UI layout
5. **Progressive enhancement**: Add new component types as needed
