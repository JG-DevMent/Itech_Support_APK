package com.example.itechsupport

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton

class RegisterActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etPhone: EditText
    private lateinit var spRole: Spinner
    private lateinit var btnRegister: Button
    private lateinit var btnBackRegister: ImageButton
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        etUsername = findViewById(R.id.etUsername)
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etPhone = findViewById(R.id.etPhone)
        spRole = findViewById(R.id.spRole)
        btnRegister = findViewById(R.id.btnRegister)
        btnBackRegister = findViewById(R.id.btnBackRegister)
        db = DBHelper(this)

        val roles = arrayOf("Administrador", "Técnico", "Vendedor")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, roles)
        spRole.adapter = adapter

        btnRegister.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()
            val phone = etPhone.text.toString()
            val role = spRole.selectedItem.toString()

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || phone.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            } else {
                val success = db.registerUser(username, email, password, phone, role)
                if (success) {
                    Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, WelcomeActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Error: usuario o email ya existe", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnBackRegister.setOnClickListener {
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
} 