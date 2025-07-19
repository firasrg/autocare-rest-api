package com.frg.autocare;

import com.frg.autocare.entities.Car;
import com.frg.autocare.entities.Customer;
import com.frg.autocare.entities.Maintainer;
import com.frg.autocare.repository.CarRepository;
import com.frg.autocare.repository.CustomerRepository;
import com.frg.autocare.repository.MaintainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
@WithMockUser(username = "johndoe", roles = {"USER"})
public class CarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private MaintainerRepository maintainerRepository;

    @BeforeEach
    void setUp() {
        carRepository.deleteAll();
        customerRepository.deleteAll();
        maintainerRepository.deleteAll();

        // Owners
        Customer customer1 = new Customer();
        customer1.setName("John Doe");
        customerRepository.save(customer1);

        Customer customer2 = new Customer();
        customer2.setName("Jane Smith");
        customerRepository.save(customer2);

        // Maintainers
        Maintainer maintainer1 = new Maintainer();
        maintainer1.setName("Service Center A");
        maintainerRepository.save(maintainer1);

        Maintainer maintainer2 = new Maintainer();
        maintainer2.setName("Service Center B");
        maintainerRepository.save(maintainer2);

        // Cars
        Car car1 = new Car();
        car1.setMake("Toyota");
        car1.setModel("Camry");
        car1.setCustomer(customer1);
        car1.setMaintainer(maintainer1);
        carRepository.save(car1);

        Car car2 = new Car();
        car2.setMake("Honda");
        car2.setModel("Civic");
        car2.setCustomer(customer2);
        car2.setMaintainer(maintainer2);
        carRepository.save(car2);

        Car car3 = new Car();
        car3.setMake("Toyota");
        car3.setModel("Corolla");
        car3.setCustomer(customer2);
        car3.setMaintainer(maintainer1);
        carRepository.save(car3);
    }

    @Test
    void getAllCars_ShouldReturnAllCars() throws Exception {
        mockMvc.perform(get("/api/v1/cars")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$.[0].make", is("Toyota")))
                .andExpect(jsonPath("$.[1].make", is("Honda")));
    }

    @Test
    void getAllCars_WithFilters_ShouldReturnFilteredCars() throws Exception {
        mockMvc.perform(get("/api/v1/cars")
                        .param("make", "Toyota")
                        .param("maintainer", "Service Center A")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$.[*].make", everyItem(is("Toyota"))))
                .andExpect(jsonPath("$.[*].maintainerName", everyItem(is("Service Center A"))));
    }

    @Test
    void getAllCars_WithInvalidSortDirection_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/api/v1/cars")
                        .param("sortDir", "INVALID")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message", containsString("Invalid value 'INVALID' for parameter 'sortDir'")))
                .andExpect(jsonPath("$.path").value("/api/v1/cars"));
    }
}
