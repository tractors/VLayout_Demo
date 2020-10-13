package com.will.vlayout_demo

interface Config {
    //不同item必须不同的viewtype
    companion object{
        const val BANNER_VIEW_TYPE = 1
        const val MENU_VIEW_TYPE = 2
        const val NEWS_VIEW_TYPE = 3
        const val TITLE_VIEW_TYPE = 4
        const val GRID_VIEW_TYPE = 5

        val IMG_URLS: IntArray = intArrayOf(R.mipmap.ic_tian_mao, R.mipmap.ic_ju_hua_suan, R.mipmap.ic_tian_mao_guoji, R.mipmap.ic_waimai, R.mipmap.ic_chaoshi, R.mipmap.ic_voucher_center, R.mipmap.ic_travel, R.mipmap.ic_tao_gold, R.mipmap.ic_auction, R.mipmap.ic_classify)
        val ITEM_NAMES = arrayOf("天猫", "聚划算", "天猫国际", "外卖", "天猫超市", "充值中心", "飞猪旅行", "领金币", "拍卖", "分类")
        val GRID_URL = intArrayOf(
            R.mipmap.flashsale1,
            R.mipmap.flashsale2,
            R.mipmap.flashsale3,
            R.mipmap.flashsale4)
        val ITEM_URL = intArrayOf(R.mipmap.item1, R.mipmap.item2, R.mipmap.item3, R.mipmap.item4, R.mipmap.item5)
    }
}