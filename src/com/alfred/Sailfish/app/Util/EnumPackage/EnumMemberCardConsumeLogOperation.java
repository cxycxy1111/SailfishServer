package com.alfred.Sailfish.app.Util.EnumPackage;

public enum EnumMemberCardConsumeLogOperation {
    CHARGE(1),DEDUCT(2),ATTEND(3),UNATTEND(4);
    private int type;

    EnumMemberCardConsumeLogOperation(int i) {
        this.type = i;
    }
}
