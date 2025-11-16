package com.lastimp.dgh.common.core.bodyPart;

import com.lastimp.dgh.common.core.Enums.BodyCondition;

import java.util.List;

public class PlayerBlood extends AbstractBody {
    @Override
    public List<BodyCondition> getBodyConditions() {
        return BodyCondition.bloodConditions();
    }
}
