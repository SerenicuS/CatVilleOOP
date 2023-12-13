package org.dreamchurch.mediateam;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
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

    /*
        Pet Information Instantiation JComponents

     */

    private JLabel catInfoNameLabel1, catInfoNameLabel2, catInfoNameLabel3, catInfoNameLabel4, catInfoTypeLabel;
    private JLabel catInfoNameCat, catInfoTypeCat;
    private Font petDetailsFont, userInputDetailsFont;
    private JFrame petInformationFrame;
    private JPanel cat1Panel, cat2Panel, cat3Panel, cat4Panel, catPicPanel;
    private JButton editCatInfo, closeCatInfo;
    private JTextField cat1TxtName, cat2TxtName, cat3TxtName, cat4TxtName;

    /*
        GLOBAL VARIABLES THAT MIGHT BE USED FOR OTHER CLASS
     */

    CatClass myCat;




    /*






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
        /*
            THIS ACTION LISTENER STATEMENTS ARE INSIDE THE STARTGAMEPLAYFORM FRAME
         */
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
            PetInformationFrame();
        }
        if(e.getSource().equals(petShopButton)){

            PetShop();
        }

        /*
            THIS ACTION LISTENER STATEMENTS ARE INSIDE THE PETSHOP FRAME
         */

        if (e.getSource().equals(cat1Buy)){
            isCatBought1 = true;
            CatInfo();
            System.out.println("Check logic");

        }
        if (e.getSource().equals(cat2Buy)){
            isCatBought2 = true;
            CatInfo();
            System.out.println("Check logic");

        }
        if (e.getSource().equals(cat3Buy)){
            isCatBought3 = true;
            CatInfo();
            System.out.println("Check logic");

        }
        if (e.getSource().equals(cat4Buy)){
            isCatBought4 = true;
            CatInfo();
            System.out.println("Check logic");

        }
        if (e.getSource().equals(buyCat)){
            catInfo.dispose();
            PetShop.dispose();

            CatClass myCat = new CatClass(userCatName.getText());




            myCat.testCat();
            SpawnCat();
            SetUserPutName();
        }
        if(e.getSource().equals(cancelBuy)){
            catInfo.dispose();
        }

        /*
            THIS ACTION LISTENER STATEMETNS ARE INSIDE THE PETINFORMATION FRAME
         */
        if (e.getSource().equals(editCatInfo)) {

        }
        if(e.getSource().equals(closeCatInfo)){
            petInformationFrame.dispose();
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
    public void SpawnCat(){
        if(isCatBought1){
            ImageIcon boughtCat = new ImageIcon("PET CATS/Cat1.png");




            Image scaledImager = boughtCat.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);

            ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

            JLabel boughtCatShow = new JLabel(scaledImageofCat);
            boughtCatShow.setBounds(70, 430, 200, 200);


            System.out.println("Check spawnCat()");
            add(boughtCatShow);
            revalidate();
            repaint();
            System.out.println("Visible?: " + boughtCatShow.isVisible());
            System.out.println("Check End");




        }
        if(isCatBought2){
            ImageIcon boughtCat = new ImageIcon("PET CATS/Cat2.png");




            Image scaledImager = boughtCat.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);

            ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

            JLabel boughtCatShow = new JLabel(scaledImageofCat);
            boughtCatShow.setBounds(600, 330, 200, 200);


            System.out.println("Check spawnCat()");
            add(boughtCatShow);
            revalidate();
            repaint();
            System.out.println("Visible?: " + boughtCatShow.isVisible());
            System.out.println("Check End");




        }
        if(isCatBought3){
            ImageIcon boughtCat = new ImageIcon("PET CATS/Cat3.png");




            Image scaledImager = boughtCat.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);

            ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

            JLabel boughtCatShow = new JLabel(scaledImageofCat);
            boughtCatShow.setBounds(500, 250, 600, 600);


            System.out.println("Check spawnCat()");
            add(boughtCatShow);
            revalidate();
            repaint();
            System.out.println("Visible?: " + boughtCatShow.isVisible());
            System.out.println("Check End");




        }
        if(isCatBought4){
            ImageIcon boughtCat = new ImageIcon("PET CATS/Cat4.png");




            Image scaledImager = boughtCat.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);

            ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

            JLabel boughtCatShow = new JLabel(scaledImageofCat);
            boughtCatShow.setBounds(680, 250, 400, 400);


            System.out.println("Check spawnCat()");
            add(boughtCatShow);
            revalidate();
            repaint();
            System.out.println("Visible?: " + boughtCatShow.isVisible());
            System.out.println("Check End");




        }



    }

    //PET INFORMATION BUTTON FRAME
    public void PetInformationFrame(){
        petInformationFrame = new JFrame();
        petInformationFrame.setSize(500, 700);
        petInformationFrame.setTitle("Pet Information Frame");
        petInformationFrame.setLayout(null);
        PetInformationInstantiation();

        petInformationFrame.setLocationRelativeTo(null);
        petInformationFrame.setVisible(true);
        petInformationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);



    }

    public void PetInformationInstantiation() {
        /*
            FONT
         */
        petDetailsFont = new Font("Comic Sans MS", Font.PLAIN, 20);
        userInputDetailsFont = new Font("Comic Sans MS", Font.PLAIN, 30);
        /*
            PET BUTTONS
         */
        editCatInfo = new JButton("EDIT");
        editCatInfo.setFont(petDetailsFont);
        editCatInfo.setBounds(190, 590, 100, 50);
        closeCatInfo = new JButton("CLOSE");
        closeCatInfo.setFont(petDetailsFont);
        closeCatInfo.setBounds(290, 590, 100, 50);

        /*
            PET TEXTFIELD
         */
        cat1TxtName = new JTextField("NULL");
        cat1TxtName.setBounds(0, 0, 200, 30); // Set explicit size
        cat1TxtName.setFont(userInputDetailsFont);

        cat2TxtName = new JTextField("NULL");
        cat2TxtName.setBounds(0, 0, 200, 30);
        cat2TxtName.setFont(userInputDetailsFont);

        cat3TxtName = new JTextField("NULL");
        cat3TxtName.setBounds(0, 0, 200, 30);
        cat3TxtName.setFont(userInputDetailsFont);

        cat4TxtName = new JTextField("NULL");
        cat4TxtName.setBounds(0, 0, 200, 30);
        cat4TxtName.setFont(userInputDetailsFont);





        /*
            PET LABELS
         */
        catInfoNameLabel1 = new JLabel("Cat 1 Name: ");
        catInfoNameLabel1.setFont(petDetailsFont);
        //catInfoTypeLabel = new JLabel("Cat 1 Type: ");
        //catInfoTypeLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));

        catInfoNameLabel2 = new JLabel("Cat 2 Name: ");
        catInfoNameLabel2.setFont(petDetailsFont);
        //catInfoTypeLabel = new JLabel("Cat 1 Type: ");
        //catInfoTypeLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));

        catInfoNameLabel3 = new JLabel("Cat 3 Name: ");
        catInfoNameLabel3.setFont(petDetailsFont);;
        // catInfoTypeLabel = new JLabel("Cat 1 Type: ");
        //setFont(new Font("Comic Sans MS", Font.PLAIN, 25));

        catInfoNameLabel4 = new JLabel("Cat 4 Name: ");
        catInfoNameLabel4.setFont(petDetailsFont);
        //catInfoTypeLabel = new JLabel("Cat 1 Type: ");
        //catInfoTypeLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));


        /*
        CAT PANELS
         */
        cat1Panel = new JPanel();
        cat1Panel.setLayout(new BoxLayout(cat1Panel, BoxLayout.Y_AXIS));
        cat1Panel.setBackground(Color.RED);
        cat1Panel.setBounds(150, 10, 350, 130);



        cat2Panel = new JPanel();
        cat2Panel.setLayout(new BoxLayout(cat2Panel, BoxLayout.Y_AXIS));
        cat2Panel.setBackground(Color.YELLOW);
        cat2Panel.setBounds(150, 150, 350, 130);



        cat3Panel = new JPanel();
        cat3Panel.setLayout(new BoxLayout(cat3Panel, BoxLayout.Y_AXIS));
        cat3Panel.setBackground(Color.BLUE);
        cat3Panel.setBounds(150, 300, 350, 130);



        cat4Panel = new JPanel();
        cat4Panel.setLayout(new BoxLayout(cat4Panel, BoxLayout.Y_AXIS));
        cat4Panel.setBackground(Color.GREEN);
        cat4Panel.setBounds(150, 450, 350, 130);



        catPicPanel = new JPanel();
        catPicPanel.setLayout(null);
        catPicPanel.setBackground(Color.BLACK);
        catPicPanel.setBounds(0, 10, 140, 570);






        /*
            ADDING THE COMPONENTS SECTION

         */

        petInformationFrame.add(catPicPanel);

        petInformationFrame.add(cat1Panel);
        petInformationFrame.add(cat2Panel);
        petInformationFrame.add(cat3Panel);
        petInformationFrame.add(cat4Panel);

        cat1Panel.add(catInfoNameLabel1);
        cat2Panel.add(catInfoNameLabel2);
        cat3Panel.add(catInfoNameLabel3);
        cat4Panel.add(catInfoNameLabel4);
        //cat1Panel.add(catInfoTypeLabel);

        cat1Panel.add(cat1TxtName);
        cat2Panel.add(cat2TxtName);
        cat3Panel.add(cat3TxtName);
        cat4Panel.add(cat4TxtName);

        cat1TxtName.setEditable(false);
        cat2TxtName.setEditable(false);
        cat3TxtName.setEditable(false);
        cat4TxtName.setEditable(false);

        petInformationFrame.add(editCatInfo);
        petInformationFrame.add(closeCatInfo);




        //DEAD END
    }


    /*
        THIS AREA IS ONLY FOR CRUD OPERATIONS
     */

    public void SetUserPutName(){
        if(isCatBought1){
         cat1TxtName.setText(userCatName.getText());
         isCatBought1 = false;


        }
        if(isCatBought2){
            cat2TxtName.setText(userCatName.getText());
            isCatBought2 = false;


        }
        if(isCatBought3){
            cat3TxtName.setText(userCatName.getText());
            isCatBought3 = false;
        }
        if(isCatBought4){
            cat4TxtName.setText(userCatName.getText());
            isCatBought4 = false;


        }





    }






}





















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