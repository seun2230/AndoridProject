package com.example.project4_2;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button button1;
   ImageButton button2;
    //Button button3;
    Button button4;
    Button vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //R 은 리소스 아래 layout 에 activitymain 이 있다
        button1 = (Button) findViewById(R.id.button1); //리소스에 있다 button1
        button2 = (ImageButton) findViewById(R.id.button2); //리소스에 있다 button1
        //button3 = (Button) findViewById(R.id.button3); //리소스에 있다 button1
        button4 = (Button) findViewById(R.id.button4); //리소스에 있다 button1
        vv = findViewById(R.id.vv);


        //버튼 1에 대한 이벤트를 설정한다
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //인텐트 아직 배우지 않았다. 하지만 꼭 필요한 부분이다. 메인 페이지에서 다른 페이지생성 및 전환이 필요할때 사용하는 객체이다.
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.119.go.kr/Center119/main.do"));        //Uri 를 이용하여 웹브라우저를 통해 웹페이지로 이동하는 기능
                startActivity((mIntent));
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:/119"));                 //tel : 은 911번호 전화번호를 세팅하여 통화버튼을 누르면 바로 통화가 가능하다.
                startActivity((mIntent));
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();                                                                                           //finish() 현재 엑티비티(화면)을 종료하는 메소드이다.
            }
        });
        vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.safetv.go.kr/GenCMS/gencms/cmsMng.do?sub_num=244&BBS_STATE=view&BOARD_IDX=1642&gcode=CG0000014&pageNo=1"));
                startActivity((mIntent));
            }
        });

    }
}
