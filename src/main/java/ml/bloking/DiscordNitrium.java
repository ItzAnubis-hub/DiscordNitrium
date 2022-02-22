package ml.bloking;

import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.experimental.UtilityClass;
import ml.bloking.command.AbstractCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.EventListener;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public final class DiscordNitrium {
    @Getter
    @NonFinal
    JDA jda;

    @Getter
    String token;

    @Getter
    @NonFinal
    JDABuilder jdaBuilder;

    public JDABuilder createBuilder() {
        if (token == null) throw new IllegalStateException("token can't be null");

        jdaBuilder = JDABuilder.createDefault(token);

        addListener(new CommandListener());

        return jdaBuilder;
    }

    public DiscordNitrium addListener(final EventListener eventListener) {
        if (jdaBuilder == null) throw  new IllegalStateException("jda builder can not be null");

        jdaBuilder.addEventListeners(eventListener);

        return this;
    }

    public DiscordNitrium createCommandPrefix(final char prefix) {
        CommandUtility.installPrefix(prefix);

        return this;
    }

    public DiscordNitrium createCommand(final AbstractCommand command) {
        if (command.getPing() == null) throw new IllegalStateException("ping can't be null.");

        CommandUtility.addCommand(command);
        // logger.log('created new command');

        return this;
    }


    @SneakyThrows
    public void build() {
        if (jdaBuilder == null) return;

        jdaBuilder.build();
    }

    @UtilityClass
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    private static class CommandUtility {
        final static Map<String, AbstractCommand> ABSTRACT_COMMAND_MAP = new HashMap<>();

        @Setter
        @Getter
        char prefix;

        public void addCommand(final AbstractCommand abstractCommand) {
            if (abstractCommand.getPing() == null) throw new IllegalStateException("ping can't be null");

            if (getPrefix() != ' ') {
                ABSTRACT_COMMAND_MAP.put(getPrefix() + abstractCommand.getPing(), abstractCommand);
                return;
            }

            ABSTRACT_COMMAND_MAP.put(abstractCommand.getPing(), abstractCommand);
        }

        public void installPrefix(final char prefix) {
            setPrefix(prefix);
        }

        public AbstractCommand getCommand(final String ping) {
            return ABSTRACT_COMMAND_MAP.get(ping);
        }

    }

    @RequiredArgsConstructor
    @FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
    private static class CommandListener extends ListenerAdapter {
        @Getter @Setter @NonFinal
        MessageChannel messageChannel;

        @Override
        public void onMessageReceived(@NotNull final MessageReceivedEvent event) {
            setMessageChannel(event.getChannel());

            val message = event.getMessage();
            val contentRaw = message.getContentRaw();
            val splitContent = contentRaw.split(" ");

            if (CommandUtility.getCommand(splitContent[0]) == null) return;

            val command = CommandUtility.getCommand(splitContent[0]);

            command.setMessageChannel(messageChannel);
            command.execute();
        }
    }


}
