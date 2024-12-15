package id.ac.polbeng.ardianto.ecomerce.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import id.ac.polbeng.ardianto.ecomerce.databinding.ItemProductBinding
import id.ac.polbeng.ardianto.ecomerce.models.Product

class Productadapter(
    private val productList: List<Product>,
    private val onProductClick: (Product) -> Unit
) : RecyclerView.Adapter<Productadapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = productList[position]
        with(holder.binding) {
            productImage.setImageResource(product.imageRes)
            productName.text = product.name
            productPrice.text = product.price

            // Klik pada item produk
            root.setOnClickListener {
                onProductClick(product)
            }
        }
    }

    override fun getItemCount(): Int = productList.size
}
