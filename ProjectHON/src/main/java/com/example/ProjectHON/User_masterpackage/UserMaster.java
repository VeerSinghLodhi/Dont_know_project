package com.example.ProjectHON.User_masterpackage;

import com.example.ProjectHON.Badge_masterpackage.BadgeMaster;
import com.example.ProjectHON.Post_masterpackage.PostMaster;
import com.example.ProjectHON.Rating_masterpackage.RatingMaster;
import com.example.ProjectHON.Whisper_masterpackage.WhisperMaster;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;


@Entity
@Table(name = "users_master")
public class UserMaster{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @ManyToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<BadgeMaster> badge;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<PostMaster> post;

    @OneToMany(mappedBy = "userFrom" , cascade = CascadeType.ALL)
    private List<RatingMaster> userFrom;

    @OneToMany(mappedBy = "userTo" , cascade = CascadeType.ALL)
    private List<RatingMaster> userTo;

    @ManyToMany(mappedBy = "sender", cascade = CascadeType.ALL)
    private List<WhisperMaster> sentWhispers; // Sender

    @ManyToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
    private List<WhisperMaster> receivedWhispers; // Receiver

    private String username;

    private String password;

    private String email;

    private double points;

    private byte[] profilePhoto;

    private String gender;

    private LocalDate dateOfBirth;

    private LocalDate joinDate;

    private String status; //Active, Inactive

    private String contactNo;

    private String relationshipStatus; //Single, Married, Committed

    private String bio;

    private String jiolocation;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<BadgeMaster> getBadge() {
        return badge;
    }

    public void setBadge(List<BadgeMaster> badge) {
        this.badge = badge;
    }

    public List<PostMaster> getPost() {
        return post;
    }

    public void setPost(List<PostMaster> post) {
        this.post = post;
    }

    public List<RatingMaster> getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(List<RatingMaster> userFrom) {
        this.userFrom = userFrom;
    }

    public List<RatingMaster> getUserTo() {
        return userTo;
    }

    public void setUserTo(List<RatingMaster> userTo) {
        this.userTo = userTo;
    }

    public List<WhisperMaster> getSentWhispers() {
        return sentWhispers;
    }

    public void setSentWhispers(List<WhisperMaster> sentWhispers) {
        this.sentWhispers = sentWhispers;
    }

    public List<WhisperMaster> getReceivedWhispers() {
        return receivedWhispers;
    }

    public void setReceivedWhispers(List<WhisperMaster> receivedWhispers) {
        this.receivedWhispers = receivedWhispers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getPoints() {
        return points;
    }

    public void setPoints(double points) {
        this.points = points;
    }

    public byte[] getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(byte[] profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getRelationshipStatus() {
        return relationshipStatus;
    }

    public void setRelationshipStatus(String relationshipStatus) {
        this.relationshipStatus = relationshipStatus;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getJiolocation() {
        return jiolocation;
    }

    public void setJiolocation(String jiolocation) {
        this.jiolocation = jiolocation;
    }
}
