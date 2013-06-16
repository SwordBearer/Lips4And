package xmu.swordbearer.lips.bean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/** Created by SwordBearers on 13-6-16. */

@Deprecated
public class Category  extends BaseBean {
    private static final long serialVersionUID = 1L;
    //
    private long id;
    private String name;
    private String desc;

    private Category() {}

    public static Category fromJSON(JSONObject jo) throws JSONException {
        Category cate = new Category();
        cate.id = jo.getLong("id");
        cate.name = jo.getString("name");
        return cate;

    }

    public static List<Category> fromJSON(JSONArray ja) throws JSONException {
        List<Category> cates = new ArrayList<Category>();
        int size = ja.length();
        for (int i = 0; i < size; i++) {
            cates.add(fromJSON(ja.getJSONObject(i)));
        }
        return cates;
    }

    public String getDesc() {
        return desc;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static void save2Cache(){

    }

}