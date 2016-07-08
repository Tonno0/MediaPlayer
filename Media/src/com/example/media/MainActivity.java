package com.example.media;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	private SurfaceView mSurface;
	private Button start,stop,pre;
	private static MediaPlayer mediaPlayer;
	private Context mContext=null;
	
	private int position=0;
	public String tag="MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mContext=this;
		mSurface=(SurfaceView) findViewById(R.id.surface);
		start=(Button) findViewById(R.id.start);
		stop=(Button) findViewById(R.id.stop);
		pre=(Button) findViewById(R.id.pre);
		
		mediaPlayer=new MediaPlayer();
		
		mSurface.getHolder().setKeepScreenOn(true);
		SurfaceViewLis mv = new SurfaceViewLis();
		mSurface.getHolder().addCallback(mv);
		start.setOnClickListener(this);
		stop.setOnClickListener(this);
		pre.setOnClickListener(this);
		
	}

	public void onClick(View v) {

		switch(v.getId()){
		case R.id.start:
			try{
				play();
			}catch(IllegalArgumentException e){
				e.printStackTrace();
			}catch (SecurityException e) {
				e.printStackTrace();
			}catch (IllegalStateException e) {
				e.printStackTrace();
			}
			break;
		case R.id.pre:
			if(mediaPlayer.isPlaying()){
				pre.setText("播放");
				mediaPlayer.pause();
			}else{
				pre.setText("暂停");
				mediaPlayer.setDisplay(mSurface.getHolder());
				mediaPlayer.start();
			}
			break;
		case R.id.stop:
			if(mediaPlayer.isPlaying()){
				mediaPlayer.stop();
			}
			break;
		default:
			break;
		}
	}

	//播放
	public void play() {
		mediaPlayer.reset();
		Log.d(tag, "mediaPlayer reset: ");
		
		try {
			position=0;
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			//AssetFileDescriptor fd = null;
			//fd = this.getAssets().openFd("2.mp4"); 
			//mediaPlayer.setDataSource(fd.getFileDescriptor(),  
            //        fd.getStartOffset(), fd.getLength());
			//Uri uri = Uri.parse("http://v.cctv.com/flash/mp4video6/TMS/2011/01/05/cf752b1c12ce452b3040cab2f90bc265_h264818000nero_aac32-1.mp4"); 
			//Uri uri = Uri.parse("http://172.16.3.149:4000/group1/M00/1E/20/rBADlVcY_RiAEnXxAF7LT0yFZvs712.mp4");  
			//Uri uri = Uri.parse("http://bobo.hzduoqu.com/group1/M00/00/02/ChM3JFdnZRKAEBN9AROG3x8-3lk234.mp4");  
			//mediaPlayer.setDataSource(MainActivity.this, uri);
			mediaPlayer.setDataSource("http://bobo.hzduoqu.com/group1/M00/00/02/ChM3JFdnZRKAEBN9AROG3x8-3lk234.mp4");
			Log.d(tag, "mediaPlayer setDataSource: ");
			mediaPlayer.setDisplay(mSurface.getHolder());
			mediaPlayer.prepare();
			mediaPlayer.start();
			Log.d(tag, "mediaPlayer start(1): ");
			Toast.makeText(this, "播放成功", 0).show();
		} catch (IllegalArgumentException e) {
			Toast.makeText(this, "播放失败1", 0).show();
			e.printStackTrace();
		} catch (SecurityException e) {
			Toast.makeText(this, "播放失败2", 0).show();
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Toast.makeText(this, "播放失败3", 0).show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(this, "播放失败4", 0).show();
			e.printStackTrace();
		}
	}
	public void play2() {
		mediaPlayer.reset();
		try {
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			AssetFileDescriptor fd = null;
			fd = this.getAssets().openFd("1.flv"); 
			mediaPlayer.setDataSource(fd.getFileDescriptor(),  
                    fd.getStartOffset(), fd.getLength());
			mediaPlayer.setDisplay(mSurface.getHolder());
			mediaPlayer.prepare();
			mediaPlayer.seekTo(position);
			mediaPlayer.start();
			Toast.makeText(this, "播放成功", 0).show();
		} catch (IllegalArgumentException e) {
			Toast.makeText(this, "播放失败11", 0).show();
			e.printStackTrace();
		} catch (SecurityException e) {
			Toast.makeText(this, "播放失败21", 0).show();
			e.printStackTrace();
		} catch (IllegalStateException e) {
			Toast.makeText(this, "播放失败31", 0).show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(this, "播放失败41", 0).show();
			e.printStackTrace();
		}
	}
	
	
	private class SurfaceViewLis implements SurfaceHolder.Callback{

		
		public void surfaceCreated(SurfaceHolder holder) {
			//第一次进来
			if(position==0){
				try{
					Log.d(tag, "surfaceCreated position1 "+position);
					play();
				}catch(IllegalArgumentException e){
					e.printStackTrace();
				}
			}else if(position>0){
				try {
					Log.d(tag, "surfaceCreated position2 "+position);
					mediaPlayer.setDisplay(mSurface.getHolder());
					mediaPlayer.start();
					Log.d(tag, "mediaPlayer start(2): ");
					//play2();
				} catch (IllegalStateException e) {				
					e.printStackTrace();
				}
				
				
	        }
				
			
		}

		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {
			Log.d(tag, "surfaceChanged");
			
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			Log.d(tag, "surfaceDestroyed");
			/*if(mediaPlayer.isPlaying()){
				position=mediaPlayer.getCurrentPosition();
				Log.d(tag, "onPause mediaPlayer.stop1()");
				mediaPlayer.stop();
			}*/
		}
		
	}

	
	
	  @Override
	protected void onStart() {
		  Log.d(tag, "onStart");
		super.onStart();
	}

	@Override
	protected void onRestart() {
		
		
		Log.d(tag, "onRestart");
		super.onRestart();
	}

	@Override
	protected void onStop() {
		Log.d(tag, "onStop");
		super.onStop();
	}

	@Override
	    protected void onResume() {
		if(position>0){
			try {
				
				mediaPlayer.prepare();
				mediaPlayer.seekTo(position);
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		  Log.d(tag, "onResume");
	        super.onResume();

	    }
	  /*
	  @Override
	    protected void onSaveInstanceState(Bundle outState) {
	        super.onSaveInstanceState(outState);
	        outState.putInt("position", position);
	    }
*/

	@Override
	protected void onPause() {
		if(mediaPlayer.isPlaying()){
			position=mediaPlayer.getCurrentPosition();
			Log.d(tag, "onPause mediaPlayer.pause()");
			//mediaPlayer.pause();
			mediaPlayer.pause();
		}
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		if(mediaPlayer.isPlaying())
			mediaPlayer.stop();
		Log.d(tag, "onDestroy mediaPlayer.release() ");
		mediaPlayer.release();
		Log.d(tag, "mediaPlayer release: ");
		super.onDestroy();
	}
	
	
	

	
}
