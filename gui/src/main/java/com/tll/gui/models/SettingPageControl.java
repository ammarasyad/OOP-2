package com.tll.gui.models;

import com.tll.backend.model.barang.Barang;
import com.tll.backend.repository.impl.barang.BarangRepository;
import com.tll.backend.repository.impl.bill.TemporaryBillRepository;
import com.tll.gui.DisplayWidget;
import com.tll.gui.PluginWidget;
import com.tll.gui.ProductWidget;
import com.tll.gui.controllers.KasirPageModel;
import com.tll.gui.controllers.SettingPageModel;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import org.javatuples.Pair;

import java.io.File;
import java.util.List;

public class SettingPageControl {
    private List<File> fileList;
    private SettingPageModel settingPageModel;

    public SettingPageControl(List<File> fileList, SettingPageModel settingPageModel){
        this.fileList = fileList;
        this.settingPageModel = settingPageModel;

        refreshFileList();
    }

    public void refreshFileList(){
        FlowPane pluginList = settingPageModel.getPluginList();
        pluginList.getChildren().clear();
        for(File file : fileList){
            PluginWidget pluginWidget = new PluginWidget(file);

            pluginList.getChildren().addAll(pluginWidget);
        }
    }
}
