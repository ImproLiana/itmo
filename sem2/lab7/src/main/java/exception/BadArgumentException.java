package exception;

/**
 * Исключение, выбрасываемое при некорректных аргументах команды.
 * Используется для обработки ошибок, связанных с неверными входными данными.
 */
public class BadArgumentException extends Exception {
    /**
     * Создает новое исключение с указанным сообщением об ошибке.
     * @param error сообщение об ошибке, описывающее причину исключения
     */
    public BadArgumentException(String error) {
        super(error);
    }
}