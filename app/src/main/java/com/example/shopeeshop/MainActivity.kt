package com.example.shopeeshop

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.viewpager2.widget.ViewPager2
import com.example.shopeeshop.adapter.AdapterRecyclerViewMainActivity
import com.example.shopeeshop.adapter.AdapterViewPagerActivity
import com.example.shopeeshop.adapter.ClickProduct
import com.example.shopeeshop.database.MyDatabase
import com.example.shopeeshop.database.Product
import com.example.shopeeshop.databinding.ActivityMainBinding
import com.example.shopeeshop.model.ProductResponse
import com.example.shopeeshop.repository.ProductRepository
import com.example.shopeeshop.ui.CartFragment
import com.example.shopeeshop.ui.DetailFragment
import com.example.shopeeshop.ui.SignupFragment
import com.example.shopeeshop.viewmodel.MainViewModel
import com.example.shopeeshop.viewmodel.MainViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.relex.circleindicator.CircleIndicator3


class MainActivity : AppCompatActivity() , ClickProduct{

    private lateinit var viewPager: ViewPager2
    private lateinit var viewModel: MainViewModel
    private lateinit var recyclerViewMainActivity : RecyclerView
    private lateinit var logout : Spinner
    private lateinit var productAdapter : AdapterRecyclerViewMainActivity
    private lateinit var adapter : AdapterViewPagerActivity
    private lateinit var binding : ActivityMainBinding
    private  var data : List<ProductResponse> = mutableListOf()
    var dataInCart = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar()?.hide(); // hide the title bar
        setContentView(binding.root)
        logout = findViewById<Spinner>(R.id.img_logout)
        GlobalScope.launch {
            dataInCart = getDatabase().productDao().getByName(getName()) as MutableList<Product>
        }
        setEvent()
        initRecycleView()
        initViewPager()
    }
    fun getName() : String{
        return binding.tvSignUp.text.toString()
    }

    fun getDatabase() : MyDatabase {
        val db = Room.databaseBuilder(
            this,
            MyDatabase::class.java, "todo-list.db")
            .fallbackToDestructiveMigration()
            .build()
        return db
    }
    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
        viewModel.getData()
    }
    private fun initRecycleView() {
        recyclerViewMainActivity = findViewById(R.id.rcv_product)
        recyclerViewMainActivity.layoutManager = GridLayoutManager(this, 2, RecyclerView.VERTICAL,false)
        productAdapter = AdapterRecyclerViewMainActivity(this, data, this)
        recyclerViewMainActivity.adapter = productAdapter
        val viewModelFactory = MainViewModelFactory(ProductRepository())
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.getData()
        viewModel.data.observe(this) { res ->
            if (res.isSuccessful) {
                    res.body()?.let {
                        data = it.products
                        productAdapter.setDataForAdapter(data)
                        adapter.setDataForAdapter(data.subList(0,3))
                        binding.loadingLayout.visibility = View.INVISIBLE
                        binding.btnNext.visibility = View.VISIBLE
                        binding.btnPrevious.visibility = View.VISIBLE
                        binding.linearLayoutViewpager.visibility = View.VISIBLE
                        binding.tvLatest.visibility = View.VISIBLE
                    }!!
            }
        }
    }

    private fun initViewPager() {
        viewPager = findViewById(R.id.view_pager2)
        adapter = AdapterViewPagerActivity(this)
        viewPager.adapter = adapter
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        var myIndicator = findViewById<CircleIndicator3>(R.id.indicator)
        myIndicator.setViewPager(viewPager)
        adapter.registerAdapterDataObserver(myIndicator.getAdapterDataObserver());
        myIndicator.createIndicators(5,0);
        myIndicator.animatePageSelected(2)

        var next : ImageView = findViewById(R.id.btn_next)
        var previous : ImageView = findViewById(R.id.btn_previous)

        next.setOnClickListener(View.OnClickListener {
            viewPager.setCurrentItem(viewPager.currentItem + 1, true)

        })

        previous.setOnClickListener(View.OnClickListener {
            viewPager.setCurrentItem(viewPager.currentItem - 1, true)
        })

        val handler = Handler()
        val runnable = object : Runnable {
            override fun run () {
                if (viewPager.currentItem == 2)
                    viewPager.setCurrentItem(0)
                else
                    viewPager.setCurrentItem(viewPager.currentItem + 1)
                handler.postDelayed(this,5000)
            }
        }
        handler.post(runnable)
    }

    @SuppressLint("ResourceAsColor")
    private fun setEvent() {

        val itemsSpiner = resources.getStringArray(R.array.logout)

        if (logout != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, itemsSpiner)
            logout.adapter = adapter
        }
        logout.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>,
                                        view: View, position: Int, id: Long) {
//                for (i in 0..supportFragmentManager.backStackEntryCount - 1) {
//                    supportFragmentManager.popBackStack()
//                }
//                binding.homeLayout.visibility = View.VISIBLE
//                viewModel.getData()
                binding.tvSignUp.text = "SIGN IN"
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // write code to perform some action
            }
        }

        binding.tvNameShop.setOnClickListener(View.OnClickListener {
            for (i in 0..supportFragmentManager.backStackEntryCount - 1) {
                supportFragmentManager.popBackStack()
            }
            binding.homeLayout.visibility = View.VISIBLE
            viewModel.getData()
        })

        binding.tvCart.setOnClickListener(View.OnClickListener {
            binding.tvCart.setTextColor(resources.getColor(R.color.white))
            binding.tvSignUp.setTextColor(R.color.gray)
            binding.homeLayout.visibility = View.INVISIBLE
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_layout, CartFragment())
                .addToBackStack(null)
                .commitAllowingStateLoss()
        })

        binding.tvSignUp.setOnClickListener(View.OnClickListener {
            if(binding.tvSignUp.text.equals("SIGN IN")) {
                binding.tvSignUp.setTextColor(resources.getColor(R.color.white))
                binding.tvCart.setTextColor(R.color.gray)
                binding.homeLayout.visibility = View.INVISIBLE
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, SignupFragment())
                    .addToBackStack(null)
                    .commitAllowingStateLoss()
            }
        })


        binding.txtSearch.setOnClickListener(View.OnClickListener {
            hideKeyBoard()
            searchProduct()
        })
    }

     fun resetData(name : String) {
         viewModel.getData()
         binding.homeLayout.visibility = View.VISIBLE
         hideKeyBoard()
         binding.tvSignUp.text = name
         binding.imgSignUp.visibility = View.INVISIBLE
         binding.imgLogout.visibility = View.VISIBLE
     }

    private fun searchProduct() {
        var keyWord = binding.edtSearch.text
        var dataAfterSearch : MutableList<ProductResponse> = mutableListOf()
        for (product : ProductResponse in data) {
            if (product.name.contains(keyWord))
                dataAfterSearch.add(product)
        }
        productAdapter.setDataForAdapter(dataAfterSearch)
    }

    private fun hideKeyBoard() {
        val imm: InputMethodManager =
            this.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        //Find the currently focused view, so we can grab the correct window token from it.
        var view: View? = this.getCurrentFocus()
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

     fun addFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_layout, fragment)
            .addToBackStack(null)
            .commitAllowingStateLoss()
        binding.homeLayout.visibility = View.INVISIBLE

    }

    override fun clickItem(productResponse: ProductResponse) {
        val name = binding.tvSignUp.text as String
        val isLogin = !name.equals("SIGN IN")
        addFragment(DetailFragment(productResponse, isLogin, name))
    }
}