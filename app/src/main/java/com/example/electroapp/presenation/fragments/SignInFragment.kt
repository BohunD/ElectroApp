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
import androidx.core.widget.addTextChangedListener
import com.example.electroapp.R
import com.example.electroapp.databinding.FragmentSignInBinding
import com.example.electroapp.presenation.activities.HomeActivity
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth


class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val firebaseAuthListener = FirebaseAuth.AuthStateListener {
        val user = firebaseAuth.currentUser?.uid
        user?.let {
            requireActivity().startActivity(HomeActivity.newIntent(requireContext()))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setOnTextChangedListener(etEmail, tilEmail)
            setOnTextChangedListener(etPassword, tilPassword)
            btnSignin.setOnClickListener { onSignIn() }
            tvSignup.setOnClickListener { goToSignup() }
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

    private fun onSignIn() = with(binding) {
        var proceed = true
        if (etEmail.text.isNullOrEmpty()) {
            tilEmail.error = "Email is required"
            tilEmail.isErrorEnabled = true
            proceed = false
        }
        if (etPassword.text.isNullOrEmpty()) {
            tilPassword.error = "Password is required"
            tilPassword.isErrorEnabled = true
            proceed = false
        }
        if (proceed) {
            loginProgressLayout.visibility = View.VISIBLE
            firebaseAuth.signInWithEmailAndPassword(
                etEmail.text.toString(),
                etPassword.text.toString()
            ).addOnCompleteListener { task ->
                if(!task.isSuccessful){
                    loginProgressLayout.visibility = View.GONE
                    Toast.makeText(
                        requireContext(),
                        "Login failed: ${task.exception?.localizedMessage}",
                        Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                it.printStackTrace()
                loginProgressLayout.visibility = View.GONE
            }
        }
    }

    private fun goToSignup(){
        requireActivity().supportFragmentManager
            .beginTransaction().replace(R.id.main_container, SignUpFragment.newInstance())
            .commit()
    }

    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener (firebaseAuthListener)
    }

    override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(firebaseAuthListener)
    }


    companion object {

        @JvmStatic
        fun newInstance() =
            SignInFragment()
    }
}