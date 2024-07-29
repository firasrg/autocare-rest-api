package com.frg.carservice.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frg.carservice.entities.Car;
import com.frg.carservice.entities.Tool;
import com.frg.carservice.repository.CarRepository;
import com.frg.carservice.repository.ToolRepository;
import com.frg.carservice.technical.ModelObject;
import com.frg.carservice.technical.ModelObjectBuilder;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CarService {

  @Autowired private CarRepository carRepository;

  @Autowired private ToolRepository toolRepository;

  private final ObjectMapper mapper = new ObjectMapper();

  public String getAll() {
    List<Car> cars = carRepository.findAll();
    List<ModelObject> carList = new ArrayList<>();

    for (Car car : cars) {
      ModelObjectBuilder carBuilder =
          ModelObjectBuilder.createBuilder()
              .addAttribute("id", car.getId())
              .addAttribute("model", car.getModel())
              .addAttribute("make", car.getMake())
              .addAttribute("clientName", car.getClient().getName())
              .addAttribute("maintainerName", car.getMaintainer().getName());

      List<Tool> tools = toolRepository.findToolsByMaintainerId(car.getMaintainer().getId());
      ModelObject[] toolBuilders =
          tools.stream()
              .map(
                  tool ->
                      ModelObjectBuilder.createBuilder()
                          .addAttribute("id", tool.getId())
                          .addAttribute("name", tool.getName())
                          .build())
              .toArray(ModelObject[]::new);

      carBuilder.addArrayAttribute("tools", toolBuilders);

      ModelObject finalBuilder =
          ModelObjectBuilder.createBuilder().addAttribute("car", carBuilder.build()).build();

      carList.add(finalBuilder);
    }

    try {
      return this.mapper.writeValueAsString(carList);
    } catch (Exception e) {
      throw new RuntimeException("Error converting to JSON", e);
    }
  }
}
