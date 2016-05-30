package com.yikeguan.boardgamestore.app;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Build;
import android.os.Environment;
import android.widget.ImageView;

import com.easemob.chat.EMChat;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.MemoryCacheAware;
import com.nostra13.universalimageloader.cache.memory.impl.LRULimitedMemoryCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.yikeguan.boardgamestore.R;
import com.yikeguan.boardgamestore.util.AppSharedPreferences;

public class App extends Application {
	public static App app;

	public AppSharedPreferences preferences;

	public static DisplayImageOptions mNormalImageOptions;
	public static final String SDCARD_PATH = Environment
			.getExternalStorageDirectory().toString();
	public static final String IMAGES_FOLDER = SDCARD_PATH + File.separator
			+ "demo" + File.separator + "images" + File.separator;

	private static DisplayImageOptions HEADERS;
	private static DisplayImageOptions GAMES;
	private static DisplayImageOptions IMAGES;
	private static DisplayImageOptions PUBLISHS;

	@Override
	public void onCreate() {
		super.onCreate();
		app = this;
		preferences = new AppSharedPreferences(this);
		initImageLoader(this);

		EMChat.getInstance().init(this);
	}

	private void initImageLoader(Context context) {
		int memoryCacheSize = (int) (Runtime.getRuntime().maxMemory() / 5);
		MemoryCacheAware<String, Bitmap> memoryCache;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			memoryCache = new LruMemoryCache(memoryCacheSize);
		} else {
			memoryCache = new LRULimitedMemoryCache(memoryCacheSize);
		}

		mNormalImageOptions = new DisplayImageOptions.Builder()
				.bitmapConfig(Config.RGB_565).cacheInMemory(true)
				.cacheOnDisc(true).resetViewBeforeLoading(true).build();

		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).defaultDisplayImageOptions(mNormalImageOptions)
				.denyCacheImageMultipleSizesInMemory()
				.discCache(new UnlimitedDiscCache(new File(IMAGES_FOLDER)))
				.memoryCache(memoryCache)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.threadPriority(Thread.NORM_PRIORITY - 2).threadPoolSize(3)
				.build();
		ImageLoader.getInstance().init(config);
	}

	public void showUrlHeader(ImageView iv, String url) {
		if (HEADERS == null)
			HEADERS = new DisplayImageOptions.Builder()
					.showStubImage(R.drawable.def_header)
					.showImageForEmptyUri(R.drawable.def_header)
					.showImageOnFail(R.drawable.def_header).cacheInMemory()
					.cacheOnDisc().bitmapConfig(Bitmap.Config.ARGB_8888)
					.build();
		ImageLoader.getInstance().displayImage(url, iv, HEADERS);
	}

	public void showUrlGame(ImageView iv, String url) {
		if (GAMES == null)
			GAMES = new DisplayImageOptions.Builder()
					.showStubImage(R.drawable.def_game)
					.showImageForEmptyUri(R.drawable.def_game)
					.showImageOnFail(R.drawable.def_game).cacheInMemory()
					.cacheOnDisc().bitmapConfig(Bitmap.Config.ARGB_8888)
					.build();
		ImageLoader.getInstance().displayImage(url, iv, GAMES);
	}

	public void showUrlImg(ImageView iv, String url) {
		if (IMAGES == null)
			IMAGES = new DisplayImageOptions.Builder()
					.showStubImage(R.drawable.def_img)
					.showImageForEmptyUri(R.drawable.def_img)
					.showImageOnFail(R.drawable.def_img).cacheInMemory()
					.cacheOnDisc().bitmapConfig(Bitmap.Config.ARGB_8888)
					.build();
		ImageLoader.getInstance().displayImage(url, iv, IMAGES);
	}

	public void showUrlPublish(ImageView iv, String url) {
		if (PUBLISHS == null)
			PUBLISHS = new DisplayImageOptions.Builder()
					.showStubImage(R.drawable.def_publish)
					.showImageForEmptyUri(R.drawable.def_publish)
					.showImageOnFail(R.drawable.def_publish).cacheInMemory()
					.cacheOnDisc().bitmapConfig(Bitmap.Config.ARGB_8888)
					.build();
		ImageLoader.getInstance().displayImage(url, iv, PUBLISHS);
	}
}
