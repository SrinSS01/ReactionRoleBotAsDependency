package me.srin;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor(staticName = "fromID")
public class Message {
    private final String id;
    private final Map<String, String> map = new HashMap<>();

    public Message add(Emoji format, Role id) {
        map.put(format.getName(), id.getId());
        return this;
    }
}
