package org.example;

import java.io.Serializable;

public class CallInfo implements Serializable {

    public final String method;
    public final Object[] args;

    public CallInfo(String method_, Object[] args_) {
        method = method_;
        args = args_;
    }

    public String getMethod() {
        return method;
    }
}
