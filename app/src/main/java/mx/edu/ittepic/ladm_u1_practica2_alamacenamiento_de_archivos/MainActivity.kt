package mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.databinding.ActivityMainBinding
import mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.databinding.FragmentCreateBinding
import mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos.databinding.FragmentReadBinding
import java.io.File
import java.io.OutputStreamWriter

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val fileName = "/data/data/mx.edu.ittepic.ladm_u1_practica2_alamacenamiento_de_archivos/files/archivo.txt"
        var file = File(fileName)
        var fileExists = file.exists()
        if(fileExists){
            Log.i("########3","Existe.")
        } else {
            Log.i("########3","No existe.")
            guardarEnArchivo("")
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_read, R.id.nav_create, R.id.nav_update
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun guardarEnArchivo(car:String){
        try {
            val archivo = OutputStreamWriter(this.openFileOutput("archivo.txt", 0))

            archivo.write(car)
            archivo.flush()
            archivo.close()
            Log.i("%%%%%%%%%%%","main")

        } catch (e: Exception) {
            AlertDialog.Builder(this)
                .setTitle("Error guardar")
                .setMessage(e.message.toString())
                .show()
        }

    }

    fun showdialog():String {

        var car =""
        val builder = AlertDialog.Builder(this)
            .create()
        builder.setTitle("Agregar Auto")
        val view = layoutInflater.inflate(R.layout.view_add_car,null)
        val  button = view.findViewById<Button>(R.id.btn_add_v)
        builder.setView(view)
        button.setOnClickListener {
            var model = view.findViewById<EditText>(R.id.model_v).text.toString()
            var brand = view.findViewById<EditText>(R.id.brand_v).text.toString()

            if(model.isEmpty() || brand.isEmpty()){
                Toast.makeText(this,"Ingrese todos los campos",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            car += model+" "+brand+"\n"
            builder.dismiss()
        }

        builder.setCanceledOnTouchOutside(false)
        builder.show()
        return car
    }
}