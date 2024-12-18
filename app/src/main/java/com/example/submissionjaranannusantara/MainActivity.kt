package com.example.submissionjaranannusantara

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var rvJaranan: RecyclerView
    private val list = ArrayList<Jaranan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvJaranan = findViewById(R.id.rv_jaranan)
        rvJaranan.setHasFixedSize(true)

        list.addAll(getListJaranan())
        showRecyclerList()
    }

    private fun getListJaranan(): ArrayList<Jaranan> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_deskripsi)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listJaranan = ArrayList<Jaranan>()
        for (j in dataName.indices) {
            val jaranan = Jaranan(dataName[j], dataDescription[j], dataPhoto.getResourceId(j, -1))
            listJaranan.add(jaranan)
        }
        return listJaranan
    }

    private fun showSelectedJaranan(jaranan: Jaranan) {
        Toast.makeText(this, "Ini adalah " + jaranan.name, Toast.LENGTH_SHORT).show()
    }

    private fun showRecyclerList() {
        rvJaranan.layoutManager = LinearLayoutManager(this)
        val jarananAdapter = JarananAdapter(list)
        rvJaranan.adapter = jarananAdapter

        jarananAdapter.setOnItemClickCallback(object : JarananAdapter.OnItemClickCallback{
            override fun onItemClicked(data: Jaranan) {
                showSelectedJaranan(data)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.about_page -> {
                val intent = Intent(this, AboutPage::class.java)
                startActivity(intent)
                true
            } else -> super.onOptionsItemSelected(item)
        }
    }

}