package test;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Demo {
    private  int age ;
    public  Demo(){
        System.out.println("========I am demo ========");
    }
    static {

        System.out.println("========I am demo  static========");
    }
}
