package com.example.hector.hectorjustjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            // Show an error message as a toast
            Toast.makeText(this, getResources().getString(R.string.max), Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            // Show an error message as a toast
            Toast.makeText(this, getResources().getString(R.string.min), Toast.LENGTH_SHORT).show();
            // Exit this method early because there's nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText nameField = (EditText) findViewById(R.id.name_field);
        String name = nameField.getText().toString();

        // Figure out if the user wants whipped cream topping
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();

        // Figure out if the user wants the chocolate topping
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        // Figure out if the user wants the chocolate topping
        CheckBox CaramelCheckBox = (CheckBox) findViewById(R.id.caramel_checkbox);
        boolean hasCaramel = CaramelCheckBox.isChecked();

        // Figure out if the user wants the chocolate topping
        CheckBox VanillaCheckBox = (CheckBox) findViewById(R.id.vanilla_checkbox);
        boolean hasVanilla = VanillaCheckBox.isChecked();

        // Calculate the price
        int price = calculatePrice(hasWhippedCream, hasChocolate, hasCaramel, hasVanilla);

        // Display the order summary on the screen
        String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate, hasCaramel, hasVanilla);
        displayMessage(message);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Calculates the price of the order.
     * @param addVanilla      is whether or not the user wants the Vanilla topping
     * @param addCaramel      is whether or not the user wants the Caramel topping
     * @param addWhippedCream is whether or not the user wants the the whipped cream topping
     * @param addChocolate    is whether or not the user wants the chocolate topping
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate, boolean addCaramel, boolean addVanilla) {
        int basePrice = 5;

        if (addWhippedCream) {
            basePrice = basePrice + 1;
        }
        if (addChocolate) {
            basePrice = basePrice + 2;
        }
        if (addCaramel) {
            basePrice = basePrice + 3;
        }
        if(addVanilla)  {
            basePrice = basePrice + 3;
        }
        //calculate the total order price by multiplying by quantity
        return quantity * basePrice;
    }


    /**
     * Create summary of the order.
     * @param name            of the customer
     * @param price           of the order
     * @param addWhippedCream is whether or not to add whipped cream to the coffee
     * @param addChocolate    is whether or not to add chocolate to the coffee
     * @param addCaramel      is whether or not to add Caramel to the coffee
     * @param addVanilla      is whether or not to add Vanilla to the coffee
     * @return text summary
     */
    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate, boolean addCaramel, boolean addVanilla) {
        String priceMessage = "Name: " + name;
        priceMessage += "\nAdd whipped cream? " + addWhippedCream;
        priceMessage += "\nAdd chocolate? " + addChocolate;
        priceMessage += "\nAdd caramel? " + addCaramel;
        priceMessage += "\nAdd vanilla? " + addVanilla;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int numberOfCoffees) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffees);
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}