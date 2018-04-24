package sample.shared;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package com.shared;
//

//
///**
// *
// * @author ahmed mar3y
// */
//public class AutoCompleteComboBoxListener<T> {
//
//    private ComboBox<T> cmb;
//    String filter = "";
//    private ObservableList<T> originalItems;
//
//    public AutoCompleteComboBoxListener(ComboBox<T> cmb) {
//        this.cmb = cmb;
//        originalItems = FXCollections.observableArrayList(cmb.getItems());
//        cmb.setTooltip(new Tooltip());
//        cmb.setOnKeyPressed(this::handleOnKeyPressed);
//        cmb.setOnHidden(this::handleOnHiding);
//    }
//
//    public void handleOnKeyPressed(KeyEvent e) {
//        ObservableList<T> filteredList = FXCollections.observableArrayList();
//        KeyCode code = e.getCode();
//
//        if (code.isLetterKey()) {
//            filter += e.getText();
//        }
//        if (code == KeyCode.BACK_SPACE && filter.length() > 0) {
//            filter = filter.substring(0, filter.length() - 1);
//            cmb.getItems().setAll(originalItems);
//        }
//        if (code == KeyCode.ESCAPE) {
//            filter = "";
//        }
//        if (filter.length() == 0) {
//            filteredList = originalItems;
//            cmb.getTooltip().hide();
//        } else {
//            Stream<T> itens = cmb.getItems().stream();
//            String txtUsr = unaccent(filter.toString().toLowerCase());
//            itens.filter(el -> unaccent(el.toString().toLowerCase()).contains(txtUsr)).forEach(filteredList::add);
//            cmb.getTooltip().setText(txtUsr);
////            Window stage = cmb.getScene().getWindow();
////            double posX = stage.getX() + cmb.getBoundsInParent().getMinX();
////            double posY = stage.getY() + cmb.getBoundsInParent().getMinY();
////            cmb.getTooltip().show(stage, posX, posY);
//            Point2D p = cmb.localToScene(0.0, 0.0);
//            cmb.getTooltip().show(cmb,
//                    p.getX(),
//                    p.getY());
//
//
//
//            cmb.show();
//        }
//        cmb.getItems().setAll(filteredList);
//    }
//
//    public void handleOnHiding(Event e) {
//        filter = "";
//        cmb.getTooltip().hide();
//        T s = cmb.getSelectionModel().getSelectedItem();
//        cmb.getItems().setAll(originalItems);
//        cmb.getSelectionModel().select(s);
//    }
//
//    private String unaccent(String s) {
//        String temp = Normalizer.normalize(s, Normalizer.Form.NFD);
//        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
//        return pattern.matcher(temp).replaceAll("");
//    }
//
//}
public class AutoCompleteComboBoxListener<T> implements EventHandler<KeyEvent> {

    private ComboBox<T> comboBox;
    private ObservableList<T> data;
    private boolean moveCaretToPos = false;
    private int caretPos;

    public AutoCompleteComboBoxListener(final ComboBox<T> comboBox) {
        this.comboBox = comboBox;
        data = comboBox.getItems();

        this.comboBox.setEditable(true);
        this.comboBox.setOnKeyReleased(AutoCompleteComboBoxListener.this);
    }

    @Override
    public void handle(KeyEvent event) {


        if(event.getCode() == KeyCode.UP) {
            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        } else if(event.getCode() == KeyCode.DOWN) {
            if(!comboBox.isShowing())
                comboBox.show();

            caretPos = -1;
            moveCaret(comboBox.getEditor().getText().length());
            return;
        }

        if (event.getCode() == KeyCode.RIGHT || event.getCode() == KeyCode.LEFT
                || event.isControlDown() || event.getCode() == KeyCode.HOME
                || event.getCode() == KeyCode.END || event.getCode() == KeyCode.TAB) {
            return;
        }

        comboBox.hide();

        if(event.getCode() == KeyCode.BACK_SPACE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        } else if(event.getCode() == KeyCode.DELETE) {
            moveCaretToPos = true;
            caretPos = comboBox.getEditor().getCaretPosition();
        }



        ObservableList<T> list = FXCollections.observableArrayList();
        for (int i=0; i<data.size(); i++) {
            if(data.get(i).toString().toLowerCase().startsWith(
                    AutoCompleteComboBoxListener.this.comboBox
                            .getEditor().getText().toLowerCase())) {
                list.add(data.get(i));
            }
        }
        String t = comboBox.getEditor().getText();

        comboBox.setItems(list);
        comboBox.getEditor().setText(t);
        if(!moveCaretToPos) {
            caretPos = -1;
        }
        moveCaret(t.length());
        if(!list.isEmpty()) {
            comboBox.show();
        }
    }

    private void moveCaret(int textLength) {
        if(caretPos == -1)
            comboBox.getEditor().positionCaret(textLength);
        else
            comboBox.getEditor().positionCaret(caretPos);

        moveCaretToPos = false;
    }

}