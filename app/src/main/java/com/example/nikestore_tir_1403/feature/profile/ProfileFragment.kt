package com.example.nikestore_tir_1403.feature.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nikestore_tir_1403.common.NikeFragment
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.feature.auth.AuthActivity
import com.example.nikestore_tir_1403.feature.favorites.FavoriteProductsActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment: NikeFragment() {
    val profileViewModel:ProfileViewModel by viewModel()
    lateinit var authBtn : TextView
    lateinit var userNameProfileTv : TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
return inflater.inflate(R.layout.fragment_profile,container,false)   }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authBtn = view.findViewById(R.id.authProfileTv)
        userNameProfileTv = view.findViewById(R.id.email_profile_profile)
        view.findViewById<TextView>(R.id.favorite_btn_profile).setOnClickListener {
            startActivity(Intent(requireContext(),FavoriteProductsActivity::class.java))
        }

    }
    override fun onResume() {
        super.onResume()
       checkAuthState()
    }
private fun checkAuthState(){
if(profileViewModel.isSignedIn){
authBtn.text = getString(R.string.signOut)
    authBtn.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sign_out,0)
userNameProfileTv.text = profileViewModel.userName
    authBtn.setOnClickListener { profileViewModel.signOut()
    checkAuthState()}
}else {
    authBtn.text = getString(R.string.signIn)
    authBtn.setOnClickListener{
        startActivity(Intent(requireContext(),AuthActivity::class.java))
    }
    authBtn.setCompoundDrawablesWithIntrinsicBounds(0,0,R.drawable.ic_sign_in,0)
userNameProfileTv.text = getString(R.string.guest_user)
}
}
}