package net.dzultra.config;

import dev.isxander.yacl3.api.utils.Dimension;
import dev.isxander.yacl3.gui.AbstractWidget;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.events.ContainerEventHandler;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import org.jspecify.annotations.NonNull;

import java.util.List;

public class ModEntryWidget extends AbstractWidget implements ContainerEventHandler {
    private static final int FIELD_GAP = 2;

    private final List<AbstractWidget> fields;

    private GuiEventListener focused;
    private boolean dragging;

    public ModEntryWidget(Dimension<Integer> dim, List<AbstractWidget> fields) {
        super(dim);
        this.fields = fields;
        setDimension(dim);
    }

    @Override
    public void setDimension(Dimension<Integer> dim) {
        super.setDimension(dim);

        // ListEntryWidget moves this widget every frame, so the fields are laid out again on every call
        int fieldWidth = (dim.width() - FIELD_GAP * (fields.size() - 1)) / fields.size();
        for (int i = 0; i < fields.size(); i++) {
            int x = dim.x() + i * (fieldWidth + FIELD_GAP);
            // The last field takes the pixels left over by the integer division
            int width = i == fields.size() - 1 ? dim.xLimit() - x : fieldWidth;
            fields.get(i).setDimension(Dimension.ofInt(x, dim.y(), width, dim.height()));
        }
    }

    @Override
    public void extractRenderState(@NonNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float a) {
        for (AbstractWidget field : fields) {
            field.extractRenderState(graphics, mouseX, mouseY, a);
        }
    }

    @Override
    public boolean mouseClicked(@NonNull MouseButtonEvent mouseButtonEvent, boolean doubleClick) {
        GuiEventListener clicked = null;
        for (AbstractWidget field : fields) {
            if (field.mouseClicked(mouseButtonEvent, doubleClick)) {
                clicked = field;
            }
        }
        setFocused(clicked);
        return clicked != null;
    }

    @Override
    public void unfocus() {
        fields.forEach(AbstractWidget::unfocus);
        focused = null;
    }

    @Override
    public void updateNarration(NarrationElementOutput builder) {
        if (focused instanceof AbstractWidget field) {
            field.updateNarration(builder);
        }
    }

    @Override
    public @NonNull List<? extends GuiEventListener> children() {
        return fields;
    }

    @Override
    public boolean isDragging() {
        return dragging;
    }

    @Override
    public void setDragging(boolean dragging) {
        this.dragging = dragging;
    }

    @Override
    public GuiEventListener getFocused() {
        return focused;
    }

    @Override
    public void setFocused(GuiEventListener focused) {
        this.focused = focused;
    }

    @Override
    public boolean mouseReleased(@NonNull MouseButtonEvent mouseButtonEvent) {
        return ContainerEventHandler.super.mouseReleased(mouseButtonEvent);
    }

    @Override
    public boolean mouseDragged(@NonNull MouseButtonEvent mouseButtonEvent, double dx, double dy) {
        return ContainerEventHandler.super.mouseDragged(mouseButtonEvent, dx, dy);
    }

    @Override
    public boolean keyPressed(@NonNull KeyEvent keyEvent) {
        return ContainerEventHandler.super.keyPressed(keyEvent);
    }

    @Override
    public boolean keyReleased(@NonNull KeyEvent keyEvent) {
        return ContainerEventHandler.super.keyReleased(keyEvent);
    }

    @Override
    public boolean charTyped(@NonNull CharacterEvent characterEvent) {
        return ContainerEventHandler.super.charTyped(characterEvent);
    }
}
