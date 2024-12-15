package id.ac.polbeng.ardianto.ecomerce.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import id.ac.polbeng.ardianto.ecomerce.R
import id.ac.polbeng.ardianto.ecomerce.adapters.Productadapter
import id.ac.polbeng.ardianto.ecomerce.databinding.ActivityMainBinding
import id.ac.polbeng.ardianto.ecomerce.models.Product

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var newProductsAdapter: Productadapter
    private lateinit var recentlyViewedAdapter: Productadapter

    private lateinit var productList: List<Product> // Data asli untuk Produk Baru
    private val filteredNewProducts = mutableListOf<Product>() // Data hasil pencarian
    private val recentlyViewedList = mutableListOf<Product>() // Daftar produk yang baru saja dilihat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Data produk
        productList = listOf(
            Product("Lenovo Thinkpad", "2024", "Second", "Rp 4.400.000", R.drawable.lenovo),
            Product("Realme", "2023", "Second", "Rp 7.500.000", R.drawable.realme),
            Product("Canter", "2022", "Second", "Rp 10.000.000", R.drawable.canter),
            Product("Infinix", "2020", "XP-Pen", "Rp 400.000", R.drawable.pen_tablet),
            Product("Brio", "2019", "Second", "Rp 100.200.000", R.drawable.brio)
        )

        filteredNewProducts.addAll(productList)

        // RecyclerView Produk Baru
        newProductsAdapter = Productadapter(filteredNewProducts) { product ->
            onProductClicked(product)
        }
        binding.recyclerViewNewProducts.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewNewProducts.adapter = newProductsAdapter

        // RecyclerView Baru Saja Dilihat
        recentlyViewedAdapter = Productadapter(recentlyViewedList) { product ->
            onProductClicked(product)
        }
        binding.recyclerViewRecentlyViewed.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewRecentlyViewed.adapter = recentlyViewedAdapter

        // Search
        setupSearchView()
    }

    // Fungsi untuk memfilter produk berdasarkan pencarian
    private fun filterProducts(query: String?) {
        filteredNewProducts.clear()
        if (query.isNullOrEmpty()) {

            filteredNewProducts.addAll(productList)
        } else {

            filteredNewProducts.addAll(productList.filter { product ->
                product.name.contains(query, ignoreCase = true)
            })
        }

        newProductsAdapter.notifyDataSetChanged()
    }

    // Fungsi ketika produk diklik
    private fun onProductClicked(product: Product) {
        Toast.makeText(this, "Klik pada produk: ${product.name}", Toast.LENGTH_SHORT).show()

        // Tambahkan produk ke "Baru Saja Dilihat"
        if (!recentlyViewedList.contains(product)) {
            recentlyViewedList.add(0, product) // Tambahkan di awal daftar
            recentlyViewedAdapter.notifyDataSetChanged() // Perbarui adapter
            Log.d("BaruSajaDilihat", "Produk ditambahkan: ${product.name}")
        } else {
            Log.d("BaruSajaDilihat", "Produk sudah ada di daftar: ${product.name}")
        }
    }


    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterProducts(newText)
                return true
            }
        })
    }
}
