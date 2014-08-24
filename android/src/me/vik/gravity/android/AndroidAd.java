package me.vik.gravity.android;

import me.vik.gravity.LD30Game;
import me.vik.gravity.game.Ad;
import android.app.Activity;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.InterstitialAd;

public class AndroidAd implements Ad {

	private Activity activity;
	private InterstitialAd ad;
	private AdRequest request;
	
	public AndroidAd(Activity activity, final LD30Game game) {
		this.activity = activity;
		
		ad = new InterstitialAd(activity);
		request = new Builder().build();
		
		ad.setAdListener(new AdListener() {
            public void onAdClosed() {
            	game.switchToGameOverScreen();
            }
		});
	}
	
	public void setId(final String id) {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				ad.setAdUnitId(id);
			}
		});
	}
	
	public void load() {
		activity.runOnUiThread(loadRunnable);
	}

	public void show() {
		activity.runOnUiThread(showRunnable);
	}
	
	private Runnable loadRunnable = new Runnable() {
		public void run() {
			ad.loadAd(request);
		}
	};
	
	private Runnable showRunnable = new Runnable() {
		public void run() {
			if (ad.isLoaded()) {
				ad.show();
			}	
		}
	};

}
