package com.example.demo_jsonbuilder.configs;

import com.example.demo_jsonbuilder.entities.Car;
import com.example.demo_jsonbuilder.entities.Client;
import com.example.demo_jsonbuilder.entities.Maintainer;
import com.example.demo_jsonbuilder.entities.Tool;
import com.example.demo_jsonbuilder.entities.User;
import com.example.demo_jsonbuilder.repository.CarRepository;
import com.example.demo_jsonbuilder.repository.ClientRepository;
import com.example.demo_jsonbuilder.repository.MaintainerRepository;
import com.example.demo_jsonbuilder.repository.ToolRepository;
import com.example.demo_jsonbuilder.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("demo")
public class LoadDatabaseConfig {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository,
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
