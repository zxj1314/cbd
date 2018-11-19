package com.test.cbd.websocket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WsCacheBean implements Serializable {

    private String token;

    private String userId;

    private long connectTime;

    private long lastTime;

    private Map<String, Object> sipMap = new HashMap<String, Object>();//<callId, xxxx>
}
