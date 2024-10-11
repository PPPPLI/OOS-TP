package com.cloud.spring.tp1.jpaRepository;

import com.cloud.spring.tp1.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


public interface CarRepository extends JpaRepository<Car, Integer> {

    Car findByPlateNumber(String plateNumber);

    @Modifying
    @Transactional
    @Query("update car set beginDate = :begin, endDate = :end , isRent = true where plateNumber = :plateNumber")
    int updateCar(Date begin, Date end, String plateNumber);

    @Modifying
    @Transactional
    @Query("update car set isRent = false, beginDate = null, endDate = null where plateNumber = :plateNumber")
    int getBackCar(String plateNumber);

}
