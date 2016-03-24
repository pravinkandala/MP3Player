package edu.niu.cs.z1761257.mp3player;

import android.app.ListActivity;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {

    //variables
    private Button playPauseBtn, stopBtn;
    private List<String> songs;
    private MediaPlayer player;

    private final static String TAG = "MP3";
    private final static String SD_PATH = "/sdcard/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        songs = new ArrayList<String>();
        player = new MediaPlayer();

        updatePlayList();

        playPauseBtn = (Button)findViewById(R.id.playPauseButton);
        stopBtn = (Button)findViewById(R.id.stopButton);

        playPauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(player.isPlaying()){
                    player.pause();
                    playPauseBtn.setBackgroundResource(R.drawable.play);
                }
                else{
                    player.start();
                    playPauseBtn.setBackgroundResource(R.drawable.pause);
                }
            }
        });//end of playPauseBtn

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.stop();
                playPauseBtn.setBackgroundResource(R.drawable.pause);
                playPauseBtn.setVisibility(View.INVISIBLE);
                stopBtn.setVisibility(View.INVISIBLE);

            }
        });//end of stopBtn


    }//end of onCreate

    public void updatePlayList(){

        File home = new File(SD_PATH);
        File [] mp3Files = home.listFiles(new MP3Filter());

        if(mp3Files.length > 0){
            for (File file: mp3Files)
            {
                songs.add(file.getName());
            }
        }
        ArrayAdapter<String> songListAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,songs);
        setListAdapter(songListAdapter);

    }//end of updatePlayList

    class MP3Filter implements FilenameFilter{
        @Override
        public boolean accept(File dir, String filename){
            return (filename.endsWith(".mp3"));
        }

    }//end of MP3Filter class

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        //super.onListItemClick(l, v, position, id);
        try{
            player.reset();
            player.setDataSource(SD_PATH + songs.get(position));
            player.prepare();
            player.start();

            playPauseBtn.setBackgroundResource(R.drawable.pause);
            playPauseBtn.setVisibility(View.VISIBLE);
            stopBtn.setVisibility(View.VISIBLE);

        }catch (IOException e){
            Log.d(TAG, e.getMessage());
        }
    }
}//end of MainActivity
