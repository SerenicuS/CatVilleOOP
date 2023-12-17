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

        System.out.println("---------------------------------------------------------");
        System.out.println("Function:ensureInitialized started its run");

        if (!isInitialized) {
            progressData = loadProgress();
            // Other initialization logic if needed
            isInitialized = true;
            System.out.println("!isInitialized is run");
        }

        System.out.println("Function:ensureInitialized ended its run");
        System.out.println("---------------------------------------------------------");

    }




    public StartGameplayForm() {

        System.out.println("---------------------------------------------------------");
        System.out.println("CONSTRUCTOR: StartGameplayForm started its run");

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
            System.out.println("TRY AND CATCH: background was loaded correctly in the FUNCTION STARTGAMEPLAYFORM");
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



        setVisible(true);

        System.out.println("CONSTRUCTOR: StartGameplayForm is visible: " + isVisible());

        SpawnCat(); // IF THE CAT X WAS ALREADY BOUGHT, IT WILL BE IN THE FRAME

        System.out.println("CONSTRUCTOR: StartGamePlayForm ended its run");
        System.out.println("---------------------------------------------------------");

    }

    public void saveAndLoad() {

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION: saveAndLoad started its run");

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


        System.out.println("saveAndLoad Preloaded catStorage values: " + Arrays.toString(catStorage));
        // Update catStorage without reassigning
        String[] loadedCatStorage = progressData.getCatStorage();
        System.arraycopy(loadedCatStorage, 0, catStorage, 0, loadedCatStorage.length);
        System.out.println("saveAndLoad After loading catStorage values: " + Arrays.toString(catStorage));

        // Update arrangementofCats

        List<Integer> loadedArrangement = progressData.getArrangementofCats();
        System.out.println("saveAndLoad Loaded Arrangement values: " + loadedArrangement);

        if (arrangementofCats == null) {
            System.out.println("saveAndload arrangementofCats is null. Initializing...");
            arrangementofCats = new ArrayList<>(loadedArrangement);
        } else {
            arrangementofCats.clear();
            arrangementofCats.addAll(loadedArrangement);
        }


        System.out.println("FUNCTION TO FUNCTION: saveAndLoad Calling the ButtonDisabler");
        ButtonDisabler();
        System.out.println("FUNCTION TO FUNCTION: saveAndLoad ended calling the ButtonDisabler");


        // DEBUG CHECK STATEMENT
        System.out.println("saveAndLoad Check Progress Data Value final after loadAndSave: " + progressData);
        System.out.println("saveAndLoad Check Amount: " + userCatCoinz);
        System.out.println("saveAndLoad Check Counter Value: " + counter);
        System.out.println("saveAndLoad Check Boolean Variables of CAT INFO: : " + isCatBought1 + isCatBought2 + isCatBought3 + isCatBought4);
        System.out.println("saveAndLoad Check Amount: " + userCatCoinz);
        System.out.println("saveAndLoad Check arrangementofcats: " + arrangementofCats.isEmpty());

        System.out.println("saveAndLoad Before loadBoughtCats");
        loadBoughtCats();
        System.out.println("saveAndLoad After loadBoughtCats");

        System.out.println("FUNCTION: loadAndSave ended its run");
        System.out.println("---------------------------------------------------------");

    }






     /*
        MAKING THE JCOMPONENTS ALIVE
     */

    public void StartGameplayFormComponentsDeclaration(){

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION: StartGamePlayFormComponentsDeclaration started its run");

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

        System.out.println("FUNCTION: StartGamePlayFormComponentsDeclaration ended its run");
        System.out.println("---------------------------------------------------------");

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        /*
            THIS ACTION LISTENER STATEMENTS ARE INSIDE THE STARTGAMEPLAYFORM FRAME
         */
        if (e.getSource().equals(mainMenuButton)) {

            System.out.println("---------------------------------------------------------");
            System.out.println("ACTIONPERFORMED: if (e.getSource().equals(mainMenuButton)) is checked");

            try {

                System.out.println("TRY AND CATCH:  if (e.getSource().equals(mainMenuButton)) is checked and running the try method, check progressData" + progressData);

                mainMenuFrame.setLocation(getLocationOnScreen());

                System.out.println("TRY AND CATCH:  if (e.getSource().equals(mainMenuButton)) is checked and ending the try method, check progressData" + loadProgress());

                setVisible(false);
            } catch (IllegalComponentStateException ex) {
                // Handle the exception, or ignore it if not critical
            }
            mainMenuFrame.setVisible(true);


            System.out.println("ACTION PERFORMED: if (e.getSource().equals(mainMenuButton)) ended its run");
            System.out.println("---------------------------------------------------------");
        }
        if(e.getSource().equals(petInformationButton)){

            System.out.println("---------------------------------------------------------");
            System.out.println("ACTION PERFORMED: if(e.getSource().equals(petInformationButton)) started its run");

            petInformationFrame.setVisible(true);

            System.out.println("ACTION PERFORMED: if(e.getSource().equals(petInformationButton)) ended its run");
            System.out.println("---------------------------------------------------------");

        }
        if(e.getSource().equals(petShopButton)){

            System.out.println("---------------------------------------------------------");
            System.out.println("ACTION PERFORMED: if(e.getSource().equals(petShopButton))");

            PetShop.setVisible(true);

            System.out.println("ACTION PERFORMED: if(e.getSource().equals(petShopButton)) ended its run");
            System.out.println("---------------------------------------------------------");

        }

        /*
            THIS ACTION LISTENER STATEMENTS ARE INSIDE THE PETSHOP FRAME
         */

        if (e.getSource().equals(cat1Buy)){

            System.out.println("---------------------------------------------------------");
            System.out.println("ACTION PERFORMED: if (e.getSource().equals(cat1Buy)) started its run");

            isCatBought1 = true;
            catInfo.setVisible(true);

            LoadImageCat();

            catInfo.revalidate();
            catInfo.repaint();


            System.out.println("ACTION PERFORMED: if (e.getSource().equals(cat1Buy)) ended its run");
            System.out.println("---------------------------------------------------------");

        }
        if (e.getSource().equals(cat2Buy)){

            System.out.println("---------------------------------------------------------");
            System.out.println("ACTION PERFORMED: if (e.getSource().equals(cat2Buy)) started its run, calling the LoadImageCat Function");


            isCatBought2 = true;
            catInfo.setVisible(true);
            LoadImageCat();
            catInfo.revalidate();
            catInfo.repaint();

            System.out.println("ACTION PERFORMED: if (e.getSource().equals(cat2Buy)) ended its run");
            System.out.println("---------------------------------------------------------");

        }
        if (e.getSource().equals(cat3Buy)){

            System.out.println("---------------------------------------------------------");
            System.out.println("ACTION PERFORMED: if (e.getSource().equals(cat3Buy)) started its run, calling the LoadImageCat Function");

            isCatBought3 = true;
            catInfo.setVisible(true);
            LoadImageCat();
            catInfo.revalidate();
            catInfo.repaint();

            System.out.println("ACTION PERFORMED: if (e.getSource().equals(cat3Buy)) ended its run");
            System.out.println("---------------------------------------------------------");

        }
        if (e.getSource().equals(cat4Buy)){

            System.out.println("---------------------------------------------------------");
            System.out.println("ACTION PERFORMED: if (e.getSource().equals(cat4Buy)) started its run, calling the LoadImageCat Function");

            isCatBought4 = true;
            catInfo.setVisible(true);
            LoadImageCat();
            catInfo.revalidate();
            catInfo.repaint();

            System.out.println("ACTION PERFORMED: if (e.getSource().equals(cat4Buy)) ended its run");
            System.out.println("---------------------------------------------------------");

        }
        if (e.getSource().equals(buyCat)){

            System.out.println("---------------------------------------------------------");
            System.out.println("ACTION PERFORMED: if (e.getSource().equals(buyCat)) started its run, calling the CatTransaction Function");

            CatTransaction(100);

            System.out.println("ACTION PERFORMED: if (e.getSource().equals(buyCat)) ended its run");
            System.out.println("---------------------------------------------------------");

        }
        if(e.getSource().equals(cancelBuy)){

            System.out.println("---------------------------------------------------------");
            System.out.println("ACTION PERFORMED: if(e.getSource().equals(cancelBuy)) started its run, calling the BooleanDisabler Function");

            BooleanDisabler();
            boughtCatShowIcon.setVisible(false);
            catInfo.dispose();

            System.out.println("ACTION PERFORMED: if(e.getSource().equals(cancelBuy)) ended its run");
            System.out.println("---------------------------------------------------------");

        }

        /*
            THIS ACTION LISTENER STATEMETNS ARE INSIDE THE PETINFORMATION FRAME
         */
        if (e.getSource().equals(editCatInfo)) {

            System.out.println("---------------------------------------------------------");
            System.out.println("ACTION PERFORMED: if (e.getSource().equals(editCatInfo)) started its run, calling the EditTheCatInfo Function");

            EditTheCatInfo();

            System.out.println("ACTION PERFORMED: if(e.getSource().equals(cancelBuy)) ended its run");
            System.out.println("---------------------------------------------------------");

        }

        if(e.getSource().equals(closeCatInfo)){

            System.out.println("---------------------------------------------------------");
            System.out.println("ACTION PERFORMED: if(e.getSource().equals(closeCatInfo)) started its run");

            editCatInfo.setEnabled(false);
            isCatEdited[0] = false;
            isCatEdited[1] = false;
            isCatEdited[2] = false;
            isCatEdited[3] = false;
            petInformationFrame.dispose();

            System.out.println("ACTION PERFORMED: if(e.getSource().equals(closeCatInfo)) ended its run");
            System.out.println("---------------------------------------------------------");

        }

        /*
            THIS ACTION LISTENER IS FOR THE EDIT THE CAT INFO
         */
        if(e.getSource().equals(saveCat)){

            System.out.println("---------------------------------------------------------");
            System.out.println("ACTION PERFORMED: if(e.getSource().equals(saveCat)) started its run, calling the CatEditFeature");

            CatEditFeature();

            System.out.println("ACTION PERFORMED: if(e.getSource().equals(saveCat)) ended its run");
            System.out.println("---------------------------------------------------------");

        }
        if(e.getSource().equals(cancelSave)){

            System.out.println("---------------------------------------------------------");
            System.out.println("ACTION PERFORMED: if(e.getSource().equals(cancelSave)) started its run");

            editTool.dispose();

            System.out.println("ACTION PERFORMED:  if(e.getSource().equals(cancelSave)) ended its run");
            System.out.println("---------------------------------------------------------");

        }


    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Check Mouse Event ");




        if (e.getSource().equals(cat1Panel) && !cat1TxtName.getText().isEmpty()) {

            System.out.println("---------------------------------------------------------");
            System.out.println("MOUSE CLICKED PERFORMED: if (e.getSource().equals(cat1Panel) && !cat1TxtName.getText().isEmpty()) started its run");

            editCatInfo.setEnabled(true);
            Arrays.fill(isCatEdited, false);
            isCatEdited[0] = true;

            System.out.println("MOUSE CLICKED PERFORMED:  if (e.getSource().equals(cat1Panel) && !cat1TxtName.getText().isEmpty()) ended its run");
            System.out.println("---------------------------------------------------------");

        } else if (e.getSource().equals(cat2Panel) && !cat2TxtName.getText().isEmpty()) {

            System.out.println("---------------------------------------------------------");
            System.out.println("MOUSE CLICKED PERFORMED: else if (e.getSource().equals(cat2Panel) && !cat2TxtName.getText().isEmpty()) started its run");

            editCatInfo.setEnabled(true);
            Arrays.fill(isCatEdited, false);
            isCatEdited[1] = true;

            System.out.println("MOUSE CLICKED PERFORMED:  else if (e.getSource().equals(cat2Panel) && !cat2TxtName.getText().isEmpty()) ended its run");
            System.out.println("---------------------------------------------------------");

        } else if (e.getSource().equals(cat3Panel) && !cat3TxtName.getText().isEmpty()) {

            System.out.println("---------------------------------------------------------");
            System.out.println("MOUSE CLICKED PERFORMED: else if (e.getSource().equals(cat3Panel) && !cat3TxtName.getText().isEmpty()) started its run");


            editCatInfo.setEnabled(true);
            Arrays.fill(isCatEdited, false);
            isCatEdited[2] = true;


            System.out.println("MOUSE CLICKED PERFORMED:  else if (e.getSource().equals(cat3Panel) && !cat3TxtName.getText().isEmpty()) ended its run");
            System.out.println("---------------------------------------------------------");

        } else if (e.getSource().equals(cat4Panel) && !cat4TxtName.getText().isEmpty()) {

            System.out.println("---------------------------------------------------------");
            System.out.println("MOUSE CLICKED PERFORMED: else if (e.getSource().equals(cat4Panel) && !cat4TxtName.getText().isEmpty()) started its run");

            editCatInfo.setEnabled(true);
            Arrays.fill(isCatEdited, false);
            isCatEdited[3] = true;

            System.out.println("MOUSE CLICKED PERFORMED:  else if (e.getSource().equals(cat4Panel) && !cat4TxtName.getText().isEmpty()) ended its run");
            System.out.println("---------------------------------------------------------");

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

            System.out.println("---------------------------------------------------------");
            System.out.println("WINDOW CLOSING PERFORMED: if(e.getSource().equals(petInformationFrame)) started its run, setting all boolean isCatEdited array to false");

            editCatInfo.setEnabled(false);
            isCatEdited[0] = false;
            isCatEdited[1] = false;
            isCatEdited[2] = false;
            isCatEdited[3] = false;
            petInformationFrame.dispose();


            System.out.println("WINDOW CLOSING PERFORMED: if(e.getSource().equals(petInformationFrame)) ended its run");
            System.out.println("---------------------------------------------------------");

        }
        if(e.getSource().equals(catInfo)){

            System.out.println("---------------------------------------------------------");
            System.out.println("WINDOW CLOSING PERFORMED:  if(e.getSource().equals(catInfo)) started its run, setting all boolean isCatBought1-5 to false");

            boughtCatShowIcon.setVisible(false);
            isCatBought1 = false;
            isCatBought2 = false;
            isCatBought3 = false;
            isCatBought4 = false;
            catInfo.dispose();

            System.out.println("WINDOW CLOSING PERFORMED: if(e.getSource().equals(catInfo)) ended its run");
            System.out.println("---------------------------------------------------------");

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

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION:  PetShop started its run");

        PetShop = new JFrame();
        PetShop.setSize(700, 500);
        PetShop.setTitle("Pet Shop");
        PetShop.setLayout(null);

        System.out.println("FUNCTION to FUNCTION CALLING: PetShop calling initializationofPets");

        initializationOfPets();

        System.out.println("FUNCTION INSIDE: PetShop finished calling initializationofPets");

        PetShop.setLocationRelativeTo(null);
        PetShop.setVisible(false);
        PetShop.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


        System.out.println("FUNCTION:  PetShop ended its run");
        System.out.println("---------------------------------------------------------");

    }

    public void initializationOfPets(){

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION:  initializationofPets started its run");

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

        System.out.println("FUNCTION:  initializationofPets ended its run");
        System.out.println("---------------------------------------------------------");


    }


    // RESIZING IMAGE ICONS
    private ImageIcon resizeImageIcon(ImageIcon originalIcon, int width, int height) {

        System.out.println("---------------------------------------------------------");
        System.out.println("IMAGEICON FUNCTION:  resizeImageIcon started its run");

        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        System.out.println("IMAGEICON FUNCTION:  resizeImageIcon ended its run, returning resizeImage");
        System.out.println("---------------------------------------------------------");

        return new ImageIcon(resizedImage);



    }

    //SETTING UP THE NAME OF THE CAT WHEN BOUGHT
    public void CatInfo() {

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION:  CatInfo started its run");

        catInfo = new JFrame();
        catInfoPanel = new JPanel();
        catInfoImagePanel = new JPanel();

        catInfo.setTitle("Cat Info");
        catInfo.setSize(500, 300);
        catInfo.setLayout(new BorderLayout(2, 2));

        catInfo.add(catInfoPanel, BorderLayout.CENTER);
        catInfo.add(catInfoImagePanel, BorderLayout.WEST);

        System.out.println("FUNCTION TO FUNCTION: CatInfo calling catInfoInstantiation");

        catInfoInstantiation();

        System.out.println("FUNCTION: nCatInfo finished calling catInfoInstantiation");

        catInfo.setLocationRelativeTo(null);
        catInfo.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        System.out.println("FUNCTION:  CatInfo ended its run");
        System.out.println("---------------------------------------------------------");

    }

    public void catInfoInstantiation() {

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION:  catInfoInstantiation started its run");

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

        System.out.println("FUNCTION:  catInfoInstantiation ended its run");
        System.out.println("---------------------------------------------------------");

    }



    //SPAWNING THE CAT
        public void SpawnCat(){

            System.out.println("FUNCTION:  SpawnCat started its run, checking boolean variables: ");
            System.out.println("isCatBought1: " + isCatBought1);
            System.out.println("isCatBought2: " + isCatBought2);
            System.out.println("isCatBought3: " + isCatBought3);
            System.out.println("isCatBought4: " + isCatBought4);

            System.out.println("FUNCTION: SpawnCat started to run the if statements");

            if(isCatBought1){

                System.out.println("---------------------------------------------------------");
                 System.out.println("Running the isCatBought1 block");

                 ImageIcon boughtCat = new ImageIcon("PET CATS/Cat1.png");




                Image scaledImager = boughtCat.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);

                ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

                JLabel boughtCatShow = new JLabel(scaledImageofCat);
                boughtCatShow.setBounds(70, 430, 200, 200);

                add(boughtCatShow);

                revalidate();
                repaint();

                System.out.println("Cat1 is added");
                System.out.println("Ending the isCatBought1 block, checking the values before proceeding");
                System.out.println("Block 1 visiblity of boughtCatShow" + boughtCatShow.isVisible());
                System.out.println("---------------------------------------------------------");

            }
            if(isCatBought2){

                System.out.println("---------------------------------------------------------");
                System.out.println("Running the isCatBought2 block");

                ImageIcon boughtCat = new ImageIcon("PET CATS/Cat2.png");

                Image scaledImager = boughtCat.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);

                ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

                JLabel boughtCatShow = new JLabel(scaledImageofCat);
                boughtCatShow.setBounds(600, 330, 200, 200);

                add(boughtCatShow);
                revalidate();
                repaint();

                System.out.println("Cat2 is added");
                System.out.println("Ending the isCatBought2 block, checking the values before proceeding");
                System.out.println("Block 2 visiblity of boughtCatShow" + boughtCatShow.isVisible());
                System.out.println("---------------------------------------------------------");

            }
            if(isCatBought3){

                System.out.println("---------------------------------------------------------");
                System.out.println("Running the isCatBought3 block");

                ImageIcon boughtCat = new ImageIcon("PET CATS/Cat3.png");

                Image scaledImager = boughtCat.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);

                ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

                JLabel boughtCatShow = new JLabel(scaledImageofCat);
                boughtCatShow.setBounds(500, 250, 600, 600);

                add(boughtCatShow);
                revalidate();
                repaint();

                System.out.println("Cat3 is added");
                System.out.println("Ending the isCatBought3 block, checking the values before proceeding");
                System.out.println("Block 3 visiblity of boughtCatShow" + boughtCatShow.isVisible());
                System.out.println("---------------------------------------------------------");

            }
            if(isCatBought4){

                System.out.println("---------------------------------------------------------");
                System.out.println("Running the isCatBought4 block");

                ImageIcon boughtCat = new ImageIcon("PET CATS/Cat4.png");

                Image scaledImager = boughtCat.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);

                ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

                JLabel boughtCatShow = new JLabel(scaledImageofCat);
                boughtCatShow.setBounds(800, 330, 400, 400);

                add(boughtCatShow);
                revalidate();
                repaint();


                System.out.println("Cat4 is added");
                System.out.println("Ending the isCatBought4 block, checking the values before proceeding");
                System.out.println("Block 4 visiblity of boughtCatShow" + boughtCatShow.isVisible());
                System.out.println("---------------------------------------------------------");

            }



        }

    //PET INFORMATION BUTTON FRAME
    public void PetInformationFrame(){

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION: PetInformationFrame started its run");

        petInformationFrame = new JFrame();
        petInformationFrame.setSize(500, 700);
        petInformationFrame.setTitle("Pet Information Frame");
        petInformationFrame.setLayout(null);
        PetInformationInstantiation();

        petInformationFrame.setLocationRelativeTo(null);
        petInformationFrame.setVisible(false);

        System.out.println("FUNCTION: PetInformationFrame ended its run");
        System.out.println("---------------------------------------------------------");

    }

    public void PetInformationInstantiation() {

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION: PetInformationInstantiation started its run");

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


        System.out.println("FUNCTION: PetInformationInstantiation ended its run");
        System.out.println("---------------------------------------------------------");

    }


    // THIS FUNCTION WILL ASSESS IF THE USER CAN BUY SOMETHING
    public void CatTransaction(int purchaseGoods){

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION: CatTransaction started its run, checking the if statements instantly");


        if(userCatCoinz >= purchaseGoods && counter < 4){

            System.out.println("Runnning the if(userCatCoinz >= purchaseGoods && counter < 4) block, purchase is ingoing");

            userCatCoinz = userCatCoinz - purchaseGoods;

            System.out.println("userCatCoinz is being deducted");

            System.out.println("Started the progressData.setUserCatCoinz(userCatCoinz), storing the userCatCoinz into the progress Data");

            progressData.setUserCatCoinz(userCatCoinz);

            System.out.println("Ended the progressData.setUserCatCoinz(userCatCoinz), checking the userCatCoinz in the progressData");

            System.out.println("CatTransaction's progressData :" + progressData.getUserCatCoinz());

            System.out.println("Updating the catCoinzAmount by userCatCoinz");

            catCoinzAmount.setText(String.valueOf(userCatCoinz));



            catInfo.dispose();
            PetShop.dispose();

            System.out.println("SENSITIVE AND IMPORTANT: catStorage[counter] = userCatName.getText() started storing the cat's name in the catStorage");

            catStorage[counter] = userCatName.getText();
            userCatName.setText(""); // Resetting the text to remove the name the user's input

            System.out.println("SENSITIVE AND IMPORTANT: catStorage[counter] = userCatName.getText() ended storing the cat's name in the catStorage,  progressData.setCatStorage(catStorage);");


            System.out.println("SENSITIVE AND IMPORTANT: progressData.setCatStorage(catStorage) is getting called and now storing the catStorage components of " + Arrays.toString(catStorage) + " inside the progress data");

            progressData.setCatStorage(catStorage);

            System.out.println("SENSITIVE AND IMPORTANT: progressData.setCatStorage(catStorage) ended the call and now the components of array catStorage:" + progressData.getCatStorage() + " is inside the progress data");


            System.out.println("FUNCTION TO FUNCTION: CatTransaction is calling the SpawnCat to spawn cats after the purchase");

            SpawnCat();

            System.out.println("FUNCTION TO FUNCTION: CatTransaction ended its call to the SpawnCat");
            System.out.println("CatTransaction If statements now being checked");
            // TWO IDENTICAL CATS THAT ARE BOUGHT SHOULD NOT HAPPEN
            // Add the index of the bought cat to arrangementofCats
            if (isCatBought1) {

                System.out.println("If (isCatBought1) block started its run");

                System.out.println("arrangementofCats.add(1) is starting to run");


                arrangementofCats.add(1);

                System.out.println("Checking the values stored in arrangementofCats: " + arrangementofCats);

                System.out.println("FUNCTION TO FUNCTION: CatTransaction is calling Button Disabler");

                ButtonDisabler();

                System.out.println("FUNCTION TO FUNCTION: CatTransaction is calling Button Disabler");

                System.out.println("If (isCatBought1) block ended its run");

            }

            if (isCatBought2) {

                System.out.println("If (isCatBought2) block started its run");

                System.out.println("arrangementofCats.add(2) is starting to run");


                arrangementofCats.add(2);

                System.out.println("Checking the values stored in arrangementofCats: " + arrangementofCats);

                System.out.println("FUNCTION TO FUNCTION: CatTransaction is calling Button Disabler");

                ButtonDisabler();

                System.out.println("FUNCTION TO FUNCTION: CatTransaction is calling Button Disabler");

                System.out.println("If (isCatBought2) block ended its run");

            }

            if (isCatBought3) {

                System.out.println("If (isCatBought3) block started its run");

                System.out.println("arrangementofCats.add(3) is starting to run");


                arrangementofCats.add(3);

                System.out.println("Checking the values stored in arrangementofCats: " + arrangementofCats);

                System.out.println("FUNCTION TO FUNCTION: CatTransaction is calling Button Disabler");

                ButtonDisabler();

                System.out.println("FUNCTION TO FUNCTION: CatTransaction is calling Button Disabler");

                System.out.println("If (isCatBought3) block ended its run");

            }

            if (isCatBought4) {

                System.out.println("If (isCatBought4) block started its run");

                System.out.println("arrangementofCats.add(4) is starting to run");


                arrangementofCats.add(4);

                System.out.println("Checking the values stored in arrangementofCats: " + arrangementofCats);

                System.out.println("FUNCTION TO FUNCTION: CatTransaction is calling Button Disabler");

                ButtonDisabler();

                System.out.println("FUNCTION TO FUNCTION: CatTransaction is calling Button Disabler");

                System.out.println("If (isCatBought4) block ended its run");

            }

            System.out.println("CatTransaction if statements ended its checking");


            System.out.println("SENSITIVE AND IMPORTANT: SetUserPutName(catStorage[counter], arrangementofCats) is being called");

            SetUserPutName(catStorage[counter], arrangementofCats);

            System.out.println("SENSITIVE AND IMPORTANT: SetUserPutName(catStorage[counter], arrangementofCats) ended its call");


            boughtCatShowIcon.setVisible(false);
            counter++;

            System.out.println("FUNCTION: PetInformationInstantiation ended the Purchase feature");
            System.out.println("---------------------------------------------------------");

            saveProgress(progressData);
        } else if (counter == 4){

            System.out.println("---------------------------------------------------------");
            System.out.println("else if (counter == 4) was checked, the purchase was not successful because the cats bought are already 4");

            JOptionPane.showMessageDialog(null, "You have bought the maximum amount of Cats");
            catInfo.dispose();

            System.out.println("FUNCTION: PetInformationInstantiation ended the maximum amount of cats limitation feature");
            System.out.println("---------------------------------------------------------");

        } else if(userCatCoinz == 0) {

            System.out.println("---------------------------------------------------------");
            System.out.println("else was checked, the purchase was not successful because there is no enough catCoinz to purchase a cat");

            JOptionPane.showMessageDialog(null, "You do not have enough Cat Coinz to purchase a cat");
            catInfo.dispose();

            System.out.println("FUNCTION: PetInformationInstantiation ended the Cat Coinz not enough limitation feature");
            System.out.println("---------------------------------------------------------");
        }
    }

    //TODO : ADD EVERY FUNCTIONS WITH PRINT STATEMENTS TO DEBUG AND EASILY ADD MORE FEATURES, THIS IS THE LAST LINE WHERE THE DEBUG PRINTS ARE COMPLETELY REVISED AND IS CLEAR, ADD THE REST BELOW THIS
    //FIXME : ISSUES THAT ARE CURRENTLY BEING FIXED:
    /*
        1. A code here that is still not found is adding a phantom value that is being added in arrangementOfCats for some reason and
        it makes the add of new cats weird because the 2nd catimage will go to the 3rd catimage's location.

        2. After buying a cat and bought another one, the CatInfo label showing the cat's image before being bought is not the cat's own image and it is the previous cat that is bought.
        For some reason if you close that frame and click it again it will show the correct label, what annoying bug.
    */


    public void ButtonDisabler(){
        if(isCatBought1){
            cat1Buy.setEnabled(false);
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

                    System.out.println("CatStorage updated in loadProgress" + Arrays.toString(catStorage));

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
        System.out.println("before catStorage in saveprogress" + String.valueOf(catStorage));

        progressData.setCatStorage(catStorage);
        System.out.println("after catStorage in saveprogress" + String.valueOf(catStorage));

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