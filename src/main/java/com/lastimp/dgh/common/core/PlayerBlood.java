package com.lastimp.dgh.common.core;

public class PlayerBlood extends AbstractBody{
    @Override
    public BodyCondition[] getBodyConditions() {
        return BodyCondition.bloodConditions();
    }
}
