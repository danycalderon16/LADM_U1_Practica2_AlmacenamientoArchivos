package mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.ui.delete

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.adapter.MyAdapterCars
import mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.databinding.FragmentDeleteBinding
import mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.ui.create.SlideshowViewModel
import java.io.File
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class DeleteFragment : Fragment() {

    private var _binding: FragmentDeleteBinding? = null

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
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentDeleteBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val rv = binding.rvCars
        adapterCars = MyAdapterCars(cars,object: MyAdapterCars.onItemClickListenr {
            override fun onItemClick(position: Int) {
                Log.i("$$$$$$$$$$4",cars[position])
                AlertDialog.Builder(requireContext())
                    .setTitle("Eliminar auto")
                    .setMessage("¿Está seguro de borrar '${cars[position]}'")
                    .setPositiveButton("Sí") { d, i ->
                        borrarAuto(position)
                        d.dismiss()
                    }
                    .setNegativeButton("Salir",{d,i->d.dismiss()})
                .show()
            }

        })

        leerEnArchivo()

        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapterCars

        return root
    }

    private fun leerEnArchivo(){
        try {
            val archivo = InputStreamReader(requireActivity().openFileInput("archivo.txt"))

            var listaContenido = archivo.readLines()
            listaContenido.forEach {
                Log.i("%%%%%%%%%%% D",it)
                cars.add(it)
            }
            archivo.close()

        }catch (e:Exception){
            AlertDialog.Builder(requireContext())
                .setTitle("Error leer delete")
                .setMessage(e.message.toString())
                .show()
        }
    }

    private fun borrarAuto(i:Int) {
        var c = ""
     /*   cars.forEach {
            c+=it+"\n"
        }
        c+="--------\n"
        Log.i("%%%%%%%%%%% D 90",c)*/
        cars.removeAt(i)
        cars.forEach{
            c+=it+"\n"
        }

        val fileName = "/data/data/mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos/files/archivo.txt"
        var file = File(fileName)
        file.delete()

        val archivo = OutputStreamWriter(requireContext().openFileOutput("archivo.txt", 0))
        archivo.write(c)
        archivo.flush()
        archivo.close()
        adapterCars.notifyDataSetChanged()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}