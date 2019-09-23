package com.github.lightverse.baselib.router

import android.os.Bundle
import android.os.PersistableBundle
import com.github.lightverse.baselib.BaseActivity
import com.alibaba.android.arouter.launcher.ARouter



class UriProxyActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val uri = intent.data
        ARouter.getInstance().build(uri).navigation()
        finish()
    }
}
