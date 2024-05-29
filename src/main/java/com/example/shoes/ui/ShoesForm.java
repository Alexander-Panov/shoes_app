package com.example.shoes.ui;

import com.example.shoes.backend.model.Shoes;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.binder.ValidationException;
import com.vaadin.flow.shared.Registration;

import java.util.List;

public class ShoesForm extends FormLayout {
    TextField name = new TextField("Name");
    TextField size = new TextField(("Size"));

    Button save = new Button("Сохранить");
    Button delete = new Button("Удалить");
    Button close = new Button("Отмена");

    Binder<Shoes> binder = new BeanValidationBinder<>(Shoes.class);

    private Shoes shoes;


    public ShoesForm() {
        addClassName("shoes-form");
        binder.bindInstanceFields(this); // Совмещает инпуты и атрибуты класса по именам
        add(name,
            size,
            createButtonLayouts());
    }

    private HorizontalLayout createButtonLayouts() {
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        close.addThemeVariants(ButtonVariant.LUMO_TERTIARY);

        save.addClickShortcut(Key.ENTER);
        delete.addClickShortcut(Key.DELETE);
        close.addClickShortcut(Key.ESCAPE);

        save.addClickListener(event -> validateAndSave());
        delete.addClickListener(event -> fireEvent(new DeleteEvent(this, shoes)));
        close.addClickListener(event -> fireEvent(new CloseEvent(this)));

        binder.addStatusChangeListener(e -> save.setEnabled(binder.isValid())); // Подтверждение формы каждый раз,
        // когда она меняеся

        return new HorizontalLayout(save, delete, close);
    }

    private void validateAndSave() {
        try {
            binder.writeBean(shoes);
            fireEvent(new SaveEvent(this, shoes));
        } catch (ValidationException e) {
            e.printStackTrace();
        }
    }

    public void setShoes(Shoes shoes) {
        this.shoes = shoes;
        binder.readBean(shoes);
    }

    // Events
    public static abstract class ShoesFormEvent extends ComponentEvent<ShoesForm> {
        private Shoes shoes;

        protected ShoesFormEvent(ShoesForm source, Shoes shoes) {
            super(source, false);
            this.shoes = shoes;
        }
        public Shoes getShoes() {
            return shoes;
        }
    }
    public static class SaveEvent extends ShoesFormEvent {
        SaveEvent(ShoesForm source, Shoes shoes) {
            super(source, shoes);
        }
    }
    public static class DeleteEvent extends ShoesFormEvent {
        DeleteEvent(ShoesForm source, Shoes shoes) {
            super(source, shoes);
        }
    }
    public static class CloseEvent extends ShoesFormEvent {
        CloseEvent(ShoesForm source) {
            super(source, null);
        }
    }

    public <T extends ComponentEvent<?>> Registration addListener(Class<T> eventType,
                                                                  ComponentEventListener<T> listener) {
        return getEventBus().addListener(eventType, listener);
    }
}




