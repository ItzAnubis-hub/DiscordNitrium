package ml.bloking.command;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.dv8tion.jda.api.entities.MessageChannel;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public abstract class AbstractCommand {
    @Getter
    String ping;

    @Setter
    @Getter
    @NonFinal
    MessageChannel messageChannel;

    public abstract void execute();
}
