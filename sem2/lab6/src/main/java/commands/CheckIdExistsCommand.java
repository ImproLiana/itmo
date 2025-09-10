package commands;

import java.io.Serializable;

import myCommands.CommandResponse;
import progclasses.Dragon;
import server.ServerCollectionManager;

    /**
     * Команда для проверки существования дракона с заданным ID.
     */
    public class CheckIdExistsCommand implements ICommand, Serializable {

        /**
         * Проверяет, существует ли объект с указанным ID в коллекции.
         * @param argument ожидается Long (ID)
         * @param manager менеджер коллекции
         * @return CommandResponse с результатом
         */
        @Override
        public CommandResponse execute(Object argument, ServerCollectionManager manager) {
            Long id;
            try {
                id = Long.parseLong(argument.toString());
            } catch (Exception e) {
                return new CommandResponse("Некорректный ID: " + argument, false);
            }

            if (id == null) return new CommandResponse("ID не может быть null", false);

            boolean exists = manager.getCollection().stream()
                    .filter(dragon -> dragon.getId() != null)
                    .anyMatch(dragon -> dragon.getId().equals(id));

            return new CommandResponse(
                    exists ? "Объект с ID " + id + " найден." : "Объект с таким ID не найден.",
                    exists
            );
        }



        @Override
        public String getDescription() {
            return "проверяет, существует ли элемент коллекции с указанным ID";
        }
    }


