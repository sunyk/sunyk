package register;

/**
 * Create by sunyang on 2018/6/25 23:46
 * For me:One handred lines of code every day,make myself stronger.
 */
public enum RegiterEnum {
    RED("red","121"),
    BLANK("blank","12"),
    WHILE("while","1");

    private String name;

    private String color;

    RegiterEnum(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }
}
