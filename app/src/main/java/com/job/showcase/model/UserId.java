package com.job.showcase.model;

import android.support.annotation.NonNull;

/**
 * Created by Job on Friday : 3/23/2018.
 */

public class UserId {

    public String userId;
    public <T extends UserId> T withId(@NonNull final String userId){
        this.userId = userId;
        return (T) this;
    }
}
