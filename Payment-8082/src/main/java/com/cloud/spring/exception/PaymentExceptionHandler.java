package com.cloud.spring.exception;

import io.grpc.*;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.interceptor.GrpcGlobalServerInterceptor;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
@GrpcGlobalServerInterceptor
@Slf4j
public class PaymentExceptionHandler implements ServerInterceptor {

    @Override
    public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {

        ServerCall.Listener<ReqT> listener = serverCallHandler.startCall(serverCall, metadata);

        return new ForwardingServerCallListener.SimpleForwardingServerCallListener<>(listener) {
            @Override
            public void onHalfClose() {

                try {
                    super.onHalfClose();
                }catch (Exception e){

                    log.error("Exception occurred: {}", e.getMessage(), e);
                    StatusRuntimeException statusException = mapExceptionToStatus(e);
                    serverCall.close(statusException.getStatus(),new Metadata());
                }
            }
        };
    }

    private StatusRuntimeException mapExceptionToStatus(Exception e) {

        if (e instanceof IllegalArgumentException) {
            return Status.INVALID_ARGUMENT.withDescription(e.getMessage()).asRuntimeException();
        } else if (e instanceof SQLException) {
            return Status.UNAVAILABLE.withDescription("Sql error: " + e.getMessage()).asRuntimeException();
        } else {
            return Status.INTERNAL.withDescription("Internal server error").asRuntimeException();
        }
    }
}
