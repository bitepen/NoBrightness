package com.lighty.nobrightness;

import android.view.Window;
import android.view.WindowManager;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class BrightnessHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        
        // 核心镇压逻辑：只要逮到 LayoutParams 参数，直接把亮度扒光
        XC_MethodHook nukeLogic = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                // 遍历它传进来的所有参数，精准抓取窗口属性
                for (Object arg : param.args) {
                    if (arg instanceof WindowManager.LayoutParams) {
                        WindowManager.LayoutParams lp = (WindowManager.LayoutParams) arg;
                        // 只要企图提亮，强制按死在跟随系统 (-1.0f)
                        if (lp.screenBrightness > 0.0f) {
                            lp.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
                        }
                    }
                }
            }
        };

        try {
            // 第一道鬼门关：拦截常规的 Window.setAttributes
            // 用 hookAllMethods 抓取所有的重载方法，绝不漏网
            XposedBridge.hookAllMethods(Window.class, "setAttributes", nukeLogic);

            // 第二道鬼门关：直捣黄龙，拦截安卓最底层的 WindowManagerGlobal
            Class<?> globalClass = XposedHelpers.findClass("android.view.WindowManagerGlobal", lpparam.classLoader);
            
            // 拦截 updateViewLayout（完美克制支付宝的网络延迟提亮和渐渐变亮的动画）
            XposedBridge.hookAllMethods(globalClass, "updateViewLayout", nukeLogic);
            
            // 拦截 addView（完美克制强行塞进来的付款码弹窗和浮层）
            XposedBridge.hookAllMethods(globalClass, "addView", nukeLogic);

        } catch (Throwable t) {
            // 捕获异常，确保它不会导致支付宝崩溃
        }
    }
}