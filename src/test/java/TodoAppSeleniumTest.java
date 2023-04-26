import com.github.javafaker.Faker;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TodoAppSeleniumTest {

    private WebDriver driver;

    @BeforeAll
    public static void setupDriver(){
        WebDriverManager.firefoxdriver().setup();
    }

    @BeforeEach
    void setUp() {
        driver = new FirefoxDriver();
    }

    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLogin() throws SQLException, ClassNotFoundException, InterruptedException {
        // 1) Test Login
        driver.navigate().to("http://localhost:8080/TodoApp-1.0-SNAPSHOT/");
        WebElement username = driver.findElement(By.id("username"));
        WebElement password = driver.findElement(By.id("password"));
        WebElement submitButton = driver.findElement(By.xpath("//button[@type='submit']"));
        username.sendKeys("testuser");
        password.sendKeys("testpassword");
        submitButton.click();
        Thread.sleep(1000);
        assertEquals("http://localhost:8080/TodoApp-1.0-SNAPSHOT/hello-servlet", driver.getCurrentUrl());


        // 2) Test New Todo Form
        WebElement addNewTodoButton = driver.findElement(By.xpath("//a[text()='Add New']"));
        addNewTodoButton.click();
        Thread.sleep(1000);

        // Verify that the correct page has loaded
        String pageTitle = driver.getTitle();
        assertEquals("Todo App - Add New", pageTitle);


        // 3) Test Enter the Todo details in the form
        WebElement subjectField = driver.findElement(By.id("subject"));
        subjectField.sendKeys("New Todo");
        WebElement descriptionField = driver.findElement(By.id("description"));
        descriptionField.sendKeys("This is a test Todo.");
        WebElement todoDateField = driver.findElement(By.id("todoDate"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        todoDateField.sendKeys(LocalDate.now().format(formatter));
        WebElement isDoneField = driver.findElement(By.id("isDone"));
        isDoneField.click();

        // Submit the form and verify that the Todo is added to the list
        WebElement submitButton2 = driver.findElement(By.xpath("//button[text()='Save']"));
        Thread.sleep(500);
        submitButton2.click();

        WebElement todoItem = driver.findElement(By.xpath("//td[contains(text(), 'New Todo')]"));
        assert(todoItem.isDisplayed());


        // 4) Test to Verify that the todo list contains the user's todos
        List<WebElement> todoItems = driver.findElements(By.className("table"));
        Thread.sleep(1000);
        assertTrue(todoItems.size() > 0);


        // 5) Test to verify edit form is loaded with details
        // Click on a specific edit link
        // Get the maximum id value
        List<WebElement> rows = driver.findElements(By.xpath("//table//a[contains(@href, 'edit')]"));
        List<Integer> ids = new ArrayList<>();

        for (WebElement row : rows) {
            String href = row.getAttribute("href");
            int id = Integer.parseInt(href.substring(href.indexOf("id=") + 3));
            ids.add(id);
        }

        int maxId = Collections.max(ids);

        WebElement editLink = driver.findElement(By.cssSelector("a[href='edit?id=" + maxId + "']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", editLink);
        editLink.click();

        // Verify that the correct page has loaded
        String pageTitle5 = driver.getTitle();
        assertEquals("Todo App - Edit", pageTitle5);

        WebElement subjectField5 = driver.findElement(By.name("subject"));
        WebElement descriptionField5 = driver.findElement(By.name("description"));
        WebElement todoDateField5 = driver.findElement(By.name("todoDate"));
        WebElement isDoneCheckbox5 = driver.findElement(By.name("isDone"));

        String expectedSubject5 = "New Todo";
        String expectedDescription5 = "This is a test Todo.";
        String expectedTodoDate5 = LocalDate.now().format(formatter);
        boolean expectedIsDone5 = false;

        assertEquals(expectedSubject5, subjectField5.getAttribute("value"));
        assertEquals(expectedDescription5, descriptionField5.getAttribute("value"));
        assertEquals(expectedTodoDate5, todoDateField5.getAttribute("value"));
        assertEquals(expectedIsDone5, isDoneCheckbox5.isSelected());

        Thread.sleep(1000);


        // 6) Test to update a todo

        // Fill out the edit form
        WebElement subjectInput = driver.findElement(By.id("subject"));
        WebElement descriptionInput = driver.findElement(By.id("description"));
        WebElement todoDateInput = driver.findElement(By.id("todoDate"));
        WebElement isDoneCheckbox = driver.findElement(By.id("isDone"));

        Thread.sleep(500);
        subjectInput.clear();
        Thread.sleep(500);
        subjectInput.sendKeys("New Test Subject");

        Thread.sleep(500);
        descriptionInput.clear();
        Thread.sleep(500);
        descriptionInput.sendKeys("New Test Description");

        Thread.sleep(500);
        todoDateInput.clear();
        Thread.sleep(500);
        todoDateInput.sendKeys("2023-05-01");

        Thread.sleep(500);
        isDoneCheckbox.click();

        // Submit the form

        Thread.sleep(1000);
        WebElement submitButton6 = driver.findElement(By.xpath("//button[text()='Save']"));
        submitButton6.click();

        // Check that the todo item has been updated
        WebElement updatedSubject = driver.findElement(By.xpath("//td[contains(text(),'New Test Subject')]"));
        //WebElement updatedDescription = driver.findElement(By.xpath("//td[contains(text(),'New Test Description')]"));
        WebElement updatedDate = driver.findElement(By.xpath("//td[contains(text(),'2023-05-01')]"));
        WebElement updatedIsDone = driver.findElement(By.xpath("//td[contains(text(),'false')]"));
        Thread.sleep(1500);
        assertEquals("New Test Subject", updatedSubject.getText());
        //assertEquals("New Test Description", updatedDescription.getText());
        assertEquals("2023-05-01", updatedDate.getText());
        assertEquals("false", updatedIsDone.getText());


        // 7) Test Delete Todo
        // Find the delete button
        WebElement deleteLink = driver.findElement(By.cssSelector("a[href='delete?id=" + maxId + "']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", deleteLink);
        deleteLink.click();
        Thread.sleep(1500);
    }

    @Test
    public  void  testResgistration() throws  SQLException, ClassNotFoundException {
        // Register New User
        driver.get("http://localhost:8080/TodoApp-1.0-SNAPSHOT/register");
        WebElement firstNameField = driver.findElement(By.id("firstName"));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        firstNameField.sendKeys(new Faker().name().firstName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement lastNameField = driver.findElement(By.id("lastName"));
        lastNameField.sendKeys(new Faker().name().lastName());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement usernameField = driver.findElement(By.id("username"));
        usernameField.sendKeys(new Faker().name().username());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.sendKeys(new Faker().internet().password());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Submit registration form
        WebElement submitButton = driver.findElement(By.xpath("//button[text()='Submit']"));
        submitButton.click();

        // Verify that registration was successful
        /*WebElement notification = driver.findElement(By.id("notify"));
        assertEquals("User Registered Successfully!", notification.getText());*/

    }

}
