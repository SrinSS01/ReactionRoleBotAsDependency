# Get started

### Create `Emoji` instance

```java
Emoji emoji = Emoji.fromFormat("emoji format");
```
_format should follow discord's emoji format_  
**example:**
- _Custom emoji_: `<:emoji name:emojiID>`
- _Unicode emoji_: `U+XXXX` **XXXX**: code point of the unicode emoji
### Create `Role` instance
```java
Role role = Role.fromID("role id");
```

### Create `Message` instance
```java
Message message = Message.fromID("message id");
message.add(emoji, role)
        .add(emoji, role);
```

### Create `Channel` instance

```java
Channel channel = Channel.fromID("channel id");
channel.add(message1)
        .add(message2)
        .add(message3);
```

### Create ReactionRoleMap

```java
ReactionRoleMapBuilder builder = ReactionRoleMapBuilder.create();
builder.add(channel1)
       .add(channel2)
       .add(channel3); // chain channel instances
```

## Add event
```java
ReactionRoleMapBuilder builder = ReactionRoleMapBuilder.create();
        builder.add(channel1)
        .add(channel2)
        .add(channel3);

Map<String, Map<String, Map<String, String>>> map = builder.build();

JDABuilder.create() // setup jda builder instance as usual
        .addEventListeners(
                /* ... */
                // your other event listeners
                ReactionEvents.createEvent(map)
        );
```