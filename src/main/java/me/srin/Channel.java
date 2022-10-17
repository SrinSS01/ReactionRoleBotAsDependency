package me.srin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor(staticName = "fromID")
public class Channel {
    private final String id;
    private final Map<String, Map<String, String>> map = new HashMap<>();

    public Channel add(Message id) {
        map.put(id.getId(), id.getMap());
        return this;
    }
}
