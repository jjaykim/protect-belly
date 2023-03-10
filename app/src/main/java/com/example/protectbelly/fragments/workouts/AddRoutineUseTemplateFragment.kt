package com.example.protectbelly.fragments.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.protectbelly.databinding.FragmentAddRoutineUseTemplateBinding
import com.example.protectbelly.models.Routine

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "Routine"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddRoutineUseTemplateFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddRoutineUseTemplateFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var routine: Routine? = null;
    private var param2: String? = null
    private lateinit var binding: FragmentAddRoutineUseTemplateBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            routine = it.getSerializable(ARG_PARAM1) as Routine?;
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddRoutineUseTemplateBinding.inflate(inflater,container,false);

        if(routine == null) {
            routine = Routine();
        }

        binding.btNo.setOnClickListener {
            val action = AddRoutineUseTemplateFragmentDirections.actionAddRoutineUseTemplateFragmentToAddRoutineEnterDetailsFragment();
            action.arguments.putSerializable("Routine", routine);
            container?.findNavController()?.navigate(action);
        }

        // Inflate the layout for this fragment
        return binding.root;
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddRoutineUseTemplateFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddRoutineUseTemplateFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}