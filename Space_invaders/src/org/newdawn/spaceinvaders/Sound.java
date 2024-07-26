package org.newdawn.spaceinvaders;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

public class Sound {

    //사운드 재생 클래스입니다.



    public Sound() {}
    public void soundPlay(String file, boolean Loop){

        //사운드재생용메소드

        //사운드파일을받아들여해당사운드를재생시킨다.


        Clip clip;


        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream(file)));

            clip = AudioSystem.getClip();

            clip.open(ais);

            clip.start();


            if ( Loop) clip.loop(-1);

            //Loop 값이 true면 무한재생한다.

            //false면 한 번만 재생시킨다.


        } catch (Exception e) {

            e.printStackTrace();

        }

    }
}
