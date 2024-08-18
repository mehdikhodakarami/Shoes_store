package com.example.nikestore_tir_1403.common

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.LAYOUT_DIRECTION_RTL
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nikestore_tir_1403.R
import com.example.nikestore_tir_1403.common.exception.NikeException
import com.example.nikestore_tir_1403.feature.auth.AuthActivity
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

abstract class NikeFragment:Fragment(), NikeView {

    override val viewContext: Context?
        get() = context
    override val rootview: CoordinatorLayout?
        get() = view as CoordinatorLayout?



}
abstract class NikeActivity:AppCompatActivity(), NikeView {
    override val rootview: CoordinatorLayout?
        get() {val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        if (viewGroup is CoordinatorLayout){
        return viewGroup
        }
        else{viewGroup.children.forEach { if (it is CoordinatorLayout) return it }
            throw IllegalAccessException("root view should be instance of coordinator")
        }}
    override val viewContext: Context?
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState )
//        EventBus.getDefault().register(this)
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
    }

    override fun onDestroy() {
//        EventBus.getDefault().unregister(this)

        super.onDestroy()

    }

}
interface NikeView{

    val viewContext:Context?
    val rootview : CoordinatorLayout?
    fun setProgressIndicator(mustShow:Boolean){
rootview?.let {
    viewContext?.let {context->
        var loadingView = it.findViewById<View>(R.id.loading_view)
        if ( mustShow&&loadingView == null){
            loadingView = LayoutInflater.from(context).inflate(
                R.layout.view_loading,
            it,false)
            it.addView(loadingView)
        }
        loadingView?.visibility = if (mustShow) View.VISIBLE else View.GONE
    }

    }



    }


    fun showEmptyState(layoutResId:Int):View?{
        rootview?.let {rootview ->
            viewContext?.let {
                var emptyStates = rootview.findViewById<View>(R.id.emptyStateRootView)
                if(emptyStates == null){
                    emptyStates= LayoutInflater.from(it).inflate(layoutResId,rootview,false)
                    rootview.addView(emptyStates)
                }
                emptyStates.visibility = View.VISIBLE
                return emptyStates

            }
        }
        return null

    }
@Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
fun showError(nikeException: NikeException){
viewContext?.let {
    when(nikeException.type){
    NikeException.Type.SIMPLE -> showsnackBar(nikeException.serverMessage?:it.getString(nikeException.userFriendlyMessage))

        NikeException.Type.AUTH->{
            if(InAuthCounter.counerStartActivity == 0){
                it.startActivity(Intent( it,AuthActivity::class.java))
                InAuthCounter.counerStartActivity =1
            }






        }
        else ->  { Toast.makeText(it,nikeException.serverMessage,Toast.LENGTH_SHORT).show()}

}

}    }
    fun showsnackBar(message:String,length:Int = Snackbar.LENGTH_SHORT){
        rootview?.let { Snackbar.make(it,message,length).show() }
    }
}
abstract class NikeViewModel: ViewModel() {
    val progressLiveData = MutableLiveData<Boolean>()
val compositeDisposable = CompositeDisposable()
    override fun onCleared() {
compositeDisposable.clear()
        super.onCleared()
    }
}
object InAuthCounter{
    var counerStartActivity = 0
}