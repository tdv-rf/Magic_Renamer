/**
 * Created by uset on 27.06.17.
 */
public class MyError extends Exception {
    EnumError description;

    public MyError(EnumError description){
        this.description = description;
    }
}
