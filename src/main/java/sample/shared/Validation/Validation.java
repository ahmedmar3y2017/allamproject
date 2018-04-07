/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.shared.Validation;

import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

/**
 *
 * @author ahmed mar3y
 */
public class Validation {

    public static TextFormatter<Double> DoubleValidation() {
        // force the field to be double only
        Pattern validEditingState = Pattern.compile("-?(([1-9][0-9]*)|0)?(\\.[0-9]*)?");

        UnaryOperator<Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c;
            } else {
                return null;
            }
        };

        StringConverter<Double> converter = new StringConverter<Double>() {

            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || "-".equals(s) || ".".equals(s) || "-.".equals(s)) {
                    return 0.0;
                } else {
                    return Double.valueOf(s);
                }
            }

            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };

        TextFormatter<Double> textFormatter = new TextFormatter<>(converter, 0.0, filter);

        return textFormatter;

    }

    public static TextFormatter<Integer> IntegerValidation() {
        UnaryOperator<Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("-?([1-9][0-9]*)?")) {
                return change;
            }
            return null;
        };

        return new TextFormatter<>(new IntegerStringConverter(), 0, integerFilter);

    }

    public static TextFormatter<Integer> PhoneValidation() {
        UnaryOperator<Change> integerFilter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*") && newText.length() <= 11) {
                return change;
            }
            return null;
        };

        return new TextFormatter<>(new IntegerStringConverter(), null, integerFilter);

    }

    public static void phoneValidation(TextField textField) {

        textField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d*")) {
                    textField.setText(newValue.replaceAll("[^\\d]", ""));
                }
                if (textField.getText().length() > 11) {
                    String s = textField.getText().substring(0, 11);
                    textField.setText(s);
                }
            }
        });
//        textField.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(final ObservableValue<? extends String> ov, final String oldValue, final String newValue) {
//                if (textField.getText().length() > 11) {
//                    String s = textField.getText().substring(0, 11);
//                    textField.setText(s);
//                }
//            }
//        });

    }

}
