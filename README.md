# VLayout_Demo
阿里巴巴的开源空间VLayout 控件的使用实例 并重写了MarqueeView,即MarqueeView2使其支持kotlin
例如:
<com.will.vlayout_demo.widget.MarqueeView2
                android:id="@+id/marqueeView1"
                android:layout_width="match_parent"
                android:layout_height="20dp"
                android:layout_marginLeft="6dp"
                app:mvAnimDuration="1000"
                app:mvDirection="bottom_to_top"
                app:mvInterval="3000"
                app:mvSingleLine="true"
                app:mvTextColor="#666"
                app:mvTextSize="12sp" />
加入控件

依然要添加依赖库 implementation 'com.sunfusheng:MarqueeView:1.4.1'

自定义文件 MarqueeView2 项目中有
需要注意的是 
   marqueeView1.setOnItemClickListener(MarqueeView.OnItemClickListener { _, textView -> Toast.makeText(applicationContext, textView.text.toString(), Toast.LENGTH_SHORT).show() })
   
   这里使用的OnItemClickListener 依然是原来依赖库中的 MarqueeView 中的OnItemClickListener  不过你也可以在MarqueeView2改成自己定义的OnItemClickListener事件
   
   主要文件是MarqueeView2这个文件
