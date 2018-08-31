package com.hallmg.alferza.materialdesign;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    //Definimos variables para enlazar los elementos del layout con el codigo para acceder a sus propiedades
    EditText txtCorreo, txtPassword;
    Button btnIngresar;
    TextInputLayout impCorreo, impPassword;

    //Definimos variables para comprobar los datos ingresados
    boolean correo=false, password=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);//Esta linea enlaza el codigo con el layout

        //Enlazamos nuestras cajas de texto
        txtCorreo = (EditText) findViewById(R.id.txtCorreo);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

        //Enlazamos el material Design
        impCorreo = (TextInputLayout) findViewById(R.id.impCorreo);
        impPassword = (TextInputLayout) findViewById(R.id.impPassword);

        //Enlazamos el boton de ingresar
        btnIngresar = (Button) findViewById(R.id.btnLogin);

        //Asignamos la funcion de onclick al boton
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Pattern ya tiene patrones predefinidos para correos y muchas cosas mas, aqui comprobamos que el mail sea bien escrito
                if (Patterns.EMAIL_ADDRESS.matcher(txtCorreo.getText().toString()).matches() == false){
                    impCorreo.setError("Correo Invalido"); //Asignamos un mensaje de error
                    correo = false;
                } else {//En caso de lo contrario asignams nuestra variable correo en true y no mostramos un error
                    correo = true;
                    impCorreo.setError(null);
                }

                //Creamos un patron personalizado para comprobar password, en este caso seran 5 numeros del 0 al 9
                Pattern p = Pattern.compile("[0-9][0-9][0-9][0-9][0-9]");

                //Creamos nuestra validacion en el patron que creamos, personalzado
                if (p.matcher(txtPassword.getText().toString()).matches() == false){
                    impPassword.setError("Password Invalido"); //Asignamos un mensaje de error
                    password = false;
                } else {//En caso de lo contrario asignams nuestra variable correo en true y no mostramos un error
                    password = true;
                    impPassword.setError(null);
                }

                //Verificamos que los fomatos de entrada de datos sean los correctos
                if (correo == true && password == true){
                    //Asignamos los valores de las cajas de texto a variables
                    String usuario = txtCorreo.getText().toString();
                    String pass = txtPassword.getText().toString();
                    //Verificamos que las variables sena iguales a nuestro usuario y password NOTA: esto se hara con BD
                    if (usuario.equals("soporte@hallmg.com") && pass.equals("12345")){
                        //Si son correctos los dejamos pasar a la pantalla de inicio
                        Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        //de ser incorrectos no los dejamos pasar y les mostramos este mensaje
                        Toast.makeText(getApplicationContext(), "Usuario o Contraseña incorrectos",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}
