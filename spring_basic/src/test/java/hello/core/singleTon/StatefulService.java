package hello.core.singleTon;

public class StatefulService {

    private int price; // 상태를 유지하는 필드 <== 문제발생

    //public void order(String name,int price){
      //  System.out.println("name = " + name + ", price = "+ price);
        //this.price=price;
    //}

    public int order(String name, int price){ //해결
        System.out.println("name = " + name + ", price = "+price);
        return price;
    }

    public int getPrice(){
        return price;
    }

}
