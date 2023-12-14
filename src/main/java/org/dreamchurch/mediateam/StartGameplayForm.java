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
    private int userCatCoinz = 400;



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
    private JPanel catInfoPanel, catInfoButtonPanel;

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
    private JTextField cat1TxtName;
    private JTextField cat2TxtName;
    private JTextField cat3TxtName;
    private JTextField cat4TxtName;

    /*
        GLOBAL VARIABLES THAT MIGHT BE USED FOR OTHER CLASS
     */


    /*
        EDIT CAT FRAME
     */
    JFrame editTool;

    private JButton saveCat, cancelSave;
    private JPanel editInfoPanel, editInfoButtonPanel;
    private Font editInfoFont;
    private JLabel catEditedName;
    private JTextField catEditedTextField;


    /*
        STORAGE OF THE CATNAMES
     */
    private String[] catStorage;
    private int counter = 0;



    /*






     */

    public StartGameplayForm(){
        setSize(1300, 700);
        setTitle("Cat Ville");

        StartGameplayFormComponentsDeclaration();
        PetInformationFrame();

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

        catStorage = new String[4];



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
        petShopButton.setBounds(500, 0, 240, 50);

        //LABELS
        catCoinz = new JLabel("Cat Coinz: ");
        catCoinz.setBounds(800, 0, 300, 50);

        catCoinzAmount = new JLabel(String.valueOf(userCatCoinz));
        catCoinzAmount.setBounds(950, 0, 200, 50);



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
            petInformationFrame.setVisible(true);

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
            CatTransaction(100);
        }
        if(e.getSource().equals(cancelBuy)){
            catInfo.dispose();
        }

        /*
            THIS ACTION LISTENER STATEMETNS ARE INSIDE THE PETINFORMATION FRAME
         */
        if (e.getSource().equals(editCatInfo)) {
            EditTheCatInfo();
        }
        if(e.getSource().equals(closeCatInfo)){
            editCatInfo.setEnabled(false);
            petInformationFrame.dispose();
        }

        /*
            THIS ACTION LISTENER IS FOR THE EDIT THE CAT INFO
         */
        if(e.getSource().equals(saveCat)){

        }
        if(e.getSource().equals(cancelSave)){
            editTool.dispose();
        }


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        /*
            THIS SECTION IS TO EDIT THE NAMES OF THE CATS IN PETINFORMTIONFRAME
         */
        System.out.println("Check Mouse Event ");
        if (e.getSource().equals(cat1Panel) && !cat1TxtName.getText().isEmpty()) {
            editCatInfo.setEnabled(true);

            System.out.println("Check MouseCLicked in cat1Panel");
        }
        if (e.getSource().equals(cat2Panel) && !cat2TxtName.getText().isEmpty()) {
            editCatInfo.setEnabled(true);

            System.out.println("Check MouseCLicked in cat2Panel");
        }
        if (e.getSource().equals(cat3Panel) && !cat3TxtName.getText().isEmpty()) {
            editCatInfo.setEnabled(true);

            System.out.println("Check MouseCLicked in cat3Panel");
        }
        if (e.getSource().equals(cat4Panel) && !cat4TxtName.getText().isEmpty()) {
            editCatInfo.setEnabled(true);

            System.out.println("Check MouseCLicked in cat4Panel");
        }
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
        if(e.getSource().equals(petInformationFrame)){
            editCatInfo.setEnabled(false);

            petInformationFrame.dispose();
        }
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
            boughtCatShow.setBounds(800, 330, 400, 400);


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
        petInformationFrame.setVisible(false);




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
        editCatInfo.setEnabled(false);
        editCatInfo.setBounds(190, 590, 100, 50);
        closeCatInfo = new JButton("CLOSE");
        closeCatInfo.setFont(petDetailsFont);
        closeCatInfo.setBounds(300, 590, 100, 50);


        /*
            PET TEXTS
         */
        cat1TxtName = new JTextField("");
        cat1TxtName.setBounds(0, 0, 200, 30);
        cat1TxtName.setFont(userInputDetailsFont);

        cat2TxtName = new JTextField("");
        cat2TxtName.setBounds(0, 0, 200, 30);
        cat2TxtName.setFont(userInputDetailsFont);

        cat3TxtName = new JTextField("");
        cat3TxtName.setBounds(0, 0, 200, 30);
        cat3TxtName.setFont(userInputDetailsFont);

        cat4TxtName = new JTextField("");
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



        cat1TxtName.setEditable(false);
        cat2TxtName.setEditable(false);
        cat3TxtName.setEditable(false);
        cat4TxtName.setEditable(false);

        cat1Panel.add(cat1TxtName);
        cat2Panel.add(cat2TxtName);
        cat3Panel.add(cat3TxtName);
        cat4Panel.add(cat4TxtName);


        petInformationFrame.add(editCatInfo);
        petInformationFrame.add(closeCatInfo);

        editCatInfo.addActionListener(this);

        closeCatInfo.addActionListener(this);


        cat1Panel.addMouseListener(this);
        cat2Panel.addMouseListener(this);
        cat3Panel.addMouseListener(this);
        cat4Panel.addMouseListener(this);

        petInformationFrame.addWindowListener(this);



        //DEAD END
    }


    // THIS FUNCTION WILL ASSESS IF THE USER CAN BUY SOMETHING
    public void CatTransaction(int purchaseGoods){

        if(userCatCoinz >= purchaseGoods && counter < 4){
            userCatCoinz = userCatCoinz - purchaseGoods;
            catCoinzAmount.setText(String.valueOf(userCatCoinz));
            catInfo.dispose();
            PetShop.dispose();

            catStorage[counter] = userCatName.getText();

            System.out.println(catStorage[counter]);
            SpawnCat();
            SetUserPutName(catStorage[counter]);

            counter++;

        }
        else if (counter == 4){

            JOptionPane.showMessageDialog(null, "You have bought the maximum amount of Cats");
            catInfo.dispose();
        }
        else{

            JOptionPane.showMessageDialog(null, "You have bought the maximum amount of Cats");
            catInfo.dispose();
        }

    }


    /*
        THIS AREA IS ONLY FOR CRUDE OPERATIONS
     */

    public void SetUserPutName(String nameParameter){


        System.out.println("Check SetUserPutName");

        if(isCatBought1){
            //GENERATING THE IMAGE
            ImageIcon boughtCat = new ImageIcon("PET CATS/Cat1.png");
            Image scaledImager = boughtCat.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

            JLabel boughtCatShow = new JLabel(scaledImageofCat);
            boughtCatShow.setBounds(-35, -20, 200, 200);

            catPicPanel.add(boughtCatShow);

            petInformationFrame.repaint();
            petInformationFrame.revalidate();

            cat1TxtName.setText(nameParameter);
            isCatBought1 = false;

            System.out.println("Check isCatBought1 true and written :" + cat1TxtName.getText());



        }
        if(isCatBought2){
            //GENERATING THE IMAGE
            ImageIcon boughtCat = new ImageIcon("PET CATS/Cat2.png");
            Image scaledImager = boughtCat.getImage().getScaledInstance(250, 250, Image.SCALE_SMOOTH);
            ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

            JLabel boughtCatShow = new JLabel(scaledImageofCat);
            boughtCatShow.setBounds(-35, 140, 200, 200);

            catPicPanel.add(boughtCatShow);

            petInformationFrame.repaint();
            petInformationFrame.revalidate();


            cat2TxtName.setText(nameParameter);
            isCatBought2 = false;


        }
        if(isCatBought3){
            //GENERATING THE IMAGE
            ImageIcon boughtCat = new ImageIcon("PET CATS/Cat3.png");
            Image scaledImager = boughtCat.getImage().getScaledInstance(250, 230, Image.SCALE_SMOOTH);
            ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

            JLabel boughtCatShow = new JLabel(scaledImageofCat);
            boughtCatShow.setBounds(-35, 260, 200, 200);

            catPicPanel.add(boughtCatShow);

            cat3TxtName.setText(nameParameter);
            isCatBought3 = false;
        }
        if(isCatBought4){
            //GENERATING THE IMAGE
            ImageIcon boughtCat = new ImageIcon("PET CATS/Cat4.png");
            Image scaledImager = boughtCat.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

            JLabel boughtCatShow = new JLabel(scaledImageofCat);
            boughtCatShow.setBounds(-35, 400, 200, 200);

            catPicPanel.add(boughtCatShow);

            cat4TxtName.setText(nameParameter);
            isCatBought4 = false;


        }

    }

    public void EditTheCatInfo() {
        editTool = new JFrame();
        editInfoPanel = new JPanel();
        editTool.setTitle("Edit Tool");
        editTool.setSize(500, 300);
        editTool.setLayout(new BorderLayout(2, 2));
        editTool.add(editInfoPanel, BorderLayout.CENTER);  // Changed to BorderLayout.CENTER
        EditTheCatInfoInstantiation();
        editTool.setVisible(true);
        editTool.setLocationRelativeTo(null);
        editTool.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void EditTheCatInfoInstantiation() {
        editInfoFont = new Font("Comic Sans MS", Font.PLAIN, 20);

        // DECLARING the BUTTONS
        saveCat = new JButton("SAVE");
        cancelSave = new JButton("CANCEL");

        // DECLARING THE LABELS
        catEditedName = new JLabel("New Cat's Name: ");

        // DECLARING THE TEXTFIELD
        catEditedTextField = new JTextField();
        catEditedTextField.setColumns(20);

        // SETTING THE FONT
        catEditedName.setFont(catInfoFont);
        catEditedTextField.setFont(editInfoFont);

        // SETTING THE LOCATION OF THE JCOMPONENTS
        editInfoPanel.setLayout(new BorderLayout(10, 10));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(catEditedName);
        centerPanel.add(catEditedTextField);

        editInfoPanel.add(centerPanel, BorderLayout.CENTER);

        // ADDING THE BUTTONS, LABELS, TEXTFIELD ON THE CAT INFO GUI
        editInfoButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        editInfoButtonPanel.add(saveCat);
        editInfoButtonPanel.add(cancelSave);

        editInfoPanel.add(editInfoButtonPanel, BorderLayout.SOUTH);


        //ADDING ACTION LISTENER
        saveCat.addActionListener(this);
        cancelSave.addActionListener(this);
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