package yath.sfwrtech4sa3.remindme;

import androidx.annotation.NonNull;

import com.google.firebase.firestore.IgnoreExtraProperties;

import org.json.JSONArray;

@IgnoreExtraProperties
public class User {
    public String display_name = "";
    public String uid = "";
    public String profile_pic_uri = "";
    public String email = "";

    public User() { }

    public User(String incoming_display_name, String incoming_uid, String incoming_profile_pic_uri, String incoming_email) {
        this.display_name = incoming_display_name;
        this.uid = incoming_uid;
        this.profile_pic_uri = incoming_profile_pic_uri;
        this.email = incoming_email;
    }

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "display_name='" + display_name + '\'' +
                ", uid='" + uid + '\'' +
                ", profile_pic_uri='" + profile_pic_uri + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
