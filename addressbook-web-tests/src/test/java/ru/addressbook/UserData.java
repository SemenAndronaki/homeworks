package ru.addressbook;

public class UserData {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String mobileNumber;
    private final String email;

    public UserData(String firstName, String lastName, String address, String mobileNumber, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAddress() {
        return address;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public String getEmail() {
        return email;
    }
}
