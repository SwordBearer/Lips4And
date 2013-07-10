package xmu.swordbearer.lips.bean;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/** Created by SwordBearer on 13-6-27. */
public class LinesList extends BaseBean {

    private List<Line> lines = new ArrayList<Line>();
    private long firstId = 0;
    private long lastId = 0;

    /**
     * 向头部添加数据
     *
     * @param ja
     * @return 返回新添加的数据条数
     */
    public int preappend(JSONArray ja) {
        int size = ja.length();
        try {
            for (int i = 0; i < size; i++) {
                this.lines.add(0, new Line(ja.getJSONObject(i)));
            }
            if (size > 0) {
                this.firstId = lines.get(0).getId();
            }
        } catch (JSONException e) {
        }
        return size;
    }

    /**
     * 向尾部追加数据
     *
     * @param ja
     * @return 返回追加的数据条数
     */
    public int append(JSONArray ja) {
        int size = ja.length();
        try {
            for (int i = 0; i < size; i++) {
                lines.add(new Line(ja.getJSONObject(i)));
            }
            if (size > 0) {
                this.lastId = lines.get(lines.size() - 1).getId();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return size;
    }

    /** 清空数据 */
    public void clear() {
        this.lines.clear();
        this.firstId = 0;
        this.lastId = 0;
    }

    public List<Line> getLines() {
        return this.lines;
    }

    public int getLinesCount() {
        return this.lines.size();
    }

    public long getFirstId() {
        return firstId;
    }

    public long getLastId() {
        return lastId;
    }
}
