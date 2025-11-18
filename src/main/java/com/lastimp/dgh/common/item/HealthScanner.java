package com.lastimp.dgh.common.item;

import com.lastimp.dgh.client.gui.MenuProvider.HealthMenuProvider;
import com.lastimp.dgh.api.enums.BodyCondition;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.lastimp.dgh.api.enums.BodyCondition.*;

public class HealthScanner extends Item {
    private static List<BodyCondition> HEALTH_SCANNER_CONDITIONS;

    private boolean used = false;

    public HealthScanner(Properties properties) {
        super(properties);
    }

    public static List<BodyCondition> healthScannerConditions() {
        if (HEALTH_SCANNER_CONDITIONS == null) {
            HEALTH_SCANNER_CONDITIONS = List.of(new BodyCondition[]{
                    BURN,
                    INTERNAL_INJURY,
                    OPEN_WOUND,
                    INFECTION,
                    FOREIGN_OBJECT,

                    BANDAGED,
                    OINMENTED,
            });
        }
        return HEALTH_SCANNER_CONDITIONS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide && !this.used) {
            player.openMenu(new HealthMenuProvider(player.getUUID()), buf -> buf.writeUUID(player.getUUID()));
        }
        this.used = false;
        return super.use(level, player, usedHand);
    }

    @Override
    public @NotNull InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
        if (!(target instanceof Player)) {
            return InteractionResult.PASS;
        }

        if (!player.level().isClientSide) {
            this.used = true;
            player.openMenu(new HealthMenuProvider(target.getUUID()), buf -> buf.writeUUID(target.getUUID()));
        }
        return InteractionResult.PASS;
    }


}
