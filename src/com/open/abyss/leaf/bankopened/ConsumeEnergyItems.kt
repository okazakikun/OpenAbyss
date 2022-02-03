package com.open.abyss.leaf.bankopened

import com.open.abyss.Script
import com.open.abyss.helpers.SupplyHelper
import org.powbot.api.Condition
import org.powbot.api.rt4.Bank
import org.powbot.api.rt4.Inventory
import org.powbot.api.rt4.Movement
import org.powbot.api.script.tree.Leaf

class ConsumeEnergyItems(script: Script) : Leaf<Script>(script, "Consume supplies") {
    override fun execute() {
        val energyPotion = Inventory.stream().filter { SupplyHelper.isEnergyPotion(it.name()) }.toList()

        if (Movement.energyLevel() == 100) {
            energyPotion.forEach {
                Bank.deposit(it.name(), 0)
            }
        } else if (energyPotion.isNotEmpty()) {
            energyPotion.forEach {
                val action = if (it.actions().contains("Drink")) "Drink" else "Eat"
                it.interact(action) && Condition.wait { !it.valid() }

                if (Movement.energyLevel() == 100) {
                    return
                }
            }
        }
    }
}