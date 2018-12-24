package cn.sjtu.meetingroom.meetingroomcore.Domain;

import cn.sjtu.meetingroom.meetingroomcore.Util.Type;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User {
    @Id
    String id;
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
}
