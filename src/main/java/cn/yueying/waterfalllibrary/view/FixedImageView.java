package cn.yueying.waterfalllibrary.view;

import cn.yueying.waterfalllibrary.tool.ImageLoader;
import cn.yueying.waterfalllibrary.tool.LoggerFactory;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 
 * @author LuoJ@huoLi.com
 *
 */
public class FixedImageView extends ImageView {
	public static final LoggerFactory.Logger mLogger = LoggerFactory
			.getLogger(FixedImageView.class);
	private boolean mFixWidth = false;
	private int mInitWidth;
	private int mInitHeight;
	private String mCurrentUrl;

	public FixedImageView(Context context) {
		super(context);
	}

	public FixedImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * 设置按照bitmap的宽度与view的宽度来等比（等bitmap的比）拉伸/压缩view的高度。
	 * 
	 * @param fitWidth
	 *            void
	 */
	public void setFitWidth(boolean fitWidth, int initWidth, int initHeight) {
		mFixWidth = fitWidth;
		mInitWidth = initWidth;
		mInitHeight = initHeight;
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		if (mFixWidth && bm != null) {
			int width = mInitWidth;
			int height = (int) ((float) width * (float) bm.getHeight() / (float) bm.getWidth());
			setMeasuredDimension(width, height);
		}
		super.setImageBitmap(bm);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (mFixWidth) {
			int width = MeasureSpec.getSize(widthMeasureSpec);
			int height = mInitWidth == 0 ? MeasureSpec.getSize(heightMeasureSpec)
					: (int) ((float) width * (float) mInitHeight / (float) mInitWidth);
			setMeasuredDimension(width, height);
		} else {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}

	public void loadImage(String url, long waitTime) {
		mCurrentUrl = url;
		mLogger.LogE("mCurrentUrl = {}", mCurrentUrl);
		ImageLoader.getInstance(getContext()).DisplayImage(url, this);
	}

	public void reload() {
		ImageLoader.getInstance(getContext()).DisplayImage(mCurrentUrl, this);
	}
}
