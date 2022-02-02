package com.open.abyss.leaf

import com.open.abyss.Script
import com.open.abyss.extensions.nearestGameObject
import org.powbot.api.Condition
import org.powbot.api.Tile
import org.powbot.api.rt4.GameObject
import org.powbot.api.rt4.Movement
import org.powbot.api.rt4.Objects
import org.powbot.api.rt4.Players
import org.powbot.api.script.tree.Leaf

class CrossDitch(script: Script) : Leaf<Script>(script, "Crossing ditch") {

    private val ditchTile: Tile get() = script.configuration.teleport.ditchTile

    override fun execute() {

        val ditch = Objects.nearestGameObject("Wilderness ditch")

        if (ditch == GameObject.Nil) {
            Movement.builder(ditchTile)
                .setWalkUntil { ditchTile.distance() < 3 }
                .setRunMin(50)
                .setRunMax(70)
                .move()
        } else {
            walkToDitch(ditch)
        }
    }

    private fun walkToDitch(ditch: GameObject) {
        if (!ditch.inViewport()) {
            Movement.builder(ditch.tile.derive(0, -2))
                .setWalkUntil { ditch.tile.distance() < 3 }
                .setRunMin(50)
                .setRunMax(70)
                .move()
        }

        if (ditch.inViewport() && ditch.interact("Cross")) {
            Condition.wait({ Players.local().y() >= 3523 }, 1000, 10)
        }
    }

}