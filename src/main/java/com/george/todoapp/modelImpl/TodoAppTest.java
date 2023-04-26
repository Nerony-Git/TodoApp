package com.george.todoapp.modelImpl;

import com.george.todoapp.TodoApp;
import com.github.javafaker.Faker;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Configuration.browser;
import static com.codeborne.selenide.Selenide.*;


@GraphWalker(value = "random(edge_coverage(100))", start = "e_StartBrowser")
public class TodoAppTest extends ExecutionContext implements TodoApp {

    //static WebDriver driver;

    @Override
    public void v_LoginPage() {
        $("h1").shouldHave(text("Login Form"));
        $("h1").shouldBe(visible);
    }

    @Override
    public void e_SignUpSuccess(){
        fillUserData();
        $(By.id("password")).sendKeys(new Faker().internet().password());
        $("button[type=\"submit\"]").click();

    }

    @Override
    public void e_SignUp() {
        //$("[title='Signup']").click();
        $(By.xpath("//a[text()='Signup']")).click();
    }

    @Override
    public void v_SignUpPage() {
        $("h2").shouldHave(text("User Register Form"));
        $("h2").shouldBe(visible);

    }

    @Override
    public void v_TodoList () {
        $("h3").shouldHave(text("List of Todos"));
        $("h3").shouldBe(visible);

    }

    @Override
    public  void e_LoginSuccess() {
        $(By.id("username")).clear();
        $(By.id("username")).sendKeys("testuser");
        $(By.id("password")).clear();
        $(By.id("password")).sendKeys("testpassword");
        $("button[type=\"submit\"]").click();

    }

    @Override
    public void e_Logout() {
        $("a[href='logout']").click();

    }

    @Override
    public void e_LoginFail(){
        $(By.id("username")).sendKeys(new Faker().name().firstName());
        $(By.id("password")).sendKeys(new Faker().name().lastName());
        $("button[type=\"submit\"]").click();
    }

    @Override
    public void v_LoginFail(){
        $("h1").shouldHave(text("Login Form"));
        $("h1").shouldBe(visible);

    }

    @Override
    public void e_SignUpFail(){
        fillUserData();
        $("button[type=\"submit\"]").click();
    }

    private void fillUserData() {
        $(By.id("firstName")).clear();
        $(By.id("firstName")).sendKeys(new Faker().name().firstName());

        $(By.id("lastName")).clear();
        $(By.id("lastName")).sendKeys(new Faker().name().lastName());

        $(By.id("username")).clear();
        $(By.id("username")).sendKeys(new Faker().name().username());

        $(By.id("password")).clear();
    }

    @Override
    public void e_StartBrowser(){
        open("http://localhost:8080/TodoApp-1.0-SNAPSHOT");
    }

    @BeforeExecution
    public void setup() {
        System.out.println("TodoApp: Any setup steps happens here. " +
                "The annotation @BeforeExecution makes sure that before any elements in the " +
                "model is called, this method is called first");

        browser = "firefox";
    }

    @AfterExecution
    public void cleanup() {
        System.out.println("TodoApp: Any cleanup  steps happens here. " +
                "The annotation @AfterExecution makes sure that after the test is done, " +
                "this method is called last.");
    }

    @BeforeElement
    public void printBeforeElement() {
        System.out.println("Before element " + getCurrentElement().getName());
    }

    @AfterElement
    public void printAfterElement() {
        System.out.println("After element " + getCurrentElement().getName());
    }

}
