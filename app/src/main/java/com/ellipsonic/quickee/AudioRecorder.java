package com.ellipsonic.quickee;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class AudioRecorder extends Activity {
	String audio_path_to_db;
	private static final String AUDIO_RECORDER_FILE_EXT_3GP = ".3gp";
	private static final String AUDIO_RECORDER_FILE_EXT_MP4 = ".mp4";
    TextView record_done;
    ImageView audio_back;
    Button btstart;
    ImageView mic;
	ProgressBar progressBar;
	private MediaRecorder recorder = null;
	private int currentFormat = 0;
	private int output_formats[] = { MediaRecorder.OutputFormat.MPEG_4,
			MediaRecorder.OutputFormat.THREE_GPP };
	private String file_exts[] = { AUDIO_RECORDER_FILE_EXT_MP4,
			AUDIO_RECORDER_FILE_EXT_3GP };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.audio_recorder);
        getActionBar().hide();
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setButtonHandlers();
		enableButtons(false);
        record_done = (TextView) findViewById(R.id.audio_recorded);
        audio_back = (ImageView) findViewById(R.id.audio_back);
        mic = (ImageView) findViewById(R.id.Microphone);
        btstart=(Button) findViewById(R.id.btnStart);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
       audio_back.setOnClickListener(new View.OnClickListener(){
            public  void  onClick(View v){
                Intent intent = new Intent();
                intent.putExtra("ImgPath","");
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        record_done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (audio_path_to_db.length() > 0) {

                    Intent intent = new Intent();
                    intent.putExtra("ImgPath",audio_path_to_db);
                    setResult(RESULT_OK, intent);
                    audio_path_to_db="";
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), "Audio Is not Recorded", Toast.LENGTH_LONG).show();
                }
            }
        });
	}




    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("ImgPath","");
        setResult(RESULT_OK, intent);
        finish();
    }

	private void setButtonHandlers() {
		((Button) findViewById(R.id.btnStart)).setOnClickListener(btnClick);
		((Button) findViewById(R.id.btnStop)).setOnClickListener(btnClick);

	}

	private void enableButton(int id, boolean isEnable) {
		((Button) findViewById(id)).setEnabled(isEnable);
	}

	private void enableButtons(boolean isRecording) {
		enableButton(R.id.btnStart, !isRecording);
	    enableButton(R.id.btnStop, isRecording);
	}


	private String getFilename() {
		String root = Environment.getExternalStorageDirectory().toString();
		File myDir = new File(root + "/Quickee/Audio/");
		Random generator = new Random();
		int n = 10000;
		n = generator.nextInt(n);
		String fname = "Audio"+ n +".mp3";
		audio_path_to_db =root + "/Quickee/Audio/"+fname;
		File   file=new File(myDir,fname );
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream oFile = new FileOutputStream(file, false);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	/*	String filepath = Environment.getExternalStorageDirectory().getPath();
	//	File file = new File(filepath, AUDIO_RECORDER_FOLDER);

		if (!file.exists()) {
			file.mkdirs();
		}*/

		return(file.getAbsolutePath());
	}

	private void startRecording() {
		recorder = new MediaRecorder();

		recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
		recorder.setOutputFormat(output_formats[currentFormat]);
		recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		recorder.setOutputFile(getFilename());

		recorder.setOnErrorListener(errorListener);
		recorder.setOnInfoListener(infoListener);

		try {
			recorder.prepare();
			recorder.start();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void stopRecording() {
		if (null != recorder) {
			recorder.stop();
			recorder.reset();
			recorder.release();
            recorder = null;
		}
	}



	private MediaRecorder.OnErrorListener errorListener = new MediaRecorder.OnErrorListener() {
		@Override
		public void onError(MediaRecorder mr, int what, int extra) {
			Toast.makeText(AudioRecorder.this,
					"Error: " + what + ", " + extra, Toast.LENGTH_LONG).show();
		}
	};

	private MediaRecorder.OnInfoListener infoListener = new MediaRecorder.OnInfoListener() {
		@Override
		public void onInfo(MediaRecorder mr, int what, int extra) {
			Toast.makeText(AudioRecorder.this,
					"Warning: " + what + ", " + extra, Toast.LENGTH_LONG)
					.show();
		}
	};

	private View.OnClickListener btnClick = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btnStart: {
				Toast.makeText(getApplicationContext(), "Recording",
						Toast.LENGTH_SHORT).show();

				enableButtons(true);

               mic.setImageResource(R.drawable.mic_100_red);
                startRecording();
                progressBar.setVisibility(View.VISIBLE);
				break;
			}
			case R.id.btnStop: {
				Toast.makeText(getApplicationContext(), "Recorded",
						Toast.LENGTH_SHORT).show();
				enableButtons(false);
			     mic.setImageResource(R.drawable.mic_100_bl);
				stopRecording();
                progressBar.setVisibility(View.GONE);
				break;
			}




			}
		}
	};

}
