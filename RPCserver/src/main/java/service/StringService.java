package service;

public class StringService{

    public String uppercase(String str) {
        System.out.println("调用uppercase( "+str+" )");
        return str.toUpperCase();
    }
}
