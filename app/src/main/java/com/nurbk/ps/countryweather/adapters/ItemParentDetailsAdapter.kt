package com.nurbk.ps.countryweather.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nurbk.ps.countryweather.R
import com.nurbk.ps.countryweather.databinding.ItemCitiesBinding
import com.nurbk.ps.countryweather.databinding.ItemImageBinding
import com.nurbk.ps.countryweather.model.ObjectDetails
import com.nurbk.ps.countryweather.model.cities.City
import com.nurbk.ps.countryweather.model.countries.Currency
import com.nurbk.ps.countryweather.model.countries.Language
import com.nurbk.ps.countryweather.model.photos.Photo

class ItemParentDetailsAdapter(var data: ObjectDetails) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class ItemCitiesViewHolder(val item: ItemCitiesBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(city: City) {
            item.item = city
            item.chip4.setSingleLine()
            item.chip4.isSelected = true
            item.chip4.setOnClickListener {
                onItemClickListener?.let {
                    it(city)
                }
            }
        }
    }

    inner class ItemBordersViewHolder(val item: ItemCitiesBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(text: String) {
            item.itemBorder = text
            item.chip4.text = text
        }
    }

    inner class ItemImageViewHolder(val item: ItemImageBinding) :
        RecyclerView.ViewHolder(item.root) {
        fun bind(photos: Photo) {
            Glide.with(item.root.context).load(getUrl(photos)).into(item.itemImage)
            item.root.setOnClickListener {
                onItemClickListener?.let {
                    it(photos)
                }
            }
        }

        private fun getUrl(photo: Photo): String {
            return "https://live.staticflickr.com/${photo.server}/${photo.id}_${photo.secret}_w.jpg"
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> {
                return ItemImageViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.item_image, parent, false
                    )
                )
            }
            2 -> {
                return ItemCitiesViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.item_cities, parent, false
                    )
                )
            }
            else -> {
                return ItemBordersViewHolder(
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context), R.layout.item_cities, parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        try {
            when (holder) {
                is ItemImageViewHolder -> {
                    holder.bind(data.data[position] as Photo)
                }
                is ItemCitiesViewHolder -> {
                    holder.bind(data.data[position] as City)
                }
                is ItemBordersViewHolder -> {
                    when {
                        data.data[position] is Currency -> try {
                            holder.bind((data.data[position] as Currency).symbol)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                        data.data[position] is Language -> holder.bind((data.data[position] as Language).name)
                        else -> holder.bind(data.data[position].toString())
                    }
                }
            }
        } catch (e: Exception) {

        }
    }

    override fun getItemCount() = data.data.size


    override fun getItemViewType(position: Int): Int {
        if (data.type == 1) {
            return 1
        } else if (data.type == 2) {
            return 2
        }
        return 3
    }

    private var onItemClickListener: ((Any) -> Unit)? = null

    fun setItemClickListener(listener: (Any) -> Unit) {
        onItemClickListener = listener
    }

}