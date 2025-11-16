package com.lastimp.dgh.common.core;

import java.util.List;

public class PlayerBlood extends AbstractBody{
    @Override
    public List<BodyCondition> getBodyConditions() {
        return BodyCondition.bloodConditions();
    }
}
