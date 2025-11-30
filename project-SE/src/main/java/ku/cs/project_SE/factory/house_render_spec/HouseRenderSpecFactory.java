package ku.cs.project_SE.factory.house_render_spec;

import ku.cs.project_SE.entity.house.Address;
import ku.cs.project_SE.entity.house.House;
import ku.cs.project_SE.entity.house.HouseRenovate;
import ku.cs.project_SE.entity.house.HouseRental;
import ku.cs.project_SE.entity.image.Image;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.model.filter.house_detail_renderer.*;
import ku.cs.project_SE.service.HouseRegisterService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HouseRenderSpecFactory {
    private final HouseRegisterService houseRegisterService;
    public HouseRenderSpecFactory(HouseRegisterService houseRegisterService) {
        this.houseRegisterService = houseRegisterService;
    }

    public RenderSpec getRenderSpec(House house, User user) {
        long primaryImageId = getPrimaryImageId(house);

        boolean isRenovate;
        isRenovate = house.getRenovate() != null;

        HouseHeroRenderSpec houseHeroRenderSpec = new HouseHeroRenderSpec(primaryImageId,house.getHouseName(), house.getProject().getProjectName(), house.getPrice(), isRenovate);

        List<Image> sortedImages = house.getImages().stream()
                .sorted(Comparator.comparing(Image::getImageId))
                .collect(Collectors.toList());

        ArrayList<HouseGalleryRenderSpec.Image> images = getImagesForGallery(house, sortedImages);
        HouseGalleryRenderSpec houseGalleryRenderSpec = new HouseGalleryRenderSpec(images);

        List<InformationTabRenderSpec.InformationPage> pages = getInformationPages(house);
        InformationTabRenderSpec houseInformationTab = new InformationTabRenderSpec(pages);

        List<HouseGalleryRenderSpec.Image> imageAll = sortedImages.stream()
                .map(image -> new HouseGalleryRenderSpec.Image(
                        image.getImageId(),
                        "Image " + image.getImageId()
                ))
                .toList();

        AllGalleryRenderSpec galleryRenderSpec = new AllGalleryRenderSpec(imageAll);

        boolean isRegistered = isRegistered(house, user);

        HouseRegistrationRenderSpec registrationRenderSpec = new HouseRegistrationRenderSpec(house.getHouseId(), isRegistered);

        return new HouseDetailPageRenderSpec(
                houseHeroRenderSpec,
                houseGalleryRenderSpec,
                houseInformationTab,
                galleryRenderSpec,
                registrationRenderSpec
        );
    }

    private boolean isRegistered(House house, User user) {
        if (user != null) {
            return houseRegisterService.isUserRegisterToThisHouse(house.getHouseId(), user.getUserId());
        }
        else {
            return false;
        }
    }

    private static List<InformationTabRenderSpec.InformationPage> getInformationPages(House house) {
        // Create house information tabs
        List<InformationTabRenderSpec.InformationContent> basicInfoContents = List.of(
                new InformationTabRenderSpec.InformationContent(
                        "House Name",
                        house.getHouseName(),
                        "🏠"
                ),
                new InformationTabRenderSpec.InformationContent(
                        "House Type",
                        house.getHouseType(),
                        "🏗️"
                ),
                new InformationTabRenderSpec.InformationContent(
                        "Price",
                        String.format("฿%,.2f", house.getPrice()),
                        "💰"
                ),
                new InformationTabRenderSpec.InformationContent(
                        "Status",
                        house.getHouseStatus().toString(),
                        "📋"
                ),
                new InformationTabRenderSpec.InformationContent(
                        "Caption",
                        house.getCaption(),
                        "📝"
                )
        );

        List<InformationTabRenderSpec.InformationContent> propertyDetailsContents = List.of(
                new InformationTabRenderSpec.InformationContent(
                        "Bedrooms",
                        String.valueOf(house.getBedrooms()),
                        "🛏️"
                ),
                new InformationTabRenderSpec.InformationContent(
                        "Bathrooms",
                        String.valueOf(house.getBathrooms()),
                        "🚿"
                ),
                new InformationTabRenderSpec.InformationContent(
                        "Square Footage",
                        String.format("%.2f sq.m.", house.getSquareFootage()),
                        "📐"
                ),
                new InformationTabRenderSpec.InformationContent(
                        "Year Built",
                        String.valueOf(house.getYearBuilt()),
                        "📅"
                ),
                new InformationTabRenderSpec.InformationContent(
                        "Garage Spaces",
                        String.valueOf(house.getGarageSpaces()),
                        "🚗"
                )
        );

        List<InformationTabRenderSpec.InformationContent> locationContents = List.of(
                new InformationTabRenderSpec.InformationContent(
                        "Address",
                        house.getAddress().getHouseAddress(),
                        "📍"
                ),
                new InformationTabRenderSpec.InformationContent(
                        "Area",
                        house.getAddress().getArea(),
                        "🗺️"
                ),
                new InformationTabRenderSpec.InformationContent(
                        "Subdistrict",
                        house.getAddress().getSubstrict(),
                        "🏘️"
                ),
                new InformationTabRenderSpec.InformationContent(
                        "Province",
                        house.getAddress().getProvince(),
                        "🌆"
                ),
                new InformationTabRenderSpec.InformationContent(
                        "Postal Code",
                        house.getAddress().getPostalCode(),
                        "📮"
                ),
                new InformationTabRenderSpec.InformationContent(
                        "Coordinates",
                        String.format("%.6f, %.6f", house.getAddress().getLat(), house.getAddress().getLon()),
                        "🧭"
                )
        );

        List<InformationTabRenderSpec.InformationContent> projectContents = List.of(
                new InformationTabRenderSpec.InformationContent(
                        "Project Name",
                        house.getProject() != null ? house.getProject().getProjectName() : house.getProposedProjectName(),
                        "🏢"
                ),
                new InformationTabRenderSpec.InformationContent(
                        "Owner Email",
                        house.getOwner().getEmail(),
                        "👤"
                )
        );

        List<InformationTabRenderSpec.InformationContent> legalStructureContents = List.of(
                new InformationTabRenderSpec.InformationContent(
                        "Legal Information",
                        house.getLawInformation() != null ? house.getLawInformation() : "Not specified",
                        "⚖️"
                ),
                new InformationTabRenderSpec.InformationContent(
                        "Structure Details",
                        house.getStructure() != null ? house.getStructure() : "Not specified",
                        "🏗️"
                ),
                new InformationTabRenderSpec.InformationContent(
                        "Description",
                        house.getDescription() != null ? house.getDescription() : "No description available",
                        "📄"
                )
        );

        List<InformationTabRenderSpec.InformationContent> rentalContents = new ArrayList<>();
        if (house.getRental() != null) {
            rentalContents = List.of(
                    new InformationTabRenderSpec.InformationContent(
                            "Rental Yield",
                            house.getRental().getRentalYield() != null ?
                                    String.format("฿%,.2f", house.getRental().getRentalYield()) : "N/A",
                            "💵"
                    ),
                    new InformationTabRenderSpec.InformationContent(
                            "Property Tax",
                            house.getRental().getPropertyTax() != null ?
                                    String.format("฿%,.2f", house.getRental().getPropertyTax()) : "N/A",
                            "🏛️"
                    ),
                    new InformationTabRenderSpec.InformationContent(
                            "Maintenance Cost",
                            house.getRental().getMaintenanceCost() != null ?
                                    String.format("฿%,.2f", house.getRental().getMaintenanceCost()) : "N/A",
                            "🔧"
                    ),
                    new InformationTabRenderSpec.InformationContent(
                            "Additional Information",
                            house.getRental().getAdditionalData() != null ?
                                    house.getRental().getAdditionalData() : "None",
                            "ℹ️"
                    )
            );
        }

// If house has renovation information
        List<InformationTabRenderSpec.InformationContent> renovateContents = new ArrayList<>();
        if (house.getRenovate() != null) {
            renovateContents = List.of(
                    new InformationTabRenderSpec.InformationContent(
                            "Renovation Cost",
                            house.getRenovate().getInnovateCost() != null ?
                                    String.format("฿%,.2f", house.getRenovate().getInnovateCost()) : "N/A",
                            "🔨"
                    ),
                    new InformationTabRenderSpec.InformationContent(
                            "Legal Information",
                            house.getRenovate().getLawInformation() != null ?
                                    house.getRenovate().getLawInformation() : "Not specified",
                            "⚖️"
                    ),
                    new InformationTabRenderSpec.InformationContent(
                            "Structure Changes",
                            house.getRenovate().getStructure() != null ?
                                    house.getRenovate().getStructure() : "Not specified",
                            "🏗️"
                    ),
                    new InformationTabRenderSpec.InformationContent(
                            "Additional Data",
                            house.getRenovate().getAdditionalData() != null ?
                                    house.getRenovate().getAdditionalData() : "None",
                            "ℹ️"
                    )
            );
        }

// Create pages
        List<InformationTabRenderSpec.InformationPage> pages = new ArrayList<>();
        pages.add(new InformationTabRenderSpec.InformationPage(basicInfoContents));
        pages.add(new InformationTabRenderSpec.InformationPage(propertyDetailsContents));
        pages.add(new InformationTabRenderSpec.InformationPage(locationContents));
        pages.add(new InformationTabRenderSpec.InformationPage(projectContents));
        pages.add(new InformationTabRenderSpec.InformationPage(legalStructureContents));

        if (!rentalContents.isEmpty()) {
            pages.add(new InformationTabRenderSpec.InformationPage(rentalContents));
        }

        if (!renovateContents.isEmpty()) {
            pages.add(new InformationTabRenderSpec.InformationPage(renovateContents));
        }
        return pages;
    }

    private static ArrayList<HouseGalleryRenderSpec.Image> getImagesForGallery(House house, List<Image> sortedImages) {
        ArrayList<HouseGalleryRenderSpec.Image> images = new ArrayList<>();

        int imageCount = house.getImages().size();

        // Second image shows square footage
        if (imageCount >= 2) {
            images.add(new HouseGalleryRenderSpec.Image(
                    sortedImages.get(1).getImageId(),
                    "พื้นที่ " + house.getSquareFootage() + " ตร.ม."
            ));
        }

        // Third image shows bedrooms and bathrooms
        if (imageCount >= 3) {
            images.add(new HouseGalleryRenderSpec.Image(
                    sortedImages.get(2).getImageId(),
                    house.getBedrooms() + " ห้องนอน " + house.getBathrooms() + " ห้องน้ำ"
            ));
        }

        // Fourth image shows location
        if (imageCount >= 4) {
            images.add(new HouseGalleryRenderSpec.Image(
                    sortedImages.get(3).getImageId(),
                    house.getAddress().getSubstrict() + ", " + house.getAddress().getProvince()
            ));
        }

        return images;
    }

    private static long getPrimaryImageId(House house) {
        Set<Image> houseImages = house.getImages();
        long imageId;
        if (houseImages.isEmpty()) {
            imageId = 0;
        }
        else {
            Image minImage = houseImages.stream()
                    .min(Comparator.comparing(Image::getImageId))
                    .orElse(null);
            imageId = minImage.getImageId();
        }
        return imageId;
    }
}