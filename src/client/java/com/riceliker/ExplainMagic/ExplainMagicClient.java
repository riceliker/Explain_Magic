package com.riceliker.ExplainMagic;

import com.riceliker.ExplainMagic.GUI.ModScreenRegistry;
import net.fabricmc.api.ClientModInitializer;

public class ExplainMagicClient implements ClientModInitializer {
	@Override
	public void onInitializeClient()
	{
		new ModScreenRegistry();
	}
}