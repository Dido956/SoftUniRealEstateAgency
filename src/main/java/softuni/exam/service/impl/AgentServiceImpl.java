package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AgentsSeedDto;
import softuni.exam.models.entity.Agent;
import softuni.exam.models.entity.Town;
import softuni.exam.repository.AgentRepository;
import softuni.exam.repository.TownRepository;
import softuni.exam.service.AgentService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

import static softuni.exam.util.Constants.*;

@Service
public class AgentServiceImpl implements AgentService {

    private AgentRepository agentRepository;
    private TownRepository townRepository;
    private ValidationUtil validationUtil;
    private ModelMapper modelMapper;
    private Gson gson;

    public AgentServiceImpl(AgentRepository agentRepository, TownRepository townRepository, ValidationUtil validationUtil, ModelMapper modelMapper, Gson gson) {
        this.agentRepository = agentRepository;
        this.townRepository = townRepository;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return agentRepository.count() > 0;
    }

    @Override
    public String readAgentsFromFile() throws IOException {
        return Files
                .readString(Path.of(AGENTS_FILE_PATH));
    }

    @Override
    public String importAgents() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson
                        .fromJson(readAgentsFromFile(), AgentsSeedDto[].class))
                .filter(agentsSeedDto -> {
                    boolean isValid = validationUtil.isValid(agentsSeedDto)
                            && !existsByFirstName(agentsSeedDto.getFirstName());

                    sb.append(isValid
                            ? String.format(SUCCESSFULLY_IMPORTED_AGENT,agentsSeedDto.getFirstName(),agentsSeedDto.getLastName())
                            : String.format(INVALID,AGENT))
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(agentsSeedDto -> {
                    Agent agent = modelMapper.map(agentsSeedDto, Agent.class);

                    agent.setTown(findTownByName(agentsSeedDto.getTown()));

                    return agent;
                })
                .forEach(agentRepository::save);


        return sb.toString().trim();
    }

    private Town findTownByName(String town) {
        return townRepository.findByTownName(town);
    }

    private boolean existsByFirstName(String firstName) {
        return agentRepository.existsByFirstName(firstName);
    }
}
