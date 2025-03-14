package com.cloud.spring.tp1.controller;

import com.cloud.spring.tp1.entity.Car;
import com.cloud.spring.tp1.service.CarService;
import com.cloud.spring.tp1.service.impl.CarPaymentServiceImpl;
import jakarta.annotation.Resource;
import net.devh.boot.grpc.examples.lib.PaymentReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class CarController {

    @Autowired
    CarService carService;

    @Autowired
    CarPaymentServiceImpl carPaymentService;


    //Premier api de test
    @GetMapping("/")
    public String hello() {

        return "Hello";
    }

    //Ajouter la nouvelle voiture
    @PostMapping("/cars/add")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {

        Car new_car = carService.addCar(car);

        return ResponseEntity.ok(new_car);

    }

    //Rechercher la voiture correspondante par la plaque d'immatriculation
    @GetMapping("/cars/{plateNumber}")
    public ResponseEntity<Car> getCar(@PathVariable String plateNumber, String... a) {

        Car car = carService.getCarByPlateNumber(plateNumber);

        return ResponseEntity.ok(car);
    }

    //Louer la voiture
    @PutMapping("cars/rent")
    public ResponseEntity<?> rentCar(@RequestBody Car car
    ) {

        //Traiter d'abord le payment
        PaymentReply paymentRes = carPaymentService.rentPayment(car.getPlateNumber(), car.getPrice());

        //Si le service Payment est échoué, retourner directement le message d'erreur
        if (!paymentRes.getIsSuccess()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Please try later");
        }

        //Si la donnée de Payment est insérée, stocker ensuite la commande de location
        int result = carService.rentCar(car.getBeginDate(),car.getEndDate(),car.getPlateNumber());

        //En cas réussite, retourner le message de la réussite
        if(result == 1) {

            return ResponseEntity.ok("Rent successfully");
        }

        //Sinon faire une compensation pour la table Payment
        carPaymentService.deletePayment(paymentRes.getPaymentId());

        //Retourner le message d'erreur
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body("Please try later");
    }

    //Rendre la voiture
    @PutMapping("/cars/back/{plateNumber}")
    public ResponseEntity<String> getBackCar(@PathVariable String plateNumber) {

        int result = carService.getBackCar(plateNumber);

        return result == 1? ResponseEntity.ok("Return successfully"):ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Please try later");

    }


}
