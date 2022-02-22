package ml.bloking.utils.type;

import lombok.Getter;

import java.awt.*;

public enum ErrorType {

    SQL_EXCEPTION("Произошла внутренняя ошибка базы данных:", Color.RED),
    LOGGER_EXCEPTION("Произошла внутрення ошибка логгера:", Color.RED),
    TYPICAL_EXCEPTION("Произошла внутренняя ошибка сервера/бота:", Color.RED), // OR YOU CAN CREATE YOUR ERROR.
    ;

    @Getter
    String title;

    @Getter
    Color color;

    ErrorType(String title, Color color) {
        this.title = title;
        this.color = color;
    }
}
