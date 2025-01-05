package com.akto.utils;

import com.akto.log.LoggerMaker;
import com.akto.util.DashboardMode;

import static com.akto.listener.InitializerListener.loadTemplateFilesFromDirectory;

public class TestTemplateUtils {

    private static final LoggerMaker loggerMaker = new LoggerMaker(TestTemplateUtils.class, LoggerMaker.LogDb.DASHBOARD);

    public static byte[] getTestingTemplates() {
        byte[] repoZip = null;

        if (!DashboardMode.isMetered()) {
            GithubSync githubSync = new GithubSync();
            repoZip = githubSync.syncRepo("akto-api-security/tests-library", "hotfix/fixed_some_minor_bugs"); // TODO("change source")

            if(repoZip == null) {
                loggerMaker.infoAndAddToDb("Failed to load test templates from github, trying to load from local directory");
            } else {
                loggerMaker.infoAndAddToDb("Loaded test templates from github");
            }
        }
        
        if (repoZip == null) {
            repoZip = loadTemplateFilesFromDirectory();

            if(repoZip == null) {
                loggerMaker.errorAndAddToDb("Failed to load test templates from local directory");
            } else {
                loggerMaker.infoAndAddToDb("Loaded test templates from local directory");
            }
        }
        
        return repoZip;
    }

}
