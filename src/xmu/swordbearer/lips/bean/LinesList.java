package xmu.swordbearer.lips.bean;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.util.Log;

/** Created by SwordBearer on 13-6-27. */
public class LinesList extends BaseBean {
	private static final long serialVersionUID = 1L;

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
		if (size == 0)
			return 0;
		List<Line> tempList = new ArrayList<Line>(size);
		try {
			for (int i = 0; i < size; i++) {
				tempList.add(new Line(ja.getJSONObject(i)));
			}
			this.lines.addAll(0, tempList);
			this.firstId = lines.get(0).getId();
			this.lastId = lines.get(lines.size() - 1).getId();
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
		if (size == 0)
			return 0;
		List<Line> tempList = new ArrayList<Line>(size);
		try {
			for (int i = 0; i < size; i++) {
				tempList.add(new Line(ja.getJSONObject(i)));
			}
			this.lines.addAll(lines.size(), tempList);
			this.lastId = lines.get(lines.size() - 1).getId();
			this.firstId = lines.get(0).getId();
			this.lastId = lines.get(lines.size() - 1).getId();
			Log.e("TEST", "LinesList lastId ===>" + lastId);
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
