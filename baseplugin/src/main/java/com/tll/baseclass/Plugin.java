package com.tll.baseclass;

import com.tll.annotation.AutoWired;
import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;


public abstract class Plugin {

    @AutoWired
    private Menu pageMenu;

    @AutoWired
    private VBox pluginList;

    public abstract void loadPage();

}
