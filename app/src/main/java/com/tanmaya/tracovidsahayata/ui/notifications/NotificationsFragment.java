package com.tanmaya.tracovidsahayata.ui.notifications;

import android.icu.util.Currency;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.shreyaspatil.EasyUpiPayment.EasyUpiPayment;
import com.shreyaspatil.EasyUpiPayment.listener.PaymentStatusListener;
import com.shreyaspatil.EasyUpiPayment.model.PaymentApp;
import com.shreyaspatil.EasyUpiPayment.model.TransactionDetails;
import com.tanmaya.tracovidsahayata.HomeActivity;
import com.tanmaya.tracovidsahayata.R;

import me.abhinay.input.CurrencyEditText;
import me.abhinay.input.CurrencySymbols;

public class NotificationsFragment extends Fragment implements PaymentStatusListener {

    private NotificationsViewModel notificationsViewModel;
    CurrencyEditText etInput;
    private Button payButton;
    private EasyUpiPayment mEasyUpiPayment;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        payButton = root.findViewById(R.id.button_pay);
        etInput = (CurrencyEditText) root.findViewById(R.id.etInput);
        etInput.setCurrency(CurrencySymbols.INDIA);
        etInput.setDelimiter(false);
        etInput.setSpacing(false);
        etInput.setDecimals(true);
        //Make sure that Decimals is set as false if a custom Separator is used
        etInput.setSeparator(".");
        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay();
            }
        });
        return root;
    }

    private void pay() {
        String amount = etInput.getText().toString();
        final EasyUpiPayment easyUpiPayment = new EasyUpiPayment.Builder()
                .with(getActivity())
                .setPayeeVpa("pmcares@sbi")
                .setPayeeName("PM CARES")
                .setTransactionId("20190603022401")
                .setTransactionRefId("0120192019060302240")
                .setDescription("Donation")
                .setAmount(amount)
                .build();

        mEasyUpiPayment.setPaymentStatusListener(NotificationsFragment.this);
        easyUpiPayment.setDefaultPaymentApp(PaymentApp.GOOGLE_PAY);
        // Check if app exists or not
        if (mEasyUpiPayment.isDefaultAppExist()) {
            onAppNotFound();
            return;
        }
        // END INITIALIZATION

        // START PAYMENT
        mEasyUpiPayment.startPayment();
    }

    public void onTransactionCompleted(TransactionDetails transactionDetails) {
        // Transaction Completed
        Log.d("TransactionDetails", transactionDetails.toString());
    }


    public void onTransactionSuccess() {
        // Payment Success
        Toast.makeText(getActivity(), "Success", Toast.LENGTH_SHORT).show();
    }


    public void onTransactionSubmitted() {
        // Payment Pending
        Toast.makeText(getActivity(), "Pending | Submitted", Toast.LENGTH_SHORT).show();
    }

    public void onTransactionFailed() {
        // Payment Failed
        Toast.makeText(getActivity(), "Failed", Toast.LENGTH_SHORT).show();
    }

    public void onTransactionCancelled() {
        // Payment Cancelled by User
        Toast.makeText(getActivity(), "Cancelled", Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onAppNotFound() {
        Toast.makeText(getActivity(), "App Not Found", Toast.LENGTH_SHORT).show();
    }
}

