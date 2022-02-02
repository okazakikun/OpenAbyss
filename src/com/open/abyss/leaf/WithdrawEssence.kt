package com.open.abyss.leaf

import com.open.abyss.Constants
import com.open.abyss.Constants.ITEM_RUNE_AIR
import com.open.abyss.Constants.ITEM_RUNE_EARTH
import com.open.abyss.Constants.ITEM_RUNE_LAW
import com.open.abyss.Constants.ITEM_PURE_ESSENCE
import com.open.abyss.Script
import com.open.abyss.models.RunecraftingMethod
import org.powbot.api.rt4.Bank
import org.powbot.api.script.tree.Leaf

class WithdrawEssence(script: Script) : Leaf<Script>(script, "Consume supplies") {

    var itemsToKeep: Array<String> = arrayOf()

    init {
        val items = mutableListOf(
            ITEM_PURE_ESSENCE,
            Constants.ITEM_SMALL_POUCH,
            Constants.ITEM_MEDIUM_POUCH,
            Constants.ITEM_LARGE_POUCH
        )
        if (script.configuration.teleport == RunecraftingMethod.House) {
            items.addAll(arrayOf(ITEM_RUNE_AIR, ITEM_RUNE_EARTH, ITEM_RUNE_LAW))
        }

        itemsToKeep = items.toTypedArray()
    }

    override fun execute() {
        if (Bank.depositAllExcept(*itemsToKeep)) {
            Bank.withdraw(ITEM_PURE_ESSENCE, 0)
        }
    }
}