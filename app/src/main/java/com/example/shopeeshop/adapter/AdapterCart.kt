package com.example.shopeeshop.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.shopeeshop.R
import com.example.shopeeshop.database.Product

class AdapterCart( val context : Context , val deleteProduct: DeleteProduct) :  RecyclerView.Adapter<AdapterCart.ViewHolderComment>(){

    var listData  = mutableListOf<Product>()
    inner class ViewHolderComment(view : View) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(R.id.tv_name_product)
        var img = view.findViewById<ImageView>(R.id.img_product_cart)
        var imgDelete = view.findViewById<ImageView>(R.id.btn_delete)
        var price = view.findViewById<TextView>(R.id.tv_price)
        var spinner = view.findViewById<Spinner>(R.id.quality)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderComment = ViewHolderComment(
        LayoutInflater.from(context).inflate(R.layout.item_cart_product,parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolderComment, position: Int) {
        val currentProduct = listData?.get(position)
        holder.name.text = currentProduct?.nameProduct
        Glide.with(context).load( currentProduct.img).into(holder.img)
        holder.price.text = "$" + currentProduct?.price

        val itemsSpiner = context.resources.getStringArray(R.array.quality)
        if (holder.spinner != null) {
            val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, itemsSpiner)
            holder.spinner.adapter = adapter
        }
        holder.spinner.setSelection(currentProduct.quality - 1)
        holder.spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                currentProduct.quality = itemsSpiner[position].toInt()
                deleteProduct.changeData(currentProduct)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }
        holder.imgDelete.setOnClickListener(View.OnClickListener {
            deleteProduct.delete(currentProduct)
        })
    }

    override fun getItemCount(): Int {
        return listData?.size
    }

    fun setDataForAdapter (data: List<Product>){
        listData = data as MutableList<Product>
        notifyDataSetChanged()
    }
}