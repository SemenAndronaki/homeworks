package ru.addressbook.bdd;

import cucumber.api.testng.AbstractTestNGCucumberTests;
import cucumber.api.CucumberOptions;

@CucumberOptions(features = "classpath:bdd", plugin = {"pretty", "html:build/cucumber-report"})
public class TestRunner extends AbstractTestNGCucumberTests {

}