package cn.sjtu.meetingroom.meetingroomcore.Domain;

import cn.sjtu.meetingroom.meetingroomcore.Util.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document
public class User {
    @Id
    String id;
    String enterpriceId;
    String phone;
    String password;
    String name;
    Type type;
    Byte[] faceFile;
    Byte[] featureFile;

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

    public Byte[] getFaceFile() {
        return faceFile;
    }

    public void setFaceFile(Byte[] faceFile) {
        this.faceFile = faceFile;
    }

    public Byte[] getFeatureFile() {
        return featureFile;
    }

    public void setFeatureFile(Byte[] featureFile) {
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


}
