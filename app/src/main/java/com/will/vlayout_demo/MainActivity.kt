package com.will.vlayout_demo

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.vlayout.DelegateAdapter
import com.alibaba.android.vlayout.LayoutHelper
import com.alibaba.android.vlayout.VirtualLayoutManager
import com.alibaba.android.vlayout.layout.GridLayoutHelper
import com.alibaba.android.vlayout.layout.LinearLayoutHelper
import com.bumptech.glide.Glide
import com.sunfusheng.marqueeview.MarqueeView
import com.will.vlayout_demo.widget.MarqueeView2
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var mRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
    }

    private fun initView() {
        mRecyclerView = findViewById(R.id.recycler)
        //初始化
       val virtualLayoutManager:VirtualLayoutManager = VirtualLayoutManager(this)
        mRecyclerView.layoutManager = virtualLayoutManager

        //设置回收复用池大小，（如果一屏内相同类型的 View 个数比较多，需要设置一个合适的大小，防止来回滚动时重新创建 View)
        val viewPool : RecyclerView.RecycledViewPool = RecyclerView.RecycledViewPool()
        mRecyclerView.setRecycledViewPool(viewPool)
        viewPool.setMaxRecycledViews(0,10)

        //轮播图布局
        val bannerAdapter :BaseDelegateAdapter = object :BaseDelegateAdapter(this,
            LinearLayoutHelper(),R.layout.vlayout_banner,1,Config.BANNER_VIEW_TYPE){
            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                val arrayList: MutableList<String?> =
                    ArrayList()
                arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602515491509&di=8bb92ba38754278fbf0dd991641cd31d&imgtype=0&src=http%3A%2F%2Fimg.ivsky.com%2Fimg%2Fbizhi%2Fpre%2F201802%2F03%2Fzhang_li.jpg")
                arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602516206414&di=658f1ba6009cc328934df406b5db89a2&imgtype=0&src=http%3A%2F%2Fdingyue.nosdn.127.net%2FPh0tJVzsz29kgW3m1FmhRuyAx3CyCD18GWT2S6crDuqsc1528973905800.jpeg")
                arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602515679580&di=d818573734a26d5e0df956a551c270e9&imgtype=0&src=http%3A%2F%2Fwww.11job.com%2Farticle%2Fuploadfile%2F2019%2F1213%2F20191213091637539.jpg")
                arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602515818929&di=7a2d0c2c6732bf4051b03b5be7650f47&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fq_mini%2Cc_zoom%2Cw_640%2Fimages%2F20170822%2F8e52369285624e5d9c0cb8039831a498.jpeg")
                arrayList.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2369298971,3248602409&fm=26&gp=0.jpg")
                arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602515995056&di=c33c30a1b463a8c16880a43b90c68b74&imgtype=0&src=http%3A%2F%2Fent.hnr.cn%2Fjdt%2F201305%2FW020130503349223736037.jpg")
                // 绑定数据
                val mBanner = holder.getView<Banner>(R.id.banner)
                //设置banner样式
                mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                //设置图片加载器
                mBanner.setImageLoader(GlideImageLoader())
                //设置图片加载器
                mBanner.setImages(arrayList)
                //设置banner动画效果
                mBanner.setBannerAnimation(Transformer.DepthPage)
                //设置标题集合（当banner样式有显示title时）
                //        mBanner.setBannerTitles(titles);
                //设置自动轮播，默认为true
                mBanner.isAutoPlay(true)
                //设置轮播时间
                mBanner.setDelayTime(5000)
                //设置指示器位置（当banner模式中有指示器时）
                mBanner.setIndicatorGravity(BannerConfig.RIGHT)
                //banner设置方法全部调用完毕时最后调用
                mBanner.start()
                mBanner.setOnBannerListener { position ->
                    Toast.makeText(
                        this@MainActivity.applicationContext,
                        "banner点击了$position",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        //网格布局
        val gridLayoutHelper = GridLayoutHelper(5)
        //设置内边距
        gridLayoutHelper.setPadding(0,16,0,0)
        //控制子元素之间的垂直边距
        gridLayoutHelper.vGap = 10
        //控制子元素之间的水平边距
        gridLayoutHelper.hGap = 0
        val menuAdapter = object :BaseDelegateAdapter(this,gridLayoutHelper,R.layout.vlayout_menu,10,Config.MENU_VIEW_TYPE){
            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                holder.setText(R.id.tv_menu_title_home,Config.ITEM_NAMES[position] + "")
                holder.setImageResource(R.id.iv_menu_home,Config.IMG_URLS[position])
                holder.getView<View>(R.id.ll_menu_home).setOnClickListener {
                    Toast.makeText(applicationContext,Config.ITEM_NAMES[position],Toast.LENGTH_SHORT).show()
                }
            }

        }

        //跑马灯布局
        val newsAdapter = object : BaseDelegateAdapter(this,LinearLayoutHelper(),R.layout.vlayout_news,1,Config.NEWS_VIEW_TYPE){
            override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                val marqueeView1 : MarqueeView2<String> = holder.getView<MarqueeView2<String>>(R.id.marqueeView1)
                val marqueeView2 : MarqueeView2<String> = holder.getView<MarqueeView2<String>>(R.id.marqueeView2)

                val info1 : MutableList<String> = mutableListOf()
                info1.add("这个是用来搞笑的，不要在意这写小细节！")
                info1.add("用来搞笑的，在意这写小细节！")

                val info2 : MutableList<String> = mutableListOf()
                info2.add("搞笑使用懂得的滴，细节才能决定是否成功！")
                info2.add("啦啦啦啦，我就是来搞笑的！")

                marqueeView1.startWithList(info1)
                marqueeView2.startWithList(info2)

                //在代码里设置自己的动画
                marqueeView1.startWithList(info1,R.anim.anim_bottom_in,R.anim.anim_top_out)
                marqueeView2.startWithList(info2,R.anim.anim_bottom_in,R.anim.anim_top_out)
                
                marqueeView1.setOnItemClickListener(MarqueeView.OnItemClickListener { _, textView -> Toast.makeText(applicationContext, textView.text.toString(), Toast.LENGTH_SHORT).show() })

                marqueeView2.setOnItemClickListener(MarqueeView.OnItemClickListener { _, textView -> Toast.makeText(applicationContext, textView.text.toString(), Toast.LENGTH_SHORT).show() })
            }

        }

        val delegateAdapter = DelegateAdapter(virtualLayoutManager,true)

        delegateAdapter.addAdapter(bannerAdapter)
        delegateAdapter.addAdapter(menuAdapter)
        delegateAdapter.addAdapter(newsAdapter)

        //这里我就循环item 实际项目中不同的ITEM 继续往下走就行
        for (i in Config.ITEM_URL.indices){
            //item1 title
            var finalI = i
            val titleAdapter : BaseDelegateAdapter = object : BaseDelegateAdapter(this,LinearLayoutHelper(),R.layout.vlayout_title,1,Config.TITLE_VIEW_TYPE){
                override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                    holder.setImageResource(R.id.iv,Config.ITEM_URL[finalI])

                    val imageView : ImageView = holder.getView(R.id.iv)
                    imageView.setOnClickListener {
                        Toast.makeText(applicationContext,"imageView :$position",Toast.LENGTH_SHORT).show()
                    }
                }

            }

            delegateAdapter.addAdapter(titleAdapter)
            //item1 gird
            val gridLayoutHelper1 : GridLayoutHelper = GridLayoutHelper(2)
            gridLayoutHelper1.setMargin(0,0,0,0)
            gridLayoutHelper1.setPadding(0,0,0,0)
            gridLayoutHelper1.vGap = 0 //控制子元素之间的垂直间距
            gridLayoutHelper1.hGap = 0 //控制子元素之间的水平间距
            gridLayoutHelper1.bgColor = Color.WHITE
            gridLayoutHelper1.setAutoExpand(true) //是否自动填充空白区域

            val gridAdapter:BaseDelegateAdapter = object : BaseDelegateAdapter(this,gridLayoutHelper1,R.layout.vlayout_grid,4,Config.GRID_VIEW_TYPE){
                override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
                    val item : Int = Config.GRID_URL[position]

                    val iv : ImageView = holder.getView(R.id.iv)

                    Glide.with(applicationContext).load(item)
                        .into(iv)
                    iv.setOnClickListener {
                        Toast.makeText(applicationContext,"item:$position",Toast.LENGTH_SHORT).show()
                    }
                }

            }

            delegateAdapter.addAdapter(gridAdapter)
        }
        mRecyclerView.adapter = delegateAdapter

    }

    class BannerAdapter(private val context: Context) : DelegateAdapter.Adapter<BaseViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
            return BaseViewHolder(LayoutInflater.from(context).inflate(R.layout.vlayout_banner,parent,false))
        }

        override fun getItemCount(): Int {
            return 1
        }

        override fun onCreateLayoutHelper(): LayoutHelper {
            return LinearLayoutHelper()
        }

        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            val arrayList: MutableList<String?> =
                ArrayList()
            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602515491509&di=8bb92ba38754278fbf0dd991641cd31d&imgtype=0&src=http%3A%2F%2Fimg.ivsky.com%2Fimg%2Fbizhi%2Fpre%2F201802%2F03%2Fzhang_li.jpg")
            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602516206414&di=658f1ba6009cc328934df406b5db89a2&imgtype=0&src=http%3A%2F%2Fdingyue.nosdn.127.net%2FPh0tJVzsz29kgW3m1FmhRuyAx3CyCD18GWT2S6crDuqsc1528973905800.jpeg")
            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602515679580&di=d818573734a26d5e0df956a551c270e9&imgtype=0&src=http%3A%2F%2Fwww.11job.com%2Farticle%2Fuploadfile%2F2019%2F1213%2F20191213091637539.jpg")
            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602515818929&di=7a2d0c2c6732bf4051b03b5be7650f47&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fq_mini%2Cc_zoom%2Cw_640%2Fimages%2F20170822%2F8e52369285624e5d9c0cb8039831a498.jpeg")
            arrayList.add("https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=2369298971,3248602409&fm=26&gp=0.jpg")
            arrayList.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1602515995056&di=c33c30a1b463a8c16880a43b90c68b74&imgtype=0&src=http%3A%2F%2Fent.hnr.cn%2Fjdt%2F201305%2FW020130503349223736037.jpg")
            // 绑定数据
            val mBanner = holder.getView<Banner>(R.id.banner)
            //设置banner样式
            mBanner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            //设置图片加载器
            mBanner.setImageLoader(GlideImageLoader())
            //设置图片加载器
            mBanner.setImages(arrayList)
            //设置banner动画效果
            mBanner.setBannerAnimation(Transformer.DepthPage)
            //设置标题集合（当banner样式有显示title时）
            //        mBanner.setBannerTitles(titles);
            //设置自动轮播，默认为true
            mBanner.isAutoPlay(true)
            //设置轮播时间
            mBanner.setDelayTime(5000)
            //设置指示器位置（当banner模式中有指示器时）
            mBanner.setIndicatorGravity(BannerConfig.RIGHT)
            //banner设置方法全部调用完毕时最后调用
            mBanner.start()
            mBanner.setOnBannerListener { position ->
                Toast.makeText(
                    context.applicationContext,
                    "banner点击了$position",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }
}