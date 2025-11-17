package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.common.core.Enums.BodyCondition;

import java.util.List;

public class PlayerBlood extends AbstractBody {
    public PlayerBlood() {
        super();
    }

    public PlayerBlood(Void v) {
        this();
    }

    @Override
    public List<BodyCondition> getBodyConditions() {
        return BodyCondition.bloodConditions();
    }
}
