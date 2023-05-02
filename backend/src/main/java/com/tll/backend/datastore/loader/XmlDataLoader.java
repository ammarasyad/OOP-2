package com.tll.backend.datastore.loader;

public class XmlDataLoader extends DataLoader {
    public XmlDataLoader(String fileName) {
        super(new XmlAdapter(fileName));
    }
}
