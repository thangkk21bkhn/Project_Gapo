package com.example.shopeeshop.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.shopeeshop.MainActivity
import com.example.shopeeshop.R
import com.example.shopeeshop.adapter.AdapterRecyclerViewMainActivity
import com.example.shopeeshop.adapter.AdapterReviews
import com.example.shopeeshop.database.MyDatabase
import com.example.shopeeshop.database.Product
import com.example.shopeeshop.model.InforReviewPerson
import com.example.shopeeshop.model.ProductResponse
import com.example.shopeeshop.util.Constant
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DetailFragment(val productResponse: ProductResponse, val isLogin : Boolean, val namePerson : String) : Fragment(R.layout.fragment_detail_product) {

    private  var ratingOfPerson = 0
    private var quality = 0
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageView : ImageView = view.findViewById(R.id.img_product)
        val goBack : TextView = view.findViewById(R.id.tv_go_back)
        val comment : TextView = view.findViewById(R.id.tv_sign_in_to_comment)
        val commentPerson : EditText = view.findViewById(R.id.edt_comment)
        val name : TextView = view.findViewById(R.id.tv_name_product)
        val reviews : TextView = view.findViewById(R.id.tv_reviews_number)
        val description : TextView = view.findViewById(R.id.tv_description)
        val price : TextView = view.findViewById(R.id.tv_price)
        val priceProduct : TextView = view.findViewById(R.id.tv_price_product)
        val rating : RatingBar = view.findViewById(R.id.rating_bar)
        val layout : LinearLayout = view.findViewById(R.id.layout)
        val listReview : RecyclerView = view.findViewById(R.id.rcv_reviews)
        val subMit : Button = view.findViewById(R.id.btn_submit)
        val addToCart : Button = view.findViewById(R.id.btn_add_to_cart)
        val itemsSpiner = resources.getStringArray(R.array.quality)
        val itemsRating = resources.getStringArray(R.array.rating)

        listReview.layoutManager = LinearLayoutManager( requireActivity(), RecyclerView.VERTICAL, false)
        val reviewAdapter = AdapterReviews(requireActivity())
        listReview.adapter = reviewAdapter
        reviewAdapter.setDataForAdapter(productResponse.reviews as MutableList<InforReviewPerson>)

        addToCart.setOnClickListener(View.OnClickListener {
            val name : String = name.text.toString()
            val linkImg : String = Constant.urlImage + productResponse.image
            val price : Double = productResponse.price
            val qualityProduct = quality
            val data = Product(0,name,(activity as MainActivity).getName(),0,price,linkImg,qualityProduct)
            (activity as MainActivity).dataInCart.add(data)
            Toast.makeText(requireActivity(),"ADDED PRODUCT TO CART", Toast.LENGTH_SHORT).show()
            addDataToRoom()
        })

        subMit.setOnClickListener(View.OnClickListener {
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            val currentDate = sdf.format(Date())
            val inforReviewPerson = InforReviewPerson("",namePerson,ratingOfPerson ,commentPerson.text.toString(),"", currentDate, currentDate.toString())
            val data  = reviewAdapter.listData
            data.add(inforReviewPerson)
            reviewAdapter.setDataForAdapter(data)
        })

        val spinnerQuality = view.findViewById<Spinner>(R.id.quality)
        if (spinnerQuality != null) {
            val adapter = ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_item, itemsSpiner
            )
            spinnerQuality.adapter = adapter
        }

        spinnerQuality.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                    quality = itemsSpiner[position].toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        val spinnerRating = view.findViewById<Spinner>(R.id.spinner)
        if (spinnerRating != null) {
            val adapter = ArrayAdapter(
                requireActivity(),
                android.R.layout.simple_spinner_item, itemsRating
            )
            spinnerRating.adapter = adapter
        }
        spinnerRating.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
                if(position != 0)
                    ratingOfPerson = itemsRating[position].toInt()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        if (!isLogin) {
            comment.visibility = View.VISIBLE
            layout.visibility = View.INVISIBLE
        }
        else {
            comment.visibility = View.INVISIBLE
            layout.visibility = View.VISIBLE
        }
        priceProduct.text = "$" + productResponse.price
        rating.rating = productResponse.rating.toFloat()
        name.text = productResponse.name
        reviews.text = " "+ productResponse.numReviews + " reviewers"
        description.text = productResponse.description
        price.text = "Price: $" + productResponse.price

        comment.setOnClickListener(View.OnClickListener {
            (activity as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, SignupFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        })
        goBack.setOnClickListener(View.OnClickListener {
            for (i in 0..(this.activity?.supportFragmentManager?.backStackEntryCount?.minus(1) ?: 0)) {
                this.activity?.supportFragmentManager?.popBackStack()
            }
            (activity as MainActivity?)!!.resetData(namePerson)
        })
        context?.let { Glide.with(it).load(Constant.urlImage + productResponse.image).into(imageView)}
    }

    private fun addDataToRoom() {
        val db = Room.databaseBuilder(
            requireContext(),
            MyDatabase::class.java, "todo-list.db")
            .fallbackToDestructiveMigration()
            .build()

        GlobalScope.launch {
            db.productDao().insertAll(
                Product(0,
                    productResponse.name, (activity as MainActivity).getName() ,productResponse.rating, productResponse.price,
                    Constant.urlImage + productResponse.image, quality
                )
            )
            val data1 = db.productDao().getByName((activity as MainActivity).getName())
            val data = db.productDao().getAll()

            Log.d("room","" + data1.toString())
        }
    }
}