package com.example.electroapp.presenation.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.electroapp.R
import com.example.electroapp.data.models.User
import com.example.electroapp.data.util.DATA_USERS
import com.example.electroapp.data.util.DEFAULT_USER_PHOTO
import com.example.electroapp.databinding.FragmentSignUpBinding
import com.example.electroapp.presenation.activities.HomeActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpFragment : Fragment() {

    private lateinit var binding: FragmentSignUpBinding
    private val firebaseDB = FirebaseFirestore.getInstance()
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = firebaseAuth.currentUser?.uid
        user?.let{
            startActivity(HomeActivity.newIntent(requireContext()))
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setOnTextChangedListener(etEmail, tilEmail)
            setOnTextChangedListener(etPassword, tilPassword)
            setOnTextChangedListener(etUsername, tilUsername)
            setOnTextChangedListener(etPhone, tilPhone)
            btnSignup.setOnClickListener { onSignUp() }
            tvSignin.setOnClickListener { goToSignIn() }

        }
    }

    private fun setOnTextChangedListener(et: EditText, til: TextInputLayout) {
        et.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                til.isErrorEnabled = false
            }

            override fun afterTextChanged(p0: Editable?) {

            }
        })
    }

    private fun onSignUp() = with(binding){
        var proceed = true
        if (etUsername.text.isNullOrEmpty()) {
            tilUsername.error = "Username is required"
            tilUsername.isErrorEnabled = true
            proceed = false
        }
        if (etEmail.text.isNullOrEmpty()) {
            tilEmail.error = "Email is required"
            tilEmail.isErrorEnabled = true
            proceed = false
        }
        if (etPhone.text.isNullOrEmpty()) {
            tilPhone.error = "Phone number is required"
            tilPhone.isErrorEnabled = true
            proceed = false
        }
        if (etPassword.text.isNullOrEmpty()) {
            tilPassword.error = "Password is required"
            tilPassword.isErrorEnabled = true
            proceed = false
        }
        if(proceed){
            signupProgressLayout.visibility= View.VISIBLE
            firebaseAuth.createUserWithEmailAndPassword(
                etEmail.text.toString(), etPassword.text.toString()
            ).addOnCompleteListener { task ->
                if(!task.isSuccessful){
                    Toast.makeText(
                        requireContext(),
                        "Sign up failed: ${task.exception?.localizedMessage}",
                        Toast.LENGTH_SHORT).show()
                }
                else{
                    val user = User(
                        etEmail.text.toString(),
                        etUsername.text.toString(),
                        etPhone.text.toString(),
                        DEFAULT_USER_PHOTO,
                        arrayListOf()
                    )
                    firebaseDB.collection(DATA_USERS).document(firebaseAuth.uid!!).set(user)
                }
                signupProgressLayout.visibility = View.GONE
            }.addOnFailureListener {
                it.printStackTrace()
                signupProgressLayout.visibility = View.GONE
            }
        }
    }

    private fun goToSignIn(){
        requireActivity().supportFragmentManager
            .beginTransaction().replace(R.id.main_container, SignInFragment.newInstance())
            .commit()
    }
    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(firebaseAuthListener)
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            SignUpFragment()
    }
}