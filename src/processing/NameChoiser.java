package processing;

public enum NameChoiser {
    CREATION_DATE("Дата создания"),
    DIRECTORY_AND_NAME("Директория + имя"),
    YEAR_AND_NAME("Год + имя"),
    DEL_PART_NAME("Удалить часть имени"),
    ADD_PART_NAME("Добавить часть имени"),
    MAKE_OWN_NAME("Задать свое имя"),
    DATE_BY_MASK("Дата по маске");

    private String name;

    NameChoiser(String name) {
        this.name = name;
    }

    public String getValue() {
        return this.name;
    }

    public static NameChoiser getValueformString(String value) {
        for (NameChoiser name : NameChoiser.values()) {
            if (name.name.equals(value)) {
                return name;
            }
        }
        return CREATION_DATE;
    }

    public static String[] toStringArray(){
        String[] arrayOfenum = new String[NameChoiser.values().length];
        int i = 0;
        for(NameChoiser name: NameChoiser.values()){
            arrayOfenum[i] = name.name;
            i++;
        }
        return arrayOfenum;
    }
}
