package com.riceliker.ExplainMagic;

import com.riceliker.ExplainMagic.Network.NetworkRegistryClient;
import net.fabricmc.api.ClientModInitializer;

public class ExplainMagicClient implements ClientModInitializer {
	@Override
	public void onInitializeClient()
	{
		new NetworkRegistryClient();
	}
}