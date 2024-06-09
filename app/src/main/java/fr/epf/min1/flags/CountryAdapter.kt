package fr.epf.min1.flags

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CountryAdapter(private val countries: List<Country>, private val onClick: (Country) -> Unit) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_country, parent, false)
        return CountryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val country = countries[position]
        holder.bind(country, onClick)
        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, CountryDetails::class.java)
            intent.putExtra("countryName", country.name.common)
            intent.putExtra("capital", country.capital.joinToString())
            intent.putExtra("population", country.population)
            intent.putExtra("flagUrl", country.flags.png)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = countries.size

    class CountryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val flagImageView: ImageView = itemView.findViewById(R.id.imageViewFlag)
        private val nameTextView: TextView = itemView.findViewById(R.id.textViewName)

        fun bind(country: Country, onClick: (Country) -> Unit) {
            nameTextView.text = country.name.common
            Glide.with(itemView.context).load(country.flags.png).into(flagImageView)
            itemView.setOnClickListener { onClick(country) }
        }
    }
}