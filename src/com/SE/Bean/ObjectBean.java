/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.SE.Bean;

import com.SE.myPlayer.MusicPlayerGUI;

/**
 *
 * @author Anand
 */
public class ObjectBean {
    private MusicPlayerGUI mpg;
    private String title;
    private String lastOpen;

    public String getLastOpen() {
        return lastOpen;
    }

    public void setLastOpen(String lastOpen) {
        this.lastOpen = lastOpen;
    }

    public MusicPlayerGUI getMpg() {
        return mpg;
    }

    public void setMpg(MusicPlayerGUI mpg) {
        this.mpg = mpg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
