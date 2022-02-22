package ml.bloking.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import ml.bloking.message.MessageBuilder;
import ml.bloking.utils.type.ErrorType;
import net.dv8tion.jda.api.entities.MessageChannel;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public final class ErrorEmbed {
    String errorName;
    String description;

    ErrorType errorType;

    public void send(MessageChannel channel) {
        if (channel == null) throw new IllegalStateException("Channel is null");

        new MessageBuilder.EmbedBuilder()
                .create()
                .setColor(errorType.getColor())
                .addTitle(errorName)
                .addField(errorType.getTitle(), description, true)
                .build(channel);
    }
}
