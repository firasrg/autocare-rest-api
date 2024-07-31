/**
 * Car Service REST API - Load database configuration component.
 * Copyright (C) 2024  Car Service REST API original author or authors.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this application.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.frg.carservice.configs;

import com.frg.carservice.entities.Car;
import com.frg.carservice.entities.Client;
import com.frg.carservice.entities.Maintainer;
import com.frg.carservice.entities.Tool;
import com.frg.carservice.entities.User;
import com.frg.carservice.repository.CarRepository;
import com.frg.carservice.repository.ClientRepository;
import com.frg.carservice.repository.MaintainerRepository;
import com.frg.carservice.repository.ToolRepository;
import com.frg.carservice.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("demo")
public class LoadDatabaseConfig {

  @Bean
  CommandLineRunner initDatabase(
      UserRepository userRepository,
      CarRepository carRepository,
      ClientRepository clientRepository,
      MaintainerRepository maintainerRepository,
      ToolRepository toolRepository) {
    return args -> {
      User user1 = new User();
      user1.setName("John Doe");
      user1.setEmail("john.doe@example.com");
      userRepository.save(user1);

      Client client1 = new Client();
      client1.setName("Client One");
      clientRepository.save(client1);

      Maintainer maintainer1 = new Maintainer();
      maintainer1.setName("Technician One");
      maintainerRepository.save(maintainer1);

      Car car1 = new Car();
      car1.setModel("Model S");
      car1.setMake("Tesla");
      car1.setClient(client1);
      car1.setMaintainer(maintainer1);
      carRepository.save(car1);

      Tool tool1 = new Tool();
      tool1.setName("Wrench");
      tool1.setMaintainer(maintainer1);
      toolRepository.save(tool1);

      Tool tool2 = new Tool();
      tool2.setName("Screwdriver");
      tool2.setMaintainer(maintainer1);
      toolRepository.save(tool2);
    };
  }
}
