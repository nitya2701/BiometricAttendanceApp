package com.example.biometricattendance;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.biometric.BiometricPrompt.PromptInfo;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import java.util.concurrent.Executor;

public class BiometricAct {
    private BiometricManager biometricManager;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private PromptInfo promptInfo;

    public boolean checkCompatibility(Context context)
    {
        biometricManager = androidx.biometric.BiometricManager.from(context);

        if(biometricManager.canAuthenticate()==biometricManager.BIOMETRIC_SUCCESS)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public void setBiometricPrompt(Context context)
    {
        executor = ContextCompat.getMainExecutor(context);
        biometricPrompt = new androidx.biometric.BiometricPrompt((FragmentActivity) context, executor, new androidx.biometric.BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errCode, CharSequence errString)
            {
                super.onAuthenticationError(errCode, errString);
                Toast.makeText(context, "Authentication Error", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);

                Toast.makeText(context, "Authentication Succeeded", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, DiaryActivity.class);
                context.startActivity(intent);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();

                Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric Login")
                .setSubtitle("Login using your fingerprint")
                .setNegativeButtonText("Cancel")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }
}
