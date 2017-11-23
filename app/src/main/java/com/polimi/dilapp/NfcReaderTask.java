package com.polimi.dilapp;

import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.os.AsyncTask;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static android.content.ContentValues.TAG;

/**
 * Created by Roberta on 23/11/2017.
 */

public class NfcReaderTask extends AsyncTask<Tag, Void, String> {
    //This task is done in background in order to not stop the UI while analyzing the content of the NFC

    @Override
    protected String doInBackground(Tag... parameters) {
        Tag tag = parameters[0];

        Ndef ndef = Ndef.get(tag);
        if (ndef == null) {
            // NDEF is not supported by this Tag.
            return null;
        }

        NdefMessage ndefMessage = ndef.getCachedNdefMessage();

        //here we extract the record associated to the text ndef message in the tag
        NdefRecord[] records = ndefMessage.getRecords();
        for (NdefRecord ndefRecord : records) {
            if (ndefRecord.getTnf() == NdefRecord.TNF_WELL_KNOWN && Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                try {
                    return readText(ndefRecord);
                } catch (UnsupportedEncodingException e) {
                    Log.e(TAG, "Unsupported Encoding", e);
                }
            }
        }

        return null;
    }

    private String readText(NdefRecord record) throws UnsupportedEncodingException {

       //all the parameters are taken from the standards defined by the NFC forum

        byte[] payload = record.getPayload();

        // Get the Text Encoding
        String textEncoding = ((payload[0] & 128) == 0) ? "UTF-8" : "UTF-16";

        // Get the Language Code
        int languageCodeLength = payload[0] & 0063;

        //this is the code of the language alphabet used
        String languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");


        // Get the text from the NFC analyzing its payload
        return new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
    }

   /* @Override
    protected void onPostExecute(String result) {
                if (result != null){

                }
    }
}*/


}
