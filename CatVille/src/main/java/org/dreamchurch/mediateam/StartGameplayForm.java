package org.dreamchurch.mediateam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class StartGameplayForm extends JFrame implements ActionListener, WindowListener, MouseListener {
    /*
     DECLARATION OF JCOMPONENTS
     */
    // This buttons only exist in START GAMEPLAY FFORM
    private JButton mainMenuButton, petInformationButton, petShopButton;
    private Font topFontButtons, catCurrency, petShopFont, catInfoFont;
    private JLabel catCoinz, catCoinzAmount;
    private int userCatCoinz;



    /*
        PETSHOP JCOMPONENTS
     */
    JFrame PetShop;
    private JLabel cat1, cat2, cat3, cat4;
    private JButton cat1Buy, cat2Buy, cat3Buy, cat4Buy;

    private static Main mainMenuFrame;

    /*
        CAT INFO JCOMPONENTS
     */
    JFrame catInfo;
    private JButton buyCat, cancelBuy;
    private JLabel catName;
    private JTextField userCatName;
    private JPanel catInfoPanel, catInfoButtonPanel, catArea;

    private boolean isCatBought1, isCatBought2, isCatBought3, isCatBought4;


    /* public StartGameplayForm(){
        setSize(1300, 700);
        setTitle("Cat Ville");
        StartGameplayFormComponentsDeclaration();

        try {
            BufferedImage backgroundImage = ImageIO.read(new File("GameplayFiles/GameplayBackground.png"));
            setContentPane(new BackgroundPanel(backgroundImage));
        } catch (IOException e) {
            e.printStackTrace();
        }




        catArea = new JPanel();
        catArea.setLayout(null);
        catArea.setBounds(0, 500, 500, 500);
        catArea.setBackground(Color.RED);
        catArea.setOpaque(true);


        //BACKGROUND IMAGE

        setLayout(new FlowLayout(FlowLayout.LEFT)); // Putting the buttons in the top left




        //ADDING THE BUTTONS IN THE FRAME
        add(mainMenuButton);
        add(petInformationButton);
        add(petShopButton);

        //ADDING THE LABELS IN THE FRAME
        add(catCoinz);
        add(catCoinzAmount);

        //ADDING THE LOCATION OF CATS
        add(catArea);
        catArea.setVisible(true);

        //ADDING THE BUTTONS IN THE FRAME
        add(mainMenuButton);
        add(petInformationButton);
        add(petShopButton);

        //ADDING THE LABELS IN THE FRAME
        add(catCoinz);
        add(catCoinzAmount);






        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        System.out.println("Panel Visiblity:" + catArea.isVisible());
        System.out.println("Panel Visiblity:" + catArea.getBounds());
        System.out.println("Frame visibility: " + isVisible());

    }


     */

    public StartGameplayForm(){
        setSize(1300, 700);
        setTitle("Cat Ville");

        StartGameplayFormComponentsDeclaration();

        try {
            BufferedImage backgroundImage = ImageIO.read(new File("GameplayFiles/GameplayBackground.png"));
            setContentPane(new BackgroundPanel(backgroundImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Use null layout for precise positioning
        setLayout(null);

      /*
        catArea = new JPanel();
        catArea.setLayout(null);
        catArea.setBounds(0, 500, 1290, 200);
        //catArea.setBackground(Color.RED);
        catArea.setOpaque(true);
       */

        // Adding components to Frame
        add(mainMenuButton);
        add(petInformationButton);
        add(petShopButton);
        add(catCoinz);
        add(catCoinzAmount);

        // Adding the catArea panel
        //add(catArea);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        System.out.println("Frame visibility: " + isVisible());
    }



     /*
        MAKING THE JCOMPONENTS ALIVE
     */

    public void StartGameplayFormComponentsDeclaration(){

        //FONT
        topFontButtons = new Font("Comic Sans MS", Font.PLAIN, 25);
        catCurrency = new Font("Helvetica", Font.BOLD, 25);

        //BUTTONS
        mainMenuButton = new JButton("MAIN MENU");
        mainMenuButton.setBounds(0, 0, 240, 50);
        petInformationButton = new JButton("CAT INFO");
        petInformationButton.setBounds(250, 0, 240, 50);
        petShopButton = new JButton("CAT SHOP");
        petShopButton.setBounds(510, 0, 240, 50);

        //LABELS
        catCoinz = new JLabel("Cat Coinz: ");
        catCoinzAmount = new JLabel("100");




        //SETTING FONT FOR BUTTONS/LABELS
        mainMenuButton.setFont(topFontButtons);
        petInformationButton.setFont(topFontButtons);
        petShopButton.setFont(topFontButtons);
        catCoinz.setFont(topFontButtons);
        catCoinzAmount.setFont(catCurrency);


        //ADDING ACTION LISTENER
        mainMenuButton.addActionListener(this);
        petInformationButton.addActionListener(this);
        petShopButton.addActionListener(this);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(mainMenuButton)) {

            try {
                mainMenuFrame.setLocation(getLocationOnScreen());
                dispose();
            } catch (IllegalComponentStateException ex) {
                // Handle the exception, or ignore it if not critical
            }
            mainMenuFrame.setVisible(true);
        }
        if(e.getSource().equals(petInformationButton)){

        }
        if(e.getSource().equals(petShopButton)){

            PetShop();
        }
        if (e.getSource().equals(cat1Buy)){
            isCatBought1 = true;
            CatInfo();
            System.out.println("Check logic ");

        }
        if (e.getSource().equals(buyCat)){
            catInfo.dispose();
            PetShop.dispose();
            System.out.println("Check buyCat Button ");

            spawnCat();
        }

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

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
    public static void setMainMenuFrame(Main main) {
        mainMenuFrame = main;

    }



    /*
     SMALL JFRAMES
     */

    public void PetShop(){
        PetShop = new JFrame();

        PetShop.setSize(700, 500);
        PetShop.setTitle("Pet Shop");
        PetShop.setLayout(null);
        initializationOfPets();


        PetShop.setLocationRelativeTo(null);

        PetShop.setVisible(true);
        PetShop.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



    }

    public void initializationOfPets(){
        //FONT
        petShopFont = new Font("Comic Sans MS", Font.PLAIN, 15);

        //PET SHOP LABELS
        cat1 = new JLabel(resizeImageIcon(new ImageIcon("PET CATS/Cat1.png"), 150, 150));
        cat1.setBounds(30, 20, 150, 150);
        cat2 = new JLabel(resizeImageIcon(new ImageIcon("PET CATS/Cat2.png"), 240, 240));
        cat2.setBounds(190, 20, 150, 150);
        cat3 = new JLabel(resizeImageIcon(new ImageIcon("PET CATS/Cat3.png"), 240, 240));
        cat3.setBounds(350, 20, 150, 150);
        cat4 = new JLabel(resizeImageIcon(new ImageIcon("PET CATS/Cat4.png"), 180, 170));
        cat4.setBounds(510, 20, 150, 150);

        //PET SHOP BUTTONS
        cat1Buy = new JButton("100 CatCoinz");
        cat1Buy.setFont(petShopFont);
        cat1Buy.setBounds(20, 200, 150, 30);
        cat2Buy = new JButton("100 CatCoinz");
        cat2Buy.setFont(petShopFont);
        cat2Buy.setBounds(180, 200, 150, 30);
        cat3Buy = new JButton("100 CatCoinz");
        cat3Buy.setFont(petShopFont);
        cat3Buy.setBounds(340, 200, 150, 30);
        cat4Buy = new JButton("100 CatCoinz");
        cat4Buy.setFont(petShopFont);
        cat4Buy.setBounds(500, 200, 150, 30);



        //ADDING THE BUTTONS AND LABELS IN THE FRAME
        PetShop.add(cat1);
        PetShop.add(cat2);
        PetShop.add(cat3);
        PetShop.add(cat4);
        PetShop.add(cat1Buy);
        PetShop.add(cat2Buy);
        PetShop.add(cat3Buy);
        PetShop.add(cat4Buy);

        //ADDING ACTION LISTENER
        cat1Buy.addActionListener(this);
        cat2Buy.addActionListener(this);
        cat3Buy.addActionListener(this);
        cat4Buy.addActionListener(this);


    }


    // RESIZING IMAGE ICONS
    private ImageIcon resizeImageIcon(ImageIcon originalIcon, int width, int height) {
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

    //SETTING UP THE NAME OF THE CAT WHEN BOUGHT
    public void CatInfo() {
        catInfo = new JFrame();
        catInfoPanel = new JPanel();
        catInfo.setTitle("Cat Info");
        catInfo.setSize(500, 300);
        catInfo.setLayout(new BorderLayout(2, 2));
        catInfo.add(catInfoPanel, BorderLayout.CENTER);  // Changed to BorderLayout.CENTER
        catInfoInstantiation();
        catInfo.setVisible(true);
        catInfo.setLocationRelativeTo(null);
        catInfo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void catInfoInstantiation() {
        catInfoFont = new Font("Comic Sans MS", Font.PLAIN, 20);

        // DECLARING the BUTTONS
        buyCat = new JButton("BUY");
        cancelBuy = new JButton("CANCEL");

        // DECLARING THE LABELS
        catName = new JLabel("Cat Name: ");

        // DECLARING THE TEXTFIELD
        userCatName = new JTextField();
        userCatName.setColumns(20);

        // SETTING THE FONT
        catName.setFont(catInfoFont);

        // SETTING THE LOCATION OF THE JCOMPONENTS
        catInfoPanel.setLayout(new BorderLayout(10, 10));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(catName);
        centerPanel.add(userCatName);

        catInfoPanel.add(centerPanel, BorderLayout.CENTER);

        // ADDING THE BUTTONS, LABELS, TEXTFIELD ON THE CAT INFO GUI
        catInfoButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        catInfoButtonPanel.add(buyCat);
        catInfoButtonPanel.add(cancelBuy);

        catInfoPanel.add(catInfoButtonPanel, BorderLayout.SOUTH);


        //ADDING ACTION LISTENER
        buyCat.addActionListener(this);
        cancelBuy.addActionListener(this);
    }

    //SPAWNING THE CAT
    public void spawnCat(){
        if(isCatBought1){
            ImageIcon boughtCat = new ImageIcon("PET CATS/Cat1.png");




            Image scaledImager = boughtCat.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);

            ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

            JLabel boughtCatShow = new JLabel(scaledImageofCat);
            boughtCatShow.setBounds(40, 430, 200, 200);


            System.out.println("Check spawnCat()");
            add(boughtCatShow);
            revalidate();
            repaint();
            System.out.println("Visible?: " + boughtCatShow.isVisible());
            System.out.println("Check End");




        }



    }



}
