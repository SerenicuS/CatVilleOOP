package org.dreamchurch.mediateam;


/*
    THIS CLASS IS USED FOR STORING AND LOADING THE DATA PROGRESS OF THE USER, IT IS STILL IN BETA
    SO IT MIGHT CREATE SOME WEIRD RESULTS!
 */
public class ProgressData {
    private int userCatCoinz = 400;
    private String[] catStorage;
    private boolean isCat1Edited, isCat2Edited, isCat3Edited, isCat4Edited;



    public int getUserCatCoinz() {
        return userCatCoinz;
    }

    public void setUserCatCoinz(int userCatCoinz) {
        this.userCatCoinz = userCatCoinz;
    }

    @Override
    public String toString() {
        return String.valueOf(userCatCoinz); // STILL IN BETA, WILL ADD MORE IF IT IS POSSIBLE
    }

    // You can add a static method to parse a string and create an instance of ProgressData
    public static ProgressData fromString(String data) {
        try {
            if (data != null) {
                String[] parts = data.split(",");

                ProgressData progressData = new ProgressData();

                // Set the fields based on the parsed values
                progressData.setUserCatCoinz(Integer.parseInt(parts[0]));

                return progressData;
            } else {
                // Handle the case where data is null (e.g., empty file)
                return new ProgressData();
            }
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            // Handle exceptions (e.g., invalid data format)
            e.printStackTrace();
            return new ProgressData(); // Return a default instance if an error occurs
        }
    }

}
