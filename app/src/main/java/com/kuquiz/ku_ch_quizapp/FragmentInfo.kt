package com.kuquiz.ku_ch_quizapp

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.android.synthetic.main.fragment_info.*

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

        info_type_del_btn.setOnClickListener {
            Log.d("del", "info_type_del_btn clicked")
            Toast.makeText(this.activity, "유형별 점수 기록이 삭제되었습니다", Toast.LENGTH_SHORT).show()
            var db_helper = getActivity()?.let { DBHelper(it) }
            db_helper?.delete_score(true)
            type_1_total.text = "총 0회"
            type_1_avg.text = "도전해 보세요!"
            type_2_total.text = "총 0회"
            type_2_avg.text = "도전해 보세요!"
            type_3_total.text = "총 0회"
            type_3_avg.text = "도전해 보세요!"
            type_4_total.text = "총 0회"
            type_4_avg.text = "도전해 보세요!"
            type_5_total.text = "총 0회"
            type_5_avg.text = "도전해 보세요!"
            type_6_total.text = "총 0회"
            type_6_avg.text = "도전해 보세요!"

        }
        info_test_del_btn.setOnClickListener {
            Log.d("del", "test_del_btn clicked")
            Toast.makeText(this.activity, "기출별 점수 기록이 삭제되었습니다", Toast.LENGTH_SHORT).show()
            var db_helper = getActivity()?.let { DBHelper(it) }
            db_helper?.delete_score(false)

            test_total.text = "총 0회"
            test_avg.text = "도전해 보세요!"
            test_10_total.text = "총 0회"
            test_10_avg.text = "도전해 보세요!"
            test_20_total.text = "총 0회"
            test_20_avg.text = "도전해 보세요!"
            test_30_total.text = "총 0회"
            test_30_avg.text = "도전해 보세요!"
            test_40_total.text = "총 0회"
            test_40_avg.text = "도전해 보세요!"
            test_50_total.text = "총 0회"
            test_50_avg.text = "도전해 보세요!"

        }



        // 점수 게시판 cnt 0 -> 문제를 풀어달라고 요청, 자동으로 avg -> 0. 이외에는 그대로.

        var db_helper = getActivity()?.let { DBHelper(it) }
        var my_score:MutableList<Float>

        my_score = db_helper?.get_score(0, 1)!!
        type_1_total.text = "총 ${my_score[0].toInt()}회"
        if (my_score[0].toInt() == 0) {
            type_1_avg.text = "도전해 보세요!"
        } else {
            type_1_avg.text = "평균 ${my_score[1]}점"
        }

        my_score = db_helper.get_score(0, 2)
        type_2_total.text = "총 ${my_score[0].toInt()}회"
        if (my_score[0].toInt() == 0) {
            type_2_avg.text = "도전해 보세요!"
        } else {
            type_2_avg.text = "평균 ${my_score[1]}점"
        }

        my_score = db_helper.get_score(0, 3)
        type_3_total.text = "총 ${my_score[0].toInt()}회"
        if (my_score[0].toInt() == 0) {
            type_3_avg.text = "도전해 보세요!"
        } else {
            type_3_avg.text = "평균 ${my_score[1]}점"
        }

        my_score = db_helper.get_score(0, 4)
        type_4_total.text = "총 ${my_score[0].toInt()}회"
        if (my_score[0].toInt() == 0) {
            type_4_avg.text = "도전해 보세요!"
        } else {
            type_4_avg.text = "평균 ${my_score[1]}점"
        }

        my_score = db_helper.get_score(0, 5)
        type_5_total.text = "총 ${my_score[0].toInt()}회"
        if (my_score[0].toInt() == 0) {
            type_5_avg.text = "도전해 보세요!"
        } else {
            type_5_avg.text = "평균 ${my_score[1]}점"
        }

        my_score = db_helper.get_score(0, 6)
        type_6_total.text = "총 ${my_score[0].toInt()}회"
        if (my_score[0].toInt() == 0) {
            type_6_avg.text = "도전해 보세요!"
        } else {
            type_6_avg.text = "평균 ${my_score[1]}점"
        }

        var total_test_cnt = 0
        var total_test_avg = 0F

        my_score = db_helper.get_score(10, 0)
        total_test_cnt += my_score[0].toInt()
        total_test_avg += my_score[1]

        test_10_total.text = "총 ${my_score[0].toInt()}회"
        if (my_score[0].toInt() == 0) {
            test_10_avg.text = "도전해 보세요!"
        } else {
            test_10_avg.text = "평균 ${my_score[1]}점"
        }

        my_score = db_helper.get_score(20, 0)
        total_test_cnt += my_score[0].toInt()
        total_test_avg += my_score[1]

        test_20_total.text = "총 ${my_score[0].toInt()}회"
        if (my_score[0].toInt() == 0) {
            test_20_avg.text = "도전해 보세요!"
        } else {
            test_20_avg.text = "평균 ${my_score[1]}점"
        }

        my_score = db_helper.get_score(30, 0)
        total_test_cnt += my_score[0].toInt()
        total_test_avg += my_score[1]

        test_30_total.text = "총 ${my_score[0].toInt()}회"
        if (my_score[0].toInt() == 0) {
            test_30_avg.text = "도전해 보세요!"
        } else {
            test_30_avg.text = "평균 ${my_score[1]}점"
        }

        my_score = db_helper.get_score(40, 0)
        total_test_cnt += my_score[0].toInt()
        total_test_avg += my_score[1]

        test_40_total.text = "총 ${my_score[0].toInt()}회"
        if (my_score[0].toInt() == 0) {
            test_40_avg.text = "도전해 보세요!"
        } else {
            test_40_avg.text = "평균 ${my_score[1]}점"
        }

        my_score = db_helper.get_score(50, 0)
        total_test_cnt += my_score[0].toInt()
        total_test_avg += my_score[1]

        test_50_total.text = "총 ${my_score[0].toInt()}회"
        if (my_score[0].toInt() == 0) {
            test_50_avg.text = "도전해 보세요!"
        } else {
            test_50_avg.text = "평균 ${my_score[1]}점"
        }

        if (total_test_avg.isNaN()) {total_test_avg = 0F}
        test_total.text = "총 ${total_test_cnt}회"


        if (total_test_cnt == 0) {
            test_avg.text = "도전해 보세요!"
        } else {
            test_avg.text = "평균 ${total_test_avg}점"
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