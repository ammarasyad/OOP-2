package com.tll.gui.models;

import com.tll.backend.repository.impl.barang.BarangRepository;
import com.tll.backend.repository.impl.bill.FixedBillRepository;
import com.tll.backend.repository.impl.bill.TemporaryBillRepository;
import com.tll.backend.repository.impl.user.CustomerRepository;
import com.tll.backend.repository.impl.user.MemberRepository;
import com.tll.gui.PluginWidget;
import com.tll.gui.controllers.SettingPageModel;
import javafx.scene.layout.FlowPane;
import lombok.Getter;

import java.io.File;
import java.util.List;

@Getter
public class SettingPageControl {
    private List<File> fileList;
    private SettingPageModel settingPageModel;
    private MemberRepository memberRepository;
    private CustomerRepository customerRepository;
    private BarangRepository barangRepository;
    private FixedBillRepository fixedBillRepository;
    private TemporaryBillRepository temporaryBillRepository;

    public SettingPageControl(List<File> fileList, SettingPageModel settingPageModel,
                              MemberRepository memberRepository, TemporaryBillRepository temporaryBillRepository,
                              FixedBillRepository fixedBillRepository, CustomerRepository customerRepository,
                              BarangRepository barangRepository) {
        this.fileList = fileList;
        this.settingPageModel = settingPageModel;
        this.barangRepository = barangRepository;
        this.temporaryBillRepository = temporaryBillRepository;
        this.fixedBillRepository = fixedBillRepository;
        this.memberRepository = memberRepository;
        this.customerRepository = customerRepository;

        refreshFileList();
    }

    public void refreshFileList() {
        FlowPane pluginList = settingPageModel.getPluginList();
        pluginList.getChildren().clear();
        for (File file : fileList) {
            PluginWidget pluginWidget = new PluginWidget(file);

            pluginList.getChildren().addAll(pluginWidget);
        }
    }

}
