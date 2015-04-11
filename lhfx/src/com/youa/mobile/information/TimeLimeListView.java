package com.youa.mobile.information;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.text.Html.ImageGetter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.youa.mobile.R;
import com.youa.mobile.common.base.BaseListView;
import com.youa.mobile.common.manager.ApplicationManager;
import com.youa.mobile.common.util.picture.ImageData;
import com.youa.mobile.common.util.picture.ImageUtil;
import com.youa.mobile.common.util.picture.ViewPicturePage;
import com.youa.mobile.content.ContentOriginActivity;
import com.youa.mobile.friend.data.ContentImg;
import com.youa.mobile.friend.data.HomeData;
import com.youa.mobile.friend.data.User;
import com.youa.mobile.information.TimeLimeListView.TimeLimeHolder;
import com.youa.mobile.information.data.DraftAdapter;
import com.youa.mobile.input.data.PublishData;
import com.youa.mobile.location.util.LocationUtil;
import com.youa.mobile.location.util.LocationUtil.EarthPoint;
import com.youa.mobile.ui.base.BaseHolder;
import com.youa.mobile.utils.Tools;

public class TimeLimeListView extends
		BaseListView<TimeLimeHolder, List<HomeData>> {
	private LayoutInflater mInflater;
	private String priMonth;
	private Context mContext;

	public class TimeLimeHolder extends BaseHolder {
		private ImageView headView;
		private ImageView headerMaskView;
		private RelativeLayout headViewArea;
		private RelativeLayout timerLineView;
		private TextView nameView;
		private TextView timeView;
		private TextView timeView1;
		// private LinearLayout contentView;
		private TextView contentImageNumView;
		private TextView publicContentView;
		private View contentTextAreaView;
		// ---------------------------
		private LinearLayout transpond;
		private TextView contentView;
		private LinearLayout commerial;
		private TextView placeView;
		private TextView priceView;
		private View contentImageAreaView;
		private ImageView contentImgView;
		// ---------------------------
		private TextView fromWhereView;
		private TextView likeView;
		private TextView commentView;
		private TextView transpondView;
		// --
		// private boolean isHeadImgGet;
		private boolean isContentImgGet;
		// -------------------------
		private ImageView likeImage;
		private ImageView commentImage;
		private ImageView transpondImage;

		private View timerLine1;
		private View timerLine2;
		private TextView timeLineMonth;
		private TextView feedType;
		private RelativeLayout rightArea;
		private RelativeLayout userInfoArea;
		private TextView orgName;

		private ListView draftList;
	}

	public TimeLimeListView(ListView listView, View header, View footer) {
		super(listView, header, footer);
		mInflater = LayoutInflater.from(listView.getContext());
		mContext = listView.getContext();
	}

	private TapGestureRecognize mListener;

	public interface TapGestureRecognize {
		void onTapGestureRecognizeListener(String[] str);
	}

	public void setTapGestureRecognizeListener(TapGestureRecognize listener) {
		mListener = listener;
	}

	@Override
	protected View createTemplateView(int pos) {
		LinearLayout linear = (LinearLayout) mInflater.inflate(
				R.layout.feed_home_item, null);
		return linear;
	}

	@Override
	protected TimeLimeHolder getHolder(View convertView, int pos) {
		TimeLimeHolder holder = new TimeLimeHolder();
		holder.headView = (ImageView) convertView.findViewById(R.id.user_head);
		holder.headViewArea = (RelativeLayout) convertView
				.findViewById(R.id.user_head_area);
		holder.headView.setVisibility(View.GONE);
		holder.headView.setVisibility(View.GONE);
		holder.headViewArea.setVisibility(View.GONE);
		Context context = holder.headView.getContext();
		if (context instanceof OwnFeedPage || context instanceof EnjoyFeedPage) {
			convertView.setBackgroundColor(context.getResources().getColor(
					R.color.timeline_item_bg));
		}
		holder.userInfoArea = (RelativeLayout) convertView
				.findViewById(R.id.user_info_area);
		holder.userInfoArea.setVisibility(View.GONE);
		holder.headerMaskView = (ImageView) convertView
				.findViewById(R.id.user_type);
		holder.headerMaskView.setVisibility(View.GONE);
		holder.timerLineView = (RelativeLayout) convertView
				.findViewById(R.id.timer_line_layout);
		holder.timerLineView.setVisibility(View.VISIBLE);
		holder.timerLine1 = convertView.findViewById(R.id.timer_line1);
		holder.timerLine2 = convertView.findViewById(R.id.timer_line2);
		holder.timeLineMonth = (TextView) convertView
				.findViewById(R.id.time_line_month);
		holder.nameView = (TextView) convertView.findViewById(R.id.user_name);
		holder.nameView.setVisibility(View.GONE);
		holder.feedType = (TextView) convertView.findViewById(R.id.feed_type);
		holder.feedType.setVisibility(View.GONE);
		holder.timeView = (TextView) convertView.findViewById(R.id.time);
		holder.timeView.setVisibility(View.GONE);
		holder.timeView1 = (TextView) convertView.findViewById(R.id.time1);
		holder.timeView1.setVisibility(View.VISIBLE);
		holder.publicContentView = (TextView) convertView
				.findViewById(R.id.public_content);
		holder.transpond = (LinearLayout) convertView
				.findViewById(R.id.transpond);
		holder.orgName = (TextView) convertView.findViewById(R.id.org_name);
		holder.contentView = (TextView) holder.transpond
				.findViewById(R.id.content);
		holder.commerial = (LinearLayout) holder.transpond
				.findViewById(R.id.commerial);
		holder.placeView = (TextView) holder.commerial.findViewById(R.id.place);
		holder.priceView = (TextView) holder.commerial.findViewById(R.id.price);
		holder.contentImageAreaView = convertView
				.findViewById(R.id.content_img_area);
		holder.contentImgView = (ImageView) convertView
				.findViewById(R.id.content_img);
		holder.fromWhereView = (TextView) convertView
				.findViewById(R.id.form_where);
		LinearLayout view = (LinearLayout) convertView
				.findViewById(R.id.bottom);
		holder.likeView = (TextView) view.findViewById(R.id.like);
		holder.commentView = (TextView) view.findViewById(R.id.comment);
		holder.transpondView = (TextView) view.findViewById(R.id.transport);
		holder.likeImage = (ImageView) view.findViewById(R.id.like_img);
		holder.commentImage = (ImageView) view.findViewById(R.id.comment_img);
		holder.transpondImage = (ImageView) view
				.findViewById(R.id.transpond_img);
		holder.contentTextAreaView = convertView
				.findViewById(R.id.content_text_area);
		// --------------------------
		holder.rightArea = (RelativeLayout) convertView
				.findViewById(R.id.rightarea);
		holder.contentImageNumView = (TextView) convertView
				.findViewById(R.id.content_img_num);

		// ----------------------------
		holder.draftList = (ListView) convertView.findViewById(R.id.draft_list);
		return holder;
	}

	@Override
	protected void setDataWithHolder(final TimeLimeHolder holder, int position,
			boolean isMoving) {
		final HomeData data = mDataList.get(position);
		if (data.draftList != null && data.draftList.size() > 0) {
			DraftAdapter adpter = new DraftAdapter(getContext(), data.draftList);
			ViewGroup.LayoutParams params = holder.draftList.getLayoutParams();
			params.height = data.draftList.size()*(int)getContext().getResources().getDimension(R.dimen.draft_item_height);
			holder.draftList.setAdapter(adpter);
			holder.draftList.setLayoutParams(params);
			holder.draftList.setVisibility(View.VISIBLE);
			holder.rightArea.setVisibility(View.GONE);
			return;
		} else {
			holder.rightArea.setVisibility(View.VISIBLE);
			holder.draftList.setVisibility(View.GONE);
		}
		if (position == 0) {
			holder.timerLine1.setVisibility(View.GONE);
			holder.timerLine2.setVisibility(View.VISIBLE);
		} else {
			holder.timerLine1.setVisibility(View.VISIBLE);
			holder.timerLine2.setVisibility(View.GONE);
		}

		holder.headView.setVisibility(View.GONE);
		holder.headerMaskView.setVisibility(View.GONE);
		holder.timerLineView.setVisibility(View.VISIBLE);
		// if (data.PublicUser.name != null) {
		// holder.nameView.setText(data.PublicUser.name);
		// holder.nameView.setVisibility(View.VISIBLE);
		// } else {
		// holder.nameView.setVisibility(View.GONE);
		// holder.nameView.setText(null);
		// }
		setTime(data, holder);
		setTranspondContent(data, holder);
		setContent(data, holder);
		if (data.PublicUser.fromWhere != null) {
			holder.fromWhereView.setVisibility(View.VISIBLE);
			holder.fromWhereView.setText(data.PublicUser.fromWhere);
		} else {
			holder.fromWhereView.setVisibility(View.GONE);
			holder.fromWhereView.setText(null);
		}
		User user = null;
		if ("0".equals(data.PublicUser.feedType)) {
			user = data.PublicUser;
		} else {
			user = data.originUser;
		}
		if (user.like_num != null) {
			holder.likeView.setText(user.like_num);
			holder.likeImage.setVisibility(View.VISIBLE);
			holder.likeView.setVisibility(View.VISIBLE);
		} else {
			holder.likeImage.setVisibility(View.GONE);
			holder.likeView.setVisibility(View.GONE);
			holder.likeView.setText(null);
		}
		if (user.comment_num != null) {
			holder.commentView.setText(user.comment_num);
			holder.commentImage.setVisibility(View.VISIBLE);
			holder.commentView.setVisibility(View.VISIBLE);
		} else {
			holder.commentImage.setVisibility(View.GONE);
			holder.commentView.setVisibility(View.GONE);
			holder.commentView.setText(null);
		}
		if (user.transpond_num != null) {
			holder.transpondView.setText(user.transpond_num);
			holder.transpondImage.setVisibility(View.VISIBLE);
			holder.transpondView.setVisibility(View.VISIBLE);
		} else {
			holder.transpondImage.setVisibility(View.GONE);
			holder.transpondView.setVisibility(View.GONE);
			holder.transpondView.setText(null);
		}
		setHeaderImg(data, holder);
		setContentImg(data, holder);
		// --------------------------
		// 喜欢
		holder.rightArea
				.setTag(new String[] {
						"2".equals(data.PublicUser.feedType)
								&& data.originUser != null ? data.originUser.postId
								: data.PublicUser.postId,
						data.PublicUser.feedType });
		// --------------------------
		// if (!isMoving) {
		// 设置图片
		// getHeaderImg(holder, position);
		getContentImg(holder, position);
		holder.feedType.setText("");
		if ("0".equals(data.PublicUser.feedType)) {
			holder.feedType.setVisibility(View.GONE);
		} else if ("1".equals(data.PublicUser.feedType)) {
			holder.feedType.setVisibility(View.VISIBLE);
			holder.feedType.append(Html.fromHtml("<img src='"
					+ R.drawable.feed_transport + "'/>", getImage(), null));
			holder.feedType.append(mContext.getResources().getString(
					R.string.forward_title));
		} else if ("2".equals(data.PublicUser.feedType)) {
			holder.feedType.setVisibility(View.VISIBLE);
			holder.feedType.append(Html.fromHtml("<img src='"
					+ R.drawable.xiaoxi_xihuan_feed_xihuanicon + "'/>",
					getImage(), null));
			holder.feedType.append(mContext.getResources().getString(
					R.string.feed_content_like));
		}
		holder.contentTextAreaView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener
							.onTapGestureRecognizeListener((String[]) holder.rightArea
									.getTag());
				}
			}
		});
		holder.contentImageAreaView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mListener != null) {
					mListener
							.onTapGestureRecognizeListener((String[]) holder.rightArea
									.getTag());
				}
			}
		});
		holder.contentImageAreaView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ContentImg[] imgs = null;
				if ("0".equals(data.PublicUser.feedType)) {
					imgs = data.PublicUser.contentImg;
				} else if (null != data.originUser) {
					imgs = data.originUser.contentImg;
				}
				if (imgs == null) {
					return;
				}
				Bundle bundle = new Bundle();
				ImageData[] imgDataArray = new ImageData[imgs.length];
				for (int i = 0; i < imgs.length; i++) {
					ContentImg img = imgs[i];
					if (null != img) {
						ImageData imgData = new ImageData(img.img_content_id,
								img.img_desc, null, 0);
						imgDataArray[i] = imgData;
					}
				}
				bundle.putParcelableArray(ViewPicturePage.EXTRA_IMG_ARRAY,
						imgDataArray);
				Intent intent = new Intent();
				intent.putExtras(bundle);
				intent.setClass(getContext(), ViewPicturePage.class);
				getContext().startActivity(intent);
			}

		});
		// }
	}

	private void setTranspondContent(HomeData data, TimeLimeHolder holder) {
		holder.transpond.setPadding(0, 0, 0, 0);
		holder.transpond.setBackgroundDrawable(null);
		if ("0".equals(data.PublicUser.feedType)) {
			holder.publicContentView.setText(null);
			holder.publicContentView.setVisibility(View.GONE);
			return;
		}
		if ("2".equals(data.PublicUser.feedType)) {// 喜欢
			holder.publicContentView.setVisibility(View.GONE);
		} else {// "1".equals(data.PublicUser.feedType)&&
			holder.publicContentView.setVisibility(View.VISIBLE);
			if (data.PublicUser.charSequence != null) {
				holder.publicContentView.setText(data.PublicUser.charSequence);
			}
		}
	}

	private ImageGetter getImage() {
		ImageGetter imageGetter = new ImageGetter() {
			@Override
			public Drawable getDrawable(String source) {
				int id = Integer.parseInt(source);
				// 根据id从资源文件中获取图片对象
				Drawable d = mContext.getResources().getDrawable(id);
				d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
				return d;
			}
		};
		return imageGetter;
	}

	private void setContent(HomeData data, TimeLimeHolder holder) {
		User user = null;
		if (!"0".equals(data.PublicUser.feedType)) {
			user = data.originUser;
		} else {
			user = data.PublicUser;
		}
		if (user == null && !"0".equals(data.PublicUser.feedType)) {
			holder.contentView.setText(mContext.getResources().getString(
					R.string.feed_deleted));
			holder.contentView.setVisibility(View.VISIBLE);
			holder.orgName.setVisibility(View.GONE);
			holder.orgName.setText(null);
			holder.contentImgView.setImageDrawable(null);
			holder.contentImgView.setVisibility(View.GONE);
			holder.commerial.setVisibility(View.GONE);
			holder.placeView.setText(null);
			holder.priceView.setText(null);
			return;
		}
		holder.transpond.setVisibility(View.VISIBLE);
		if ((!"0".equals(data.PublicUser.feedType))
				&& user.charSequence != null) {
			holder.contentView.setVisibility(View.VISIBLE);
			holder.contentView.setText(user.charSequence);
		} else if (user.charSequence != null) {
			holder.contentView.setVisibility(View.VISIBLE);
			holder.contentView.setText(user.charSequence);
		} else {
			holder.contentView.setVisibility(View.GONE);
			holder.contentView.setText(null);
		}
		if ((!"0".equals(data.PublicUser.feedType))
				&& user.nameCharSequence != null) {
			holder.orgName.setVisibility(View.VISIBLE);
			holder.orgName.setText(data.originUser.nameCharSequence);
		} else {
			holder.orgName.setVisibility(View.GONE);
			holder.orgName.setText(null);
		}
		// ----------------------------------------------------------
		int wi = holder.transpond.getMeasuredWidth()
				- holder.transpond.getPaddingLeft()
				- holder.transpond.getPaddingRight();
		wi = ApplicationManager.getInstance().getWidth()
				- holder.headView.getLayoutParams().width
				- Tools.dip2px(mContext, 17 + holder.transpond.getPaddingLeft()
						+ holder.transpond.getPaddingRight());
		Tools.setLimitText(holder.contentView, wi, 3, 0);
		// -----------------------------------------------------------
		if (user.contentImg != null && user.contentImg.length > 0) {//
			// 针对不同分辨率，进行处理
			holder.contentImageAreaView.setVisibility(View.VISIBLE);
			holder.contentImgView.setImageBitmap(null);
			holder.contentTextAreaView
					.setBackgroundResource(R.drawable.feed_content_haspic_bg_selector);
			holder.contentImageNumView.setText("" + user.contentImg.length);
		} else {
			holder.contentImageAreaView.setVisibility(View.GONE);
			holder.contentImgView.setImageBitmap(null);
			holder.contentTextAreaView
					.setBackgroundResource(R.drawable.feed_content_nopic_bg_selector);
		}
		holder.contentTextAreaView.setPadding(10, 10, 10, 10);
		if (TextUtils.isEmpty(user.place) && TextUtils.isEmpty(user.price)) {
			holder.commerial.setVisibility(View.GONE);
		} else {
			holder.commerial.setVisibility(View.VISIBLE);
			if (!TextUtils.isEmpty(user.place)) {
				holder.placeView.setVisibility(View.VISIBLE);
				holder.placeView.setText(user.place);
			} else {
				holder.placeView.setVisibility(View.GONE);
			}
			if (!TextUtils.isEmpty(user.price)) {
				holder.priceView.setVisibility(View.VISIBLE);
				holder.priceView.setText(" " + user.price.trim());
			} else {
				holder.priceView.setVisibility(View.GONE);
			}
		}
		holder.transpond.setTag(user.postId);
		holder.transpond.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String postId = (String) arg0.getTag();
				if (!TextUtils.isEmpty(postId)) {
					Bundle bundle = new Bundle();
					bundle.putString(ContentOriginActivity.ORIGIN_FEED_ID,
							postId);
					Intent intent = new Intent(mContext,
							ContentOriginActivity.class);
					intent.putExtras(bundle);
					mContext.startActivity(intent);
				}
			}
		});
	}

	private void setTime(HomeData data, TimeLimeHolder holder) {
		if (data==null||data.PublicUser==null||data.PublicUser.time == null) {
			holder.timeView1.setVisibility(View.GONE);
			holder.timeView1.setText(null);
			return;
		}
		long t = 0;
		try {
			t = Long.valueOf(data.PublicUser.time) * 1000;
		} catch (Exception e) {
			holder.timeView1.setVisibility(View.GONE);
			return;
		}
		holder.timeView1.setText(Tools
				.translateTimelineToString(data.PublicUser.timeLine));
		holder.timeView1.setVisibility(View.VISIBLE);
		holder.timeLineMonth.setVisibility(View.GONE);
		if (!Tools.translateTime(t).equals(priMonth)) {
			priMonth = Tools.translateTime(t);
			holder.timeLineMonth.setText(priMonth);
			holder.timeLineMonth.setVisibility(View.VISIBLE);
		}

	}

	// --------------------------图片处理-------------------------------------

	private void setContentImg(HomeData data, TimeLimeHolder holder) {
		// 从缓存去，如果有则世界设上，没有则，去取
		holder.setNeedTreateScroolStopEvent(true);
		User user = null;
		if (!"0".equals(data.PublicUser.feedType)) {
			user = data.originUser;
		} else {
			user = data.PublicUser;
		}
		if (user == null) {
			return;
		}
		user.isContentImgNeedGet = true;
		holder.isContentImgGet = false;
	}

	private void setHeaderImg(HomeData data, TimeLimeHolder holder) {
		// 从缓存去，有则设上;没有则，去取
		holder.setNeedTreateScroolStopEvent(true);
		data.PublicUser.isHeadNeedGet = true;
		// holder.isHeadImgGet = false;
	}

	// private void getHeaderImg(TimeLimeHolder holder, int position) {
	// List<HomeData> list = getData();
	// HomeData data = list.get(position);
	// if (data == null || holder == null || holder.isHeadImgGet
	// || !data.PublicUser.isHeadNeedGet
	// || data.PublicUser.img_head_id == null
	// || "".equals(data.PublicUser.img_head_id)) {
	// return;
	// }
	// holder.isHeadImgGet = true;
	// data.PublicUser.isHeadNeedGet = false;
	// int size = 0;
	// if (ApplicationManager.getInstance().getDensityDpi() < 240) {//
	// // 背景40,
	// size = ImageUtil.HEADER_SIZE_SMALL;
	// } else {// 背景60,
	// size = ImageUtil.HEADER_SIZE_BIG;
	// }
	// // ImageUtil.setImageViewOriginal(mContext, holder.headView,
	// // data.PublicUser.img_head_id, -1);
	// ImageUtil.setImageView(mContext, holder.headView,
	// data.PublicUser.img_head_id, size, size, -1);
	// }

	@Override
	protected void treateStopEvent(TimeLimeHolder holder, int position) {
		// getHeaderImg(holder, position);
		getContentImg(holder, position);
	}

	private void getContentImg(TimeLimeHolder holder, int position) {
		HomeData data = mDataList.get(position);
		if (data == null) {
			return;
		}
		User user = null;
		if (!"0".equals(data.PublicUser.feedType)) {
			user = data.originUser;
		} else {
			user = data.PublicUser;
		}
		if (user == null || holder.isContentImgGet || !user.isContentImgNeedGet
				|| user.contentImg == null || user.contentImg[0] == null
				|| user.contentImg[0].img_content_id == null
				|| "".equals(user.contentImg[0].img_content_id)) {
			return;
		}
		holder.isContentImgGet = true;
		user.isContentImgNeedGet = false;
		int size = 0;
		if (ApplicationManager.getInstance().getDensityDpi() < 240) {//
			// 背景86,图片80
			size = ImageUtil.CONTENT_SIZE_SMALL;
		} else {// 背景126,图片120
			size = ImageUtil.CONTENT_SIZE_BIG;
		}
		// ImageUtil.setImageViewOriginal(mContext, holder.contentImgView,
		// user.contentImg[0].img_content_id, -1);
		// int padding = user.contentImg.length > 1 ? 12 : 6;
		ImageUtil.setImageView(mContext, holder.contentImgView,
				user.contentImg[0].img_content_id, size, size, -1);
		final ArrayList<ImageData> imageList = new ArrayList<ImageData>();
		for (int i = 0; i < user.contentImg.length; i++) {
			ImageData imageData = new ImageData(
					user.contentImg[i].img_content_id, null, null);
			imageList.add(imageData);
		}
		// holder.contentImgView.setOnClickListener(new View.OnClickListener ()
		// {
		//
		// @Override
		// public void onClick(View v) {
		//
		// Intent intent = new Intent(getContext(), ImageBrowserPage.class);
		// intent.putParcelableArrayListExtra(ImageBrowserPage.KEY_IMAGES,
		// imageList);
		// getContext().startActivity(intent);
		// }
		//
		// });
	}
}
