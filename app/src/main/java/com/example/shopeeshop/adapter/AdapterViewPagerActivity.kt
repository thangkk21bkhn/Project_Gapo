package com.example.shopeeshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopeeshop.R
import com.example.shopeeshop.model.ProductResponse
import com.example.shopeeshop.util.Constant

class AdapterViewPagerActivity( val context : Context) :  RecyclerView.Adapter<AdapterViewPagerActivity.MyViewHolder>(){

    private var listData : List<ProductResponse> = mutableListOf()
    inner class MyViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(R.id.tv_name_product)
        var img = view.findViewById<ImageView>(R.id.img_product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder = MyViewHolder(
        LayoutInflater.from(context).inflate(R.layout.item_view_pager_main,parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentProduct = listData?.get(position)
        holder.name.text = currentProduct?.name + " ($" + currentProduct.price + ")"
        Glide.with(context).load(Constant.urlImage + currentProduct?.image).into(holder.img)

    }

    override fun getItemCount(): Int {
        return listData?.size?:0
    }

    fun setDataForAdapter (data : List<ProductResponse>){
        listData = data
        notifyDataSetChanged()
    }
}