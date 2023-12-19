package org.dreamchurch.mediateam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Array;
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

    private boolean tempisCatBought1, tempisCatBought2, tempisCatBought3, tempisCatBought4;
    private static boolean finalCatBought1, finalCatBought2, finalCatBought3, finalCatBought4;

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

        reSpawnCat();
        restoreName();

        System.out.println("CONSTRUCTOR: StartGameplayForm is visible: " + isVisible());



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
        finalCatBought1 = progressData.isCatBought1();
        finalCatBought2 = progressData.isCatBought2();
        finalCatBought3 = progressData.isCatBought3();
        finalCatBought4 = progressData.isCatBought4();
        counter = progressData.getCounter();

        tempisCatBought1 = finalCatBought1;
        tempisCatBought2 = finalCatBought2;
        tempisCatBought3 = finalCatBought3;
        tempisCatBought4 = finalCatBought4;

        System.out.println("saveAndLoad Preloaded catStorage values: " + Arrays.toString(catStorage));

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
        System.out.println("saveAndLoad Check Boolean Variables of tempiscatBought1: : " + tempisCatBought1 + tempisCatBought2 + tempisCatBought3 + tempisCatBought4);
        System.out.println("saveAndLoad Check Boolean Variables of finalCatBought: : " + finalCatBought1 + finalCatBought2 + finalCatBought3 + finalCatBought4);
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

    public void initializationOfPets(){

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION:  initializationofPets started its run");

        petShopFont = new Font("Comic Sans MS", Font.PLAIN, 15);

        cat1 = new JLabel(resizeImageIcon(new ImageIcon("PET CATS/Cat1.png"), 150, 150));
        cat1.setBounds(30, 20, 150, 150);
        cat2 = new JLabel(resizeImageIcon(new ImageIcon("PET CATS/Cat2.png"), 240, 240));
        cat2.setBounds(190, 20, 150, 150);
        cat3 = new JLabel(resizeImageIcon(new ImageIcon("PET CATS/Cat3.png"), 240, 240));
        cat3.setBounds(350, 20, 150, 150);
        cat4 = new JLabel(resizeImageIcon(new ImageIcon("PET CATS/Cat4.png"), 180, 170));
        cat4.setBounds(510, 20, 150, 150);

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

        PetShop.add(cat1);
        PetShop.add(cat2);
        PetShop.add(cat3);
        PetShop.add(cat4);
        PetShop.add(cat1Buy);
        PetShop.add(cat2Buy);
        PetShop.add(cat3Buy);
        PetShop.add(cat4Buy);

        cat1Buy.addActionListener(this);
        cat2Buy.addActionListener(this);
        cat3Buy.addActionListener(this);
        cat4Buy.addActionListener(this);

        System.out.println("FUNCTION:  initializationofPets ended its run");
        System.out.println("---------------------------------------------------------");


    }

    public void catInfoInstantiation() {

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION:  catInfoInstantiation started its run");

        catInfoFont = new Font("Comic Sans MS", Font.PLAIN, 20);

        buyCat = new JButton("BUY");
        cancelBuy = new JButton("CANCEL");
        catName = new JLabel("Cat Name: ");


        userCatName = new JTextField();
        userCatName.setColumns(20);

        catName.setFont(catInfoFont);

        catInfoPanel.setLayout(new BorderLayout(10, 10));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(catName);
        centerPanel.add(userCatName);

        catInfoPanel.add(centerPanel, BorderLayout.CENTER);

        catInfoButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        catInfoButtonPanel.add(buyCat);
        catInfoButtonPanel.add(cancelBuy);

        catInfoPanel.add(catInfoButtonPanel, BorderLayout.SOUTH);

        buyCat.addActionListener(this);
        cancelBuy.addActionListener(this);

        catInfo.addWindowListener(this);

        System.out.println("FUNCTION:  catInfoInstantiation ended its run");
        System.out.println("---------------------------------------------------------");

    }

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

        petDetailsFont = new Font("Comic Sans MS", Font.PLAIN, 20);
        userInputDetailsFont = new Font("Comic Sans MS", Font.PLAIN, 30);

        editCatInfo = new JButton("EDIT");
        editCatInfo.setFont(petDetailsFont);
        editCatInfo.setEnabled(false);
        editCatInfo.setBounds(190, 590, 100, 50);
        closeCatInfo = new JButton("CLOSE");
        closeCatInfo.setFont(petDetailsFont);
        closeCatInfo.setBounds(300, 590, 100, 50);

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

        catInfoNameLabel1 = new JLabel("Cat 1 Name: ");
        catInfoNameLabel1.setFont(petDetailsFont);

        catInfoNameLabel2 = new JLabel("Cat 2 Name: ");
        catInfoNameLabel2.setFont(petDetailsFont);

        catInfoNameLabel3 = new JLabel("Cat 3 Name: ");
        catInfoNameLabel3.setFont(petDetailsFont);;

        catInfoNameLabel4 = new JLabel("Cat 4 Name: ");
        catInfoNameLabel4.setFont(petDetailsFont);

        cat1Panel = new JPanel();
        cat1Panel.setLayout(new BoxLayout(cat1Panel, BoxLayout.Y_AXIS));
        //cat1Panel.setBackground(Color.RED);
        cat1Panel.setBounds(150, 10, 350, 130);



        cat2Panel = new JPanel();
        cat2Panel.setLayout(new BoxLayout(cat2Panel, BoxLayout.Y_AXIS));
        //cat2Panel.setBackground(Color.YELLOW);
        cat2Panel.setBounds(150, 150, 350, 130);



        cat3Panel = new JPanel();
        cat3Panel.setLayout(new BoxLayout(cat3Panel, BoxLayout.Y_AXIS));
        //cat3Panel.setBackground(Color.BLUE);
        cat3Panel.setBounds(150, 300, 350, 130);



        cat4Panel = new JPanel();
        cat4Panel.setLayout(new BoxLayout(cat4Panel, BoxLayout.Y_AXIS));
        //cat4Panel.setBackground(Color.GREEN);
        cat4Panel.setBounds(150, 450, 350, 130);



        catPicPanel = new JPanel();
        catPicPanel.setLayout(null);
        //catPicPanel.setBackground(Color.BLACK);
        catPicPanel.setBounds(0, 10, 140, 570);

        petInformationFrame.add(catPicPanel);

        petInformationFrame.add(cat1Panel);
        petInformationFrame.add(cat2Panel);
        petInformationFrame.add(cat3Panel);
        petInformationFrame.add(cat4Panel);

        cat1Panel.add(catInfoNameLabel1);
        cat2Panel.add(catInfoNameLabel2);
        cat3Panel.add(catInfoNameLabel3);
        cat4Panel.add(catInfoNameLabel4);

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



    /*
       ACTIVE FUNCTIONS THAT WILL PROVIDE THE USERS ITS FEATURES
     */

    public void SpawnCat(){

        System.out.println("FUNCTION:  SpawnCat started its run, checking boolean variables: ");
        System.out.println("isCatBought1: " + tempisCatBought1);
        System.out.println("isCatBought2: " + tempisCatBought2);
        System.out.println("isCatBought3: " + tempisCatBought3);
        System.out.println("isCatBought4: " + tempisCatBought4);


        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION: SpawnCat started to run the if statements");

        if(tempisCatBought1 && !finalCatBought1){


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
        if(tempisCatBought2 && !finalCatBought2){

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
        if(tempisCatBought3  && !finalCatBought3){

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
        if(tempisCatBought4  && !finalCatBought4){


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
            userCatName.setText("");

            System.out.println("SENSITIVE AND IMPORTANT: catStorage[counter] = userCatName.getText() ended storing the cat's name in the catStorage,  progressData.setCatStorage(catStorage);");


            System.out.println("SENSITIVE AND IMPORTANT: progressData.setCatStorage(catStorage) is getting called and now storing the catStorage components of " + Arrays.toString(catStorage) + " inside the progress data");

            progressData.setCatStorage(catStorage);

            System.out.println("SENSITIVE AND IMPORTANT: progressData.setCatStorage(catStorage) ended the call and now the components of array catStorage:" + Arrays.toString(progressData.getCatStorage()) + " is inside the progress data");


            System.out.println("FUNCTION TO FUNCTION: CatTransaction is calling the SpawnCat to spawn cats after the purchase");

            SpawnCat();

            System.out.println("FUNCTION TO FUNCTION: CatTransaction ended its call to the SpawnCat");
            System.out.println("CatTransaction If statements now being checked");
            System.out.println("Check Boolean Values of final and temp: " + finalCatBought1 + finalCatBought2 + finalCatBought3 + finalCatBought4 + "|" + tempisCatBought1 + tempisCatBought2 + tempisCatBought3 + tempisCatBought4);


            // TO PREVENT THE USER FROM BUYING THE SAME CAT DURING GAMEPLAY
            if (tempisCatBought1 && !finalCatBought1) {

                System.out.println("If (isCatBought1) block started its run");

                System.out.println("arrangementofCats.add(1) is starting to run");

                finalCatBought1 = tempisCatBought1;
                arrangementofCats.add(1);

                System.out.println("Checking the values stored in arrangementofCats: " + arrangementofCats);

                System.out.println("FUNCTION TO FUNCTION: CatTransaction is calling Button Disabler");

                ButtonDisabler();

                System.out.println("FUNCTION TO FUNCTION: CatTransaction is calling Button Disabler");

                System.out.println("If (isCatBought1) block ended its run");

            }

            else if (tempisCatBought2 && !finalCatBought2) {

                System.out.println("If (isCatBought2) block started its run");

                System.out.println("arrangementofCats.add(2) is starting to run");

                finalCatBought2 = tempisCatBought2;
                arrangementofCats.add(2);

                System.out.println("Checking the values stored in arrangementofCats: " + arrangementofCats);

                System.out.println("FUNCTION TO FUNCTION: CatTransaction is calling Button Disabler");

                ButtonDisabler();

                System.out.println("FUNCTION TO FUNCTION: CatTransaction is calling Button Disabler");

                System.out.println("If (isCatBought2) block ended its run");

            }

            else if (tempisCatBought3 && !finalCatBought3) {

                System.out.println("If (isCatBought3) block started its run");

                System.out.println("arrangementofCats.add(3) is starting to run");

                finalCatBought3 = tempisCatBought3;
                arrangementofCats.add(3);

                System.out.println("Checking the values stored in arrangementofCats: " + arrangementofCats);

                System.out.println("FUNCTION TO FUNCTION: CatTransaction is calling Button Disabler");

                ButtonDisabler();

                System.out.println("FUNCTION TO FUNCTION: CatTransaction is calling Button Disabler");

                System.out.println("If (isCatBought3) block ended its run");

            }

            else if (tempisCatBought4 && !finalCatBought4) {

                System.out.println("If (isCatBought4) block started its run");

                System.out.println("arrangementofCats.add(4) is starting to run");

                finalCatBought4 = tempisCatBought4;
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


    public void SetUserPutName(String nameParameter, List<Integer> arrangement) {

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION: SetUserPutName started its run, started the for loop run");

        JTextField[] catTextFields = {cat1TxtName, cat2TxtName, cat3TxtName, cat4TxtName};

        for (int catIndex : arrangement) {

            System.out.println("FOR LOOP: for (int catIndex : arrangement) was checked");

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

            System.out.println("SetUserPutName Values: ");
            System.out.println("SHOW LOCATION OF THE CAT: " + imagePath);
            System.out.println("SHOW arrangement: " + arrangement);
            System.out.println("SHOW catIndex: " + catIndex);
            System.out.println("SHOW Y calculations: " + " arrangement.indexOf(catIndex): " + arrangementofCats.indexOf(catIndex) + " * " + " 140");

            petInformationFrame.repaint();
            petInformationFrame.revalidate();

            System.out.println("FUNCTION: for (int catIndex : arrangement) ended its run");

        }

        for (JTextField textField : catTextFields) {

            System.out.println("FOR LOOP:  for (JTextField textField : catTextFields)");

            if (textField.getText().isEmpty()) {

                System.out.println(" if (textField.getText().isEmpty()) was checked");

                textField.setText(nameParameter);

                System.out.println("CHECK TXT" + cat1TxtName.getText());
                System.out.println("CHECK TXT" + cat2TxtName.getText());
                System.out.println("CHECK TXT" + cat3TxtName.getText());
                System.out.println("CHECK TXT" + cat4TxtName.getText());

                break; // Exit the loop once a text field is set
            }
        }

        System.out.println("FUNCTION: SetUserPutName ended its run");
        System.out.println("---------------------------------------------------------");

    }

    public void LoadImageCat(int n){ // Loading the iamge of cats inside the CatInfo where you enter its details

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION: LoadImageCat started its run");
        System.out.println("Check Values of boolean Variables isCatBought1-4: " + tempisCatBought1 + tempisCatBought2 + tempisCatBought3 + tempisCatBought4);

        boughtCatImage = new ImageIcon("PET CATS/Cat" + n + ".png");
        scaledImagerCat = boughtCatImage.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        scaledImageofCatFinal = new ImageIcon(scaledImagerCat);

        boughtCatShowIcon = new JLabel(scaledImageofCatFinal);
        boughtCatShowIcon.setBounds(70, 430, 200, 200);
        catInfo.add(boughtCatShowIcon, BorderLayout.WEST);

        boughtCatShowIcon.setVisible(true);



        System.out.println("FUNCTION: LoadImageCat ended its run");
        System.out.println("---------------------------------------------------------");

    }

    /*
        THIS SECTION IS FOR RESTORING THE PROGRESS
     */

    public void restoreName() {

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION: restoreName started its run, instantly started the for loop");

        for (int i = 0; i < catTextFields.length; i++) {

            System.out.println("FOR LOOP: for (int i = 0; i < catTextFields.length; i++) started its loop " + i + " times");

            JTextField textField = catTextFields[i];
            String catName = catStorage[i];

            System.out.println("Check values inside catStorage before if statement: "+ Arrays.toString(catStorage));

            if (textField.getText().isEmpty() && catName != null && !catName.isEmpty()) {

                System.out.println("if (textField.getText().isEmpty() && catName != null && !catName.isEmpty()) is checked, load the values: ");
                System.out.println("Value of textField being stored by catTextField: " + textField.getText());
                System.out.println("Value of catName being stored by catStorage" + catName);

                textField.setText(catName);

                System.out.println(" Restoring the name of cats by putting the value of: " + catName + " to: " + textField.getText());
                System.out.println(" if (textField.getText().isEmpty() && catName != null && !catName.isEmpty()) ended");

            }

            System.out.println("FOR LOOP: for (int i = 0; i < catTextFields.length; i++) ended its loop");


        }

        System.out.println("FUNCTION: restoreName ended its run");
        System.out.println("---------------------------------------------------------");

    }

    public void reSpawnCat() {

        System.out.println("FUNCTION:  respawnCat started its run, checking boolean variables: ");
        System.out.println("finalCatBought1: " + finalCatBought1);
        System.out.println("finalCatBought2: " + finalCatBought2);
        System.out.println("finalCatBought3: " + finalCatBought3);
        System.out.println("finalCatBought4: " + finalCatBought4);


        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION: respawnCat started to run the if statements");

        if (finalCatBought1) {


            System.out.println("Running the finalCatBought1 block");

            ImageIcon boughtCat = new ImageIcon("PET CATS/Cat1.png");


            Image scaledImager = boughtCat.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);

            ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

            JLabel boughtCatShow = new JLabel(scaledImageofCat);
            boughtCatShow.setBounds(70, 430, 200, 200);

            add(boughtCatShow);

            revalidate();
            repaint();

            System.out.println("Cat1 is added");
            System.out.println("Ending the finalCatBought1 block, checking the values before proceeding");
            System.out.println("Block 1 visiblity of boughtCatShow" + boughtCatShow.isVisible());
            System.out.println("---------------------------------------------------------");

        }
        if (finalCatBought2) {

            System.out.println("Running the finalCatBought2 block");

            ImageIcon boughtCat = new ImageIcon("PET CATS/Cat2.png");

            Image scaledImager = boughtCat.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);

            ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

            JLabel boughtCatShow = new JLabel(scaledImageofCat);
            boughtCatShow.setBounds(600, 330, 200, 200);

            add(boughtCatShow);
            revalidate();
            repaint();

            System.out.println("Cat2 is added");
            System.out.println("Ending the finalCatBought2 block, checking the values before proceeding");
            System.out.println("Block 2 visiblity of boughtCatShow" + boughtCatShow.isVisible());
            System.out.println("---------------------------------------------------------");

        }
        if (finalCatBought3) {

            System.out.println("Running the finalCatBought3 block");

            ImageIcon boughtCat = new ImageIcon("PET CATS/Cat3.png");

            Image scaledImager = boughtCat.getImage().getScaledInstance(350, 350, Image.SCALE_SMOOTH);

            ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

            JLabel boughtCatShow = new JLabel(scaledImageofCat);
            boughtCatShow.setBounds(500, 250, 600, 600);

            add(boughtCatShow);
            revalidate();
            repaint();

            System.out.println("Cat3 is added");
            System.out.println("Ending thefinalCatBought3 block, checking the values before proceeding");
            System.out.println("Block 3 visiblity of boughtCatShow" + boughtCatShow.isVisible());
            System.out.println("---------------------------------------------------------");

        }
        if (finalCatBought4) {


            System.out.println("Running the finalCatBought4 block");

            ImageIcon boughtCat = new ImageIcon("PET CATS/Cat4.png");

            Image scaledImager = boughtCat.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);

            ImageIcon scaledImageofCat = new ImageIcon(scaledImager);

            JLabel boughtCatShow = new JLabel(scaledImageofCat);
            boughtCatShow.setBounds(800, 330, 400, 400);

            add(boughtCatShow);
            revalidate();
            repaint();


            System.out.println("Cat4 is added");
            System.out.println("Ending the finalCatBought4 block, checking the values before proceeding");
            System.out.println("Block 4 visiblity of boughtCatShow" + boughtCatShow.isVisible());
            System.out.println("---------------------------------------------------------");

        }

    }

    public void BooleanDisabler(){

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION: BooleanDisabler started its run");
        System.out.println("Boolean Disabler started to check the if statements");

        if(tempisCatBought1 && !finalCatBought1){
            tempisCatBought1 = false;
        }
        if(tempisCatBought2 && !finalCatBought2){
            tempisCatBought2 = false;

        }
        if(tempisCatBought3 && !finalCatBought3){
            tempisCatBought3 = false;
        }
        if(tempisCatBought4 && !finalCatBought4){
            tempisCatBought4 = false;
        }

        System.out.println("FUNCTION: ButtonDisabler ended its run");
        System.out.println("---------------------------------------------------------");

    }

    public void ButtonDisabler(){

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION: ButtonDisabler started its run");
        System.out.println("Button Disabler started to check the if statements");

        if(finalCatBought1){
            cat1Buy.setEnabled(false);
        }
        if(finalCatBought2){
            cat2Buy.setEnabled(false);

        }
        if(finalCatBought3){
            cat3Buy.setEnabled(false);
        }
        if(finalCatBought4){
            cat4Buy.setEnabled(false);
        }

        System.out.println("FUNCTION: ButtonDisabler started its run");
        System.out.println("---------------------------------------------------------");

    }

    private void loadBoughtCats() {

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION: loadBoughtCats started its run");

        System.out.println("Check PreLoadBoughtCats values of arrangementofCats: " + arrangementofCats);
        System.out.println("CHECK PreLoadBoughtCats size of arrangementofCats: " + arrangementofCats.size());
        System.out.println("CHECK PreLoadBoughtCats isEmpty? : " + arrangementofCats.isEmpty());

        if (arrangementofCats != null) {

            System.out.println("if (arrangementofCats != null) block is checked");

            catPicPanel.removeAll();


            for (int catIndex : arrangementofCats) {

                String imagePath = "PET CATS/Cat" + catIndex + ".png";

                ImageIcon boughtCat = new ImageIcon(imagePath);
                Image scaledImage = boughtCat.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                ImageIcon scaledImageofCat = new ImageIcon(scaledImage);

                int x = -35;
                int y = (arrangementofCats.indexOf(catIndex) * 140);

                JLabel boughtCatShow = new JLabel(scaledImageofCat);
                boughtCatShow.setBounds(x, y, 200, 200);

                catPicPanel.add(boughtCatShow);

                petInformationFrame.repaint();
                petInformationFrame.revalidate();

                System.out.println("Check loadBoughtCats Variables before ending its run");
                System.out.println("Check Y coordinates of CatImage: "+  y);
                System.out.println("Check the Root location of the CatImage: " + imagePath);
                System.out.println("SHOW LOCATION OF THE CAT in the loadBoughtCats: " + imagePath);
                System.out.println("Check AfterLoadBoughtCats values of arrangementofCats: " + arrangementofCats);
                System.out.println("CHECK AfterLoadBoughtCats size of arrangementofCats: " + arrangementofCats.size());
                System.out.println("CHECK AfterLoadBoughtCats isEmpty? : " + arrangementofCats.isEmpty());

            }

            catPicPanel.repaint();
            catPicPanel.revalidate();

        }

        System.out.println("FUNCTION: loadBoughtCats ended its run");
        System.out.println("---------------------------------------------------------");

    }

    //BETA FEATURES OF STORING THE PROGRESS OF THE USER, THIS ELIMINATES THE RESTART BUG

    /*
        THIS CODE IS STILL IN BETA, PROCEED WITH CAUTION!
     */

    public void EditTheCatInfo() {

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION: EditTheCatInfo started its run");

        editTool = new JFrame();
        editInfoPanel = new JPanel();
        editTool.setTitle("Edit Tool");
        editTool.setSize(500, 300);
        editTool.setLayout(new BorderLayout(2, 2));
        editTool.add(editInfoPanel, BorderLayout.CENTER);

        System.out.println("FUNCTION TO FUNCTION: EditTheCatInfo is calling EditTheCatInfoInstantiation");

        EditTheCatInfoInstantiation();

        System.out.println("FUNCTION TO FUNCTION: EditTheCatInfo ended its calling of EditTheCatInfoInstantiation");

        editTool.setVisible(true);
        editTool.setLocationRelativeTo(null);
        editTool.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        System.out.println("FUNCTION: EditTheCatInfo ended its run");
        System.out.println("---------------------------------------------------------");
    }

    public void EditTheCatInfoInstantiation() {

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION: EditTheCatInfoInstantiation started its run");

        editInfoFont = new Font("Comic Sans MS", Font.PLAIN, 20);

        saveCat = new JButton("SAVE");
        cancelSave = new JButton("CANCEL");

        catEditedName = new JLabel("New Cat's Name: ");

        catEditedTextField = new JTextField();
        catEditedTextField.setColumns(20);

        catEditedName.setFont(catInfoFont);
        catEditedTextField.setFont(editInfoFont);

        editInfoPanel.setLayout(new BorderLayout(10, 10));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.add(catEditedName);
        centerPanel.add(catEditedTextField);

        editInfoPanel.add(centerPanel, BorderLayout.CENTER);

        editInfoButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        editInfoButtonPanel.add(saveCat);
        editInfoButtonPanel.add(cancelSave);

        editInfoPanel.add(editInfoButtonPanel, BorderLayout.SOUTH);

        saveCat.addActionListener(this);
        cancelSave.addActionListener(this);

        System.out.println("FUNCTION: EditTheCatInfoInstantiation ended its run");
        System.out.println("---------------------------------------------------------");

    }

    public void CatEditFeature() {

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION: CatEditFeatures started its run, instantly runs the for loop");

        for (int i = 0; i < catStorage.length; i++) {

            System.out.println("FOR LOOP: for (int i = 0; i < catStorage.length; i++) started the loop " + i + " times");

            if (isCatEdited[i]) {

                System.out.println("if (isCatEdited[i]) block is checked");
                System.out.println("catStorage values are being edited, check values before and after");
                System.out.println("pre CatEditFeatures catStorage: " + catStorage);
                System.out.println("FUNCTION TO FUNCTION: CatEditFeature is calling editFeatures, passing the catEditedTextField.getText(), catTextFields[i]");

                catStorage[i] = catEditedTextField.getText();
                editFeature(catEditedTextField.getText(), catTextFields[i]);

                System.out.println("FUNCTION TO FUNCTION: CatEditFeature ended its call to editFeatures");
                System.out.println("After CatEditFeatures catStorage: " + catStorage);

                JOptionPane.showMessageDialog(null, "The Cat's name has been edited successfully!");

                System.out.println("Editing the cats name from " + catStorage[i] + " to " + catEditedTextField.getText());

                saveProgress(progressData);

                System.out.println("Cat name was edited, new name is: " + catStorage[i]);

                editTool.dispose();

                break;

            }
        }

        System.out.println("FUNCTION: CatEditFeatures ended its run, cat edit succesfully");
        System.out.println("---------------------------------------------------------");

    }

    public void editFeature(String nameParameter, JTextField txtParameter){

        System.out.println("---------------------------------------------------------");
        System.out.println("FUNCTION: editFeature started its run");
        System.out.println("Pre editFeatures values of passed parameters nameParameter(catEditedTextField.getText()): " + nameParameter);
        System.out.println("Pre editFeatures values of passed parameters txtParameter(catTextFields[i]): " + txtParameter);

        editCatInfo.setEnabled(false);
        txtParameter.setText(nameParameter);

        System.out.println("Set the editCatInfo to false");
        System.out.println("After editFeatures values of passed parameters nameParameter(catEditedTextField.getText()): " + nameParameter);
        System.out.println("After editFeatures values of passed parameters txtParameter(catTextFields[i]): " + txtParameter);

        System.out.println("FUNCTION: editFeature ended its run");
        System.out.println("---------------------------------------------------------");

    }


    private static ProgressData loadProgress() {

        System.out.println("---------------------------------------------------------");
        System.out.println("WARNING: ProgressData started its run, loading the progress");

        try {

            System.out.println("TRY AND CATCH: ProgressData started its try method");

            BufferedReader reader = new BufferedReader(new FileReader(PROGRESS_FILE_PATH));
            String data = reader.readLine();
            System.out.println("Check contents of Data: " + data.toString());
            reader.close();


            if (data != null) {

                System.out.println("  if (data != null) is checked");

                ProgressData progressData = ProgressData.fromString(data);

                if (progressData != null) {

                    System.out.println(" if (progressData != null)");
                    System.out.println("WARNING: PreProgress Data values that is default :");
                    System.out.println("PreProgress Data values that is default userCatCoinz :");
                    System.out.println("PreProgress Data values that is default isCatBought1:" + finalCatBought1);
                    System.out.println("PreProgress Data values that is default isCatBought2:" + finalCatBought2);
                    System.out.println("PreProgress Data values that is default isCatBought3:" + finalCatBought3);
                    System.out.println("PreProgress Data values that is default isCatBought4:" + finalCatBought4);
                    System.out.println("PreProgress Data values that is default counter:" + counter);
                    System.out.println("PreProgress Data values that is default catStorage:" + catStorage);
                    System.out.println("PreProgress Data values that is default arrangementofCats:" + arrangementofCats);

                    // TO LOAD THE PROGRESS OF THE USER
                    userCatCoinz = progressData.getUserCatCoinz();
                    finalCatBought1 = progressData.isCatBought1();
                    finalCatBought2 = progressData.isCatBought2();
                    finalCatBought3 = progressData.isCatBought3();
                    finalCatBought4 = progressData.isCatBought4();
                    counter = progressData.getCounter();
                    catStorage = progressData.getCatStorage();


                    if (progressData.getArrangementofCats() != null) {

                        System.out.println(" if (progressData.getArrangementofCats() != null), check the values of arrangementofCats in progressData:");
                        System.out.println("Before of arrangementofCats local and progressData values:" + arrangementofCats + "|" + progressData.getArrangementofCats());

                        arrangementofCats = new ArrayList<>(progressData.getArrangementofCats());

                        System.out.println("After of arrangementofCats local and progressData values:" + arrangementofCats + "|" + progressData.getArrangementofCats());

                    } else {

                        arrangementofCats = new ArrayList<>();

                    }



                    System.out.println("WARNING: After LOADING THE PROGRESS of Data values that is default :");
                    System.out.println("After loadProgress Data values userCatCoinz :");
                    System.out.println("After loadProgress Data values isCatBought1:" + finalCatBought1);
                    System.out.println("After loadProgress Data values isCatBought2:" + finalCatBought2);
                    System.out.println("After loadProgress Data values isCatBought3:" + finalCatBought3);
                    System.out.println("After loadProgress Data values isCatBought4:" + finalCatBought4);
                    System.out.println("After loadProgress Data values counter:" + counter);
                    System.out.println("After loadProgress Data values catStorage:" + Arrays.toString(catStorage));
                    System.out.println("After loadProgress Data values arrangementofCats:" + arrangementofCats);


                    System.out.println("Loaded catStorage: " + Arrays.toString(progressData.getCatStorage()));
                    System.out.println("Loaded arrangementofCats: " + progressData.getArrangementofCats());

                    System.out.println("WARNING: Returning the progressData values and ended its run");
                    System.out.println("---------------------------------------------------------");

                    return progressData;

                } else {


                    System.out.println("Progress data is null");

                    return new ProgressData();

                }
            } else {


                System.out.println("File is empty");

                return new ProgressData();

            }
        } catch (IOException e) {


            e.printStackTrace();

            return new ProgressData();

        }

    }


    private static void saveProgress(ProgressData progressData) {

        System.out.println("---------------------------------------------------------");
        System.out.println("WARNING: saveProgress started its run, saving the progress");
        System.out.println("WARNING: preSaveProgress Data values that is default :");
        System.out.println("PreProgress Data values that is default userCatCoinz :" + userCatCoinz);
        System.out.println("PreProgress Data values that is default finalCatBought1:" + finalCatBought1);
        System.out.println("PreProgress Data values that is default finalCatBought2:" + finalCatBought2);
        System.out.println("PreProgress Data values that is default finalCatBought3:" + finalCatBought3);
        System.out.println("PreProgress Data values that is default finalCatBought4:" + finalCatBought4);
        System.out.println("PreProgress Data values that is default counter:" + counter);
        System.out.println("PreProgress Data values that is default catStorage:" + Arrays.toString(catStorage));
        System.out.println("PreProgress Data values that is default arrangementOfCats" + arrangementofCats);


        progressData.setUserCatCoinz(userCatCoinz);
        progressData.setCatBought1(finalCatBought1);
        progressData.setCatBought2(finalCatBought2);
        progressData.setCatBought3(finalCatBought3);
        progressData.setCatBought4(finalCatBought4);
        progressData.setCounter(counter);
        progressData.setCatStorage(catStorage);

        if (arrangementofCats == null) {
            arrangementofCats = new ArrayList<>();
        }
        progressData.setArrangementofCats(arrangementofCats);

        System.out.println("WARNING: After LOADING THE PROGRESS of Data values that is default :");
        System.out.println("AfterSaveProgress Data values userCatCoinz :" + progressData.getUserCatCoinz());
        System.out.println("AfterSaveProgress Data values isCatBought1-4:" + progressData.getCatBooleanVariables());
        System.out.println("AfterSaveProgress Data values counter:" + progressData.getCounter());
        System.out.println("AfterSaveProgress Data values catStorage:" + Arrays.toString(progressData.getCatStorage()));
        System.out.println("AfterSaveProgress Data values arrangementOfCats" + arrangementofCats);
        System.out.println("Starting to write the savedProgress in the file");



        try {

            System.out.println("TRY AND CATCH, attempting to write the values inside the txt");

            BufferedWriter writer = new BufferedWriter(new FileWriter(PROGRESS_FILE_PATH));

            writer.write(progressData.toString());
            writer.flush();
            writer.close();

            System.out.println("TRY AND CATCH, Finished writing the values inside the txt");


        } catch (IOException e) {

            e.printStackTrace();

        }

        System.out.println("Saved catStorage: " + Arrays.toString(progressData.getCatStorage()));
        System.out.println("Saved arrangementofCats: " + progressData.getArrangementofCats());


        System.out.println("WARNING: saveProgress ended its run, saving the progress completed");
        System.out.println("---------------------------------------------------------");

    }

    /*
        ACTION, WINDOWS, MOUSE EVENTS
     */

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

            tempisCatBought1 = true;
            catInfo.setVisible(true);

            LoadImageCat(1);

            catInfo.revalidate();
            catInfo.repaint();


            System.out.println("ACTION PERFORMED: if (e.getSource().equals(cat1Buy)) ended its run");
            System.out.println("---------------------------------------------------------");

        }
        if (e.getSource().equals(cat2Buy)){

            System.out.println("---------------------------------------------------------");
            System.out.println("ACTION PERFORMED: if (e.getSource().equals(cat2Buy)) started its run, calling the LoadImageCat Function");


            tempisCatBought2 = true;
            catInfo.setVisible(true);
            LoadImageCat(2);
            catInfo.revalidate();
            catInfo.repaint();

            System.out.println("ACTION PERFORMED: if (e.getSource().equals(cat2Buy)) ended its run");
            System.out.println("---------------------------------------------------------");

        }
        if (e.getSource().equals(cat3Buy)){

            System.out.println("---------------------------------------------------------");
            System.out.println("ACTION PERFORMED: if (e.getSource().equals(cat3Buy)) started its run, calling the LoadImageCat Function");

            tempisCatBought3 = true;
            catInfo.setVisible(true);
            LoadImageCat(3);
            catInfo.revalidate();
            catInfo.repaint();

            System.out.println("ACTION PERFORMED: if (e.getSource().equals(cat3Buy)) ended its run");
            System.out.println("---------------------------------------------------------");

        }
        if (e.getSource().equals(cat4Buy)){

            System.out.println("---------------------------------------------------------");
            System.out.println("ACTION PERFORMED: if (e.getSource().equals(cat4Buy)) started its run, calling the LoadImageCat Function");

            tempisCatBought4 = true;
            catInfo.setVisible(true);
            LoadImageCat(4);
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
            tempisCatBought1 = false;
            tempisCatBought2 = false;
            tempisCatBought3 = false;
            tempisCatBought4 = false;
            catInfo.dispose();

            System.out.println("WINDOW CLOSING PERFORMED: if(e.getSource().equals(catInfo)) ended its run");
            System.out.println("---------------------------------------------------------");

        }
        if(e.getSource().equals(this)){
            saveProgress(progressData);
            System.exit(0);
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
        Miscellaneous
     */

    private ImageIcon resizeImageIcon(ImageIcon originalIcon, int width, int height) {

        System.out.println("---------------------------------------------------------");
        System.out.println("IMAGEICON FUNCTION:  resizeImageIcon started its run");

        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        System.out.println("IMAGEICON FUNCTION:  resizeImageIcon ended its run, returning resizeImage");
        System.out.println("---------------------------------------------------------");

        return new ImageIcon(resizedImage);



    }

}
/*
       DEAD END
 */