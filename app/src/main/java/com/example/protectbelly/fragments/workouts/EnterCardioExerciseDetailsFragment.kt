package com.example.protectbelly.fragments.workouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.protectbelly.databinding.FragmentEnterCardioExerciseDetailsBinding
import com.example.protectbelly.models.CardioExercise
import com.example.protectbelly.models.Routine
import com.example.protectbelly.models.WorkoutExercise

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "WorkoutExercise"
private const val ARG_PARAM2 = "Routine"
private const val ARG_PARAM3 = "WorkoutIndex"

/**
 * A simple [Fragment] subclass.
 * Use the [EnterCardioExerciseDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class EnterCardioExerciseDetailsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var workoutExercise: WorkoutExercise? = null
    private var routine: Routine? = null
    private var workoutIndex: Int = 0;
    private lateinit var binding: FragmentEnterCardioExerciseDetailsBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workoutExercise = it.getSerializable(ARG_PARAM1) as CardioExercise
            routine = it.getSerializable(ARG_PARAM2) as Routine
            workoutIndex = it.getInt(ARG_PARAM3);
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEnterCardioExerciseDetailsBinding.inflate(inflater,container,false);

        binding.tvExerciseName.text = workoutExercise?.name;

        binding.btCardioNext.setOnClickListener {
            if(validateInput()) {
                if(binding.etHeartRateGoal.text.isNotEmpty()) {
                    (workoutExercise as CardioExercise).heartRateGoal = binding.etHeartRateGoal.text.toString().toInt();
                } else {
                    (workoutExercise as CardioExercise).heartRateGoal = 0;
                }
                (workoutExercise as CardioExercise).distanceGoal = binding.etDistanceGoal.text.toString().toInt();
                (workoutExercise as CardioExercise).timeGoal = binding.etTimeGoal.text.toString().toInt();
                routine?.workouts?.last()?.workoutExercises?.add(workoutExercise as CardioExercise);
                var action = EnterCardioExerciseDetailsFragmentDirections.actionEnterCardioExerciseDetailsFragmentToAddRoutineWorkoutDetailsFragment();
                action.arguments.putSerializable("Routine", routine);
                action.arguments.putInt("WorkoutIndex", workoutIndex);
                container?.findNavController()?.navigate(action);
            }
        }

        binding.btCardioBack.setOnClickListener {
            var action = EnterCardioExerciseDetailsFragmentDirections.actionEnterCardioExerciseDetailsFragmentToSelectExerciseFragment()
            action.arguments.putSerializable("Routine", routine);
            action.arguments.putInt("WorkoutIndex", workoutIndex);
            container?.findNavController()?.navigate(action);
        }

        // Inflate the layout for this fragment
        return binding.root;
    }

    private fun validateInput(): Boolean {
        var isValid = true;
        if(binding.etDistanceGoal.text.isEmpty()) {
            binding.etDistanceGoal.error = "Please enter distance goal";
            isValid = false;
        }
        if(binding.etTimeGoal.text.isEmpty()) {
            binding.etTimeGoal.error = "Please enter time goal";
            isValid = false;
        }
        return isValid;
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment EnterCardioExerciseDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            EnterCardioExerciseDetailsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}