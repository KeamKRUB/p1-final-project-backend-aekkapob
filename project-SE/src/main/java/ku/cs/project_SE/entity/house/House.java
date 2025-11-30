package ku.cs.project_SE.entity.house;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import ku.cs.project_SE.entity.house_register.HouseRegister;
import ku.cs.project_SE.entity.image.Image;
import ku.cs.project_SE.entity.project.Project;
import ku.cs.project_SE.entity.user.User;
import ku.cs.project_SE.enums.house.HouseStatus;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "house")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Inheritance(strategy = InheritanceType.JOINED)
public class House {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long houseId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_name")
    private Project project;

    @Column(name = "house_name")
    private String houseName;

    @Column(name = "caption")
    private String caption;


    @Column(name = "proposed_projectname")
    private String proposedProjectName;

    @Column(name = "description", length = 2000)
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;

    @Column(name = "price")
    private Double price;

    @Column(name = "bedrooms")
    private int bedrooms;

    @Column(name = "bathrooms")
    private int bathrooms;

    @Column(name = "square_footage")
    private double squareFootage;

    @Column(name = "year_built")
    private int yearBuilt;

    @Column(name = "garage_spaces")
    private int garageSpaces;

    @Column(name = "house_type")
    private String houseType;

    @Column(name = "law_information", length = 2000)
    private String lawInformation;

    @Column(name = "structure", length = 1000)
    private String structure;

    @Enumerated(EnumType.STRING)
    @Column(name = "house_status")
    private HouseStatus houseStatus;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "house_id")
    private Set<Image> images = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")  // foreign key ไปยัง User
    private User owner;

    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<HouseRegister> houseRegisters = new HashSet<>();

    @OneToOne(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private HouseRental rental;

    @OneToOne(mappedBy = "house", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private HouseRenovate renovate;
}
