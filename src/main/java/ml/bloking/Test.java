package ml.bloking;

import lombok.val;
import ml.bloking.command.AbstractCommand;
import ml.bloking.message.MessageBuilder;
import ml.bloking.utils.ErrorEmbed;
import ml.bloking.utils.type.ErrorType;

import java.awt.*;

public final class Test {
    public static void main(String[] args) {
        DiscordNitrium discordNitrium = new DiscordNitrium(
            "yourtoken"
        ).createCommandPrefix('C'); // C - char, prefix, maybe: %, !, $ OR @

        discordNitrium.createBuilder();

        discordNitrium.createCommand(new ICommand("ping"));
        discordNitrium.createCommand(new ICommandErrEmbed("throw"));

        discordNitrium.build();
    }

    public static final class ICommandErrEmbed extends AbstractCommand {
        public ICommandErrEmbed(String ping) {
            super(ping);
        }

        @Override
        public void execute() {
            new ErrorEmbed("TestError", "Test Error Description", ErrorType.LOGGER_EXCEPTION)
                    .send(getMessageChannel());
        }
    }

    public static final class ICommand extends AbstractCommand {
        public ICommand(String ping) {
            super(ping);
        }

        @Override
        public void execute() {
            val embed = new MessageBuilder.EmbedBuilder().create();

            long currentOnCreateEmbed = System.currentTimeMillis();

            embed.setColor(Color.GREEN);
            embed.addTitle("New embed");

            embed.addAuthor("Bloking");

            embed.addField("Name of field", "Value of field", false);
            embed.addField("Name of field2", "Value of field (inlined)", true);

            embed.build(getMessageChannel());

            long ping = System.currentTimeMillis() - currentOnCreateEmbed;

            new MessageBuilder.TextBuilder().create().addRow("Команда выполнилась за: " + ping + "ms").build(getMessageChannel());
        }
    }
}
