/*  GROUP C's project : That's Music player with the java GUI 
using the Jlayer library

*/



import javazoom.jl.player.Player;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class MusicPlayer implements ActionListener {

    JFrame frame;

    JLabel songName;

    JButton select;

    JPanel playerPanel, controlPanel;

    Icon iconPlay, iconPause, iconResume, iconStop;

    JButton play, pause, resume, stop;

    JFileChooser fileChooser;
    FileInputStream fileInputStream;
    BufferedInputStream bufferedInputStream;
    File myFile = null;
    String filename, filePath;
    long totalLength, pauseLength;
    Player player;
    Thread playThread, resumeThread;

    public MusicPlayer() {

        initUI();
        addActionEvents();

        playThread = new Thread(runnablePlay);
        resumeThread = new Thread(runnableResume);

    }

    public void initUI() {

        songName = new JLabel("", SwingConstants.CENTER);

        select = new JButton("SELECT SONG");

        playerPanel = new JPanel();
        controlPanel = new JPanel();

        iconPlay = new ImageIcon("C:\\Users\\DAUDA\\Desktop\\groupC\\music-player-icons\\play2.png");
        iconPause = new ImageIcon("C:\\Users\\DAUDA\\Desktop\\groupC\\music-player-icons\\pause2.png");
        iconResume = new ImageIcon("C:\\Users\\DAUDA\\Desktop\\groupC\\music-player-icons\\resume2.png");
        iconStop = new ImageIcon("C:\\Users\\DAUDA\\Desktop\\groupC\\music-player-icons\\stop2.png");

        play = new JButton(iconPlay);
        pause = new JButton(iconPause);
        resume = new JButton(iconResume);
        stop = new JButton(iconStop);

        playerPanel.setLayout(new GridLayout(2, 1));

        playerPanel.add(select);
        playerPanel.add(songName);

        controlPanel.setLayout(new GridLayout(1, 4));

        controlPanel.add(play);
        controlPanel.add(pause);
        controlPanel.add(resume);
        controlPanel.add(stop);

        play.setBackground(Color.GREEN);
        pause.setBackground(Color.YELLOW);
        resume.setBackground(Color.WHITE);
        stop.setBackground(Color.RED);

        // Initialing the frame
        frame = new JFrame();

        frame.setTitle("Group C");

        frame.add(playerPanel, BorderLayout.NORTH);
        frame.add(controlPanel, BorderLayout.SOUTH);

        frame.setBackground(Color.white);
        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void addActionEvents() {

        select.addActionListener(this);
        play.addActionListener(this);
        pause.addActionListener(this);
        resume.addActionListener(this);
        stop.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(select)) {
            fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("C:\\Users\\Kwabena ODK1\\Downloads\\Music"));
            fileChooser.setDialogTitle("Select Mp3");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setFileFilter(new FileNameExtensionFilter("Mp3 files", "mp3"));
            if (fileChooser.showOpenDialog(select) == JFileChooser.APPROVE_OPTION) {
                myFile = fileChooser.getSelectedFile();
                filename = fileChooser.getSelectedFile().getName();
                filePath = fileChooser.getSelectedFile().getPath();
                songName.setText("File Selected : " + filename);
            }
        }
        if (e.getSource().equals(play)) {

            if (filename != null) {
                playThread.start();
                songName.setText(" playing : " + filename);
            } else {
                songName.setText("No Song was selected!");
            }
        }
        if (e.getSource().equals(pause)) {

            if (player != null && filename != null) {
                try {
                    pauseLength = fileInputStream.available();
                    player.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }

        if (e.getSource().equals(resume)) {

            if (filename != null) {
                resumeThread.start();
            } else {
                songName.setText("No song was selected!");
            }
        }
        if (e.getSource().equals(stop)) {
            // code for stop button
            if (player != null) {
                player.close();
                songName.setText("");
            }

        }

    }

    Runnable runnablePlay = new Runnable() {
        @Override
        public void run() {
            try {

                fileInputStream = new FileInputStream(myFile);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                player = new Player(bufferedInputStream);
                totalLength = fileInputStream.available();
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    Runnable runnableResume = new Runnable() {
        @Override
        public void run() {
            try {

                fileInputStream = new FileInputStream(myFile);
                bufferedInputStream = new BufferedInputStream(fileInputStream);
                player = new Player(bufferedInputStream);
                fileInputStream.skip(totalLength - pauseLength);
                player.play();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public static void main(String[] args) {
        MusicPlayer gc = new MusicPlayer();
    }
}