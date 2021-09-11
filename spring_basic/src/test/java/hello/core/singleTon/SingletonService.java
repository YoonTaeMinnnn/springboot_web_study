package hello.core.singleTon;

public class SingletonService {  //싱글톤으로 설계
    private static final SingletonService instance = new SingletonService();  //static 이라 메모리에 하나만 올라간다.

    public static SingletonService getInstance(){
        return instance;
    }

    private SingletonService(){  //private 생성자를 통해 외부에서 new 로 객체생성을 방지!

    }

    public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
    }

    public static void main(String[] args) {

    }
}
