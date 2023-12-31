package softuni.exam.models.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "apartments")
@XmlAccessorType(XmlAccessType.FIELD)
public class ApartmentsRootSeedDto {

    @XmlElement(name = "apartment")
    private List<ApartmentsSeedDto> apartments;

    public List<ApartmentsSeedDto> getApartments() {
        return apartments;
    }
}
