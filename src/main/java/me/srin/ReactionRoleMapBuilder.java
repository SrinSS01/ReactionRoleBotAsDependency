package me.srin;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(staticName = "create")
@ToString
public class ReactionRoleMapBuilder  {
    @Getter
    private final HashMap<String, Map<String, Map<String, String>>> reactionRoleMap = new HashMap<>();

    public ReactionRoleMapBuilder add(Channel channel) {
        reactionRoleMap.put(channel.getId(), channel.getMap());
        return this;
    }

    public HashMap<String, Map<String, Map<String, String>>> build() {
        return reactionRoleMap;
    }
}
