// 代码生成时间: 2025-09-19 03:17:41
package com.example.service;

import com.example.model.CartItem;
import com.example.model.ShoppingCart;
import com.google.inject.Inject;
import play.Logger;
import play.mvc.Http;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for shopping cart operations.
 */
public class ShoppingCartService {

    private final ShoppingCartRepository cartRepository;

    @Inject
    public ShoppingCartService(ShoppingCartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    /**
     * Adds an item to the shopping cart.
     *
     * @param userId The ID of the user.
     * @param itemId The ID of the item to add.
     * @param quantity The quantity of the item to add.
     * @return The updated shopping cart.
     */
    public ShoppingCart addItemToCart(String userId, String itemId, int quantity) {
        try {
            ShoppingCart cart = cartRepository.findCartByUserId(userId);
            if (cart == null) {
                cart = new ShoppingCart();
                cart.setUserId(userId);
                cartRepository.save(cart);
            }
            CartItem item = cart.findItem(itemId);
            if (item != null) {
                item.setQuantity(item.getQuantity() + quantity);
            } else {
                item = new CartItem(itemId, quantity);
                cart.addItem(item);
            }
            cartRepository.update(cart);
            return cart;
        } catch (Exception e) {
            Logger.error("Error adding item to cart: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Removes an item from the shopping cart.
     *
     * @param userId The ID of the user.
     * @param itemId The ID of the item to remove.
     * @return The updated shopping cart.
     */
    public ShoppingCart removeItemFromCart(String userId, String itemId) {
        try {
            ShoppingCart cart = cartRepository.findCartByUserId(userId);
            if (cart == null) {
                return null;
            }
            CartItem item = cart.removeItem(itemId);
            if (item != null) {
                cartRepository.update(cart);
            }
            return cart;
        } catch (Exception e) {
            Logger.error("Error removing item from cart: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Updates the quantity of an item in the shopping cart.
     *
     * @param userId The ID of the user.
     * @param itemId The ID of the item to update.
     * @param quantity The new quantity of the item.
     * @return The updated shopping cart.
     */
    public ShoppingCart updateItemQuantity(String userId, String itemId, int quantity) {
        try {
            ShoppingCart cart = cartRepository.findCartByUserId(userId);
            if (cart == null) {
                return null;
            }
            CartItem item = cart.findItem(itemId);
            if (item != null) {
                item.setQuantity(quantity);
                cartRepository.update(cart);
            }
            return cart;
        } catch (Exception e) {
            Logger.error("Error updating item quantity in cart: " + e.getMessage());
            throw e;
        }
    }

    /**
     * Clears the shopping cart.
     *
     * @param userId The ID of the user.
     * @return The cleared shopping cart.
     */
    public ShoppingCart clearCart(String userId) {
        try {
            ShoppingCart cart = cartRepository.findCartByUserId(userId);
            if (cart != null) {
                cart.clear();
                cartRepository.update(cart);
            }
            return cart;
        } catch (Exception e) {
            Logger.error("Error clearing cart: " + e.getMessage());
            throw e;
        }
    }
}
