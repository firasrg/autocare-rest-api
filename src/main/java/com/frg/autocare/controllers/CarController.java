/**
 * AutoCare REST API - Car controller class.
 * Copyright (C) 2024  AutoCare REST API original author or authors.
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
package com.frg.autocare.controllers;

import com.frg.autocare.dto.CarDTO;
import com.frg.autocare.dto.CarFilterDTO;
import com.frg.autocare.enums.CarSortField;
import com.frg.autocare.services.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

import static com.frg.autocare.constants.ControllerConstants.DEFAULT_PAGE_NUMBER;
import static com.frg.autocare.constants.ControllerConstants.BASE_ENDPOINT_CARS;
import static com.frg.autocare.constants.ControllerConstants.DEFAULT_PAGE_SIZE;
import static com.frg.autocare.constants.ControllerConstants.DEFAULT_SORT_BY;
import static com.frg.autocare.constants.ControllerConstants.DEFAULT_SORT_DIRECTION;

@Validated
@RestController
@RequestMapping(BASE_ENDPOINT_CARS)
@RequiredArgsConstructor
@Tag(name = "Cars", description = "Car management APIs")
public class CarController {

  private final CarService carService;

  /**
   * Retrieve a paginated and optionally filtered list of cars.
   *
   * @param make Filter by car make (optional)
   * @param model Filter by car model (optional)
   * @param ownerName Filter by owner's name (optional)
   * @param maintainerName Filter by maintainer's name (optional)
   * @param pageNumber Page number for pagination (default is 0).
   * @param pageSize Page size for pagination (default is 10).
   * @param sortBy Field to sort by (default is "id")
   * @param sortDir Sort direction, either ASC or DESC (default is ASC)
   * @return ResponseEntity containing a list of CarDTO objects
   */
  @GetMapping
  @Operation(
      summary = "Get all cars",
      description = "Retrieve a list of all cars with their details")
  @ApiResponse(responseCode = "200", description = "List of cars retrieved successfully")
  @ApiResponse(responseCode = "400",description = "Bad Request - Invalid query parameters")
  public ResponseEntity<List<CarDTO>> getAllCars(
          @Parameter(description = "Filter by car make", required = false)
          @RequestParam(required = false) String make,

          @Parameter(description = "Filter by car model", required = false)
          @RequestParam(required = false) String model,

          @Parameter(description = "Filter by owner's name", required = false)
          @RequestParam(required = false) String ownerName,

          @Parameter(description = "Filter by maintainer's name", required = false)
          @RequestParam(required = false) String maintainerName,

          @Min(0)
          @Parameter(description = "Page number for pagination", required = false)
          @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER) int pageNumber,

          @Min(0)
          @Parameter(description = "Page size for pagination", required = false)
          @RequestParam(defaultValue = DEFAULT_PAGE_SIZE) int pageSize,

          @Parameter(description = "Field to sort by", required = false)
          @RequestParam(defaultValue = DEFAULT_SORT_BY) CarSortField sortBy,

          @Parameter(description = "Sort direction: ASC or DESC", required = false)
          @RequestParam(defaultValue = DEFAULT_SORT_DIRECTION) Sort.Direction sortDir) {

    Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(sortDir,sortBy.name()));
    CarFilterDTO carFilter =  new CarFilterDTO(make, model, ownerName, maintainerName);
    List<CarDTO> cars = carService.getAllCars(carFilter, pageable).getContent();
    return ResponseEntity.ok(cars);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Get car by ID", description = "Retrieve a car by its ID")
  @ApiResponse(responseCode = "200", description = "Car retrieved successfully")
  @ApiResponse(responseCode = "404", description = "Car not found")
  public ResponseEntity<CarDTO> getCarById(@PathVariable Long id) {
    return ResponseEntity.ok(carService.getCarById(id));
  }
}
