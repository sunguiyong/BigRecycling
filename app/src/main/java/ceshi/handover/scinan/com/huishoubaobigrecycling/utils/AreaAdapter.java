//package ceshi.handover.scinan.com.huishoubaobigrecycling.utils;
//
//import android.view.View;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.chad.library.adapter.base.BaseViewHolder;
//import com.google.gson.Gson;
//
//import java.util.List;
//
//import ceshi.handover.scinan.com.huishoubaobigrecycling.R;
//import ceshi.handover.scinan.com.huishoubaobigrecycling.bean.request_result_info;
//
//
//
///**
// * Created by Administrator on 2018/9/3 0003.
// */
//
//public class AreaAdapter extends BaseQuickAdapter<request_result_info.MessageBean.PricesBean,BaseViewHolder> {
//
//    public AreaAdapter(List<request_result_info.MessageBean.PricesBean> data) {
//        super(R.layout.recycle_item, data);
//    }
//
//    @Override
//    protected void convert(BaseViewHolder helper, request_result_info.MessageBean.PricesBean item) {
//        String name=item.getName();
//        if (name.equals("纸")){
//            double  count=item.getCount();
//          String str  =String.format("%.2f",count);
//         helper.setText(R.id.tv_name,item.getName())
//        .setText(R.id.yinliao_number,str+"千克")
//        .setText(R.id.yinliao_danjia,item.getUnit_price()+"千克")
//        .setText(R.id.yinliao_xiaoji,item.getTotal()+"元");}else if (name.equals("饮料瓶")){
//            helper.setText(R.id.tv_name,item.getName())
//                    .setText(R.id.yinliao_number,item.getCount()+"个")
//                    .setText(R.id.yinliao_danjia,item.getUnit_price()+"元/个")
//                    .setText(R.id.yinliao_xiaoji,item.getTotal()+"元");
//        }else if (name.equals("电子")){
//               helper.setVisible(R.id.tv_xiaoji, false).setVisible(R.id.tv_danjia,false);
//        }
//    }
//
//}
