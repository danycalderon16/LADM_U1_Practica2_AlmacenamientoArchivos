package mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.R
import java.text.FieldPosition
import java.util.*
import kotlin.collections.ArrayList

class MyAdapterCars(private val list: ArrayList<String>,  itemListener: onItemClickListenr): RecyclerView.Adapter<MyAdapterCars.ViewHolder>() {

    var mListener : onItemClickListenr = itemListener

    fun setOnClickListener(listener: onItemClickListenr){
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.card_view_cars, parent, false)
        return ViewHolder(v,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, i: Int) {
        val car = list[i]
        var car_list = car.split(" ")
        holder.model.text = car_list[0].replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        holder.brand.text = car_list[1].replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        when (car_list[1].lowercase()){
            "chevrolet"->{holder.img.setImageResource(R.drawable.chevrolet)}
            "nissan"->{holder.img.setImageResource(R.drawable.nissan)}
            "ford"->{holder.img.setImageResource(R.drawable.ford)}
            "seat"->{holder.img.setImageResource(R.drawable.seat)}
            "jeep"->{holder.img.setImageResource(R.drawable.jeep)}
            "volkswagen"->{holder.img.setImageResource(R.drawable.volkswagen)}
            "toyota"->{holder.img.setImageResource(R.drawable.toyota)}
            "mazda"->{holder.img.setImageResource(R.drawable.mazda)}
            else->{
                holder.img.setImageResource(R.drawable.not_found)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(item: View, listener: onItemClickListenr):RecyclerView.ViewHolder(item){
        var model: TextView
        var brand: TextView
        var img: ImageView
        init {
            img = item.findViewById(R.id.img_brand)
            model = item.findViewById(R.id.model)
            brand = item.findViewById(R.id.brand)
            item.setOnClickListener {
                listener.onItemClick(bindingAdapterPosition)
            }
        }

    }

    interface onItemClickListenr{
        fun onItemClick(position: Int)
    }
}