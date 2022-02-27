package mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.ui.read

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.adapter.MyAdapterCars
import mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.databinding.FragmentReadBinding
import java.io.BufferedReader
import java.io.InputStreamReader


class ReadFragment : Fragment() {

    private var _binding: FragmentReadBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    val cars = arrayListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentReadBinding.inflate(inflater, container, false)
        val root: View = binding.root

        leerEnArchivo()
        val rv = binding.rvCars
        val adapter = MyAdapterCars(cars, object : MyAdapterCars.onItemClickListenr {
            override fun onItemClick(position: Int) {
                Log.i("$$$$$$$$$$4", cars[position])
            }

        })
        rv.layoutManager = LinearLayoutManager(requireContext())
        rv.adapter = adapter

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun leerEnArchivo() {
        try {
            val archivo = InputStreamReader(requireActivity().openFileInput("archivo.txt"))
            cars.removeAll(cars)
            var listaContenido = archivo.readLines()
            listaContenido.forEach {
                Log.i("%%%%%%%%%%%R 65 Lista", it)
                cars.add(it)
            }

            archivo.close()

        } catch (e: Exception) {
            AlertDialog.Builder(requireContext())
                .setTitle("Error leer readF")
                .setMessage(e.message.toString())
                .show()
        }
    }
}