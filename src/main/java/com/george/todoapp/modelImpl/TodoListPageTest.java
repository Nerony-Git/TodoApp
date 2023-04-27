package com.george.todoapp.modelImpl;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.george.todoapp.TodoListPage;
import com.github.javafaker.Faker;
import org.graphwalker.core.machine.ExecutionContext;
import org.graphwalker.java.annotation.GraphWalker;
import org.openqa.selenium.By;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

@GraphWalker(value = "random(edge_coverage(100))")
public class TodoListPageTest extends ExecutionContext implements TodoListPage {

    @Override
    public void v_AddTodo() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        $("h2").shouldHave(text("Add New Todo"));
        $("h2").shouldBe(visible);
    }

    @Override
    public void v_TodoList() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        $("h3").shouldHave(text("List of Todos"));
        $("h3").shouldBe(visible);

    }

    @Override
    public void e_TodoDeleted() {

    }

    @Override
    public void v_DeleteTodo() {

    }

    @Override
    public void e_TodoEdited() {
        $(By.id("subject")).clear();
        $(By.id("subject")).sendKeys(new Faker().lorem().characters(6, 15));
        $(By.id("description")).clear();
        $(By.id("description")).sendKeys(new Faker().lorem().sentence());
        $("button[type=\"submit\"]").click();

    }

    @Override
    public void e_TodoAdded() {
        fillTodoData();
        $(By.id("subject")).clear();
        $(By.id("subject")).sendKeys(new Faker().lorem().characters(6, 15));
        $("button[type=\"submit\"]").click();

    }

    @Override
    public void v_EditTodo() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        $("h2").shouldHave(text("Edit Todo"));
        $("h2").shouldBe(visible);

    }

    @Override
    public void e_AddTodoFail() {
        fillTodoData();
        $("button[type=\"submit\"]").click();
    }

    @Override
    public void e_EditTodoFail() {
        $(By.id("subject")).clear();
        $(By.id("subject")).sendKeys(new Faker().lorem().characters(6, 15));
        $(By.id("description")).clear();
        $("button[type=\"submit\"]").click();

    }

    @Override
    public void e_EditTodo() {

        // Get the maximum id value
        ElementsCollection rows = $$("table a[href*='edit']");
        List<Integer> ids = new ArrayList<>();

        if (rows != null && rows.size() > 0) {
            for (SelenideElement row : rows) {
                String href = row.getAttribute("href");
                int id = Integer.parseInt(href.substring(href.indexOf("id=") + 3));
                ids.add(id);
            }

            int maxId = Collections.max(ids);

            SelenideElement editLink = $("a[href='edit?id=" + maxId + "']");
            editLink.scrollIntoView(true);
            editLink.click();
        } else {
            System.out.println("No rows found. Skipping test case.");
        }

    }

    @Override
    public void e_DeleteTodo() {

        // Get the maximum id value
        ElementsCollection rowsz = $$("table a[href*='delete']");
        List<Integer> idsz = new ArrayList<>();

        if ( rowsz != null && rowsz.size() > 0) {
            for (SelenideElement rowz : rowsz) {
                String href = rowz.getAttribute("href");
                int id = Integer.parseInt(href.substring(href.indexOf("id=") + 3));
                idsz.add(id);
            }

            int maxIdz = Collections.max(idsz);

            SelenideElement deleteLink = $("a[href='delete?id=" + maxIdz + "']");
            deleteLink.scrollIntoView(true);
            deleteLink.click();
        } else {
            System.out.println("No rows found. Skipping test case.");
        }

    }

    @Override
    public void e_AddTodo() {
        $(By.xpath("//a[text()='Add New']")).click();

    }

    private void fillTodoData() {

        $(By.id("description")).clear();
        $(By.id("description")).sendKeys(new Faker().lorem().sentence());
        $(By.id("todoDate")).sendKeys("2023-05-15");
        $(By.id("isDone")).click();

    }

}
