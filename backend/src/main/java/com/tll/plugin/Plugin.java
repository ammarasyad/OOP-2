package com.tll.plugin;

import javafx.scene.control.Menu;
import javafx.scene.layout.VBox;


public abstract class Plugin {

    @AutoWired(identifier = "page_menu")
    private Menu pageMenu;

    @AutoWired(identifier = "plugin_list")
    private VBox pluginList;

    public abstract void loadPage();

}
