package me.srin;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "fromFormat")
@EqualsAndHashCode
public class Emoji {
    private final String name;
}
