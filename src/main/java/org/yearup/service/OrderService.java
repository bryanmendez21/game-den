package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.*;
import org.yearup.repository.OrderLineItemRepository;
import org.yearup.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderLineItemRepository orderLineItemRepository;
    private final ShoppingCartService shoppingCartService;
    private final ProfileService profileService;

    public OrderService(OrderRepository orderRepository, OrderLineItemRepository orderLineItemRepository, ShoppingCartService shoppingCartService, ProfileService profileService) {
        this.orderRepository = orderRepository;
        this.orderLineItemRepository = orderLineItemRepository;
        this.shoppingCartService = shoppingCartService;
        this.profileService = profileService;
    }

    public Order checkOut(int userId) {
        ShoppingCart cart = shoppingCartService.getByUserId(userId);
        Order order = new Order();

        order.setUserId(userId);
        order.setDate(LocalDate.now().toString());

        Profile profile = profileService.getProfileByUserId(userId);
        order.setAddress(profile.getAddress());
        order.setCity(profile.getCity());
        order.setState(profile.getState());
        order.setZip(profile.getZip());

        order.setShippingAmount(BigDecimal.ZERO);

        Order savedOrder = orderRepository.save(order);

        for(ShoppingCartItem item : cart.getItems().values()) {
            OrderLineItem orderLineItem = new OrderLineItem();

            orderLineItem.setOrderId(savedOrder.getOrderId());
            orderLineItem.setProductId(item.getProductId());
            orderLineItem.setQuantity(item.getQuantity());
            orderLineItem.setDiscount(item.getDiscountPercent());
            orderLineItem.setSalesPrice(item.getLineTotal());

            orderLineItemRepository.save(orderLineItem);
        }

        shoppingCartService.delete(userId);

        return savedOrder;
    }
}

