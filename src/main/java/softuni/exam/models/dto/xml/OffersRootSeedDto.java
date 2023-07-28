package softuni.exam.models.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OffersRootSeedDto {

    @XmlElement(name = "offer")
    private List<OfferSeedDto> offers;

    public List<OfferSeedDto> getOffers() {
        return offers;
    }
}
