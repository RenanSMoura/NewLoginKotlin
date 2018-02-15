package moura.silva.com.testnewlogin

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mAuth = FirebaseAuth.getInstance()
        mAuth.useAppLanguage()
        lateinit var mCallbacks: OnVerificationStateChangedCallbacks


        mCallbacks = object :PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                Toast.makeText(this@MainActivity,"credential" + credential.toString(),Toast.LENGTH_SHORT).show()
                Log.e("Credential", credential.toString())


                mAuth.signInWithCredential(credential).addOnCompleteListener(this@MainActivity) { task ->
                    if(task.isSuccessful){
                        Toast.makeText(this@MainActivity,"LogSuccessful",Toast.LENGTH_SHORT).show()
                        val user = task.result.user
                        Log.e("LogSuccefull",user.phoneNumber)
                    }else{
                        Log.w("LogNotSuccefull", "signInWithCredential:failure", task.exception)
                        if (task.exception is FirebaseAuthInvalidCredentialsException) {
                            Log.w("LogNotSuccefull", "signInWithCredential:failure", task.exception);
                            // The verification code entered was invalid
                        }
                    }
                }
            }

            override fun onVerificationFailed(exception: FirebaseException) {

                if(exception is FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(this@MainActivity,"Invalid Number" ,Toast.LENGTH_SHORT).show()
                }else if ( exception is FirebaseTooManyRequestsException){
                    Toast.makeText(this@MainActivity," too many requests",Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCodeSent(verificationId: String?, token: PhoneAuthProvider.ForceResendingToken?) {
                Toast.makeText(this@MainActivity, "Verification ID$verificationId / Token $token",Toast.LENGTH_SHORT).show()
                Log.e("Deu certo", verificationId + " / " + token)

            }

            override fun onCodeAutoRetrievalTimeOut(verificationId: String?) {
                super.onCodeAutoRetrievalTimeOut(verificationId)
            }
        }



        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                "+5511993086522",
                60,
                TimeUnit.SECONDS,
                this@MainActivity,
                mCallbacks)

        loginButton.setOnClickListener{

            startActivity(Intent(this@MainActivity,PhoneActivity::class.java))
        }


    }
}
