package org.example;

import java.io.Serializable;

public class CallInfo implements Serializable {

    private final String method;
    private final Object[] args;

    public CallInfo(String method_, Object[] args_) {
        method = method_;
        args = args_;
    }

    public String getMethod() {
        return method;
    }
}
