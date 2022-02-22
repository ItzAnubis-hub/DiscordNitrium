package ml.bloking.event;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.UtilityClass;

import java.util.concurrent.CompletableFuture;

@UtilityClass
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public final class EventCaller {
    public void callAsync(AbstractEvent event) {
        if (event == null) throw new IllegalStateException("Event is null");

        CompletableFuture.runAsync(event::exec);
    }

    public void call(AbstractEvent event) {
        if (event == null) throw new IllegalStateException("Event is null");

        event.exec();
    }
}
