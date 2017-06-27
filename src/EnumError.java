
public enum EnumError {
    IOException(0,"Файл не найден!"),
    EMPTY_PATH(1,"Указанная директория пуста!"),
    SecurityException(2,"Нет доступа к файлу!");



    private final int code;
    private final String description;

    EnumError(int code, String description){
        this.code = code;
        this.description = description;
    }

    private int getCode(){
        return code;
    }

    private String getDescription(){
        return description;
    }

    @Override
    public String toString(){
        return code + ": " + description;
    }

}
