package ml.bloking.message;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.dv8tion.jda.api.entities.MessageChannel;

import java.awt.*;

public final class MessageBuilder {

    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    @RequiredArgsConstructor
    private static final class Builder {
        @Getter
        MessageChannel channel;

        public void send(TextBuilder textBuilder) {
            getChannel().sendMessage(textBuilder.stringBuilder.toString()).queue();
        }

        public void send(EmbedBuilder embedBuilder) {
            getChannel().sendMessageEmbeds(embedBuilder.embedBuilder.build()).queue();
        }
    }

    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    public static final class TextBuilder {
        @NonFinal
        StringBuilder stringBuilder;

        public TextBuilder create() {
            stringBuilder = new StringBuilder();
            return this;
        }

        public TextBuilder addRow(String row) {
            stringBuilder.append(row);
            return this;
        }

        public void build(MessageChannel channel) {
            if (stringBuilder.length() == 0) throw new IllegalStateException("Builder can't be null");

            new Builder(channel).send(this);
        }
    }

    @FieldDefaults(makeFinal = true)
    public static final class EmbedBuilder {
        @NonFinal
        net.dv8tion.jda.api.EmbedBuilder embedBuilder;

        public EmbedBuilder create() {
            embedBuilder = new net.dv8tion.jda.api.EmbedBuilder();
            return this;
        }

        public EmbedBuilder addTitle(String title) {
            embedBuilder.setTitle(title);
            return this;
        }

        public EmbedBuilder setColor(Color color) {
            embedBuilder.setColor(color);
            return this;
        }

        public EmbedBuilder addField(final String name,
                                     final String value,
                                     final boolean inLine
        ) {
            embedBuilder.addField(name, value, inLine);
            return this;
        }

        public EmbedBuilder addAuthor(String name) {
            embedBuilder.setAuthor(name);
            return this;
        }

        public void build(MessageChannel channel) {
            if (embedBuilder == null || embedBuilder.isEmpty()) throw new IllegalStateException("Builder is null.");

            new Builder(channel).send(this);
        }

    }
}
