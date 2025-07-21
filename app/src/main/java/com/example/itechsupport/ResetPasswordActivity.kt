package com.example.itechsupport

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResetPasswordActivity : AppCompatActivity() {
    private lateinit var etEmail: EditText
    private lateinit var btnReset: Button
    private lateinit var btnBack: ImageButton
    private lateinit var db: DBHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        etEmail = findViewById(R.id.etEmail)
        btnReset = findViewById(R.id.btnReset)
        btnBack = findViewById(R.id.btnBack)
        db = DBHelper(this)

        btnBack.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnReset.setOnClickListener {
            val email = etEmail.text.toString()
            if (email.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa tu correo", Toast.LENGTH_SHORT).show()
            } else if (db.getUserByEmail(email)) {
                Toast.makeText(this, "Correo de restablecimiento enviado", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "No se pudo enviar el correo. Verifique el email", Toast.LENGTH_LONG).show()
            }
        }
    }
} 