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
This notepad is used to plan the Cat Meme Simulation program for OOP subject.


Summary:
        This program should mix the pet management system and a simple game of gacha about cat memes to demonstrate the whole concept of OOP.


Gameplay:
        This program should provide the users enjoyment and fun about raising cropped images of cats and feeding them.

Reminder:
       This program uses images and sounds that are owned by someone, therefore this is only for demonstration and not to be taken seriously.

Features;

GUI:

STEPS:



3 main PANEL:
        1. Menu PANEL
        2. Gameplay PANEL
        3. Help PANEL

1. Menu Panel should have 1. Start and Help
2. Gameplay Panel should have buttons to provide users control over the program
3. Help panel shoud provide the users what to do/expect.




// PROBLEMS THAT NEED TO SOLVE
1.  SETTING THE BUTTONS THE MANUALLY
2.


HERES THE SITUATION: the boughtcatshow wont show in the frame if it is in the spawnCat, however
when it is put inside the startgameplayform it will show, regardless

 */

public class Main extends JFrame implements ActionListener, WindowListener {
    /*
     DECLARATION OF JCOMPONENTS
     */
    // This buttons only exist in MENU PANEL
    private JButton startButton, helpButton, creditsButton;
    private JLabel startLabel;
    private Font startButtonFont, startLabelFont;






    /*

     */



    /*\
        MAKING THE JCOMPONENTS ALIVE
     */
    public Main() {
        StartGameplayForm.setMainMenuFrame(this);
        setSize(1300, 700);
        setTitle("Cat Ville");

        //BACKGROUND IMAGE
        try {
            BufferedImage backgroundImage = ImageIO.read(new File("StartMenuFiles/Gamebackground.png"));
            setContentPane(new BackgroundPanel(backgroundImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // CALLING THE JCOMPONENTS FUNCTIONS
        StartMenuDeclaration();


       // String filePath = "Music/menumusic.wav";
        //PlayMusicInBackground(filePath);



        //SETTING THE LAYOUT FOR MENU PANEL
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(20, 20, 20, 20);

        // ADDING THE LABEL AT THE TOP
        add(startLabel, gbc);

        gbc.gridy = 1;

        // ADDING THE BUTTONS IN THE CENTER
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

        // LABELS
        startLabel = new JLabel("Cat Ville");
        startLabel.setFont(startLabelFont);
        startLabel.setForeground(Color.BLACK);



        // BUTTONS
        startButton = new JButton("START");
        helpButton = new JButton("HELP");
        creditsButton = new JButton("CREDITS");

        // SETTING FONT FOR THE BUTTONS
        startButton.setFont(startButtonFont);
        helpButton.setFont(startButtonFont);
        creditsButton.setFont(startButtonFont);

        //ADDING THE BUTTONS IN THE FRAME
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
            // This code will open the gameplay form
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

    //This handles open and close of frames


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
            // Open the audio file
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Get the format of the audio file
            AudioFormat format = audioStream.getFormat();

            // Create a data line to play the audio
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            SourceDataLine line = (SourceDataLine) AudioSystem.getLine(info);
            line.open(format);
            line.start();

            // Read the audio data from the input stream and write it to the line
            byte[] buffer = new byte[4096];
            int bytesRead = 0;
            while ((bytesRead = audioStream.read(buffer)) != -1) {
                line.write(buffer, 0, bytesRead);
            }

            // Wait for the line to finish playing before closing it
            line.drain();
            line.close();

            // Close the audio input stream
            audioStream.close();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }
     */

}
