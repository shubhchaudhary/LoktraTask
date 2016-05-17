package com.github.model;

/**
 * Created by shubham on 17/5/16.
 */
public class CommitModel {
    String message;
    IdentityModel committer;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public IdentityModel getCommitter() {
        return committer;
    }

    public void setCommitter(IdentityModel committer) {
        this.committer = committer;
    }
}
