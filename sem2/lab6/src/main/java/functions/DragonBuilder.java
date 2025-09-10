package functions;

import progclasses.*;

import java.util.Objects;

/**
 * Строитель для создания объектов класса Dragon.
 * Обеспечивает пошаговое конструирование дракона с проверкой валидности параметров.
 */
public class DragonBuilder {
    private String name;
    private Coordinates coordinates;
    private long age;
    private Color color;
    private DragonType type;
    private DragonCharacter character;
    private Person killer;

    /**
     * Создает новый экземпляр строителя драконов.
     */
    public DragonBuilder() {}

    /**
     * Устанавливает имя дракона.
     * @param name имя дракона (не может быть null или пустым)
     * @return текущий экземпляр строителя
     * @throws IllegalArgumentException если имя пустое
     * @throws NullPointerException если имя равно null
     */
    public DragonBuilder setName(String name) {
        this.name = Objects.requireNonNull(name, "Имя дракона не может быть null");
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Имя дракона не может быть пустым");
        }
        return this;
    }

    /**
     * Устанавливает координаты дракона.
     * @param x координата x (должна быть ≤ 511)
     * @param y координата y
     * @return текущий экземпляр строителя
     */
    public DragonBuilder setCoordinates(long x, long y) {
        if (x > 511) {
            throw new IllegalArgumentException("Координата x должна быть ≤ 511");
        }
        this.coordinates = new Coordinates(x, y);
        return this;
    }

    /**
     * Устанавливает возраст дракона.
     * @param age возраст (должен быть > 0)
     * @return текущий экземпляр строителя
     * @throws IllegalArgumentException если возраст не положительный
     */
    public DragonBuilder setAge(long age) {
        if (age <= 0) {
            throw new IllegalArgumentException("Возраст должен быть положительным числом");
        }
        this.age = age;
        return this;
    }

    /**
     * Устанавливает цвет дракона.
     * @param color цвет (не может быть null)
     * @return текущий экземпляр строителя
     * @throws NullPointerException если цвет равен null
     */
    public DragonBuilder setColor(Color color) {
        this.color = Objects.requireNonNull(color, "Цвет не может быть null");
        return this;
    }

    /**
     * Устанавливает тип дракона.
     * @param type тип дракона (может быть null)
     * @return текущий экземпляр строителя
     */
    public DragonBuilder setType(DragonType type) {
        this.type = type;
        return this;
    }

    /**
     * Устанавливает характер дракона.
     * @param character характер (не может быть null)
     * @return текущий экземпляр строителя
     * @throws NullPointerException если характер равен null
     */
    public DragonBuilder setCharacter(DragonCharacter character) {
        this.character = Objects.requireNonNull(character, "Характер не может быть null");
        return this;
    }

    /**
     * Устанавливает убийцу дракона.
     * @param name имя убийцы (если null, убийца не устанавливается)
     * @param passportID номер паспорта убийцы
     * @param location местоположение убийцы
     * @return текущий экземпляр строителя
     */
    public DragonBuilder setKiller(String name, String passportID, Location location) {
        if (name != null) {
            this.killer = new Person(name, passportID, location);
        } else {
            this.killer = null;
        }
        return this;
    }

    /**
     * Создает объект Dragon на основе установленных параметров.
     * @return новый экземпляр Dragon
     * @throws IllegalStateException если не установлены обязательные параметры
     */
    public Dragon build() {
        Objects.requireNonNull(name, "Не указано имя дракона");
        Objects.requireNonNull(coordinates, "Не указаны координаты");
        Objects.requireNonNull(color, "Не указан цвет");
        Objects.requireNonNull(character, "Не указан характер");

        Dragon dragon = new Dragon();
        dragon.setName(name);
        dragon.setCoordinates(coordinates);
        dragon.setAge(age);
        dragon.setColor(color);
        dragon.setType(type);
        dragon.setCharacter(character);
        dragon.setKiller(killer);

        return dragon;
    }

    /**
     * Преобразует параметры дракона в строку в формате скрипта.
     * @return строка с параметрами дракона, разделенными переносами строк
     */
    public String toScriptString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("\n");
        sb.append(coordinates.getX()).append(" ").append(coordinates.getY()).append("\n");
        sb.append(age).append("\n");
        sb.append(color).append("\n");
        sb.append(character).append("\n");
        sb.append(type != null ? type : "null").append("\n");

        if (killer != null) {
            sb.append(killer.getName()).append("\n");
            sb.append(killer.getPassportID() != null ? killer.getPassportID() : "null").append("\n");
            Location loc = killer.getLocation();
            sb.append(loc.getX()).append(" ")
                    .append(loc.getY()).append(" ")
                    .append(loc.getZ()).append(" ")
                    .append(loc.getName()).append("\n");
        } else {
            sb.append("null\n");
        }

        return sb.toString();
    }
}