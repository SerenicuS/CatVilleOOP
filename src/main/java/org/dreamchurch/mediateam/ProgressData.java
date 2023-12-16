package org.dreamchurch.mediateam;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
    THIS CLASS IS USED FOR STORING AND LOADING THE DATA PROGRESS OF THE USER, IT IS STILL IN BETA
    SO IT MIGHT CREATE SOME WEIRD RESULTS!
 */
public class ProgressData {
    private int userCatCoinz = 400;
    private String[] catStorage = {"", "", "", ""};
    private boolean isCatBought1, isCatBought2, isCatBought3, isCatBought4;
    private int counter;
    private List<Integer> arrangementofCats;




    public int getUserCatCoinz() {
        return userCatCoinz;
    }

    public void setUserCatCoinz(int userCatCoinz) {
        this.userCatCoinz = userCatCoinz;
    }




    public boolean isCatBought1() {
        return isCatBought1;
    }

    public void setCatBought1(boolean catBought1) {
        isCatBought1 = catBought1;
    }

    public boolean isCatBought2() {
        return isCatBought2;
    }

    public void setCatBought2(boolean catBought2) {
        isCatBought2 = catBought2;
    }

    public boolean isCatBought3() {
        return isCatBought3;
    }

    public void setCatBought3(boolean catBought3) {
        isCatBought3 = catBought3;
    }

    public boolean isCatBought4() {
        return isCatBought4;
    }

    public void setCatBought4(boolean catBought4) {
        isCatBought4 = catBought4;
    }

    public String[] getCatStorage() {
        return catStorage;
    }

    public void setCatStorage(String[] catStorage) {
        this.catStorage = catStorage;
    }

    public int getCounter(){
        return counter;
    }

    public void setCounter(int counter){
        this.counter = counter;
    }

    public List<Integer> getArrangementofCats() {
        if (arrangementofCats == null) {
            System.out.println("ArrangementofCats function getarrangement of cats" + arrangementofCats);
            arrangementofCats = new ArrayList<>();
        }
        return arrangementofCats;
    }

    public void setArrangementofCats(List<Integer> arrangementofCats) {
        this.arrangementofCats = arrangementofCats;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(userCatCoinz)
                .append(",")
                .append(isCatBought1)
                .append(",")
                .append(isCatBought2)
                .append(",")
                .append(isCatBought3)
                .append(",")
                .append(isCatBought4)
                .append(",")
                .append(counter)
                .append(",")
                .append(Arrays.toString(catStorage))
                .append(",")
                .append(arrangementofCats.toString()); // Serialize the list

        return stringBuilder.toString();
    }

    public static ProgressData fromString(String data) {
        System.out.println("Raw data before parsing: " + data);
        try {

            if (data != null) {
                String[] parts = data.split(",");

                if (parts.length >= 5) {
                    ProgressData progressData = new ProgressData();
                    progressData.setUserCatCoinz(parseInteger(parts[0]));
                    progressData.setCatBought1(parseBoolean(parts[1]));
                    progressData.setCatBought2(parseBoolean(parts[2]));
                    progressData.setCatBought3(parseBoolean(parts[3]));
                    progressData.setCatBought4(parseBoolean(parts[4]));

                    if (parts.length >= 6) {
                        progressData.setCounter(parseInteger(parts[5]));
                    }

                    if (parts.length >= 7) {
                        progressData.setCatStorage(parts[6].replaceAll("\\[|\\]", "").split("\\s*,\\s*"));
                    }


                    if (parts.length >= 8) {
                        String[] listParts = parts[7].replaceAll("\\[|\\]", "").split("\\s*,\\s*");
                        progressData.getArrangementofCats().addAll(
                                Arrays.stream(listParts)
                                        .map(String::trim)  // Trim spaces
                                        .filter(s -> !s.isEmpty())  // Filter out empty strings
                                        .map(Integer::parseInt)
                                        .toList()
                        );


                    } else {
                        progressData.setArrangementofCats(new ArrayList<>());
                    }

                    return progressData;
                }
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return new ProgressData();
    }

    private static Integer parseInteger(String s) {
        return s.trim().isEmpty() ? null : Integer.parseInt(s.trim());
    }

    private static Boolean parseBoolean(String s) {
        return s.trim().isEmpty() ? null : Boolean.parseBoolean(s.trim());
    }



}




