package com.tanmaya.tracovidsahayata.ui.notifications;

import android.content.Intent;
import android.icu.util.Currency;
import android.net.Uri;
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

import com.tanmaya.tracovidsahayata.R;

import me.abhinay.input.CurrencyEditText;
import me.abhinay.input.CurrencySymbols;

public class NotificationsFragment extends Fragment  {

    private NotificationsViewModel notificationsViewModel;
    CurrencyEditText etInput;
    private Button payButton;
    private String TAG ="NotificationsFragment";
    String payeeAddress = "pmcares@sbi";
    String payeeName = "PM CARES";
    String transactionNote = "Donation";
    String amount;
    String currencyUnit = "INR";

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
                amount=etInput.getText().toString();
                Uri uri = Uri.parse("upi://pay?pa="+payeeAddress+"&pn="+payeeName+"&tn="+transactionNote+
                        "&am="+amount+"&cu="+currencyUnit);
                Log.d(TAG, "onClick: uri: "+uri);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivityForResult(intent,1);
            }
        });
        return root;
    }

    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d(TAG, "onActivityResult: requestCode: "+requestCode);
        Log.d(TAG, "onActivityResult: resultCode: "+resultCode);
        //txnId=UPI20b6226edaef4c139ed7cc38710095a3&responseCode=00&ApprovalRefNo=null&Status=SUCCESS&txnRef=undefined
        //txnId=UPI608f070ee644467aa78d1ccf5c9ce39b&responseCode=ZM&ApprovalRefNo=null&Status=FAILURE&txnRef=undefined

        if(data!=null) {
            Log.d(TAG, "onActivityResult: data: " + data.getStringExtra("response"));
            String res = data.getStringExtra("response");
            String search = "SUCCESS";
            if (res.toLowerCase().contains(search.toLowerCase())) {
                Toast.makeText(getActivity(), "Payment Successful", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Payment Failed", Toast.LENGTH_SHORT).show();
            }
        }

    }
}

