package com.cloud.spring.tp1.service.impl;

import com.cloud.spring.tp1.entity.Car;
import com.cloud.spring.tp1.jpaRepository.CarRepository;
import com.cloud.spring.tp1.service.CarService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@Slf4j
public class CarServiceImpl implements CarService {

    @Resource
    private CarRepository carRepository;

    @Override
    public Car getCarByPlateNumber(String plateNumber) {

        return carRepository.findByPlateNumber(plateNumber);
    }

    @Override
    public Car addCar(Car car) {

        return carRepository.save(car);
    }

    @Override
    public int rentCar(Date begin, Date end, String plateNumber) {

        return carRepository.updateCar(begin,end, plateNumber);
    }

    @Override
    public int getBackCar(String plateNumber) {

        return carRepository.getBackCar(plateNumber);
    }
}
