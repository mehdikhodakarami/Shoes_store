package com.example.nikestore_tir_1403.feature.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.common.NikeFragment
import com.example.nikestore_tir_1403.common.exception.NikeException
import com.example.nikestore_tir_1403.common.observer.NikeCompletableObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginFragment : Fragment() {
    val authViewModel : AuthViewModel by viewModel()

    val compositeDisposable = CompositeDisposable()
    lateinit var emailEt:EditText
    lateinit var passEt:EditText
    lateinit var loginBtn : Button
    lateinit var signUpLink : Button
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loginBtn = view.findViewById<Button>(R.id.loginBtn)
        emailEt = view.findViewById(R.id.email_login_et )
        passEt  = view.findViewById(R.id.pass_login_et)
        signUpLink = view.findViewById(R.id.signUp_link_btn)
loginBtn.setOnClickListener {
    authViewModel.login(emailEt.text.toString(),passEt.text.toString()).subscribeOn(Schedulers.io()).
            observeOn(AndroidSchedulers.mainThread()).subscribe(object : NikeCompletableObserver(compositeDisposable){
        override fun onComplete() {
            requireActivity().finish()
        }

    })
}
        signUpLink.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction().apply {
                replace( R.id.fragment_container,SignUpFragment()).commit()
            }
        }
    }
    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
fun getErrorFromEvent(nikeException: NikeException){
        if (nikeException.serverMessage.isNullOrEmpty()){
            Toast.makeText(context,"خطای نا مشخص!",Toast.LENGTH_SHORT).show()
        }
    Toast.makeText(context,nikeException.serverMessage,Toast.LENGTH_SHORT).show()
}
    override fun onStop() {
        compositeDisposable.clear()
        EventBus.getDefault().unregister(this)
        super.onStop()
    }




}