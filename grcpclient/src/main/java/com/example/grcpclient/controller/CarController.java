package com.example.grcpclient.controller;


import com.example.grcpclient.dto.CarDto;
import com.example.grcpclient.service.CarClientService;
import com.example.grcpclient.service.CarClientService2;
import interfaces.Car;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {

    @Autowired
    private CarClientService carClientService;
    @Autowired
    private CarClientService2 carClientService2;


    @GetMapping
    public List<CarDto> getCars() {
        return carClientService2.getCars();
    }
}
