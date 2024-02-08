package com.tdmapper.application.views;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import com.tdmapper.application.models.Transaction;
import com.tdmapper.application.services.RestService;
import com.tdmapper.application.services.TransactionService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility;

import jakarta.annotation.security.RolesAllowed;

@PageTitle("Transactions")
@Route(value = "transactions", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
@RolesAllowed("ADMIN")
@Uses(Icon.class)
public class TransactionsView extends Div {

    private Grid<Transaction> grid;
    private final TransactionService transactionService;
    @Autowired private RestService restService;
    public TransactionsView(TransactionService transactionService) {
        this.transactionService = transactionService;
        setSizeFull();
        addClassNames("transactions-view");

        VerticalLayout layout = new VerticalLayout(createGrid());
        layout.setSizeFull();
        layout.setPadding(false);
        layout.setSpacing(false);
        add(layout);
    }

    private Component createGrid() {     
        grid = new Grid<>(Transaction.class, false);
        grid.addColumn("bookingDate").setAutoWidth(true);
        grid.addColumn("amount").setAutoWidth(true);
        grid.addColumn("debtorName").setAutoWidth(true);
        grid.addColumn(t-> this.textLength(t.getRemittanceInformationStructured(),50)).setAutoWidth(true).setHeader("Beschreibung"); 
        
        grid.setItems(query -> transactionService.listNotNull(
                PageRequest.of(query.getPage(), query.getPageSize(), VaadinSpringDataHelpers.toSpringDataSort(query))).stream());
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addClassNames(LumoUtility.Border.TOP, LumoUtility.BorderColor.CONTRAST_10);
        
        
        grid.addItemClickListener(e->new DialogComponent(e.getItem(),restService));
        return grid;
    }

    private String textLength(String text, int length) {
        return text.length()>=length?text.substring(0, length):text;
    }


}
