package com.cloud.spring.tp1.service.impl;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import net.devh.boot.grpc.examples.lib.CarPaymentServiceGrpc;
import net.devh.boot.grpc.examples.lib.CarRequest;
import net.devh.boot.grpc.examples.lib.DeleteRequest;
import net.devh.boot.grpc.examples.lib.PaymentReply;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CarPaymentServiceImpl {

    @GrpcClient("CarPaymentService")
    private CarPaymentServiceGrpc.CarPaymentServiceBlockingStub serviceBlockingStub;

    public PaymentReply rentPayment(String plateNumber,double price){

        try {

            PaymentReply res = serviceBlockingStub.rentPayment(CarRequest.newBuilder().setCarId(plateNumber).setPrice(price).build());

            log.info("Rent payment is executed successfully: {}", res.getIsSuccess() ? "Yes" : "No");

            return res;

        }catch (StatusRuntimeException e){

            Status status = e.getStatus();
            log.error("Error: {} - {}",status.getCode(),status.getDescription());
        }

        return PaymentReply.newBuilder().setIsSuccess(false).build();
    }

    public boolean deletePayment(String paymentId){

        return serviceBlockingStub.deletePayment(DeleteRequest.newBuilder().setPaymentId(paymentId).build()).getIsSuccess();

    }
}
