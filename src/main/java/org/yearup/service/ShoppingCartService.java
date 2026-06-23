package org.yearup.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.yearup.models.*;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId)
    {
        // load the user's cart rows, look up each product, and build the ShoppingCart

       List<CartItem> cartItems = shoppingCartRepository.findByUserId(userId);
       ShoppingCart cart = new ShoppingCart();

       for(CartItem cartItem : cartItems) {
           ShoppingCartItem item = new ShoppingCartItem();
           item.setProduct(productService.getById(cartItem.getProductId()));
           item.setQuantity(cartItem.getQuantity());
           cart.add(item);
       }
        return cart;
    }


    // add additional methods here
    public CartItem addToCart(int userId, int productId){
        CartItem cartItems = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        if (cartItems == null){
            CartItem newItem = new CartItem();
            newItem.setUserId(userId);
            newItem.setProductId(productId);
            newItem.setQuantity(1);

            shoppingCartRepository.save(newItem);

            return newItem;
        }
        int i = cartItems.getQuantity();
        cartItems.setQuantity(i + 1);

        return shoppingCartRepository.save(cartItems);
    }

    public CartItem update(int userId, int productId, ShoppingCartItem item)
    {
        CartItem cartItems = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        if(cartItems == null) {
            throw new RuntimeException("product not found!");
        }

        cartItems.setQuantity(item.getQuantity());

        return shoppingCartRepository.save(cartItems);

    }


    @Transactional
    public void delete(int userId)
    {
        shoppingCartRepository.deleteByUserId(userId);
    }
}
