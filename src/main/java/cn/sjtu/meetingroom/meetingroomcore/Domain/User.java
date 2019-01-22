package cn.sjtu.meetingroom.meetingroomcore.Domain;

import cn.sjtu.meetingroom.meetingroomcore.Util.Type;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
    @Id
    @ApiModelProperty(required = false)
    String id;
    @ApiModelProperty(required = true)
    String enterpriceId;
    @ApiModelProperty(required = true)
    String phone;
    @ApiModelProperty(required = true)
    String password;
    @ApiModelProperty(required = true)
    String name;
    @ApiModelProperty(required = false)
    Type type;
    @ApiModelProperty(required = true, notes = "just the name of the file")
    String faceFile;
    @ApiModelProperty(required = true, notes = "just the name of the file")
    String featureFile;
    @ApiModelProperty(required = false, hidden = true)
    String deviceId;

    public User(String id, String enterpriceId, String phone, String password, String name, Type type, String faceFile, String featureFile) {
        this.id = id;
        this.enterpriceId = enterpriceId;
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.type = type;
        this.faceFile = faceFile;
        this.featureFile = featureFile;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getFaceFile() {
        return faceFile;
    }

    public void setFaceFile(String faceFile) {
        this.faceFile = faceFile;
    }

    public String getFeatureFile() {
        return featureFile;
    }

    public void setFeatureFile(String featureFile) {
        this.featureFile = featureFile;
    }

    public String getEnterpriceId() {
        return enterpriceId;
    }

    public void setEnterpriceId(String enterpriceId) {
        this.enterpriceId = enterpriceId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
