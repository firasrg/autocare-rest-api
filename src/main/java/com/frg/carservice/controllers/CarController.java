package com.frg.carservice.controllers;

import com.frg.carservice.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController {

  private final CarService carService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<String> getComplexCars() {
    String jsonString = carService.getComplexJson();
    return ResponseEntity.ok(jsonString);
  }
}
