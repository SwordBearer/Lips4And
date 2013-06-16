package xmu.swordbearer.lips.ui;

import xmu.swordbearer.lips.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TextLineDivider extends RelativeLayout {
	private String _title;
	private int _lineBg;
	private int _textColor;

	public TextLineDivider(Context context) {
		this(context, null);
	}

	public TextLineDivider(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (attrs != null) {
			TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TextLineDivider);
			_title = typedArray.getString(R.styleable.TextLineDivider_textLineDividerText);
			_lineBg = typedArray.getInt(R.styleable.TextLineDivider_textLineDividerBg, android.R.color.darker_gray);
			_textColor = typedArray.getColor(R.styleable.TextLineDivider_textLineDividerTextColor, android.R.color.black);
			typedArray.recycle();
		} else {// 默认样式
			_title = getResources().getString(R.string.divider);
			_lineBg = android.R.color.darker_gray;
			_textColor = android.R.color.black;
		}
		init(context);
	}

	private void init(Context context) {
		// initialize the three child-views
		TextView tv = new TextView(context);
		tv.setId(4321);
		tv.setPadding(4, 0, 4, 0);
		View leftLine = new View(context);
		View rightLine = new View(context);
		tv.setTextSize(12);
		if (_title != null) {
			tv.setText(_title);
		}
		tv.setTextColor(_textColor);
		leftLine.setBackgroundColor(_lineBg);
		rightLine.setBackgroundColor(_lineBg);
		//
		setGravity(Gravity.CENTER_VERTICAL);
		RelativeLayout.LayoutParams params1 = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params1.addRule(RelativeLayout.CENTER_HORIZONTAL, TRUE);
		addView(tv, params1);

		RelativeLayout.LayoutParams params2 = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
		params2.addRule(RelativeLayout.LEFT_OF, tv.getId());
		params2.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
		params2.addRule(ALIGN_PARENT_LEFT, TRUE);
		addView(leftLine, params2);
		//
		RelativeLayout.LayoutParams params3 = new LayoutParams(LayoutParams.MATCH_PARENT, 1);
		params3.addRule(RelativeLayout.RIGHT_OF, tv.getId());
		params3.addRule(ALIGN_PARENT_RIGHT, TRUE);
		params3.addRule(RelativeLayout.CENTER_VERTICAL, TRUE);
		addView(rightLine, params3);
	}
}
