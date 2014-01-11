package com.sopovs.moradanen.fan.bootstrap;

import java.io.InputStream;

public interface IFootballDataImporter {
    public void importDataFromFile(InputStream inputStream, String contestName);
}
