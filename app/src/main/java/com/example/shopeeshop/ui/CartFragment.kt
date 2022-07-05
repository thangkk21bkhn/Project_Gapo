package com.example.shopeeshop.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shopeeshop.MainActivity
import com.example.shopeeshop.R
import com.example.shopeeshop.adapter.AdapterCart
import com.example.shopeeshop.adapter.DeleteProduct
import com.example.shopeeshop.database.Product
import com.example.shopeeshop.model.ProductResponse
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CartFragment : Fragment(R.layout.fragment_cart) , DeleteProduct{

    private lateinit var  cartAdapter : AdapterCart
    private var totalPriceProduct = 0.00
    private var items = 0
    private lateinit var subTotal : TextView
    private lateinit var totalPrice : TextView
    private lateinit var emptyList : TextView
    private lateinit var process : Button
    private lateinit var recyclerView : RecyclerView

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView  = view.findViewById(R.id.rcv_product)
        subTotal = view.findViewById<TextView>(R.id.tv_total_product)
        totalPrice = view.findViewById<TextView>(R.id.tv_total_price)
        emptyList = view.findViewById(R.id.tv_empty)
        process = view.findViewById(R.id.btn_process)
        process.isEnabled = false


        checkData((activity as MainActivity).dataInCart.size > 0)

        process.setOnClickListener(View.OnClickListener {
            (activity as MainActivity).addFragment(ShippingFragment())
        })

        for (product : Product in (activity as MainActivity).dataInCart) {
            totalPriceProduct += product.price * product.quality
            items += product.quality
        }
        subTotal.text = "SUBTOTAL (" + items + ") ITEMS"
        totalPrice.text = "$" + Math.round((totalPriceProduct * 1000) / 1000)
        cartAdapter = AdapterCart(requireActivity(), this)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = cartAdapter
        GlobalScope.launch {
            cartAdapter.setDataForAdapter((activity as MainActivity).dataInCart)
        }
    }

    private fun checkData(isEmpty : Boolean) {
        if (isEmpty) {
            emptyList.visibility = View.INVISIBLE
            recyclerView.visibility = View.VISIBLE
            process.isEnabled = true
        }
        else {
            emptyList.visibility = View.VISIBLE
            recyclerView.visibility = View.VISIBLE
            process.isEnabled = false
        }
    }

    override fun delete(productResponse: Product) {
        items = 0
        totalPriceProduct = 0.00
        GlobalScope.launch {
            (activity as MainActivity).getDatabase().productDao().delete(productResponse)
        }
        (activity as MainActivity).dataInCart.remove(productResponse)
        for (product : Product in (activity as MainActivity).dataInCart) {
            totalPriceProduct += product.price * product.quality
            items += product.quality
        }
        subTotal.text = "SUBTOTAL (" + items + ") ITEMS"
        val cost = totalPriceProduct.toString().split(".")
        if(cost[1].length >= 3)
            totalPrice.text = "$" + cost[0] + "." + cost[1].subSequence(0,2)
        else
            totalPrice.text = "$" + totalPriceProduct.toString()
        checkData((activity as MainActivity).dataInCart.size > 0)
        cartAdapter.setDataForAdapter((activity as MainActivity).dataInCart)
    }

    override fun changeData(product: Product) {
        GlobalScope.launch {
            (activity as MainActivity).getDatabase().productDao().updateTodo(product)
        }
        items = 0
        totalPriceProduct = 0.000
        for (product : Product in (activity as MainActivity).dataInCart) {
            totalPriceProduct += product.price * product.quality
            items += product.quality
        }
        subTotal.text = "SUBTOTAL (" + items + ") ITEMS"
        val cost = totalPriceProduct.toString().split(".")
        totalPrice.text = "$" + cost[0] + "." + cost[1].subSequence(0,2)
    }
}