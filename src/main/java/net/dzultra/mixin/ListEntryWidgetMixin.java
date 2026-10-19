package net.dzultra.mixin;

import dev.isxander.yacl3.gui.controllers.ListEntryWidget;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(ListEntryWidget.class)
public abstract class ListEntryWidgetMixin implements ContainerEventHandler {
    @Unique
    private GuiEventListener lastFocused;

    @Override
    public void setFocused(boolean focused) {
        if (focused) {
            if (getFocused() == null) setFocused(lastFocused);
        } else {
            lastFocused = getFocused();
            setFocused(null);
        }
    }
}
