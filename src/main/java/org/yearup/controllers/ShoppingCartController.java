package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;
import org.yearup.service.ShoppingCartService;
import org.yearup.service.UserService;

import java.security.Principal;

// convert this class to a REST controller
@RestController
@RequestMapping("cart")
@CrossOrigin
// only logged-in users should have access to these actions
@PreAuthorize("")
public class ShoppingCartController
{
    // a shopping cart controller depends on the service layer
    private ShoppingCartService shoppingCartService;
    private UserService userService;


    @GetMapping
    // each method in this controller requires a Principal object as a parameter
    public ShoppingCart getCart(Principal principal)
    {
        // get the currently logged-in username
        String userName = principal.getName();
        // find database user by username
        User user = userService.getByUserName(userName);
        int userId = user.getId();

        // use the shoppingCartService to get all items in the cart and return the cart
        return shoppingCartService.getByUserId(userId);
    }

    // add a POST method to add a product to the cart - the url should be
    // https://localhost:8080/cart/products/15  (15 is the productId to be added)
    // return the updated cart with status 201 Created
//    @PostMapping()
//    @PreAuthorize("")
//    public ResponseEntity<Product> addCart(@RequestBody Product product)
//    {
//        Product saved = productService.create(product);
//        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
//    }


    // add a PUT method to update an existing product in the cart - the url should be
    // https://localhost:8080/cart/products/15  (15 is the productId to be updated)
    // the BODY should be a ShoppingCartItem - quantity is the only value that will be updated; return the cart (200 OK)
//    @PutMapping("{id}")
//    @PreAuthorize("")
//    public Product updateCart(@PathVariable int id, @RequestBody Product product)
//    {
//        if (productService.getById(id) == null)
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
//
//        return productService.update(id, product);
//    }


    // add a DELETE method to clear all products from the current users cart
    // https://localhost:8080/cart  - return the (now empty) cart so the front end can refresh it (200 OK)
    @DeleteMapping("{id}")
    @PreAuthorize("")
    public ResponseEntity<Void> deleteCart(@PathVariable int id)
    {
        if (shoppingCartService.getByUserId(id) == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);

        shoppingCartService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
