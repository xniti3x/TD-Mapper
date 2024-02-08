package com.tdmapper.application.views;
import com.tdmapper.application.models.Document;
import com.tdmapper.application.services.DocumentService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.domain.Specification;

@ComponentScan(basePackages = "com.tdmapper.application.views")
@org.springframework.stereotype.Component
public class Filters extends Div implements Specification<Document> {

    private final TextField name = new TextField("Name");
    private final TextField phone = new TextField("Phone");
    private final DatePicker startDate = new DatePicker("Date of Birth");
    private final DatePicker endDate = new DatePicker();
    private final MultiSelectComboBox<String> occupations = new MultiSelectComboBox<>("Occupation");
    private final CheckboxGroup<String> roles = new CheckboxGroup<>("Role");

    public Filters(){}
    public Filters(Runnable onSearch, DocumentService documentService) {

        setWidthFull();
        addClassName("filter-layout");
        addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.MEDIUM,
                LumoUtility.BoxSizing.BORDER);
        name.setPlaceholder("First or last name");
            

        occupations.setItems(documentService.readJson("/workspaces/TD-Mapper/src/main/resources/mock-api/documents.json"));

        roles.setItems("Worker", "Supervisor", "Manager", "External");
        roles.addClassName("double-width");

        // Action buttons
        Button resetBtn = new Button("Reset");
        resetBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        resetBtn.addClickListener(e -> {
            name.clear();
            phone.clear();
            startDate.clear();
            endDate.clear();
            occupations.clear();
            roles.clear();
            onSearch.run();
        });
        Button searchBtn = new Button("Search");
        searchBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        searchBtn.addClickListener(e -> onSearch.run());

        Button fetch = new Button("fetch");
        fetch.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        //fetch.addClickListener(e -> documentService.readJson());


        Div actions = new Div(fetch,resetBtn, searchBtn);
        actions.addClassName(LumoUtility.Gap.SMALL);
        actions.addClassName("actions");

        add(name, phone, createDateRangeFilter(), occupations, roles, actions);
    }
    
    private Component createDateRangeFilter() {
        startDate.setPlaceholder("From");

        endDate.setPlaceholder("To");

        // For screen readers
        startDate.setAriaLabel("From date");
        endDate.setAriaLabel("To date");

        FlexLayout dateRangeComponent = new FlexLayout(startDate, new Text(" – "), endDate);
        dateRangeComponent.setAlignItems(FlexComponent.Alignment.BASELINE);
        dateRangeComponent.addClassName(LumoUtility.Gap.XSMALL);

        return dateRangeComponent;
    }

    @Override
    public Predicate toPredicate(Root<Document> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        /* 
        if (!name.isEmpty()) {
            String lowerCaseFilter = name.getValue().toLowerCase();
            Predicate firstNameMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")),
                    lowerCaseFilter + "%");
            Predicate lastNameMatch = criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")),
                    lowerCaseFilter + "%");
            predicates.add(criteriaBuilder.or(firstNameMatch, lastNameMatch));
        }
        if (!phone.isEmpty()) {
            String databaseColumn = "phone";
            String ignore = "- ()";

            String lowerCaseFilter = ignoreCharacters(ignore, phone.getValue().toLowerCase());
            Predicate phoneMatch = criteriaBuilder.like(
                    ignoreCharacters(ignore, criteriaBuilder, criteriaBuilder.lower(root.get(databaseColumn))),
                    "%" + lowerCaseFilter + "%");
            predicates.add(phoneMatch);

        }
        if (startDate.getValue() != null) {
            String databaseColumn = "dateOfBirth";
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(databaseColumn),
                    criteriaBuilder.literal(startDate.getValue())));
        }
        if (endDate.getValue() != null) {
            String databaseColumn = "dateOfBirth";
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(criteriaBuilder.literal(endDate.getValue()),
                    root.get(databaseColumn)));
        }
        if (!occupations.isEmpty()) {
            String databaseColumn = "occupation";
            List<Predicate> occupationPredicates = new ArrayList<>();
            for (String occupation : occupations.getValue()) {
                occupationPredicates
                        .add(criteriaBuilder.equal(criteriaBuilder.literal(occupation), root.get(databaseColumn)));
            }
            predicates.add(criteriaBuilder.or(occupationPredicates.toArray(Predicate[]::new)));
        }
        if (!roles.isEmpty()) {
            String databaseColumn = "role";
            List<Predicate> rolePredicates = new ArrayList<>();
            for (String role : roles.getValue()) {
                rolePredicates.add(criteriaBuilder.equal(criteriaBuilder.literal(role), root.get(databaseColumn)));
            }
            predicates.add(criteriaBuilder.or(rolePredicates.toArray(Predicate[]::new)));
        }
        */
        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }

    private String ignoreCharacters(String characters, String in) {
        String result = in;
        for (int i = 0; i < characters.length(); i++) {
            result = result.replace("" + characters.charAt(i), "");
        }
        return result;
    }

    private Expression<String> ignoreCharacters(String characters, CriteriaBuilder criteriaBuilder,
            Expression<String> inExpression) {
        Expression<String> expression = inExpression;
        for (int i = 0; i < characters.length(); i++) {
            expression = criteriaBuilder.function("replace", String.class, expression,
                    criteriaBuilder.literal(characters.charAt(i)), criteriaBuilder.literal(""));
        }
        return expression;
    }
    
}
