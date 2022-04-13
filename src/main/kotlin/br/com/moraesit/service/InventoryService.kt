package br.com.moraesit.service

import br.com.moraesit.domain.Inventory
import br.com.moraesit.domain.ProductOption
import br.com.moraesit.util.CommonUtil.Companion.delay

class InventoryService {

    fun retrieveInventory(productOption: ProductOption): Inventory {
        delay(500)
        return Inventory(count = 2)
    }
}