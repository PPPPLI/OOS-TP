package com.cloud.spring.tp1.controller;

import com.cloud.spring.tp1.entity.Car;
import com.cloud.spring.tp1.service.CarService;
import com.cloud.spring.tp1.service.impl.CarPaymentServiceImpl;
import jakarta.annotation.Resource;
import net.devh.boot.grpc.examples.lib.PaymentReply;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class CarController {

    @Resource
    CarService carService;

    @Resource
    CarPaymentServiceImpl carPaymentService;


    @GetMapping("/")
    public String hello() {

        return "Hello";
    }

    @PostMapping("/cars/add")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {

        Car new_car = carService.addCar(car);

        return ResponseEntity.ok(new_car);

    }

    @GetMapping("/cars/{plateNumber}")
    public ResponseEntity<Car> getCar(@PathVariable String plateNumber, String... a) {

        Car car = carService.getCarByPlateNumber(plateNumber);

        return ResponseEntity.ok(car);
    }

    @PutMapping("cars/rent")
    public ResponseEntity<?> rentCar(@RequestBody Car car
    ) {

        PaymentReply paymentRes = carPaymentService.rentPayment(car.getPlateNumber(), car.getPrice());

        if (!paymentRes.getIsSuccess()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Please try later");
        }

        int result = carService.rentCar(car.getBeginDate(),car.getEndDate(),car.getPlateNumber());

        if(result == 1) {

            return ResponseEntity.ok("Rent successfully");
        }

        carPaymentService.deletePayment(paymentRes.getPaymentId());
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Please try later");
    }

    @PutMapping("/cars/back/{plateNumber}")
    public ResponseEntity<String> getBackCar(@PathVariable String plateNumber) {

        int result = carService.getBackCar(plateNumber);

        return result == 1? ResponseEntity.ok("Return successfully"):ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Please try later");

    }


}
