package prateektimer.com.firebaseemail;

/**
 * Created by user on 16-11-2017.
 */

public class User {
    String getFullName;
    String getEmailId;
    String getMobileNumber;
    String getLocation;
    String getPassword;
    String getConfirmPassword;

    public User() {

    }

    public User(String getFullName, String getEmailId, String getMobileNumber, String getLocation, String getPassword, String getConfirmPassword) {

        this.getFullName = getFullName;
        this.getEmailId = getEmailId;
        this.getMobileNumber = getMobileNumber;
        this.getLocation = getLocation;
        this.getPassword = getPassword;
        this.getConfirmPassword = getConfirmPassword;
    }

    public String getGetFullName() {
        return getFullName;
    }

    public void setGetFullName(String getFullName) {
        this.getFullName = getFullName;
    }

    public String getGetEmailId() {
        return getEmailId;
    }

    public void setGetEmailId(String getEmailId) {
        this.getEmailId = getEmailId;
    }

    public String getGetMobileNumber() {
        return getMobileNumber;
    }

    public void setGetMobileNumber(String getMobileNumber) {
        this.getMobileNumber = getMobileNumber;
    }

    public String getGetLocation() {
        return getLocation;
    }

    public void setGetLocation(String getLocation) {
        this.getLocation = getLocation;
    }

    public String getGetPassword() {
        return getPassword;
    }

    public void setGetPassword(String getPassword) {
        this.getPassword = getPassword;
    }

    public String getGetConfirmPassword() {
        return getConfirmPassword;
    }

    public void setGetConfirmPassword(String getConfirmPassword) {
        this.getConfirmPassword = getConfirmPassword;
    }
}
