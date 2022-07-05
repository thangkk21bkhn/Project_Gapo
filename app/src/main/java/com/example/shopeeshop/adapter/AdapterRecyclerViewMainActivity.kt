package com.example.shopeeshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.shopeeshop.R
import com.example.shopeeshop.model.ListProductResponse
import com.example.shopeeshop.model.ProductResponse
import com.example.shopeeshop.util.Constant

class AdapterRecyclerViewMainActivity(val clickProduct: ClickProduct, var data : List<ProductResponse>, val context : Context) : RecyclerView.Adapter<AdapterRecyclerViewMainActivity.ProductViewHolder>() {

    private var name = "SIGN IN"
    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val layoutItem : LinearLayout = view.findViewById(R.id.layout_item)
        val name : TextView = view.findViewById(R.id.tv_name)
        val rating :  TextView = view.findViewById(R.id.tv_rating)
        val price : TextView = view.findViewById(R.id.tv_price)
        val numberRating : RatingBar = view.findViewById(R.id.rating_bar)
        val image: ImageView = view.findViewById(R.id.img_product)

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : ProductViewHolder =
        ProductViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_recycle_view_main, parent, false)
        )


    fun setDataForAdapter(dataFromServer : List<ProductResponse>) {
        data = dataFromServer
        notifyDataSetChanged()
    }

    fun setName(myName : String) {
        name = myName
    }
    override fun onBindViewHolder(holder: AdapterRecyclerViewMainActivity.ProductViewHolder, position: Int) {
        val currentProduct = data.get(position)
        holder.name.text = currentProduct.name
        holder.price.text = "$" + currentProduct.price
        holder.rating.text = currentProduct.numReviews.toString() + " reviewer"
        holder.numberRating.rating = currentProduct.rating.toFloat()
        Glide.with(context).load(Constant.urlImage + currentProduct.image).into(holder.image)
        holder.layoutItem.setOnClickListener(View.OnClickListener {
            clickProduct.clickItem(currentProduct)
        })
    }

    override fun getItemCount(): Int {
        return data.size
    }

}