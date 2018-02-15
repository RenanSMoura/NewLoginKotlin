package moura.silva.com.testnewlogin

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
import java.util.concurrent.TimeUnit

class PhoneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone)

        val mAuth = FirebaseAuth.getInstance()
//
//        mAuth.signInWithCredential(credential).addOnCompleteListener(this@PhoneActivity) { task ->
//            if(task.isSuccessful){
//                Toast.makeText(this@PhoneActivity,"LogSuccessful", Toast.LENGTH_SHORT).show()
//                val user = task.result.user
//                Log.e("LogSuccefull",user.phoneNumber)
//            }else{
//                Log.w("LogNotSuccefull", "signInWithCredential:failure", task.exception)
//                if (task.exception is FirebaseAuthInvalidCredentialsException) {
//                    Log.w("LogNotSuccefull", "signInWithCredential:failure", task.exception);
//                    // The verification code entered was invalid
//                }
//            }
//        }

        mAuth.useAppLanguage()
        lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks




    }
}
