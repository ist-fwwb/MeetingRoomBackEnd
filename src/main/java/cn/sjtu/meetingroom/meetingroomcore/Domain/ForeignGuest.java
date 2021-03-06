package cn.sjtu.meetingroom.meetingroomcore.Domain;

import io.swagger.annotations.ApiModelProperty;

public class ForeignGuest {
    String phone;
    @ApiModelProperty(notes = "四位数唯一标识符")
    String UUID;
    String name;
    String signInTime;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUUID() {
        return UUID;
    }

    public void setUUID(String UUID) {
        this.UUID = UUID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSignInTime() {
        return signInTime;
    }

    public void setSignInTime(String signInTime) {
        this.signInTime = signInTime;
    }
}
