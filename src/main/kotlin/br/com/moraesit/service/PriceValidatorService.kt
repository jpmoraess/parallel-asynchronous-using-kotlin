package br.com.moraesit.service

import br.com.moraesit.domain.checkout.CartItem
import br.com.moraesit.util.CommonUtil

class PriceValidatorService {
    fun isCartItemInvalid(cartItem: CartItem): Boolean {
        val itemId = cartItem.itemId
        CommonUtil.delay(500)
        if (itemId == 7 || itemId == 9 || itemId == 11)
            return true
        return false
    }
}