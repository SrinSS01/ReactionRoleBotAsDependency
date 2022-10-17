package me.srin.events;

import lombok.RequiredArgsConstructor;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.entities.emoji.EmojiUnion;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor(staticName = "createEvent")
public class ReactionEvents extends ListenerAdapter {
    private final HashMap<String, Map<String, Map<String, String>>> reactions;
    private static final Logger LOGGER = LoggerFactory.getLogger(ReactionEvents.class);

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        Guild guild = event.getGuild();
        guild.getTextChannels().forEach(textChannel -> {
            Map<String, Map<String, String>> messageMap = reactions.get(textChannel.getId());
            if (messageMap != null) {
                messageMap.forEach((msgId, emoteRoleMap) -> emoteRoleMap.forEach((emote, roleId) ->
                        textChannel.addReactionById(
                                msgId,
                                Emoji.fromFormatted(emote)
                        ).onErrorMap(err -> {
                            LOGGER.error("{} for message ID: {}", err.getMessage(), msgId);
                            return null;
                        }).queue()
                ));
            }
        });
    }


    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent event) {
        if (event.getUserIdLong() == event.getJDA().getSelfUser().getIdLong()) return;
        String messageId = event.getMessageId();
        String channelId = event.getChannel().getId();

        var messageEmoteMap = reactions.get(channelId);
        if (messageEmoteMap == null) {
            return;
        }

        var info = messageEmoteMap.get(messageId);
        if (info != null) {
            EmojiUnion emote = event.getEmoji();
            String emoji = switch (emote.getType()) {
                case CUSTOM -> emote.asCustom().getAsMention();
                case UNICODE -> emote.asUnicode().getAsCodepoints();
            };
            LOGGER.info("added emoji: {} from messageId: {}", emoji, messageId);
            String roleId = info.get(emoji);
            if (roleId != null) {
                Guild guild = event.getGuild();
                Member member = Objects.requireNonNull(event.getMember());
                Role roleById = Objects.requireNonNull(guild.getRoleById(roleId));
                guild.addRoleToMember(member, roleById).queue();
            }
        }
    }

    @Override
    public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
        if (event.getUserIdLong() == event.getJDA().getSelfUser().getIdLong()) return;
        String messageId = event.getMessageId();
        String channelId = event.getChannel().getId();

        var messageEmoteMap = reactions.get(channelId);
        if (messageEmoteMap == null) {
            return;
        }

        var info = messageEmoteMap.get(messageId);
        if (info != null) {
            EmojiUnion emote = event.getEmoji();
            String emoji = switch (emote.getType()) {
                case CUSTOM -> emote.asCustom().getAsMention();
                case UNICODE -> emote.asUnicode().getAsCodepoints();
            };
            LOGGER.info("removed emoji: {} from messageId: {}", emoji, messageId);
            String roleId = info.get(emoji);
            if (roleId != null) {
                Guild guild = event.getGuild();
                Member member = Objects.requireNonNull(event.getMember());
                Role roleById = Objects.requireNonNull(guild.getRoleById(roleId));
                guild.removeRoleFromMember(member, roleById).queue();
            }
        }
    }
}
