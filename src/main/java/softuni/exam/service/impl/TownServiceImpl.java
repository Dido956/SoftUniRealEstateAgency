package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.TownsSeedDto;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.TownService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.util.Constants.*;

@Service
public class TownServiceImpl implements TownService {

    private TownRepository townRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private Gson gson;

    public TownServiceImpl(TownRepository townRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return townRepository.count() > 0;
    }

    @Override
    public String readTownsFileContent() throws IOException {
        return Files
                .readString(Path.of(TOWNS_FILE_PATH));
    }

    @Override
    public String importTowns() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson
                        .fromJson(readTownsFileContent(), TownsSeedDto[].class))
                .filter(townsSeedDto -> {
                    boolean isValid = validationUtil.isValid(townsSeedDto);

                    sb
                            .append(isValid
                                    ? String.format(SUCCESSFULLY_IMPORTED_TOWN,
                                    townsSeedDto.getTownName(), townsSeedDto.getPopulation())
                                    : String.format(INVALID, TOWN))
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(townsSeedDto -> modelMapper.map(townsSeedDto, Town.class))
                .forEach(townRepository::save);


        return sb.toString().trim();
    }
}
