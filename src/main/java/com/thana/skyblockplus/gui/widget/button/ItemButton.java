package com.thana.skyblockplus.gui.widget.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

import java.util.List;
import java.util.Optional;

public class ItemButton extends Button {

    private final ItemStack itemStack;
    private final Component component;

    public ItemButton(int x, int y, ItemStack itemStack, OnPress onPress) {
        this(x, y, itemStack, itemStack.getItemName(), onPress);
    }

    public ItemButton(int x, int y, ItemStack itemStack, Component component, OnPress onPress) {
        super(x, y, 18, 18, component, onPress, DEFAULT_NARRATION);
        this.itemStack = itemStack;
        this.component = component;
    }

    @Override
    protected void extractContents(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float partialTicks) {
        this.extractDefaultSprite(graphics);
        graphics.item(itemStack, getX()+1, getY()+1);

        if (this.isHovered) {
            graphics.setTooltipForNextFrame(Minecraft.getInstance().font, List.of(this.component), Optional.empty(), mouseX, mouseY);
        }
    }
}
