package com.example.examenmariandaria.utils.events;

public class EntityChangeEvent implements Event{
    private ChangeEventType type;
    //private Order order;

//    public OrderEvent(ChangeEventType type, Order order) {
//        this.type = type;
//        this.order = order;
//    }

    public ChangeEventType getType() {
        return type;
    }

//    public Order getOrder() {
//        return order;
//    }
}
