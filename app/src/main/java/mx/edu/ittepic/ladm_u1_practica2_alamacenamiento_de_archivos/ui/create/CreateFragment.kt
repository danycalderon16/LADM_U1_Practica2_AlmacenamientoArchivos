package mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.ui.create

import android.R
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.databinding.FragmentCreateBinding
import java.io.InputStreamReader
import java.io.OutputStreamWriter

class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val cars = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnAdd.setOnClickListener {
            guardarEnArchivo()
        }

        return root
    }


    private fun leerEnArchivo(){
        try {
            val archivo = InputStreamReader(requireActivity().openFileInput("archivo.txt"))
            var c =""
            var listaContenido = archivo.readLines()
            listaContenido.forEach {
                cars.add(it)
                c += it+"\n"
            }
            archivo.close()

        }catch (e:Exception){
            AlertDialog.Builder(requireContext())
                .setTitle("Error leer readF")
                .setMessage(e.message.toString())
                .show()
        }
    }

    private fun guardarEnArchivo() {
        try {
            cars.clear()

            leerEnArchivo()
            var cadena = ""
            cars.forEach {
                cadena += it +"\n"
            }
            val archivo = OutputStreamWriter(requireActivity().openFileOutput("archivo.txt",0))

            cadena += binding.model.text.toString().trim()+" "+
                    binding.price.text.toString()+" "+
                    binding.brand.text.toString().trim()+"\n"

            archivo.write(cadena)
            archivo.flush()
            archivo.close()

            binding.model.setText("")
            binding.brand.setText("")
            binding.price.setText("")
            AlertDialog.Builder(requireContext())
                .setMessage("Se guardo correctamente")
                .setPositiveButton("Ok",{d,i-> d.dismiss()})
                .show()
        }catch (e:Exception){
            AlertDialog.Builder(requireContext())
                .setTitle("Error guardar")
                .setMessage(e.message.toString())
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}