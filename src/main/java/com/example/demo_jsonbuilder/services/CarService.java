package com.example.demo_jsonbuilder.services;

import com.example.demo_jsonbuilder.entities.Car;
import com.example.demo_jsonbuilder.entities.Tool;
import com.example.demo_jsonbuilder.repository.CarRepository;
import com.example.demo_jsonbuilder.repository.ToolRepository;
import com.example.demo_jsonbuilder.technical.ModelObjectBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private ToolRepository toolRepository;

    private final ObjectMapper mapper = new ObjectMapper();

    public String getComplexJson() {
        List<Car> cars = carRepository.findAll();
        List<Map<String, Object>> carList = new ArrayList<>();

        for (Car car : cars) {
            ModelObjectBuilder carBuilder = ModelObjectBuilder.createBuilder()
                    .addAttribute("id", car.getId())
                    .addAttribute("model", car.getModel())
                    .addAttribute("make", car.getMake())
                    .addAttribute("clientName", car.getClient().getName())
                    .addAttribute("maintainerName", car.getMaintainer().getName());

            List<Tool> tools = toolRepository.findToolsByMaintainerId(car.getMaintainer().getId());
            ModelObjectBuilder[] toolBuilders = tools.stream()
                    .map(tool -> ModelObjectBuilder.createBuilder()
                            .addAttribute("id", tool.getId())
                            .addAttribute("name", tool.getName()))
                    .toArray(ModelObjectBuilder[]::new);

            carBuilder.addArrayAttribute("tools", toolBuilders);

            Map<String, Object> finalBuilder = ModelObjectBuilder.createBuilder()
                            .addAttribute("car",carBuilder.build())
                                    .build();

            carList.add(finalBuilder);
        }

        try {
            return this.mapper.writeValueAsString(carList);
        } catch (Exception e) {
            throw new RuntimeException("Error converting to JSON", e);
        }
    }
}
