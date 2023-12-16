package org.dreamchurch.mediateam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StartGameplayForm extends JFrame implements ActionListener, WindowListener, MouseListener {
    /*
         DECLARATION OF JCOMPONENTS
         */
    // This buttons only exist in START GAMEPLAY FFORM
    private JButton mainMenuButton, petInformationButton, petShopButton;
    private Font topFontButtons, catCurrency, petShopFont, catInfoFont;
    private JLabel catCoinz, catCoinzAmount;
    private static int userCatCoinz = 400;



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
    private JPanel catInfoPanel, catInfoButtonPanel, catInfoImagePanel;

    private static boolean isCatBought1, isCatBought2, isCatBought3, isCatBought4;

    ImageIcon scaledImageofCatFinal, boughtCatImage;
    Image scaledImagerCat;

    private JLabel boughtCatShowIcon;



    /*
        Pet Information Instantiation JComponents

     */


    private JLabel catInfoNameLabel1, catInfoNameLabel2, catInfoNameLabel3, catInfoNameLabel4, catInfoTypeLabel;
    private Font petDetailsFont, userInputDetailsFont;
    private JFrame petInformationFrame;
    private JPanel cat1Panel, cat2Panel, cat3Panel, cat4Panel, catPicPanel;
    private JButton editCatInfo, closeCatInfo;
    private JTextField[] catTextFields = new JTextField[4];
    private JTextField cat1TxtName, cat2TxtName, cat3TxtName, cat4TxtName;

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
    private static String[] catStorage;
    private static int counter = 0;

    /*
        CAT EDIT VARIABLES
     */
    private boolean[] isCatEdited = new boolean[4];

    /*
        CAT STORING AND LOADING DATA PROGRESS VARIABLES
     */
    private static final String PROGRESS_FILE_PATH = "UserProgessFile/user.progress.txt";
    private static ProgressData progressData;
    private static boolean isInitialized = false;
    private List<CatData> catList = new ArrayList<>();
    private static List<Integer> arrangementofCats; // THIS LIST WILL DETERMINE WHICH IS GOING TO BE SHOWN IN THE PETINFORMATION FRAM

    /*







     */



    private static void ensureInitialized() {
        if (!isInitialized) {
            progressData = loadProgress();
            // Other initialization logic if needed
            isInitialized = true;
        }
    }




    public StartGameplayForm() {
        setSize(1300, 700);
        setTitle("Cat Ville");


        StartGameplayFormComponentsDeclaration();
        PetInformationFrame();
        CatInfo();
        PetShop();
        ensureInitialized();
        saveAndLoad();
        restoreName();

        try {
            BufferedImage backgroundImage = ImageIO.read(new File("GameplayFiles/GameplayBackground.png"));
            setContentPane(new BackgroundPanel(backgroundImage));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Use null layout for precise positioning
        setLayout(null);

        // Adding components to Frame
        add(mainMenuButton);
        add(petInformationButton);
        add(petShopButton);
        add(catCoinz);
        add(catCoinzAmount);

        addWindowListener(this); // ADDING THE START1 FRAME A WINDOWS LISTENER

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        System.out.println("Frame visibility: " + isVisible());

        setVisible(true);

        SpawnCat(); // IF THE CAT X WAS ALREADY BOUGHT, IT WILL BE IN THE FRAME
    }

    public void saveAndLoad() {
        catStorage = new String[4];
        catTextFields[0] = cat1TxtName;
        catTextFields[1] = cat2TxtName;
        catTextFields[2] = cat3TxtName;
        catTextFields[3] = cat4TxtName;

        userCatCoinz = progressData.getUserCatCoinz();
        catCoinzAmount.setText(String.valueOf(userCatCoinz));
        progressData.setUserCatCoinz(userCatCoinz);
        isCatBought1 = progressData.isCatBought1();
        isCatBought2 = progressData.isCatBought2();
        isCatBought3 = progressData.isCatBought3();
        isCatBought4 = progressData.isCatBought4();
        counter = progressData.getCounter();

        System.out.println("START RUN---------------");
        System.out.println("Before setting catStorage: " + Arrays.toString(catStorage));
        // Update catStorage without reassigning
        String[] loadedCatStorage = progressData.getCatStorage();
        System.arraycopy(loadedCatStorage, 0, catStorage, 0, loadedCatStorage.length);
        System.out.println("After setting catStorage: " + Arrays.toString(catStorage));

        // Update arrangementofCats

        List<Integer> loadedArrangement = progressData.getArrangementofCats();
        System.out.println("Loaded Arrangement: " + loadedArrangement);

        if (arrangementofCats == null) {
            System.out.println("arrangementofCats is null. Initializing...");
            arrangementofCats = new ArrayList<>(loadedArrangement);
        } else {
            arrangementofCats.clear();
            arrangementofCats.addAll(loadedArrangement);
        }


        // REFRESHING A SPECIFIC PROGRESS IF THERE IS A PROGRESS
        ButtonDisabler(); // IF THE CAT X WAS ALREADY BOUGHT IN THE PREVIOUS PROGRESS, IT WILL LOCK IT

        System.out.println("Check Progress Data Value end: " + progressData);

        // DEBUG CHECK STATEMENT
        System.out.println("Check Amount: " + userCatCoinz);
        System.out.println("Check Counter Value: " + counter);
        System.out.println("Check Boolean Variables of CAT INFO: : " + isCatBought1 + isCatBought2 + isCatBought3 + isCatBought4);
        System.out.println("Check Amount: " + userCatCoinz);
        System.out.println("Check arrangementofcats: " + arrangementofCats.isEmpty());

        System.out.println("Before loadBoughtCats");
        loadBoughtCats();
        System.out.println("After loadBoughtCats");
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
                System.out.println("Progress Data value in mainMenu: " + progressData);

                System.out.println("Check Progress Data Value after saveProgressData: " + loadProgress());
                setVisible(false);
            } catch (IllegalComponentStateException ex) {
                // Handle the exception, or ignore it if not critical
            }
            mainMenuFrame.setVisible(true);
        }
        if(e.getSource().equals(petInformationButton)){
            petInformationFrame.setVisible(true);

        }
        if(e.getSource().equals(petShopButton)){

            PetShop.setVisible(true);
        }

        /*
            THIS ACTION LISTENER STATEMENTS ARE INSIDE THE PETSHOP FRAME
         */

        if (e.getSource().equals(cat1Buy)){
            isCatBought1 = true;

            catInfo.setVisible(true);
            LoadImageCat();
            catInfo.revalidate();
            catInfo.repaint();


            System.out.println("Check logic");



        }
        if (e.getSource().equals(cat2Buy)){
            isCatBought2 = true;

            catInfo.setVisible(true);
            LoadImageCat();
            catInfo.revalidate();
            catInfo.repaint();
            System.out.println("Check logic");


        }
        if (e.getSource().equals(cat3Buy)){
            isCatBought3 = true;

            catInfo.setVisible(true);


            LoadImageCat();
            catInfo.revalidate();
            catInfo.repaint();
            System.out.println("Check logic");

        }
        if (e.getSource().equals(cat4Buy)){
            isCatBought4 = true;

            catInfo.setVisible(true);

            LoadImageCat();
            catInfo.revalidate();
            catInfo.repaint();
            System.out.println("Check logic");

        }
        if (e.getSource().equals(buyCat)){
            CatTransaction(100);
        }
        if(e.getSource().equals(cancelBuy)){

            BooleanDisabler();
            boughtCatShowIcon.setVisible(false);

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

            isCatEdited[0] = false;
            isCatEdited[1] = false;
            isCatEdited[2] = false;
            isCatEdited[3] = false;
            petInformationFrame.dispose();
        }

        /*
            THIS ACTION LISTENER IS FOR THE EDIT THE CAT INFO
         */
        if(e.getSource().equals(saveCat)){
            CatEditFeature();
            System.out.println("Check saveCat clicked");
        }
        if(e.getSource().equals(cancelSave)){
            System.out.println("Check cancelSave clicked");
            editTool.dispose();
            System.out.println("Check cancelSave clicked");
        }


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Check Mouse Event ");



        // Assuming you have individual cat panels and text fields
        if (e.getSource().equals(cat1Panel) && !cat1TxtName.getText().isEmpty()) {
            editCatInfo.setEnabled(true);
            Arrays.fill(isCatEdited, false); // Reset all flags to false
            isCatEdited[0] = true; // Set the flag for the clicked cat
            System.out.println("Check MouseClicked in cat1Panel");
        } else if (e.getSource().equals(cat2Panel) && !cat2TxtName.getText().isEmpty()) {
            editCatInfo.setEnabled(true);
            Arrays.fill(isCatEdited, false);
            isCatEdited[1] = true;
            System.out.println("Check MouseClicked in cat2Panel");
        } else if (e.getSource().equals(cat3Panel) && !cat3TxtName.getText().isEmpty()) {
            editCatInfo.setEnabled(true);
            Arrays.fill(isCatEdited, false);
            isCatEdited[2] = true;
            System.out.println("Check MouseClicked in cat3Panel");
        } else if (e.getSource().equals(cat4Panel) && !cat4TxtName.getText().isEmpty()) {
            editCatInfo.setEnabled(true);
            Arrays.fill(isCatEdited, false);
            isCatEdited[3] = true;
            System.out.println("Check MouseClicked in cat4Panel");
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
            isCatEdited[0] = false;
            isCatEdited[1] = false;
            isCatEdited[2] = false;
            isCatEdited[3] = false;
            petInformationFrame.dispose();
        }
        if(e.getSource().equals(catInfo)){
            boughtCatShowIcon.setVisible(false);
            isCatBought1 = false;
            isCatBought2 = false;
            isCatBought3 = false;
            isCatBought4 = false;
            catInfo.dispose();


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

        PetShop.setVisible(false);
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
        cat1Buy.setBounds(20, 200, 150, 30);System.out.println("Check Progress Data Value of saveProgress: " + progressData);
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
        catInfoImagePanel = new JPanel();
        catInfo.setTitle("Cat Info");
        catInfo.setSize(500, 300);
        catInfo.setLayout(new BorderLayout(2, 2));
        catInfo.add(catInfoPanel, BorderLayout.CENTER);  // Changed to BorderLayout.CENTER
        catInfo.add(catInfoImagePanel, BorderLayout.WEST);


        catInfoInstantiation();

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

        //ADDING WINDOWS LISTENER
        catInfo.addWindowListener(this);
    }



    //SPAWNING THE CAT
        public void SpawnCat(){
            System.out.println("Check the Variable: " + isCatBought1);
            if(isCatBought1){
                System.out.println("Inside isCatBought1 block");
                 ImageIcon boughtCat = new ImageIcon("PET CATS/Cat1.png");




                Image scaledImager = boughtCat.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);

                ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

                JLabel boughtCatShow = new JLabel(scaledImageofCat);
                boughtCatShow.setBounds(70, 430, 200, 200);

                add(boughtCatShow);
                System.out.println("Check spawnCat()");

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
            progressData.setUserCatCoinz(userCatCoinz);
            System.out.println("Check Progress Data Value of progressData in CatTransaction: " + progressData.getUserCatCoinz());
            catCoinzAmount.setText(String.valueOf(userCatCoinz));
            catInfo.dispose();
            PetShop.dispose();

            catStorage[counter] = userCatName.getText();
            System.out.println("Before modification: " + Arrays.toString(catStorage));
            progressData.setCatStorage(catStorage);
            System.out.println("After modification: " + Arrays.toString(catStorage));

            System.out.println(catStorage[counter]);

            SpawnCat();



            // TWO IDENTICAL CATS THAT ARE BOUGHT SHOULD NOT HAPPEN
            System.out.println("isCatBought1 : " + isCatBought1);
            if(isCatBought1){
                arrangementofCats.add(counter, 1);
                ButtonDisabler();
                System.out.println("Lock Button" + cat1Buy.isEnabled());
            }
            if(isCatBought2){
                arrangementofCats.add(counter, 2);
                ButtonDisabler();
            }
            if(isCatBought3){
                arrangementofCats.add(counter, 3);
                ButtonDisabler();
            }
            if(isCatBought4){
                arrangementofCats.add(counter, 4);
                ButtonDisabler();
            }

            SetUserPutName(catStorage[counter], arrangementofCats);
            userCatName.setText("");

            boughtCatShowIcon.setVisible(false);
            System.out.println("Check Progress Data Value of progressData in CatTransaction: " + progressData);
            counter++;

            // Move the saveProgress call here
            saveProgress(progressData);
        } else if (counter == 4){
            JOptionPane.showMessageDialog(null, "You have bought the maximum amount of Cats");
            catInfo.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "You have bought the maximum amount of Cats");
            catInfo.dispose();
        }
    }


    public void ButtonDisabler(){
        if(isCatBought1){
            cat1Buy.setEnabled(false);
            System.out.println("Lock Button" + cat1Buy.isEnabled());
        }
        if(isCatBought2){
            cat2Buy.setEnabled(false);

        }
        if(isCatBought3){
            cat3Buy.setEnabled(false);
        }
        if(isCatBought4){
            cat4Buy.setEnabled(false);
        }
    }

    // THIS FUNCTION IS USED TO DISABLE THE SPECIFIC CATS ONLY WHEN THE BUYING IS CANCELED
    public void BooleanDisabler(){
        if(isCatBought1){
            isCatBought1 = false;
        }
        if(isCatBought2){
            isCatBought2 = false;

        }
        if(isCatBought3){
            isCatBought3 = false;
        }
        if(isCatBought4){
            isCatBought4 = false;
        }
    }


    /*
        THIS AREA IS ONLY FOR CRUDE OPERATIONS
     */


    public void SetUserPutName(String nameParameter, List<Integer> arrangement) {

        JTextField[] catTextFields = {cat1TxtName, cat2TxtName, cat3TxtName, cat4TxtName};

        // Iterate through all cat indices in the arrangement
        for (int catIndex : arrangement) {
            // Generate the image path based on the catIndex
            String imagePath = "PET CATS/Cat" + catIndex + ".png";

            ImageIcon boughtCat = new ImageIcon(imagePath);
            Image scaledImager = boughtCat.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
            ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

            int x = -35;
            int y = (arrangement.indexOf(catIndex) * 140);

            System.out.println("CHECK Y SETUSER: "+  y);
            JLabel boughtCatShow = new JLabel(scaledImageofCat);
            boughtCatShow.setBounds(x, y, 200, 200);

            catPicPanel.add(boughtCatShow);
            System.out.println("SHOW LOCATION OF THE CAT: " + imagePath);

            // Optionally, you may want to repaint and revalidate for each cat added
            petInformationFrame.repaint();
            petInformationFrame.revalidate();

            CatData catData = new CatData(scaledImageofCat, nameParameter, x, y, catIndex);




            catList.add(catData);
        }

        int i = 0; // Initialize index variable
        for (JTextField textField : catTextFields) {
            if (textField.getText().isEmpty()) {
                textField.setText(nameParameter);
                System.out.println("CHECK TXT" + cat1TxtName.getText());
                System.out.println("CHECK TXT" + cat2TxtName.getText());
                System.out.println("CHECK TXT" + cat3TxtName.getText());
                System.out.println("CHECK TXT" + cat4TxtName.getText());
                break; // Exit the loop once a text field is set
            }
            i++; // Increment index for the next iteration
        }

    }
    private void loadBoughtCats() {

        System.out.println("CHECK LOADBOUGHTCATS: " + arrangementofCats);
        System.out.println("CHECK LOADBOUGHTCATS size: " + arrangementofCats.size());
        System.out.println("CHECK LOADBOUGHTCATS isEmpty: " + arrangementofCats.isEmpty());
        if (arrangementofCats != null) {
            // Clear existing components in catPicPanel
            catPicPanel.removeAll();

            // Iterate through all cat indices in the arrangement
            for (int catIndex : arrangementofCats) {
                // Generate the image path based on the catIndex
                String imagePath = "PET CATS/Cat" + catIndex + ".png";

                ImageIcon boughtCat = new ImageIcon(imagePath);
                Image scaledImage = boughtCat.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                ImageIcon scaledImageofCat = new ImageIcon(scaledImage);

                int x = -35;
                int y = (arrangementofCats.indexOf(catIndex) * 140);

                System.out.println("CHECK Y LOADBOUGHTCATS: "+  y);

                JLabel boughtCatShow = new JLabel(scaledImageofCat);
                boughtCatShow.setBounds(x, y, 200, 200);

                catPicPanel.add(boughtCatShow);
                System.out.println("SHOW LOCATION OF THE CAT: " + imagePath);

                // Optionally, you may want to repaint and revalidate for each cat added
                petInformationFrame.repaint();
                petInformationFrame.revalidate();

                // You can add more logic here to update catList or perform other actions
            }

            // Repaint and revalidate catPicPanel
            catPicPanel.repaint();
            catPicPanel.revalidate();
        }
    }



    public void restoreName() { // TO RESTORE THE CATTXTNAMES
        for (int i = 0; i < catTextFields.length; i++) {
            JTextField textField = catTextFields[i];
            String catName = catStorage[i];

            if (textField.getText().isEmpty() && catName != null && !catName.isEmpty()) {
                textField.setText(catName);
            }
        }
    }




    // Helper method to get cat name by index


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

        System.out.println("The saveCat and cancelSave have actionlistener");
    }


    /*
        Actual EDIT of the CAT'S Name
     */

    public void CatEditFeature() {
        for (int i = 0; i < catStorage.length; i++) {
            if (isCatEdited[i]) {
                catStorage[i] = catEditedTextField.getText();
                JOptionPane.showMessageDialog(null, "The Cat's name has been edited successfully!");
                editFeature(catEditedTextField.getText(), catTextFields[i]);
                editTool.dispose();
                break; // Exit the loop after editing the first cat
            }
        }
    }

    public void editFeature(String nameParameter, JTextField txtParameter){
        editCatInfo.setEnabled(false);
        txtParameter.setText(nameParameter);
    }

    /*
        THIS SECTION IS FOR MISCELLANOUS
     */
    public void LoadImageCat(){ // Loading the iamge of cats inside the CatInfo where you enter its details
        System.out.println("Check loadImageCat");

        if(isCatBought1){

            System.out.println("Check image load");
            boughtCatImage = new ImageIcon("PET CATS/Cat1.png");
            scaledImagerCat = boughtCatImage.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            scaledImageofCatFinal = new ImageIcon(scaledImagerCat);

            boughtCatShowIcon = new JLabel(scaledImageofCatFinal);
            boughtCatShowIcon.setBounds(70, 430, 200, 200);
            catInfo.add(boughtCatShowIcon, BorderLayout.WEST);

            boughtCatShowIcon.setVisible(true);
        }
        else if(isCatBought2){
            System.out.println("Check image load");
            boughtCatImage = new ImageIcon("PET CATS/Cat2.png");
            scaledImagerCat = boughtCatImage.getImage().getScaledInstance(230, 250, Image.SCALE_SMOOTH);
            scaledImageofCatFinal = new ImageIcon(scaledImagerCat);

            boughtCatShowIcon = new JLabel(scaledImageofCatFinal);
            boughtCatShowIcon.setBounds(40, 430, 200, 200);
            catInfo.add(boughtCatShowIcon, BorderLayout.WEST);

            boughtCatShowIcon.setVisible(true);
        }
        else if(isCatBought3){
            System.out.println("Check image load");
            boughtCatImage = new ImageIcon("PET CATS/Cat3.png");
            scaledImagerCat = boughtCatImage.getImage().getScaledInstance(230, 250, Image.SCALE_SMOOTH);
            scaledImageofCatFinal = new ImageIcon(scaledImagerCat);

            boughtCatShowIcon = new JLabel(scaledImageofCatFinal);
            boughtCatShowIcon.setBounds(40, 430, 200, 200);
            catInfo.add(boughtCatShowIcon, BorderLayout.WEST);

            boughtCatShowIcon.setVisible(true);
        }
        else if(isCatBought4){
            System.out.println("Check image load");
            boughtCatImage = new ImageIcon("PET CATS/Cat4.png");
            scaledImagerCat = boughtCatImage.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            scaledImageofCatFinal = new ImageIcon(scaledImagerCat);

            boughtCatShowIcon = new JLabel(scaledImageofCatFinal);
            boughtCatShowIcon.setBounds(70, 430, 200, 200);
            catInfo.add(boughtCatShowIcon, BorderLayout.WEST);

            boughtCatShowIcon.setVisible(true);
        }

    }

    //BETA FEATURES OF STORING THE PROGRESS OF THE USER, THIS ELIMINATES THE RESTART BUG

    /*
        THIS CODE IS STILL IN BETA, PROCEED WITH CAUTION!
     */


    private static ProgressData loadProgress() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(PROGRESS_FILE_PATH));
            String data = reader.readLine();
            reader.close();

            // Check if data is null before creating an instance of ProgressData
            if (data != null) {
                ProgressData progressData = ProgressData.fromString(data);

                if (progressData != null) {  // Add a null check here
                    // Update the userCatCoinz value, boolean values, and counter
                    userCatCoinz = progressData.getUserCatCoinz();
                    isCatBought1 = progressData.isCatBought1();
                    isCatBought2 = progressData.isCatBought2();
                    isCatBought3 = progressData.isCatBought3();
                    isCatBought4 = progressData.isCatBought4();
                    counter = progressData.getCounter();

                    // Update the array
                    catStorage = progressData.getCatStorage();

                    // Update arrangementofCats
                    if (progressData.getArrangementofCats() != null) {
                        // Add a null check here
                        System.out.println("Check it is not null(arrangementofCats");
                        arrangementofCats = new ArrayList<>(progressData.getArrangementofCats());
                    } else {
                        arrangementofCats = new ArrayList<>();
                    }

                    return progressData;
                } else {
                    // Handle the case where progressData is null
                    System.out.println("Progress data is null");
                    return new ProgressData();
                }
            } else {
                // Handle the case where data is null (e.g., file is empty)
                System.out.println("File is empty");
                return new ProgressData();
            }
        } catch (IOException e) {
            // Handle exceptions (e.g., file not found, invalid content)
            e.printStackTrace();
            return new ProgressData(); // Return a default instance if an error occurs
        }
    }


    private static void saveProgress(ProgressData progressData) {
        // Update the userCatCoinz value, boolean values, and counter
        progressData.setUserCatCoinz(userCatCoinz);
        progressData.setCatBought1(isCatBought1);
        progressData.setCatBought2(isCatBought2);
        progressData.setCatBought3(isCatBought3);
        progressData.setCatBought4(isCatBought4);
        progressData.setCounter(counter);

        // Update the array
        progressData.setCatStorage(catStorage);

        // Append to the existing arrangementofCats
        if (arrangementofCats == null) {
            arrangementofCats = new ArrayList<>();
        }
        progressData.setArrangementofCats(arrangementofCats);

        System.out.println("Check Progress Data Value of saveProgress: " + progressData);
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(PROGRESS_FILE_PATH));
            writer.write(progressData.toString());

            writer.close();
        } catch (IOException e) {
            // Handle exceptions (e.g., unable to write to file)
            e.printStackTrace();
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