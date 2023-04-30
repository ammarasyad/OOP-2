package com.tll.baseclass;

import com.tll.annotation.AutoWired;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;


public abstract class Plugin {

    @AutoWired
    private Menu pageMenu;

    @AutoWired
    private MenuItem pluginPage;

    public abstract void loadPage();

}
