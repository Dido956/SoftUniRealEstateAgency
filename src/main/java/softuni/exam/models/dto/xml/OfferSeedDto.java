package softuni.exam.models.dto.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class OfferSeedDto {
    @XmlElement
    private BigDecimal price;
    @XmlElement
    private AgentNameDto agent;
    @XmlElement
    private ApartmentIdDto apartment;
    @XmlElement
    private String publishedOn;

    public OfferSeedDto() {
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public AgentNameDto getAgent() {
        return agent;
    }

    public void setAgent(AgentNameDto agent) {
        this.agent = agent;
    }

    public ApartmentIdDto getApartment() {
        return apartment;
    }

    public void setApartment(ApartmentIdDto apartment) {
        this.apartment = apartment;
    }

    public String getPublishedOn() {
        return publishedOn;
    }

    public void setPublishedOn(String  publishedOn) {
        this.publishedOn = publishedOn;
    }
}
