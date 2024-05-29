package com.example.shoes.ui;

import com.example.shoes.backend.model.Shoes;
import com.example.shoes.backend.services.ShoesService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;


@Route("")
@CssImport("./styles/shared-styles.css")
public class MainView extends VerticalLayout {
    private Grid<Shoes> grid = new Grid<>(Shoes.class);
    private TextField filterText = new TextField();
    private ShoesForm form;

    private ShoesService shoesService;

    public MainView(ShoesService shoesService) {
        this.shoesService = shoesService;
        addClassName("list-view"); // Дает компонентам CSS класс
        setSizeFull(); // MainView занимает весь браузер

        configureGrid();

        form = new ShoesForm();
        form.addListener(ShoesForm.SaveEvent.class, this::saveShoes);
        form.addListener(ShoesForm.DeleteEvent.class, this::deleteShoes);
        form.addListener(ShoesForm.CloseEvent.class, e -> closeEditor());

        Div content = new Div(grid, form);
        content.addClassName("content");
        content.setSizeFull();

        add(getToolBar(), content);
        updateList();

        closeEditor();
    }

    private HorizontalLayout getToolBar() {
        filterText.setPlaceholder("Найти по имени...");
        filterText.setClearButtonVisible(true);
        filterText.setValueChangeMode(ValueChangeMode.LAZY);
        filterText.addValueChangeListener(e -> updateList());

        Button addShoesButton = new Button("Добавить обувь");
        addShoesButton.addClickListener(click -> addShoes());

        HorizontalLayout toolBar = new HorizontalLayout(filterText, addShoesButton);
        toolBar.addClassName("toolbar");
        return toolBar;
    }

    private void configureGrid(){
        grid.addClassName("shoes-grid");
        grid.setSizeFull();
        grid.setColumns("id", "name", "size");

        grid.getColumns().forEach(col -> col.setAutoWidth(true));

        grid.asSingleSelect().addValueChangeListener(event -> editShoes(event.getValue()));
    }

    private void updateList() {
        grid.setItems(shoesService.findAllShoes(filterText.getValue()));
    }

    public void editShoes(Shoes shoes) {
        if (shoes == null) {
            closeEditor();
        } else {
            form.setShoes(shoes);
            form.setVisible(true);
            addClassName("editing");
        }
    }

    private void saveShoes(ShoesForm.SaveEvent event){
        shoesService.saveShoes(event.getShoes());
        updateList();
        closeEditor();
    }

    private void deleteShoes(ShoesForm.DeleteEvent event){
        shoesService.deleteShoes(event.getShoes());
        updateList();
        closeEditor();
    }

    private void addShoes() {
        grid.asSingleSelect().clear();
        editShoes(new Shoes());
    }

    private void closeEditor() {
        form.setShoes(null);
        form.setVisible(false);
        removeClassName("editing");
    }
}
