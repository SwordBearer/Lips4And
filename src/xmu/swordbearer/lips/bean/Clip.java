package xmu.swordbearer.lips.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/** Created by SwordBearers on 13-6-16. */
public class Clip {
    private long id;
    private String name;

    public static Clip fromJSON(JSONObject jo) throws JSONException {
        Clip clip = new Clip();
        clip.id = jo.getLong("id");
        clip.name = jo.getString("name");
        return clip;
    }

    public static List<Clip> fromJSON(JSONArray ja) throws JSONException {
        List<Clip> clips=new ArrayList<Clip>();
        int size=ja.length();
        for(int i=0;i<size;i++){
            clips.add(fromJSON(ja.getJSONObject(i)));
        }
        return clips;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
