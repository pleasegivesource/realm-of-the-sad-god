package me.ethius.client.rotsg.screen

import me.ethius.client.Client
import me.ethius.client.renderer.Axis
import me.ethius.client.rotsg.gui.Button
import me.ethius.client.rotsg.gui.ldu
import me.ethius.shared.bool
import me.ethius.shared.events.def.KeyPressedEvent
import me.ethius.shared.events.def.MouseClickedEvent
import me.ethius.shared.measuringTimeMS
import me.ethius.shared.withAlpha
import org.joml.Matrix4dStack
import org.lwjgl.glfw.GLFW.*

class InGameMenuScreen:Screen() {

    override val doesGuiPauseGame:bool
        get() = false
    override val shouldCloseOnEsc:bool
        get() = false

    private val startTime = measuringTimeMS()
    
    private val buttons:ArrayList<Button> = ArrayList()

    override fun render(matrix:Matrix4dStack) {
        Client.render.drawRectAlpha(matrix, 0.0, 0.0, Client.window.scaledWidth.toDouble(), 100.0, ldu, 0.4f)
        Client.render.drawGradientRect(matrix, 0.0, 100.0, Client.window.scaledWidth.toDouble(), 107.0, withAlpha(ldu, 0.7f), 0x00000000L, Axis.vertical)
        Client.render.drawRectAlpha(matrix, 0.0, Client.window.scaledHeight - 100.0, Client.window.scaledWidth.toDouble(), Client.window.scaledHeight.toDouble(), ldu, 0.4f)
        Client.render.drawGradientRect(matrix, 0.0, Client.window.scaledHeight - 107.0, Client.window.scaledWidth.toDouble(), Client.window.scaledHeight - 100.0, 0x00000000L, withAlpha(ldu, 0.7f), Axis.vertical)
        for (button in buttons) {
            button.render(matrix)
        }
    }

    override fun onMouse(event:MouseClickedEvent) {
        for (button in buttons) {
            button.onMouse(event)
        }
    }

    override fun onKey(event:KeyPressedEvent) {
        super.onKey(event)
        for (button in buttons) {
            button.onKey(event)
        }
        if (event.key == GLFW_KEY_ESCAPE && event.action == GLFW_PRESS && measuringTimeMS() - startTime > 100) {
            close()
        }
    }
    
    init {
        renderLayer = ScreenRenderLayer.after
        buttons.add(Button.make {
            // Resume button
            centerX = Client.window.midX.toDouble()
            centerY = Client.window.midY.toDouble() - 75

            width = 200.0
            height = 50.0

            setText { "Resume" }
            setOnLeft { Client.screen = null }
        })
        buttons.add(Button.make {
            // Options button
            centerX = Client.window.midX.toDouble()
            centerY = Client.window.midY.toDouble() - 25

            width = 200.0
            height = 50.0

            setText { "Options" }
            setOnLeft { Client.screen = OptionsScreen() }
        })
        buttons.add(Button.make {
            // Main menu button
            centerX = Client.window.midX.toDouble()
            centerY = Client.window.midY.toDouble() + 25

            width = 200.0
            height = 50.0

            setText { "Main menu" }
            setOnLeft {
                Client.reset()
                renderLayer = ScreenRenderLayer.hud
            }
        })
        buttons.add(Button.make {
            // Quit button
            centerX = Client.window.midX.toDouble()
            centerY = Client.window.midY.toDouble() + 75

            width = 200.0
            height = 50.0

            setText { "Quit" }
            setOnLeft { Client.tasksToRun.add { glfwSetWindowShouldClose(Client.window.handle, true) } }
        })
    }

}