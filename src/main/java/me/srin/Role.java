package me.srin;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "fromID")
@EqualsAndHashCode
public class Role {
    private final String id;
}
