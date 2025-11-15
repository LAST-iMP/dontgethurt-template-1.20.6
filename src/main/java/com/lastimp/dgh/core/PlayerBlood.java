package com.lastimp.dgh.core;

public class PlayerBlood extends AbstractBody{
    @Override
    public BodyCondition[] getBodyConditions() {
        return BodyCondition.bloodConditions();
    }
}
