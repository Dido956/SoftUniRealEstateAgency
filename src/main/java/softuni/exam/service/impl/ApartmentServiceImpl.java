package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.xml.ApartmentsRootSeedDto;
import softuni.exam.models.entity.Apartment;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.ApartmentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.ApartmentService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.util.Constants.*;

@Service
public class ApartmentServiceImpl implements ApartmentService {
    private ApartmentRepository apartmentRepository;
    private TownRepository townRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private XmlParser xmlParser;

    public ApartmentServiceImpl(ApartmentRepository apartmentRepository, TownRepository townRepository, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.apartmentRepository = apartmentRepository;
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return apartmentRepository.count() > 0;
    }

    @Override
    public String readApartmentsFromFile() throws IOException {
        return Files
                .readString(Path.of(APARTMENTS_FILE_PATH));
    }

    @Override
    public String importApartments() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();


        xmlParser.fromFile(APARTMENTS_FILE_PATH, ApartmentsRootSeedDto.class)
                .getApartments()
                .stream()
                .filter(apartmentsSeedDto -> {
                    boolean isValid = validationUtil.isValid(apartmentsSeedDto)
                            && !findByTownNameAndArea(apartmentsSeedDto.getTown(), apartmentsSeedDto.getArea());

                    sb.append(isValid
                            ? String.format(SUCCESSFULLY_IMPORTED_APARTMENT,
                            apartmentsSeedDto.getApartmentType(), apartmentsSeedDto.getArea())
                            : String.format(INVALID,APARTMENT))
                            .append(System.lineSeparator());
                    return isValid;
                })
                .map(apartmentsSeedDto -> {
                    Apartment apartment = modelMapper.map(apartmentsSeedDto, Apartment.class);

                    apartment.setTown(townRepository.findByTownName(apartmentsSeedDto.getTown()));

                    return apartment;
                })
                .forEach(apartmentRepository::save);


        return sb.toString().trim();
    }

    private boolean findByTownNameAndArea(String town, Double area) {
        return apartmentRepository.existsByTownTownNameAndArea(town, area);
    }
}
