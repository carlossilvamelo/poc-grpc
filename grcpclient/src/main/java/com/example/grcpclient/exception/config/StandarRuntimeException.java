package com.example.grcpclient.exception.config;


import io.grpc.Metadata;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import javax.annotation.Nullable;

public class StandarRuntimeException extends StatusRuntimeException {
    public StandarRuntimeException(Status status) {
        super(status);
    }

    public StandarRuntimeException(Status status, @Nullable Metadata trailers) {
        super(status, trailers);
    }
}
