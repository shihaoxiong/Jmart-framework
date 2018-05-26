package test;


public class Test {
    public static void main(String[] args) {
        String name = Demo.class.getName();
        Class<?> clazz;
        try {
            clazz =  Class.forName(name);
            System.out.println(clazz);
        } catch (ClassNotFoundException e) {
        }
    }
}
