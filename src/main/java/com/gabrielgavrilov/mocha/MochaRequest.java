package com.gabrielgavrilov.mocha;

import java.util.HashMap;
import java.util.Map;

public class MochaRequest {

    public HashMap<String, String> body = MochaClient.PAYLOAD;

    public String getRawPayload() {
        return MochaClient.PAYLOAD.toString();
    }

}
