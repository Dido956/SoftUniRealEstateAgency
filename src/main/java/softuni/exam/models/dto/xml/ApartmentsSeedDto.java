package softuni.exam.models.dto.xml;

import softuni.exam.models.ApartmentType;
import softuni.exam.models.entity.Town;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentsSeedDto {
    //??
    @XmlElement
    private ApartmentType apartmentType;

    @XmlElement
    @Positive
    @DecimalMin("40.00")
    private Double area;

    @XmlElement
    private String town;

    public ApartmentsSeedDto() {
    }

    public ApartmentType getApartmentType() {
        return apartmentType;
    }

    public void setApartmentType(ApartmentType apartmentType) {
        this.apartmentType = apartmentType;
    }

    public Double getArea() {
        return area;
    }

    public void setArea(Double area) {
        this.area = area;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
