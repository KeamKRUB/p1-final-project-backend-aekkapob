package ku.cs.project_SE.entity.house;

import jakarta.persistence.*;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "house_address")
    private String houseAddress;

    @Column(name = "area")
    private String area;

    @Column(name = "substrict")
    private String substrict;

    @Column(name = "province")
    private String province;

    @Column(name = "latitude")
    private double lat;

    @Column(name = "longitude")
    private double lon;


    public Address() {
        this("", "", "", "", "", 0, 0);
    }

    public Address(String houseAddress, String area, String substrict, String postalCode) {
        this(houseAddress, area, substrict, postalCode, "", 0, 0);
    }

    public Address(String houseAddress, String area, String substrict, String postalCode, String province, double lat, double lon) {
        this.houseAddress = houseAddress;
        this.area = area;
        this.substrict = substrict;
        this.postalCode = postalCode;
        this.province = province;
        this.lat = lat;
        this.lon = lon;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSubstrict() {
        return substrict;
    }

    public void setSubstrict(String substrict) {
        this.substrict = substrict;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}