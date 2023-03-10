package com.example.protectbelly.fragments.workouts

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.protectbelly.adapters.WorkoutListAdapter
import com.example.protectbelly.databinding.FragmentWorkoutDashboardBinding
import com.example.protectbelly.models.DBRoutine
import com.example.protectbelly.models.Routine
import com.example.protectbelly.models.Workout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "WorkoutComplete"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WorkoutDashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WorkoutDashboardFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var workoutComplete = false
    private var param2: String? = null
    private val db = Firebase.firestore;
    private var routine: Routine = Routine();
    private var workouts: ArrayList<Workout> = ArrayList<Workout>();
    private lateinit var binding: FragmentWorkoutDashboardBinding;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            workoutComplete = it.getBoolean(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//
//        var workoutExerciseList = ArrayList<WorkoutExercise>();
//        workoutExerciseList.add(CardioExercise(20,"Running",20,30,40));
//        workoutExerciseList.add(WeightExercise(20,"Squat", 5,5,200,5,1));
//        workoutExerciseList.add(WeightExercise(20,"Bench", 5,5,150,5,1));
//        workoutExerciseList.add(WeightExercise(20,"Deadlift", 1,5,240,10,1));
//        workoutExerciseList.add(CardioExercise(20,"Running",20,30,40));
//        workouts.add(Workout(0, "StrongLifts", workoutExerciseList));
//        workouts.add(Workout(0, "StrongLifts", workoutExerciseList));
//        workouts.add(Workout(0, "StrongLifts", workoutExerciseList));


        var auth = FirebaseAuth.getInstance();
        binding = FragmentWorkoutDashboardBinding.inflate(inflater, container, false);

        db.collection("users").document(auth.currentUser?.uid.toString()).collection("routines")
            .get().addOnSuccessListener { result ->
                if(!result.isEmpty) {
                    var dbRoutine = result.documents[0].toObject(DBRoutine::class.java)!!;
                    routine.convertDBObject(dbRoutine);
                }
                populatePage(container);

            }
            .addOnFailureListener { exception ->
                Log.d("ABC", "Get failed with ", exception);
            };


//        if(workoutComplete) {
//            Log.d("ABC", binding.toString())
//            Snackbar.make(binding.root, "Workout Complete", Snackbar.LENGTH_SHORT).show();
//        }
        // Inflate the layout for this fragment
        return binding.root;
    }

    private fun populatePage(container: ViewGroup?) {
        if(routine.routineName == null) {
            binding.tvEmpty.visibility = View.VISIBLE;
            binding.btAddRoutine.visibility = View.VISIBLE;
            binding.btEdit.visibility = View.GONE;
            binding.rvWorkoutList.visibility = View.GONE;
            binding.btAddRoutine.setOnClickListener {
                val action = WorkoutDashboardFragmentDirections.actionWorkoutDashboardFragmentToAddRoutineUseTemplateFragment();
                container?.findNavController()?.navigate(action);
            }
        } else {
            binding.tvEmpty.visibility = View.GONE;
            binding.btAddRoutine.visibility = View.GONE;
            binding.btEdit.visibility = View.VISIBLE;
            binding.rvWorkoutList.visibility = View.VISIBLE;
            var workoutListAdapter = WorkoutListAdapter(binding.root.context,
                routine.workouts, true
            );
            binding.rvWorkoutList.adapter = workoutListAdapter;
            binding.rvWorkoutList.layoutManager = LinearLayoutManager(binding.root.context);

        }
    }

    private fun onItemClicked() {
        val action = WorkoutDashboardFragmentDirections.actionWorkoutDashboardFragmentToStartWorkoutFragment()
        binding.root.findNavController()?.navigate(action)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WorkoutDashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WorkoutDashboardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}