package org.dreamchurch.mediateam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

/*
    CREATOR: HAROLD KARL FRANZE R. ALONSAGAY
    COURSE AND YEAR: BSIT-2ND YEAR
    SUBJECT: OOP
    SCHOOL: JOSE MARIA COLLEGE
 */

public class Main extends JFrame implements ActionListener, WindowListener {

    private JButton startButton, helpButton, creditsButton;
    private JLabel startLabel;
    private Font startButtonFont, startLabelFont;

    public Main() {
        StartGameplayForm.setMainMenuFrame(this);
        setSize(1300, 700);
        setTitle("Cat Ville");

        try {

            ImageIcon menuBackgroundImage = new ImageIcon("StartMenuFiles/pixelhouse.gif");

            Image menuBackgroundScaledImage = menuBackgroundImage.getImage().getScaledInstance(800, 600, Image.SCALE_DEFAULT);

            ImageIcon menuBackgroundScaledIcon = new ImageIcon(menuBackgroundScaledImage);

            setContentPane(new JLabel(menuBackgroundScaledIcon));

            setLayout(new FlowLayout());
        } catch (Exception e) {
            e.printStackTrace();
        }

        StartMenuDeclaration();

        String filePath = "Music/menumusic.wav";
        PlayMusicInBackground(filePath);

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);


        add(startLabel, gbc);
        gbc.gridy = 1;
        add(startButton, gbc);
        gbc.gridy = 2;
        add(helpButton, gbc);
        gbc.gridy = 3;
        add(creditsButton, gbc);

        setLocationRelativeTo(null);
        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public void StartMenuDeclaration() {
        // FONT
        startLabelFont = new Font("Comic Sans MS", Font.PLAIN, 60);
        startButtonFont = new Font("Comic Sans MS", Font.PLAIN, 25);

        startLabel = new JLabel("Cat Ville");
        startLabel.setFont(startLabelFont);
        startLabel.setForeground(Color.BLACK);

        startButton = new JButton("START");
        helpButton = new JButton("HELP");
        creditsButton = new JButton("CREDITS");

        startButton.setFont(startButtonFont);
        helpButton.setFont(startButtonFont);
        creditsButton.setFont(startButtonFont);

        startButton.addActionListener(this);
        helpButton.addActionListener(this);
        creditsButton.addActionListener(this);

    }

    /*
        THE MAIN FUNCTION
     */

   public static void main(String[]args){
       Main main = new Main();



   }

   /*
        ACTION AND WINDOW LISTENERS
    */

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(startButton)){

            Point lastLocation = getLocationOnScreen();

            setVisible(false);

            System.out.println("START RUN---------------");

            StartGameplayForm start1 = new StartGameplayForm();

            start1.setLocation(lastLocation);


        }
        else if(e.getSource().equals(helpButton)){

        }
        else if(e.getSource().equals(creditsButton)){

        }
    }

    @Override
    public void windowOpened(WindowEvent e) {


    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }


    /*
        PLAYING OF MUSIC
     */


     public void PlayMusicInBackground(String filePath) {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                try {
                    while(true) {
                        PlayMusic(filePath);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        worker.execute();
    }

    public void PlayMusic(String filePath){
        try {

            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);


            AudioFormat format = audioStream.getFormat();


            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);

            line.open(format);
            line.start();

            byte[] buffer = new byte[4096];

            int bytesRead = 0;

            while ((bytesRead = audioStream.read(buffer)) != -1) {
                line.write(buffer, 0, bytesRead);
            }

            line.drain();
            line.close();
            audioStream.close();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}
