package com.example.ku_ch_quizapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_type.*
import kotlinx.android.synthetic.main.fragment_type.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentType.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentType : Fragment() {
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
        return inflater.inflate(R.layout.fragment_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        type_1_btn.setOnClickListener {
            activity?.let{
                val intent = Intent (it, QuestionActivity::class.java)
                intent.putExtra("type_num", 1)
                it.startActivity(intent)
            }
        }
        type_2_btn.setOnClickListener {
            activity?.let{
                val intent = Intent (it, QuestionActivity::class.java)
                intent.putExtra("type_num", 2)
                it.startActivity(intent)
            }
        }

        type_3_btn.setOnClickListener {
            activity?.let {
                val intent = Intent(it, QuestionActivity::class.java)
                intent.putExtra("type_num", 3)
                it.startActivity(intent)
            }
        }

        type_4_btn.setOnClickListener {
            activity?.let {
                val intent = Intent(it, QuestionActivity::class.java)
                intent.putExtra("type_num", 4)
                it.startActivity(intent)
            }
        }

        type_5_btn.setOnClickListener {
            activity?.let {
                val intent = Intent(it, QuestionActivity::class.java)
                intent.putExtra("type_num", 5)
                it.startActivity(intent)
            }
        }

        type_6_btn.setOnClickListener {
            activity?.let {
                val intent = Intent(it, QuestionActivity::class.java)
                intent.putExtra("type_num", 6)
                it.startActivity(intent)
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
         * @return A new instance of fragment FragmentType.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentType().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}

