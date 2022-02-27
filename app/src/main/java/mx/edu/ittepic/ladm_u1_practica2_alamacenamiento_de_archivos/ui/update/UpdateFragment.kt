package mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.ui.update

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.R
import mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.adapter.MyAdapterCars
import mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.databinding.FragmentUpdateBinding
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class UpdateFragment : Fragment() {

    private var _binding: FragmentUpdateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val cars = arrayListOf<String>()
    val aux_cars = arrayListOf<String>()
    private lateinit var adapterCars: MyAdapterCars
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val galleryViewModel =
            ViewModelProvider(this).get(GalleryViewModel::class.java)

        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val rv = binding.rvCars
        adapterCars = MyAdapterCars(cars,object: MyAdapterCars.onItemClickListenr {
            override fun onItemClick(position: Int) {
                Log.i("$$$$$$$$$$4",cars[position])
                showdialog(cars[position], position)
            }

        })

        Log.i("%%%%%%%%%%%","read")
        leerEnArchivo()

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapterCars

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun leerEnArchivo(){
        try {
            val archivo = InputStreamReader(requireActivity().openFileInput("archivo.txt"))

            var listaContenido = archivo.readLines()
            listaContenido.forEach {
                Log.i("%%%%%%%%%%%",it)
                cars.add(it)
            }
            archivo.close()

        }catch (e:Exception){
            AlertDialog.Builder(requireContext())
                .setTitle("Error leer readF")
                .setMessage(e.message.toString())
                .show()
        }
    }

    fun showdialog(car:String, i:Int) {

        val builder = AlertDialog.Builder(requireContext())
            .create()
        builder.setTitle("Actualizar Auto")
        val view = layoutInflater.inflate(R.layout.view_add_car,null)
        val  button = view.findViewById<Button>(R.id.btn_add_v)
        button.setText("Actualizar")

        var model = view.findViewById<EditText>(R.id.model_v)
        var brand = view.findViewById<EditText>(R.id.brand_v)

        var car_list = car.split(" ")
        model.setText(car_list[0])

        Log.i("%%%%%%%%%%%",""+car_list.size+" "+car)
        brand.setText(car_list[1])

        builder.setView(view)
        button.setOnClickListener {
            var c = ""
            cars.forEach {
                c+=it+"\n"
            }
            Log.i("%%%%%%%%%%% U 110",c)
            cars[i] = model.text.toString().trim() +" "+ brand.text.toString().trim()
            adapterCars.notifyDataSetChanged()
            guardarEnArchivo()
            Toast.makeText(requireContext(),"Auto actualizado", Toast.LENGTH_SHORT).show()
            builder.dismiss()
        }

        builder.show()
    }

    private fun guardarEnArchivo(){
        try {
            var c = ""
            cars.forEach {
                c+=it+"\n"
            }
            Log.i("%%%%%%%%%%% U 128",c)

            val fileName = "/data/data/mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos/files/archivo.txt"
            var file = File(fileName)
            file.delete()

            val archivo = OutputStreamWriter(requireContext().openFileOutput("archivo.txt", 0))
            archivo.write(c)
            archivo.flush()
            archivo.close()
            Log.i("%%%%%%%%%%%","main")

        } catch (e: Exception) {
            AlertDialog.Builder(requireContext())
                .setTitle("Error guardar")
                .setMessage(e.message.toString())
                .show()
        }

    }
}