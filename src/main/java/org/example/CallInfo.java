package org.example;

import java.io.Serializable;
import java.util.List;

public class CallInfo implements Serializable {

    public final String method;
    public final List<String> parameterNames;
    public final Object[] args;

    public CallInfo(String method_, List<String> parameterNames_, Object[] args_) {
        method = method_;
        parameterNames = parameterNames_;
        args = args_;
    }

    public String getMethod() {
        return method;
    }
}
