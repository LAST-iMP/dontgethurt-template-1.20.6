package com.lastimp.dgh.common.item;

import com.lastimp.dgh.api.bodyPart.AbstractBody;
import com.lastimp.dgh.common.core.player.PlayerHealthCapability;
import com.lastimp.dgh.api.enums.BodyComponents;
import com.lastimp.dgh.api.healingItems.AbstractPartlyHealItem;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;

import static com.lastimp.dgh.api.enums.BodyCondition.*;

public class Bandages extends AbstractPartlyHealItem {
    private static final HashSet<BodyComponents> applicableComponents = new HashSet<>();

    static {
        applicableComponents.add(BodyComponents.HEAD);
        applicableComponents.add(BodyComponents.TORSO);
        applicableComponents.add(BodyComponents.LEFT_ARM);
        applicableComponents.add(BodyComponents.RIGHT_ARM);
        applicableComponents.add(BodyComponents.LEFT_LEG);
        applicableComponents.add(BodyComponents.RIGHT_LEG);
    }

    public Bandages(Properties properties) {
        super(properties);
    }

    @Override
    public HashSet<BodyComponents> getApplicableComponents() {
        return Bandages.applicableComponents;
    }

    @Override
    protected boolean healOn(@NotNull ServerPlayer player, BodyComponents component) {
        if (!this.getApplicableComponents().contains(component)) return false;

        return PlayerHealthCapability.getAndSet(player, health -> {
            AbstractBody body = health.getComponent(component);
            float currCondition = body.getConditionValue(BANDAGED);
            if (currCondition >= 1.0f) return false;

            body.addConditionValue(BANDAGED, 0.5f);
            return true;
        });
    }
}
