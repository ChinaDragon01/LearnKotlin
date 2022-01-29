package com.test.learnkotlin.ui.fragment

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.text.PrecomputedTextCompat
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/*
https://developer.android.com/topic/libraries/architecture/coroutines#lifecyclescope

LifecycleScope
为每个 Lifecycle 对象定义了 LifecycleScope。在此范围内启动的任何协程会在 Lifecycle 被销毁时取消。
您可以通过 lifecycle.coroutineScope 或 lifecycleOwner.lifecycleScope 属性访问 Lifecycle 的 CoroutineScope。

以下示例演示了如何使用 lifecycleOwner.lifecycleScope 异步创建预计算文本：

PrecomputedTextCompat用法及原理
https://blog.csdn.net/ecjtuhq/article/details/104366104


 */
//class MyFragment : Fragment() {
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        val textView = TextView(context)
//        viewLifecycleOwner.lifecycleScope.launch {
//            val parms = TextViewCompat.getTextMetricsParams(textView)
//            val precomputedText = withContext(Dispatchers.Default) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
//                    PrecomputedTextCompat.create("hello", parms)
//                } else {
//                    TODO("VERSION.SDK_INT < P")
//                }
//            }
//        }
//    }
//}