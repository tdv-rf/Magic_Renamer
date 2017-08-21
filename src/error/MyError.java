package error;

public class MyError extends Exception {
    public EnumError description;

    public MyError(EnumError description){
        this.description = description;
    }
}
