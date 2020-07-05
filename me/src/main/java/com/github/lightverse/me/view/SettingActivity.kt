package com.github.lightverse.me.view

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.preference.Preference
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.github.lightverse.baselib.BaseActivity
import com.github.lightverse.baselib.preference.BasePreferenceFragment
import com.github.lightverse.me.R
import com.github.lightverse.me.databinding.MeSettingMainBinding

@Route(path = "/me/setting")
class SettingActivity : BaseActivity(){

    private val meSettingMainBinding:MeSettingMainBinding by lazy {
        MeSettingMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(meSettingMainBinding.root)
        supportFragmentManager.beginTransaction().add(R.id.container,SettingFragment()).commitAllowingStateLoss()
        updateToolbarTitle("设置")
    }

}

class SettingFragment : BasePreferenceFragment(){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setDivider(ResourcesCompat.getDrawable(context!!.resources,R.drawable.me_divider,context!!.theme))
        setDividerHeight(2)
    }
    override fun onCreatePreferences(p0: Bundle?, p1: String?) {
        addPreferencesFromResource(R.xml.me_setting_fragment)
    }

    override fun onPreferenceTreeClick(preference: Preference?): Boolean {
////        val findPreference = findPreference("custom_preference")
////        findPreference.isVisible = true
////        findPreference.order = 1
//        val switchPreference = SwitchPreferenceCompat(context, null)
//        switchPreference.title = "hello"
//        preferenceScreen.addPreference(switchPreference)
        return super.onPreferenceTreeClick(preference)
    }

}