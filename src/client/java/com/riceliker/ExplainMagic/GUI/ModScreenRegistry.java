package com.riceliker.ExplainMagic.GUI;


import com.riceliker.ExplainMagic.GUI.BCMGUI.BCMGUI;
import com.riceliker.ExplainMagic.ScreenHandler.BCMGUIHandler;
import com.riceliker.ExplainMagic.ScreenHandler.ScreenHandlerRegistry;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.text.Text;

public class ModScreenRegistry
{
    public ModScreenRegistry() {
        HandledScreens.<BCMGUIHandler, BCMGUI>register(
                ScreenHandlerRegistry.bcm_gui_handler,
                (handler, inventory, text) -> new BCMGUI(handler, inventory, Text.empty())
        );
    }
    
}
