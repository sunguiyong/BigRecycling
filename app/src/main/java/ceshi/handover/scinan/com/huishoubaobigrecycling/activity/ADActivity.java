package ceshi.handover.scinan.com.huishoubaobigrecycling.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ceshi.handover.scinan.com.huishoubaobigrecycling.R;

public class ADActivity extends AppCompatActivity {
    int x = R.layout.activity_video;
    @BindView(R.id.video_view)
    VideoView videoView;
    private List<String> urlList = new ArrayList<>();
    private int index;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_video);
        ButterKnife.bind(this);

        videoView.start();

    }

    public void playVideoOne() {
        urlList.add("https://mp4.vjshi.com/2017-04-08/20160e859ebffc0f9f505a2ef946b093.mp4");
        int urlSize = urlList.size();
        index = index % urlSize;

        MediaController mediaController1 = new MediaController(this);

    }
}
