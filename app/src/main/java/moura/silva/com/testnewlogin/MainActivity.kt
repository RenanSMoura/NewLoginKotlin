package moura.silva.com.testnewlogin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.hbb20.CountryCodePicker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val firebaseAuthCallback = setUpFirebaseAuthCallbacks()


        ccp.registerCarrierNumberEditText(editTextNumber)

        ccp.setPhoneNumberValidityChangeListener {
            CountryCodePicker.PhoneNumberValidityChangeListener { isValidNumber ->
                if(isValidNumber){
                    Log.v("isValidNumber","true")
                }else{
                    Log.v("isValidNumber","false")
                }
            }
        }

        sendSmsButton.setOnClickListener{
            if(ccp.isValidFullNumber) {
                if(!allowSmsButton.isChecked){
                    PhoneAuthProvider.getInstance().verifyPhoneNumber(
                            ccp.fullNumberWithPlus,
                            30,
                            TimeUnit.SECONDS,
                            this@MainActivity,
                            firebaseAuthCallback)
                }
            }
        }

        editTextNumber.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if(s.toString().startsWith("0")) editTextNumber.setText("")

            }
        })
    }



    private fun setUpFirebaseAuthCallbacks() : PhoneAuthProvider.OnVerificationStateChangedCallbacks {
        return object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential?) {
                Log.e("Credential", credential.toString())
            }

            override fun onVerificationFailed(exception: FirebaseException?) {
                Log.e("VerificationFailed", exception.toString())
            }

            override fun onCodeSent(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken?) {
                super.onCodeSent(verificationId, token)
                Log.e("onCodeSent", verificationId)
                Log.e("onCodeSent", token.toString())
            }
        }
    }

}
