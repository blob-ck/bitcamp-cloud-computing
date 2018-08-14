package bitcamp.uml.classdiagram.ex2;

import java.util.List;

// Aggregation
// => 한 객체가 다른객체를 포함하는 것을 표현한다.
// => 포함하는 객체를 Container 라 부르고
//    포함되는 객체를 Containee 라 부른다
// => Container 와 Containee 의 생명주기가 다르다.
//    즉, Container(예:Order) 객체가 소멸되더라도 Containee(예:Customer, Product) 는 계속 유지된다.
public class Order {
    // Order객체가 포함할 객체는 필드로 선언한다.
    Customer customer;
    // 여러 개를 포함할 경우 배열이나 Collection객체로 구현한다.
    List<Product> products;
    
    public Order(Customer customer, List<Product> products) {
        this.customer = customer;
        this.products = products;
    }
}
