package org.dreamchurch.mediateam;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


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

    public String getCatBooleanVariables(){
        return isCatBought1 + "|" + isCatBought2 + "|" + isCatBought3 + "|" + isCatBought4;
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
        System.out.println("getArrangementofCats started its run");
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

        System.out.println("---------------------------------------------------------");
        System.out.println("ProgressData fromString started its run: ");
        System.out.println("Raw data before parsing: " + data);

        try {

            System.out.println("ProgressData fromString checked try");

            if (data != null) {

                System.out.println("ProgressData fromString  if (data != null) ");

                String[] parts = data.split(",");

                System.out.println("check String parts contents: " + Arrays.toString(parts));

                if (parts.length >= 5) {
                    System.out.println(" if (parts.length >= 5) is checked");
                    ProgressData progressData = new ProgressData();
                    progressData.setUserCatCoinz(parseInteger(parts[0]));
                    progressData.setCatBought1(parseBoolean(parts[1]));
                    progressData.setCatBought2(parseBoolean(parts[2]));
                    progressData.setCatBought3(parseBoolean(parts[3]));
                    progressData.setCatBought4(parseBoolean(parts[4]));

                    if (parts.length >= 6) {
                        System.out.println(" if (parts.length >= 6) is checked");
                        progressData.setCounter(parseInteger(parts[5]));
                    }

                    if (parts.length >= 7) {
                        System.out.println(" if (parts.length >= 7) is checked");
                        List<String[]> catStorageList = new ArrayList<>();
                        for (int i = 6; i < 10 && i < parts.length; i++) {
                            catStorageList.add(parts[i].replaceAll("\\[|\\]", "").split("\\s*,\\s*"));
                        }

                        // Flatten the List<String[]> into a 1D array of Strings
                        String[] flattenedCatStorage = catStorageList.stream()
                                .flatMap(Arrays::stream)
                                .map(String::trim)  // Trim spaces
                                .toArray(String[]::new);

                        System.out.println("Flattened catStorage: " + Arrays.toString(flattenedCatStorage));
                        progressData.setCatStorage(flattenedCatStorage);
                    }

                    if (parts.length >= 9) {
                        System.out.println(" if (parts.length >= 13) is checked");

                        List<Integer> arrangementList = new ArrayList<>();

                        for (int i = 10; i < parts.length; i++) {
                            String[] listParts = parts[i].replaceAll("\\[|\\]", "").split("\\s*,\\s*");

                            arrangementList.addAll(
                                    Arrays.stream(listParts)
                                            .map(String::trim)  // Trim spaces
                                            .filter(s -> !s.isEmpty())  // Filter out empty strings
                                            .map(s -> {
                                                try {
                                                    System.out.println("Integer.parseInt is :" + s);
                                                    return Integer.parseInt(s);
                                                } catch (NumberFormatException ex) {
                                                    System.out.println("Check null catch");
                                                    // Handle invalid integer, e.g., log the error
                                                    return null; // or another default value
                                                }
                                            })
                                            .filter(Objects::nonNull)  // Filter out null values
                                            .toList()
                            );

                            progressData.setArrangementofCats(arrangementList);
                        }

                        System.out.println("Check Set arrangementofCats:" + progressData.getArrangementofCats());

                    } else {
                        System.out.println("else is checked inProgressData");
                        progressData.setArrangementofCats(new ArrayList<>());
                    }

                    System.out.println("Raw data after parsing: " + data);

                    System.out.println("Raw data after parsing: " + data);
                    System.out.println("Check Values Inside ProgressDataFromString:");
                    System.out.println("Check userCatCoinz: " + progressData.getUserCatCoinz());
                    System.out.println("Check booleanVariables: " +
                            progressData.isCatBought1() + progressData.isCatBought2() +
                            progressData.isCatBought3() + progressData.isCatBought4());
                    System.out.println("Check catStorage: " + Arrays.toString(progressData.getCatStorage()));
                    System.out.println("Check arrangementofCats: " + progressData.getArrangementofCats());

                    System.out.println("FUNCTION: ProgressData fromString ended its run");
                    System.out.println("---------------------------------------------------------");

                    return progressData;
                }
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
        return new ProgressData();
    }

    private static Integer parseInteger(String s) {
        try {
            return s.trim().isEmpty() ? null : Integer.parseInt(s.trim());
        } catch (NumberFormatException e) {
            // Handle invalid integer, e.g., log the error
            return null; // or another default value
        }
    }

    private static Boolean parseBoolean(String s) {
        return s.trim().isEmpty() ? null : Boolean.parseBoolean(s.trim());
    }




}




