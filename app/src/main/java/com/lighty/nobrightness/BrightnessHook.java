package com.lighty.nobrightness;

import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class BrightnessHook implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        
        // 精准狙击 Activity 的生命周期方法：onResume
        // 也就是页面加载完毕，准备展现在你眼前的最后一刻
        XposedHelpers.findAndHookMethod(
                "android.app.Activity",
                lpparam.classLoader,
                "onResume",
                new XC_MethodHook() {
                    // 注意：这里用的是 afterHookedMethod (在 App 自己的逻辑执行完之后介入)
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        
                        // 1. 抓取当前正在运行的 Activity 实例
                        Activity activity = (Activity) param.thisObject;
                        if (activity == null) return;

                        // 2. 拿到这个页面的 Window (窗口)
                        Window window = activity.getWindow();
                        if (window == null) return;

                        // 3. 获取作者所说的 Display Flags (窗口属性参数)
                        WindowManager.LayoutParams layoutParams = window.getAttributes();
                        
                        // 4. 检查 App 是不是偷偷把亮度拉高了
                        // BRIGHTNESS_OVERRIDE_NONE 就是 -1.0f (即作者说的 Normal 跟随系统)
                        if (layoutParams.screenBrightness != WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE) {
                            
                            // 强制拨回 Normal
                            layoutParams.screenBrightness = WindowManager.LayoutParams.BRIGHTNESS_OVERRIDE_NONE;
                            
                            // 最关键的一步：把修改后的 Normal 属性强行覆盖回去，造成既定事实
                            window.setAttributes(layoutParams);
                        }
                    }
                }
        );
    }
}