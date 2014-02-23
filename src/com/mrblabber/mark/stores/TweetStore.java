package com.mrblabber.mark.stores;


public class TweetStore {
    String Tweet, User, Uuid, postedBy;

    public String getTweet() { return Tweet; }
    public String getUser() { return User; }
    public String getUuid() { return Uuid; }
    public String getPostedBy() { return postedBy; }
    public void setTweet(String Tweet) { this.Tweet = Tweet; }
    public void setUser(String User) { this.User = User; }
    public void setUuid(String Uuid) { this.Uuid = Uuid; }
    public void setPostedBy(String postedBy) { this.postedBy = postedBy; }
}
