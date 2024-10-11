package com.cloud.spring.service.impl;

import com.cloud.spring.entity.Payment;
import com.cloud.spring.repository.PaymentRepository;
import io.grpc.stub.StreamObserver;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.examples.lib.CarPaymentServiceGrpc;
import net.devh.boot.grpc.examples.lib.CarRequest;
import net.devh.boot.grpc.examples.lib.DeleteRequest;
import net.devh.boot.grpc.examples.lib.PaymentReply;
import net.devh.boot.grpc.server.service.GrpcService;

import java.time.LocalDateTime;
import java.util.UUID;

@GrpcService
@Slf4j
public class PaymentServiceImpl extends CarPaymentServiceGrpc.CarPaymentServiceImplBase {

    @Resource
    PaymentRepository paymentRepository;

    @Override
    public void rentPayment(CarRequest request, StreamObserver<PaymentReply> responseObserver) {

        //Stocker la donnée Payment pour chaque requête provenant de RentCar service
        paymentRepository.save(Payment.builder().plateNumber(request.getCarId())
                .paymentAmount(request.getPrice())
                .paymentDate(LocalDateTime.now())
                .build());
        //Retourner la donnée avec un type prédéfini
        responseObserver.onNext(PaymentReply.newBuilder().setIsSuccess(true).build());
        //Clôturer la connexion
        responseObserver.onCompleted();
    }

    //Requête de compensation en cas d'échec
    @Override
    public void deletePayment(DeleteRequest request, StreamObserver<PaymentReply> responseObserver) {

        paymentRepository.deleteById(UUID.fromString(request.getPaymentId()));

        responseObserver.onNext(PaymentReply.newBuilder().setIsSuccess(true).build());
        responseObserver.onCompleted();
    }
}
