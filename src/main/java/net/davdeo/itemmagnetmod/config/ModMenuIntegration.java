package net.davdeo.itemmagnetmod.config;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.loader.api.FabricLoader;

public class ModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("cloth-config2")) {
            return ModConfigGui::setupCloth;
        }
        return ModMenuApi.super.getModConfigScreenFactory();
    }
}
