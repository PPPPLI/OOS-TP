package com.cloud.spring.tp1.service;

import com.cloud.spring.tp1.entity.Car;

import java.util.Date;

public interface CarService {

    Car getCarByPlateNumber(String plateNumber);

    int rentCar(Date begin, Date end, String plateNumber);

    Car addCar(Car car);

    int getBackCar(String plateNumber);
}
