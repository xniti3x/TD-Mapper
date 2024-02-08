package com.tdmapper.application.views;

import java.util.List;

import com.tdmapper.application.models.CorrespondentDto;
import com.tdmapper.application.models.DocumentDto;
import com.tdmapper.application.models.Transaction;
import com.tdmapper.application.services.RestService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;

@Route("dialog-basic")
public class DialogComponent extends Dialog {

    //@Autowired private TransactionService transactionService;
    RestService restService;
    
    public DialogComponent(Transaction transaction,RestService restService) {
        this.restService = restService;
        
        Grid<DocumentDto> grid = new Grid<>(DocumentDto.class);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addSelectionListener(selection -> {
             selection.getAllSelectedItems().forEach(i->System.out.println(i.getFilename()));
        });
        
        //DocumentDto item = grid.getDataCommunicator().getItem(1);
        
        //if(grid.getGenericDataView().getItems().count() >0)
        //grid.getSelectionModel().select(grid.getGenericDataView().getItem(0));
        //grid.select(null);
        ComboBox<CorrespondentDto> comboBox = new ComboBox<>("Correspondent");
        comboBox.setWidthFull();
        
        comboBox.setItems(restService.getCorrespondence());
        comboBox.addValueChangeListener(e-> {
            List<DocumentDto> documentsByCorrespondenceId = restService.getDocumentsByCorrespondenceId(e.getValue().getId());
            grid.setItems(documentsByCorrespondenceId);
            if(documentsByCorrespondenceId.size()>0)
            grid.select(documentsByCorrespondenceId.get(0));
            
        });
        
        Button closeButton = new Button(new Icon("lumo", "cross"),(e) -> close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        
        Button okButton = new Button(LumoIcon.CHECKMARK.create(),(e) -> close());
        closeButton.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        
        VerticalLayout vl = new VerticalLayout();
        vl.add(comboBox);
        add(new Div(transaction.getRemittanceInformationStructured()));
        getHeader().add(okButton);
        getHeader().add(closeButton);
        setDraggable(true);
        setResizable(true);
        setHeaderTitle(transaction.getAmount() + "- "+ transaction.getDebtorName());
        setSizeFull();
        vl.add(grid);
        add(vl);
        open();
    }



}