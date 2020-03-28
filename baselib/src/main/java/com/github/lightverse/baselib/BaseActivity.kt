package com.github.lightverse.baselib

import android.content.res.AssetManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.baselib_base_activity.*

/**
 * todo 沉浸式
 */

open class BaseActivity : AppCompatActivity() {


    private var hasInitBaseContentView = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ensureBaseContentView()
    }


    override fun setContentView(view: View) {
        ensureBaseContentView()
        setRealContentView(view)
    }


    override fun setContentView(layoutResID: Int) {
        ensureBaseContentView()
        setRealContentView(layoutResID)
    }


    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        ensureBaseContentView()
        setRealContentView(view, params)
    }


    override fun addContentView(view: View?, params: ViewGroup.LayoutParams?) {
        ensureBaseContentView()
        addRealContentView(view, params)
    }

    private fun ensureBaseContentView() {
        if (!hasInitBaseContentView) {
            hasInitBaseContentView = true
            super.setContentView(R.layout.baselib_base_activity)
            if (supportBaseToolbar()) {
                initToolbar()
                baselib_base_toolbar.visibility = View.VISIBLE
            } else {
                baselib_base_toolbar.visibility = View.GONE
            }
        }
    }

    private fun setRealContentView(view: View) {
        baselib_base_content.removeAllViews()
        baselib_base_content.addView(view)
    }


    private fun setRealContentView(layoutResID: Int) {
        baselib_base_content.removeAllViews()
        layoutInflater.inflate(layoutResID, baselib_base_content)
    }

    private fun setRealContentView(view: View?, params: ViewGroup.LayoutParams?) {
        baselib_base_content.removeAllViews()
        baselib_base_content.addView(view, params)
    }

    private fun addRealContentView(view: View?, params: ViewGroup.LayoutParams?) {
        baselib_base_content.addView(view, params)
        window.callback.onContentChanged()
    }


    open fun supportBaseToolbar(): Boolean = true

    open fun updateToolbarTitle(title: String) {
        ensureBaseContentView()
        baselib_base_toolbar.title = title
    }


    private fun makeToolbarTitleCenter() {
        val originTitle = baselib_base_toolbar.title
        baselib_base_toolbar.title = "mockTitle" //确保mTitleTextView初始化
        val titleField = baselib_base_toolbar.javaClass.getDeclaredField("mTitleTextView")
        titleField.isAccessible = true
        val titleTextView = titleField.get(baselib_base_toolbar) as TextView
        titleTextView.layoutParams = Toolbar.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).also {
            it.rightMargin = 147
        }
        titleTextView.gravity = Gravity.CENTER
        baselib_base_toolbar.title = originTitle
//        TODO("处理147为DPIUtil.dpi2px()")
    }


    private fun initToolbar() {
        makeToolbarTitleCenter()
        baselib_base_toolbar.setNavigationOnClickListener { onBackPressed() }
    }

}
