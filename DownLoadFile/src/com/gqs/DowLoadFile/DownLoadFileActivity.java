package com.gqs.DowLoadFile;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class DownLoadFileActivity extends Activity {
	/** Called when the activity is first created. */
	private Button btnDownloadMP3;

	private LinearLayout linearLayout;
	private EditText etDownloadURL;
	private String urlStr="http://www.tjttjx.com/upload/1301475933.jpg";
    private HttpDownload downloader; 
    private List<HttpDownload> downloaderList=new ArrayList<HttpDownload>();
    private Handler handler;
    private UpdateThread updateThread;
    private EditText etFileName;
    private String result;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
		handler=new Handler()
		{
			public void handleMessage(Message msg)
			{
				HttpDownload httpDownload=(HttpDownload)msg.obj;
				httpDownload.progressBar.setProgress(httpDownload.getProgress());
//				System.out.println("handleMessage....."+httpDownload.getProgress());
//				Bundle bundle=msg.getData();
//				downloaderList.get(bundle.getInt("index")).progressBar.setProgress(bundle.getInt("progress"));
//				downloaderList.get(1).progressBar.se
				int res = downloader.getProgress()*100/downloader.fileSize; 
				downloader.tvShowProgress.setText("已下载："+res+"%"); 
				if(res==100)

				{
					downloader.tvShowProgress.setText("下载完成"); 
				}
			}
					
		};
		btnDownloadMP3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				downloader = new HttpDownload(getApplicationContext());
				if("".equals(etFileName.getText().toString().trim()))
				{
					Toast.makeText(getApplicationContext(), "请输入文件名称", Toast.LENGTH_SHORT).show();
					return;
				}
				if(downloader.isExist(etFileName.getText().toString().trim()))
				{
					Toast.makeText(getApplicationContext(), "该文件已存在", Toast.LENGTH_SHORT).show();
					return;
				}
				downloaderList.add(downloader);
				updateThread=new UpdateThread(downloader);
				Thread thread=new Thread(updateThread);
				View view=new View(getApplicationContext());
				linearLayout.addView(view,new LayoutParams(LayoutParams.FILL_PARENT,5));
				LinearLayout layout=new LinearLayout(getApplicationContext());
				layout.setOrientation(LinearLayout.VERTICAL);
				
				downloader.progressBar=new ProgressBar(getApplicationContext(),null,android.R.attr.progressBarStyleHorizontal);
				downloader.progressBar.setMax(downloader.getFileSize(urlStr));
				downloader.tvShowProgress=new TextView(getApplication());
				downloader.tvShowProgress.setGravity(Gravity.CENTER);
				layout.addView(downloader.progressBar, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				layout.addView(downloader.tvShowProgress,new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				linearLayout.addView(layout, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
				new Thread(new Runnable() {

					
					public void run() {
						// TODO Auto-generated method stub
						System.out.println("文件名"+urlStr.substring(urlStr.lastIndexOf("/") + 1));
						String result = downloader.downloadFile(urlStr,
								etFileName.getText().toString().trim());
//							Toast.makeText(DownLoadFileActivity.this, result,
//									Toast.LENGTH_SHORT).show();
						System.out.println("Activity下载完成");
					}
                    
				}).start();
				thread.start();
				etFileName.setText("");
			}

		});
	}
	public void init() {
		btnDownloadMP3 = (Button) findViewById(R.id.downloadMp3);
		linearLayout = (LinearLayout) findViewById(R.id.parentLayout);
		etDownloadURL = (EditText) findViewById(R.id.downloadURL);
		etFileName=(EditText)findViewById(R.id.etFileName);
//		scrollView=(ScrollView)findViewById(R.id.scrollView);
		etDownloadURL.setText("http://www.tjttjx.com/upload/1301475933.jpg");
	}
	public class UpdateThread implements Runnable
	{
        private HttpDownload httpDownload;
//        private int downloaderIndex;
        public UpdateThread(HttpDownload httpDownload)
        {
        	this.httpDownload=httpDownload;
//        	this.downloaderIndex=index;
        }
		@Override
		public void run() {
//			System.out.println("开始更新进度条");
			while(httpDownload.downloadSate!=httpDownload.DOWNLOAD_END)
			{
//				System.out.println("while开始更新进度条");
//				    	Bundle bundle=new Bundle();
//				    	bundle.putInt("index", downloaderIndex);
//				    	bundle.putInt("progress", downloaderList.get(downloaderIndex).getProgress());
				    	Message msg=handler.obtainMessage();
				    	msg.obj=httpDownload;
//				    	msg.setData(bundle);
				    	handler.sendMessage(msg);
			}
			System.out.println("downloaderList.size...."+downloaderList.size());
		
			downloaderList.remove(httpDownload);
			
			
		}
		
	}
}