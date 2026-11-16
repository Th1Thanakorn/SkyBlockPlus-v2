package com.thana.skyblockplus.mixin;

import com.thana.skyblockplus.SkyBlockPlus;
import com.thana.skyblockplus.util.skyblock.ItemRarity;
import com.thana.skyblockplus.util.skyblock.ItemUtil;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiGraphicsExtractor.class)
public class MixinGuiGraphicsExtractor {

    @Unique
    private static final Identifier RARITY_BASE = Identifier.fromNamespaceAndPath(SkyBlockPlus.MODID, "textures/gui/rarity_base.png");
    private final GuiGraphicsExtractor that = (GuiGraphicsExtractor) (Object) this;

    @Inject(method = "item(Lnet/minecraft/world/item/ItemStack;III)V", at = @At("HEAD"), remap = false)
    public void item(ItemStack itemStack, int x, int y, int seed, CallbackInfo ci) {
        if (itemStack.isEmpty()) return;

        String itemId = ItemUtil.getSkyBlockItemId(itemStack);
        if (itemId.isEmpty()) return;

        ItemRarity rarity = ItemUtil.getItemRarity(itemStack);
        this.that.blit(RenderPipelines.GUI_TEXTURED, RARITY_BASE, x, y, 0.0F, 0.0F, 16, 16, 16, 16, rarity.getColor());
    }
}
