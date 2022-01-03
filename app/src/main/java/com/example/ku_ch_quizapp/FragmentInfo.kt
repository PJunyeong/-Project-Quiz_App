package com.example.ku_ch_quizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.fragment_info.*
import kotlinx.android.synthetic.main.fragment_test.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentInfo.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentInfo : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreference = this.activity?.getSharedPreferences("tag", 0)
        val cur_name: String? = sharedPreference?.getString("nickname", "")
        nickname_change_input.setText(cur_name)
        nickname_change_btn.setOnClickListener {
            val new_name = nickname_change_input.getText().toString()
            if (new_name == "") {
                Toast.makeText(this.activity, "닉네임을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val editor = sharedPreference?.edit()
                editor?.putString("nickname", new_name)
                editor?.apply()
                Log.d("shared", "shared preference saved")
                Toast.makeText(this.activity, "닉네임이 ${new_name}으로 변경되었습니다", Toast.LENGTH_SHORT).show()
                this.activity?.after_login_hello?.text = "${new_name}"
            }
        }

    }




    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentInfo.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentInfo().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}