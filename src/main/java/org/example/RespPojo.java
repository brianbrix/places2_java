package org.example;

import java.util.List;

public abstract class RespPojo {
    private List candidates;
    public String error_message;
    public String status;

    public List getCandidates() {
        return candidates;
    }
}
