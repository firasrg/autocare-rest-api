package com.frg.autocare;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.frg.autocare.controllers.CarController;
import com.frg.autocare.dto.CarDTO;
import com.frg.autocare.dto.CarFilterDTO;
import com.frg.autocare.services.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WithMockUser(username = "johndoe", roles = {"USER"})
@WebMvcTest(CarController.class)
class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @Autowired
    private ObjectMapper objectMapper;

    private List<CarDTO> sampleCars;

    @BeforeEach
    void setUp() {
        sampleCars = Arrays.asList(
                new CarDTO(1L, "Camry", "Toyota", "John Doe", "Service Center A", new ArrayList<>()),
                new CarDTO(2L, "Civic", "Honda", "Jane Smith", "Service Center B", new ArrayList<>()),
                new CarDTO(3L, "Corolla", "Toyota", "Bob Johnson", "Service Center A", new ArrayList<>())
        );
    }

    @Test
    void getAllCars_WithoutFilters_ShouldReturnAllCars() throws Exception {
        // Given
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<CarDTO> expectedPage = new PageImpl<>(sampleCars, pageable, sampleCars.size());

        when(carService.getAllCars(new CarFilterDTO(null, null, null, null), pageable))
                .thenReturn(expectedPage);

        // When & Then
        mockMvc.perform(get("/api/v1/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].make").value("Toyota"))
                .andExpect(jsonPath("$[1].make").value("Honda"));

        verify(carService).getAllCars(new CarFilterDTO(null, null, null, null), pageable);
    }

    @Test
    void getAllCars_WithMultipleFilters_ShouldReturnFilteredCars() throws Exception {
        // Given
        List<CarDTO> filteredCars = Arrays.asList(sampleCars.get(0), sampleCars.get(2));
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<CarDTO> expectedPage = new PageImpl<>(filteredCars, pageable, filteredCars.size());

        when(carService.getAllCars(new CarFilterDTO("Toyota", null, null, "Service Center A"), pageable))
                .thenReturn(expectedPage);

        // When & Then
        mockMvc.perform(get("/api/v1/cars")
                        .param("make", "Toyota")
                        .param("maintainerName", "Service Center A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].make").value("Toyota"))
                .andExpect(jsonPath("$[0].maintainerName").value("Service Center A"))
                .andExpect(jsonPath("$[1].make").value("Toyota"))
                .andExpect(jsonPath("$[1].maintainerName").value("Service Center A"));

        verify(carService).getAllCars(new CarFilterDTO("Toyota", null, null, "Service Center A"), pageable);
    }

    @Test
    void getAllCars_WithAllFilters_ShouldReturnFilteredCars() throws Exception {
        // Given
        List<CarDTO> filteredCars = Arrays.asList(sampleCars.get(0));
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));
        Page<CarDTO> expectedPage = new PageImpl<>(filteredCars, pageable, filteredCars.size());

        when(carService.getAllCars(new CarFilterDTO("Toyota", "Camry", "John Doe", "Service Center A"), pageable))
                .thenReturn(expectedPage);

        // When & Then
        mockMvc.perform(get("/api/v1/cars")
                        .param("make", "Toyota")
                        .param("model", "Camry")
                        .param("ownerName", "John Doe")
                        .param("maintainerName", "Service Center A"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].make").value("Toyota"))
                .andExpect(jsonPath("$[0].model").value("Camry"))
                .andExpect(jsonPath("$[0].clientName").value("John Doe"))
                .andExpect(jsonPath("$[0].maintainerName").value("Service Center A"));

        verify(carService).getAllCars(new CarFilterDTO("Toyota", "Camry", "John Doe", "Service Center A"), pageable);
    }

    @Test
    void getAllCars_WithCustomPagination_ShouldReturnCorrectPageAndSize() throws Exception {
        // Given
        List<CarDTO> pagedCars = Arrays.asList(sampleCars.get(1)); // Second page with size 1
        Pageable pageable = PageRequest.of(1, 1, Sort.by(Sort.Direction.ASC, "id"));
        Page<CarDTO> expectedPage = new PageImpl<>(pagedCars, pageable, sampleCars.size());

        when(carService.getAllCars(new CarFilterDTO(null, null, null, null), pageable))
                .thenReturn(expectedPage);

        // When & Then
        mockMvc.perform(get("/api/v1/cars")
                        .param("pageNumber", "1")
                        .param("pageSize", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].make").value("Honda"));

        verify(carService).getAllCars(new CarFilterDTO(null, null, null, null), pageable);
    }

    @Test
    void getAllCars_WithSortingByMakeDesc_ShouldReturnSortedCars() throws Exception {
        // Given
        List<CarDTO> sortedCars = Arrays.asList(sampleCars.get(0), sampleCars.get(2), sampleCars.get(1));
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "make"));
        Page<CarDTO> expectedPage = new PageImpl<>(sortedCars, pageable, sortedCars.size());

        when(carService.getAllCars(new CarFilterDTO(null, null, null, null), pageable))
                .thenReturn(expectedPage);

        // When & Then
        mockMvc.perform(get("/api/v1/cars")
                        .param("sortBy", "make")
                        .param("sortDir", "DESC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].make").value("Toyota"))
                .andExpect(jsonPath("$[2].make").value("Honda"));

        verify(carService).getAllCars(new CarFilterDTO(null, null, null, null), pageable);
    }

    @Test
    void getAllCars_WithSortingByOwnerAsc_ShouldReturnSortedCars() throws Exception {
        // Given
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "ownerName"));
        Page<CarDTO> expectedPage = new PageImpl<>(sampleCars, pageable, sampleCars.size());

        when(carService.getAllCars(new CarFilterDTO(null, null, null, null), pageable))
                .thenReturn(expectedPage);

        // When & Then
        mockMvc.perform(get("/api/v1/cars")
                        .param("sortBy", "ownerName")
                        .param("sortDir", "ASC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        verify(carService).getAllCars(new CarFilterDTO(null, null, null, null), pageable);
    }

    @Test
    void getAllCars_WithCombinedFiltersAndPaginationAndSorting_ShouldWorkCorrectly() throws Exception {
        // Given
        List<CarDTO> filteredCars = Arrays.asList(sampleCars.get(0));
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "model"));
        Page<CarDTO> expectedPage = new PageImpl<>(filteredCars, pageable, filteredCars.size());

        when(carService.getAllCars(new CarFilterDTO("Toyota", null, "John Doe", null), pageable))
                .thenReturn(expectedPage);

        // When & Then
        mockMvc.perform(get("/api/v1/cars")
                        .param("make", "Toyota")
                        .param("ownerName", "John Doe")
                        .param("pageNumber", "0")
                        .param("pageSize", "5")
                        .param("sortBy", "model")
                        .param("sortDir", "DESC"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].make").value("Toyota"))
                .andExpect(jsonPath("$[0].clientName").value("John Doe"));

        verify(carService).getAllCars(new CarFilterDTO("Toyota", null, "John Doe", null), pageable);
    }

    @Test
    void getAllCars_WithInvalidSortDirection_ShouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/cars")
                        .param("sortBy", "make")
                        .param("sortDir", "INVALID"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCarById_WithValidId_ShouldReturnCar() throws Exception {
        // Given
        CarDTO car = sampleCars.get(0);
        when(carService.getCarById(1L)).thenReturn(car);

        // When & Then
        mockMvc.perform(get("/api/v1/cars/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.make").value("Toyota"))
                .andExpect(jsonPath("$.model").value("Camry"))
                .andExpect(jsonPath("$.clientName").value("John Doe"))
                .andExpect(jsonPath("$.maintainerName").value("Service Center A"));

        verify(carService).getCarById(1L);
    }

    @Test
    void getAllCars_WithNegativePageNumber_ShouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/cars")
                        .param("pageNumber", "-1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllCars_WithNegativePageSize_ShouldReturnBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/v1/cars")
                        .param("pageSize", "-1"))
                .andExpect(status().isBadRequest());
    }
}