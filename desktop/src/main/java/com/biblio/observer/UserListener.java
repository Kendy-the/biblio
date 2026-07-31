package com.biblio.observer;

import com.biblio.models.User;

public interface UserListener {
    public void update(Object object);
    public void addedUser(User user);
    public void editedUser(User user);
    public void deletedUser(User user);
}
