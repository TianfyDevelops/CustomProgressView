自定义ProgressView
===============
  非常简单的自定义进度条，支持自定义进度条的颜色，边框的颜色，圆角值。


使用方式：
-----



Step 1. 在Project的build.gradle文件中添加

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

Step 2. 在App的build.gradle文件中添加


    dependencies {
	        implementation 'com.github.TianfyDevelops:CustomProgressView:v1.0'
	}


Step 3. 在布局文件中引入

    <com.tianfy.progressview.ProgressView
        android:id="@+id/progress_view"
        android:layout_width="wrap_content"
        app:progressView_solidColor="@color/colorAccent"
        app:progressView_storkeColor="@color/colorPrimary"
        app:progressView_radius="5dp"
        app:progressView_padding="4dp"
        app:progressView_duration="15000"
        android:layout_height="wrap_content" />

属性说明
----

		<!--指定进度条的颜色-->
        app:progressView_solidColor="@color/colorAccent"
		<!--指定边框的颜色-->
        app:progressView_storkeColor="@color/colorPrimary"
		<!--指定圆角值-->
        app:progressView_radius="5dp"
		<!--指定边框和进度条的padding值-->
        app:progressView_padding="4dp"
		<!--指定动画持续时间-->
        app:progressView_duration="15000"

代码实现
----
> 动画效果很简单，使用ObjectAnimator属性动画来实现，这个官方提供一些Api使用，具体可以查看官方文档。如果以后需要实现更复杂的动画，可以以此为例进行自定义。这里我会对基础自定义View动画实现简单的说明，具体说明在代码注释。如果你们需要的效果跟我的类似，你可以直接把ProgressView文件拷贝下来使用，需要的属性不够用的话可以直接在里面修改添加。

代码使用
----

       ProgressView progressView = findViewById(R.id.progress_view);
	    //设置进度条颜色
        progressView.setSloidColor(R.color.colorAccent);
        //设置边框颜色
        progressView.setStorkeColor(R.color.colorPrimary);
        //设置圆角
        progressView.setRadius(4);
        //设置padding值
        progressView.setPadding(4);
        //设置动画持续时间
        progressView.setDuration(2000);
        //开始动画
        progressView.startAnim();
        //结束动画（不保留当前进度）
        progressView.endAnim();
        //取消动画（会保留当前动画进度）
        progressView.cancelAnim();
