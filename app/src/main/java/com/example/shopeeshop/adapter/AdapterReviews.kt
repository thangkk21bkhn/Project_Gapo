package com.example.shopeeshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopeeshop.R
import com.example.shopeeshop.model.InforReviewPerson
import com.example.shopeeshop.model.ProductResponse
import com.example.shopeeshop.util.Constant

class AdapterReviews( val context : Context) :  RecyclerView.Adapter<AdapterReviews.ViewHolderComment>(){

    var listData  = mutableListOf<InforReviewPerson>()
    inner class ViewHolderComment(view : View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(R.id.tv_name)
        var ratingBar = view.findViewById<RatingBar>(R.id.rating_bar)
        var date = view.findViewById<TextView>(R.id.tv_date)
        var comment = view.findViewById<TextView>(R.id.tv_comment)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderComment = ViewHolderComment(
        LayoutInflater.from(context).inflate(R.layout.item_review,parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolderComment, position: Int) {
        val currentProduct = listData?.get(position)
        holder.name.text = currentProduct?.name
        holder.ratingBar.rating = currentProduct.rating.toFloat()
        holder.date.text = currentProduct.updatedAt.subSequence(0,1)
        holder.comment.text = currentProduct.comment
    }

    override fun getItemCount(): Int {
        return listData?.size
    }

    fun setDataForAdapter (data : MutableList<InforReviewPerson>){
        listData = data
        notifyDataSetChanged()
    }
}