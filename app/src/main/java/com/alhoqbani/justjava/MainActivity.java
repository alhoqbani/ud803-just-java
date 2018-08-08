package com.alhoqbani.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText nameField = findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckbox = findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckbox = findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckbox.isChecked();

        Log.v("'MainActivity'", "Has whipped cream: " + hasWhippedCream);
        Log.v("'MainActivity'", "Has chocolate: " + hasWhippedCream);

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String orderSummary = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        // Send an email with the order summary.
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            displayMessage("Failed to compose email message.\n" + orderSummary);
        }
    }

    /**
     * Calculates the price of the order based on the current quantity.
     *
     * @param hasWhippedCream is weather or not the user wants whipped cream topping.
     * @param hasChocolate    is weather or not the user wants chocolate topping.
     * @return total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        // Price of 1 cup of coffee.
        int basePrice = 5;

        // Add $1 if the user wants whipped cream
        if (hasWhippedCream) {
            basePrice = basePrice + 1;
        }

        // Add $2 if the user wants chocolate
        if (hasChocolate) {
            basePrice = basePrice + 2;
        }

        // Calculate the total order price by multiplying by quantity.
        return quantity * basePrice;
    }

    /**
     * Create summary of the order.
     *
     * @param name            of the user
     * @param price           of the order
     * @param addWhippedCream is weather or not the user wants whipped cream.
     * @param addChocolate    is weather or not the user wants chocolate topping.
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {

        String priceMessage = getString(R.string.order_summary_name, name);

        priceMessage = priceMessage + "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage = priceMessage + "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage = priceMessage + "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage = priceMessage + "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage = priceMessage + "\n" + getString(R.string.thank_you);

        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView OrderSummaryTextView = findViewById(R.id.order_summary_text_view);
        OrderSummaryTextView.setText(message);
    }

    /**
     * This method is called when the plus button is clicked.
     *
     * @param view The Button view.
     */
    public void increment(View view) {
        quantity = quantity + 1;
        display(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     *
     * @param view The Button view.
     */
    public void decrement(View view) {
        quantity = quantity - 1;
        display(quantity);
    }
}
