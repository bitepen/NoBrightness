package com.lighty.nobrightness;

import android.view.Window;
import android.view.WindowManager;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class BrightnessHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        
        // 拦截 Window.setAttributes 方法
        XposedHelpers.findAndHookMethod(
                Window.class.getName(),
                lpparam.classLoader,
                "setAttributes",
                WindowManager.LayoutParams.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        WindowManager.LayoutParams layoutParams = (WindowManager.LayoutParams) param.args[0];
                        
                        // 如果 App 请求了特定亮度 (大于0)，则强制将其改回 -1.0f (跟随系统亮度)
                        if (layoutParams != null && layoutParams.screenBrightness > 0.0f) {
                            layoutParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
                        }
                    }
                }
        );
    }
}