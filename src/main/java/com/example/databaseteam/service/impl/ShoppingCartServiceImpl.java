package com.example.databaseteam.service.impl;

import com.example.databaseteam.model.CartItem;
import com.example.databaseteam.model.Product;
import com.example.databaseteam.model.ShoppingCart;
import com.example.databaseteam.model.UserDtls;
import com.example.databaseteam.repository.CartItemsRepository;
import com.example.databaseteam.repository.ShoppingCartRepository;
import com.example.databaseteam.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemsRepository cartItemsRepository;

    @Override
    public ShoppingCart addToCart(Product product, UserDtls user) {
        ShoppingCart cart = user.getShoppingCart();
        if (cart==null){
            cart = new ShoppingCart();
        }
        Set<CartItem> listCartItems = cart.getCartItems();
        CartItem item = findItem(product,listCartItems);
        if (listCartItems == null){
            listCartItems = new HashSet<>();
        }
        if (item==null){
            item = new CartItem();
            item.setProduct(product);
            item.setPrice(product.getPrice());
            item.setQuantity(1);
            item.setCart(cart);
            listCartItems.add(item);
            cartItemsRepository.save(item);
        }
        else {
            item.setQuantity(item.getQuantity()+1);
            cartItemsRepository.save(item);
        }
        cart.setCartItems(listCartItems);
        cart.setUser(user);
        user.setShoppingCart(cart);
        return shoppingCartRepository.save(cart);//tra ve 1 shoppingcart sau khi luu
    }

    @Override
    public ShoppingCart deleteItemFromCart(Product product, UserDtls user) {
        ShoppingCart cart = user.getShoppingCart();
        Set<CartItem> listCartItems = cart.getCartItems();
        CartItem item = findItem(product,listCartItems);
        int quantity = item.getQuantity() - 1;
        if (quantity<=0){
            return removeItemFromCart(product,user);
        }
        item.setQuantity(quantity);
        cart.setCartItems(listCartItems);
        return shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCart updateItemFromCart(Product product, UserDtls user) {
        ShoppingCart cart = user.getShoppingCart();
        Set<CartItem> listCartItems = cart.getCartItems();
        CartItem item = findItem(product,listCartItems);
        int quantity = item.getQuantity() + 1;
        item.setQuantity(quantity);
        cart.setCartItems(listCartItems);
        return shoppingCartRepository.save(cart);
    }

    @Override
    public ShoppingCart removeItemFromCart(Product product, UserDtls user) {
        ShoppingCart cart = user.getShoppingCart();
        Set<CartItem> listCartItems = cart.getCartItems();
        CartItem item = findItem(product,listCartItems);
        listCartItems.remove(item);
        cart.setCartItems(listCartItems);
        return shoppingCartRepository.save(cart);
    }

    private CartItem findItem(Product product,Set<CartItem> listCartItems){
        if (listCartItems==null) return null;
        CartItem cartItem = null;
        for (CartItem item:listCartItems){
            if (item.getProduct().getId() == product.getId()) return item;
        }
        return null;
    }
}
