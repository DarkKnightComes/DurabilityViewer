package de.guntram.mcmod.durabilityviewer;

import de.guntram.mcmod.durabilityviewer.client.gui.GuiItemDurability;
import de.guntram.mcmod.durabilityviewer.handler.ConfigurationHandler;
import de.guntram.mcmod.fabrictools.ConfigurationProvider;
import net.fabricmc.api.ClientModInitializer;

import net.fabricmc.fabric.api.client.keybinding.FabricKeyBinding;
import net.fabricmc.fabric.api.client.keybinding.KeyBindingRegistry;
import net.fabricmc.fabric.api.event.client.ClientTickCallback;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.Identifier;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_H;

public class DurabilityViewer implements ClientModInitializer
{
    public static final String MODID = "durabilityviewer";
    public static final String MODNAME = "Durability Viewer";
    public static final String VERSION = "1.16-20w10a-fabric0.5.1-1.7";

    public static DurabilityViewer instance;
    private static ConfigurationHandler confHandler;
    private static String changedWindowTitle;
    private FabricKeyBinding showHide;
    
    @Override
    public void onInitializeClient() {
        setKeyBindings();
        confHandler=ConfigurationHandler.getInstance();
        ConfigurationProvider.register(MODNAME, confHandler);
        confHandler.load(ConfigurationProvider.getSuggestedFile(MODID));
        changedWindowTitle=null;
    }
    
    public static void setWindowTitle(String s) {
        changedWindowTitle=s;
    }
    
    public static String getWindowTitle() {
        return changedWindowTitle;
    }

    public void processKeyBinds() {
        if (showHide.wasPressed()) {
            GuiItemDurability.toggleVisibility();
        }
    }

    public void setKeyBindings() {
        final String category="key.categories.durabilityviewer";
        KeyBindingRegistry.INSTANCE.addCategory(category);
        KeyBindingRegistry.INSTANCE.register(
            showHide = FabricKeyBinding.Builder
            .create(new Identifier("durabilityviewer:showhide"), InputUtil.Type.KEYSYM, GLFW_KEY_H, category)
            .build());
        ClientTickCallback.EVENT.register(e->processKeyBinds());
    }    
}
