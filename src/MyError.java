
class MyError extends Exception {
    EnumError description;

    MyError(EnumError description){
        this.description = description;
    }
}
