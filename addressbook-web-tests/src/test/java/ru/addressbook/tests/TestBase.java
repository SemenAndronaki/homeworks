package ru.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.addressbook.appManager.ApplicationManager;
import ru.addressbook.model.GroupData;
import ru.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Collectors;

public class TestBase {

    protected static final ApplicationManager app
            = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));

    Logger logger = LoggerFactory.getLogger(GroupCreationTests.class);

    @BeforeSuite(alwaysRun = true)
    public void setUp() throws IOException {
        app.init();
    }

    @AfterSuite(alwaysRun = true)
    public void tearDown() {
        app.stop();
    }

    @BeforeMethod(alwaysRun = true)
    public void logTestStart(Method method, Object[] params) {
        logger.info(String.format("Started test %s with parameters %s", method.getName(), Arrays.asList(params)));
    }

    @AfterMethod(alwaysRun = true)
    public void logTestStop(Method method, Object[] params) {
        logger.info(String.format("Stop test %s with parameters %s", method.getName(), Arrays.asList(params)));
    }

    public void verifyGroupListInUi() {
        if (Boolean.getBoolean("verifyUi")) {
            Groups groupsDB = app.getDbHelper().groups();
            Groups groupsUI = app.getGroupHelper().getGroups();
            assertThat(groupsUI, equalTo(groupsDB.stream()
                    .map((g) -> new GroupData().withGroupName(g.getGroupName()).withGroupId(g.getGroupId()))
                    .collect(Collectors.toSet())));
        }
    }
}
