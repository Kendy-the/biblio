package com.biblio.observer;

import com.biblio.models.Adherent;

public interface AdherentListener {
    public void update(Object object);
    public void addedAdherent(Adherent adherent);
    public void editedAdherent(Adherent adherent);
    public void deletedAdherent(Adherent adherent);
}
