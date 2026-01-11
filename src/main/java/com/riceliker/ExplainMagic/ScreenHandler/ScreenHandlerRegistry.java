package com.riceliker.ExplainMagic.ScreenHandler;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureSet;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

import static com.riceliker.ExplainMagic.ExplainMagic.MOD_ID;

public class ScreenHandlerRegistry
{
    public ScreenHandlerRegistry(){}

    public static final ScreenHandlerType<BCMGUIHandler> bcm_gui_handler = Registry.register(
            Registries.SCREEN_HANDLER,
            new Identifier(MOD_ID, "bcm_gui_handler"),
            new ScreenHandlerType<>(
                    (syncId, inventory) -> new BCMGUIHandler(syncId, inventory, ScreenHandlerContext.EMPTY),
                    FeatureSet.empty() // 1.20.6必填，无依赖传空
            )
    );
}
